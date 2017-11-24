package edu.demon.MultiThread16;

import org.omg.CORBA.PUBLIC_MEMBER;

public class DaemonThread extends Thread {
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(getName() + " " + i);
		}
	}
	public static void main(String[] args) {
		DaemonThread thread = new DaemonThread();
		thread.setDaemon(true);
		thread.start();
		for (int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
		//程序运行到这里，前台线程结束
		//后台线程也随之结束
	}

}
