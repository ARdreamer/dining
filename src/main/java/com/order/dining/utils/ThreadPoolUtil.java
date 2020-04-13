package com.order.dining.utils;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: baojx
 * @Date: 2020/04/13 19:20
 * @Desc:
 */
public class ThreadPoolUtil {
    private final static int MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;
    private final static int MIN_THREADS = 2;
    private final static int QUEUE_SIZE = 128;
    private static ExecutorService executorService = null;

    static {
        executorService = new ThreadPoolExecutor(MIN_THREADS, MAX_THREADS, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_SIZE));
    }

    public static void addTask(Thread thread) {
        executorService.execute(thread);
    }

    public static void closeTask() {
        executorService.shutdown();
    }

}
