package com.atguigu.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExe3 {
	
	public static void main(String[] args) {
		//在线程池中创建固定数量的线程
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		//在线程池中创建单个线程
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
		//在线程池中自适应的，即，需要多少个线程，内存自己创建
//		ExecutorService executorService = Executors.newCachedThreadPool();
		
		try {
			for (int i = 0; i < 30; i++) {
				Future<Integer> result = executorService.submit(new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName());
						return new Random().nextInt(30);
					}
				});
				System.out.println("-----------"+result.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//必须关闭资源
			executorService.shutdown();
		}
	}
	
}
