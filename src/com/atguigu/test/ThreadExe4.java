package com.atguigu.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadExe4 {

	public static void main(String[] args) {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
		
		try {
			for (int i = 1; i <= 20; i++) {
				ScheduledFuture<Integer> result = executorService.schedule(new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						Thread.sleep(500);
						System.out.println(Thread.currentThread().getName());
						return new Random().nextInt(30);
					}
				}, 3, TimeUnit.SECONDS);
				
				System.out.println("-----result："+result.get());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
