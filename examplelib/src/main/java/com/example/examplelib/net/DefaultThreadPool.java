package com.example.examplelib.net;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池 、缓冲队列
 */
public class DefaultThreadPool {
    // 阻塞队列最大任务数量
    private static final int BLOCKING_QUEUE_SIZE = 20;
    private static final int THREAD_POOL_MAX_SIZE = 10;

    private static final int THREAD_POOL_SIZE = 6;
    /**
     * 缓冲BaseRequest任务队列
     */
    private static final ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(
            DefaultThreadPool.BLOCKING_QUEUE_SIZE);

    private static DefaultThreadPool instance = null;
    /**
     * 线程池，目前是十个线程，
     */
    private static final AbstractExecutorService pool = new ThreadPoolExecutor(
            DefaultThreadPool.THREAD_POOL_SIZE,
            DefaultThreadPool.THREAD_POOL_MAX_SIZE, 15L, TimeUnit.SECONDS,
            DefaultThreadPool.blockingQueue,
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static synchronized DefaultThreadPool getInstance() {
        if (DefaultThreadPool.instance == null || DefaultThreadPool.pool.isShutdown()) {
            DefaultThreadPool.instance = new DefaultThreadPool();
        }
        return DefaultThreadPool.instance;
    }

    public static void removeAllTask() {
        DefaultThreadPool.blockingQueue.clear();
    }

    public static void removeTaskFromQueue(final Object obj) {
        DefaultThreadPool.blockingQueue.remove(obj);
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public static void shutdown() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public static void shutdownRightnow() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                DefaultThreadPool.pool.awaitTermination(1,
                        TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行任务
     *
     * @param r
     */
    public void execute(final Runnable r) {
        if (r != null) {
            try {
                DefaultThreadPool.pool.execute(r);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
