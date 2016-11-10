package com.atguigu.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableTest{

	public static void main(String[] args) {
		final Ticket1 t = new Ticket1();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 40; i++) {
					t.sale();
				}
			}
		},"AA").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 40; i++) {
					t.sale();
				}
			}
		},"BB").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 40; i++) {
					t.sale();
				}
			}
		},"CC").start();
	}
}

class Ticket1{
	private int ticket = 30;
	
	Lock lock = new ReentrantLock();
	
	public void sale (){
		lock.lock();
		try {
			if(ticket > 0){
				Thread.sleep(200);
				System.out.println(Thread.currentThread().getName()+"卖了第："+ticket--+"\t还剩："+ticket);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}