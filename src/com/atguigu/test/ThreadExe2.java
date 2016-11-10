package com.atguigu.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：三个线程，要求实现按序访问,A>B>C......
 * A打印5次，B打印10次，C打印15次
 * 接着按照上述同样的顺序，再来
 * A打印5次，B打印10次，C打印15次
 * 接着按照上述同样的顺序，再来
 * 。。。。。。来20轮
 * @author zxj
 *
 */
class NumberPrint{
	
	int num = 1;
	Lock lock = new ReentrantLock();
	Condition conditionA = lock.newCondition();
	Condition conditionB = lock.newCondition();
	Condition conditionC = lock.newCondition();
	
	public void printA(int x){
		lock.lock();
		
		try {
			while(num != 1){
				conditionA.await();
			}
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName()+"："+(i+1)+"，\t轮次："+x);
			}
			num = 2;
			conditionB.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		
	}
	
	public void printB(int x){
		lock.lock();
		
		try {
			while(num != 2){
				conditionB.await();
			}
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName()+"："+(i+1)+"，\t轮次："+x);
			}
			num = 3;
			conditionC.signal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	public void printC(int x){
		lock.lock();
		
		try {
			while(num != 3){
				conditionC.await();
			}
			for (int i = 0; i < 15; i++) {
				System.out.println(Thread.currentThread().getName()+"："+(i+1)+"，\t轮次："+x);
			}
			num = 1;
			conditionA.signal();
			
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
}

public class ThreadExe2{
	public static void main(String[] args) {
		
		final NumberPrint n = new NumberPrint();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					n.printA(i);
				}
			}
		}, "AA").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					n.printB(i);
				}
			}
		}, "BB").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					n.printC(i);
				}
			}
		}, "CC").start();
		
	}
}
