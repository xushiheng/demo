package com.xsh.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPoolUtils {
	// 线程池核心线程数
	private int CORE_POOL_SIZE;
	// 线程池最大线程数
	private int MAX_POOL_SIZE;
	// 额外线程空状态生存时间
	private int KEEP_ALIVE_TIME;
	// 阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。
	private BlockingQueue<Runnable> workQueue;
	// 线程工厂
	private static ThreadFactory threadFactory = new ThreadFactory() {
		private final AtomicInteger integer = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return new Thread(r, "My ThreadPool Thread: "
					+ integer.getAndIncrement());
		}
	};
	// 线程池
	private static ThreadPoolExecutor threadPool;
	
	private static ThreadPoolUtils instance;
	
	public static ThreadPoolUtils getInstance(){
		if(instance == null){
			instance = new ThreadPoolUtils();
		}
		return instance;
	}

	private ThreadPoolUtils() {
		this.CORE_POOL_SIZE = 5;
		this.MAX_POOL_SIZE = 10;
		this.KEEP_ALIVE_TIME = 10000;
		this.workQueue = new ArrayBlockingQueue<Runnable>(10);
		threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
				KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, threadFactory);
	}

	private ThreadPoolUtils(int CORE_POOL_SIZE, int MAX_POOL_SIZE,
			int KEEP_ALIVE_TIME, int WORKQUEUE_SIZE) {
		this.CORE_POOL_SIZE = CORE_POOL_SIZE;
		this.MAX_POOL_SIZE = MAX_POOL_SIZE;
		this.KEEP_ALIVE_TIME = KEEP_ALIVE_TIME;
		this.workQueue = new ArrayBlockingQueue<Runnable>(WORKQUEUE_SIZE);
		threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
				KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, threadFactory);
	}

	// 从线程池中抽取线程，执行指定的Runnable对象
	public void execute(Runnable runnable) {
		threadPool.execute(runnable);
	}
}
