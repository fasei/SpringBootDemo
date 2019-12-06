package com.example.demo.websocket;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Author: wangchao
 * Time: 2019-06-25
 * Description: This is
 */
public class ThreadPools {
    /**
     * 线程池
     */
    private Executor mExecutor;

    public ThreadPools() {
        mExecutor = taskExecutor();
    }

    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    private static class SingletonClassInstance {
        private static final ThreadPools instance = new ThreadPools();
    }

    public static ThreadPools getInstance() {
        return SingletonClassInstance.instance;
    }

    public void run(Runnable thread) {
        mExecutor.execute(thread);
    }
}
