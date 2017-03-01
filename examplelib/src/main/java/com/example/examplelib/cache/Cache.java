package com.example.examplelib.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.LruCache;

import com.example.examplelib.utils.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cache {
    private static final String TAG = "Cache";

    private static final int MEM_MAX_SIZE = 32 * 1024 * 1024; // Max memory
    // cache size
    // 32MB
    private static final int DISK_MAX_SIZE = 64 * 1024 * 1024; // Max disk cache
    // size 64MB
    private static final String DISK_CACHE_SUBDIR = "cocloud";
    private String bitmapType;
    private final Context mContext;
    private static Cache instance;

    private Cache(Context context) {
        mContext = context;
    }

    public static Cache getInstance(Context context) {
        if (instance == null) {
            instance = new Cache(context);
        }
        return instance;
    }

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskCache;
    /*
     * Even initializing the disk cache requires disk operations and therefore
     * should not take place on the main thread. However, this does mean there's
     * a chance the cache is accessed before initialization. To address this, a
     * lock object ensures that the app does not read from the disk cache until
     * the cache has been initialized.
     */
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    public void initMemoryCache() {
        // Initialize memory cache
        mMemoryCache = new LruCache<String, Bitmap>(MEM_MAX_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than
                // number of items.
                return bitmap.getByteCount();
            }
        };
    }

    public void initDiskCache() {
        // Initialize disk cache
        File cacheDir = getDiskCacheDir(mContext, DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);
    }

    private class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                try {
                    File cacheDir = params[0];
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs();
                    }

                    mDiskCache = DiskLruCache.open(cacheDir,
                            getAppVersion(mContext), 1, DISK_MAX_SIZE);
                    mDiskCacheStarting = false; // Finished initialization
                    mDiskCacheLock.notifyAll(); // Wake any waiting threads
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            return null;
        }
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
        return 1;
    }

    /*
     * Creates a unique subdirectory of the designated app cache directory.
     * Tries to use external but if not mounted, falls back on internal storage.
     */
    private static File getDiskCacheDir(@NonNull Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable() ? context
                .getExternalCacheDir().getPath() : context.getCacheDir()
                .getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    private void bitmapToStream(Bitmap bitmap, OutputStream out) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
            out.write(bytes);
            out.flush();
            out.close();
        } catch (IOException e) {
            // To do
        }
    }

    private void addBitmapToDiskCache(String key, Bitmap bitmap) {
        // To avoid NullPointerException
        if (bitmap == null) {
            return;
        }

        synchronized (mDiskCacheLock) {
            try {
                if (mDiskCache != null && mDiskCache.get(key) == null) {
                    DiskLruCache.Editor editor = mDiskCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        bitmapToStream(bitmap, outputStream);
                        editor.commit();
                    }
                }
            } catch (IOException | IllegalStateException e) {
                // To do
            }
        }
    }

    private Bitmap getBitmapFromDiskCache(String key) {
        synchronized (mDiskCacheLock) {
            // Wait while disk cache is started from background thread
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {
                }
            }
            if (mDiskCache != null) {
                try {
                    DiskLruCache.Snapshot snapShot = mDiskCache.get(key);
                    if (snapShot != null) {
                        InputStream is = snapShot.getInputStream(0);
                        return BitmapFactory.decodeStream(is);
                    }
                } catch (IOException e) {
                    // To do
                }
            }
        }
        return null;
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (key == null || bitmap == null) {
            return;
        }

        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        addBitmapToMemoryCache(key, bitmap);
        addBitmapToDiskCache(key, bitmap);
    }

    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = getBitmapFromMemCache(key);
        if (bitmap == null) {
            bitmap = getBitmapFromDiskCache(key);
        }

        return bitmap;
    }

    public void clearCache() {
        try {
            if (mDiskCache != null) {
                mDiskCache.flush();
            }
        } catch (IOException e) {
            // To do
        }

        mMemoryCache.evictAll();
    }

    public void removeFromCache(String cacheKey) {
        try {
            mMemoryCache.remove(cacheKey);
            if (mDiskCache != null) {
                mDiskCache.remove(cacheKey);
            }
        } catch (IOException e1) {
            // To do
        }
    }

    public String getBitmapType() {
        return bitmapType;
    }

    public void setBitmapType(String bitmapType) {
        this.bitmapType = bitmapType;
    }

}
