package com.atguigu.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class PoolOfThread
{
	public static void main(String[] args) {
		//利用工具类Executors获取一个固定数量的线程池
		//ExecutorService es = Executors.newFixedThreadPool(5);
		//再来一个不是固定数量的线程池
		//ExecutorService es = Executors.newSingleThreadScheduledExecutor();
		//再来一个缓存线程池，线程池中的线程数量不固定，而是根据需求的大小进行改变
		ExecutorService es = Executors.newCachedThreadPool();
		Future<Integer> result = null;
		 
		try {
			for (int i = 0; i < 30; i++) {
				
				result = es.submit(new Callable<Integer>() {
					
					@Override
					public Integer call() throws Exception {
						
						System.out.print(Thread.currentThread().getName());
						return new Random().nextInt(30);
						
					}
				});
				System.out.println( "------\t" + result.get());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			es.shutdown();
		}
		 
		
	}
	
}