package com.example.example.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "UploadInfo".
 */
public class UploadInfoDao extends AbstractDao<UploadInfo, Long> {

    public static final String TABLENAME = "UploadInfo";

    /**
     * Properties of entity UploadInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property UserName = new Property(1, String.class, "userName", false, "USER_NAME");
        public final static Property Did = new Property(2, String.class, "did", false, "DID");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property Url = new Property(4, String.class, "url", false, "URL");
        public final static Property FileType = new Property(5, String.class, "fileType", false, "FILE_TYPE");
        public final static Property LocalPath = new Property(6, String.class, "localPath", false, "LOCAL_PATH");
        public final static Property FileSize = new Property(7, Long.class, "fileSize", false, "FILE_SIZE");
        public final static Property State = new Property(8, Long.class, "state", false, "STATE");
        public final static Property Complete = new Property(9, Long.class, "complete", false, "COMPLETE");
        public final static Property FileName = new Property(10, String.class, "fileName", false, "FILE_NAME");
        public final static Property CreateTime = new Property(11, String.class, "createTime", false, "CREATE_TIME");
        public final static Property Md5 = new Property(12, String.class, "md5", false, "MD5");
        public final static Property ErrorMessage = new Property(13, Integer.class, "errorMessage", false, "ERROR_MESSAGE");
    }


    public UploadInfoDao(DaoConfig config) {
        super(config);
    }

    public UploadInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"UploadInfo\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_NAME\" TEXT," + // 1: userName
                "\"DID\" TEXT," + // 2: did
                "\"TYPE\" TEXT," + // 3: type
                "\"URL\" TEXT," + // 4: url
                "\"FILE_TYPE\" TEXT," + // 5: fileType
                "\"LOCAL_PATH\" TEXT," + // 6: localPath
                "\"FILE_SIZE\" INTEGER," + // 7: fileSize
                "\"STATE\" INTEGER," + // 8: state
                "\"COMPLETE\" INTEGER," + // 9: complete
                "\"FILE_NAME\" TEXT," + // 10: fileName
                "\"CREATE_TIME\" TEXT," + // 11: createTime
                "\"MD5\" TEXT," + // 12: md5
                "\"ERROR_MESSAGE\" INTEGER);"); // 13: errorMessage
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"UploadInfo\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, UploadInfo entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(2, userName);
        }

        String did = entity.getDid();
        if (did != null) {
            stmt.bindString(3, did);
        }

        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }

        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(5, url);
        }

        String fileType = entity.getFileType();
        if (fileType != null) {
            stmt.bindString(6, fileType);
        }

        String localPath = entity.getLocalPath();
        if (localPath != null) {
            stmt.bindString(7, localPath);
        }

        Long fileSize = entity.getFileSize();
        if (fileSize != null) {
            stmt.bindLong(8, fileSize);
        }

        Long state = entity.getState();
        if (state != null) {
            stmt.bindLong(9, state);
        }

        Long complete = entity.getComplete();
        if (complete != null) {
            stmt.bindLong(10, complete);
        }

        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(11, fileName);
        }

        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(12, createTime);
        }

        String md5 = entity.getMd5();
        if (md5 != null) {
            stmt.bindString(13, md5);
        }

        Integer errorMessage = entity.getErrorMessage();
        if (errorMessage != null) {
            stmt.bindLong(14, errorMessage);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public UploadInfo readEntity(Cursor cursor, int offset) {
        UploadInfo entity = new UploadInfo( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userName
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // did
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // url
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // fileType
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // localPath
                cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // fileSize
                cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // state
                cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // complete
                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // fileName
                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // createTime
                cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // md5
                cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13) // errorMessage
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, UploadInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFileType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLocalPath(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFileSize(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setState(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setComplete(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setFileName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCreateTime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMd5(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setErrorMessage(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(UploadInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(UploadInfo entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
