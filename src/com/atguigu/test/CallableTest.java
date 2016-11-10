package com.atguigu.test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Ticket{
	private int num = 30;
	Lock lock = new ReentrantLock();
	
	public void sale(){
		try {
			lock.lock();
			
			if(num > 0){
				Thread.sleep(200);
				System.out.println(Thread.currentThread().getName()+"\t卖第："+num--+"张票\t，还剩："+num);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
}

class MyThread implements Callable<Integer>{
	
	final Ticket t1 = new Ticket();
	
	@Override
	public Integer call() throws Exception {
		
		System.out.println(" come in ======");
		for (int i = 0; i < 40; i++) {
			t1.sale();
		}
		
		return 200;
	}
	
}


public class CallableTest {
	
	public static void main(String[] args) {
		
		MyThread t = new MyThread();
		
		FutureTask<Integer> task = new FutureTask<>(t);
		
		new Thread(task,"aa").start();
		
		new Thread(task,"bb").start();

		new Thread(task,"cc").start();
		
	}
}
