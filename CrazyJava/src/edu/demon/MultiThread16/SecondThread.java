package edu.demon.MultiThread16;

public class SecondThread implements Runnable {
	private int i;
	@Override
	public void run() {
		for (; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName());
			if (i == 20) {
				SecondThread sThread = new SecondThread();
				// 第一个线程
				new Thread(sThread, "first").start();
				// 第二个线程
				new Thread(sThread, "second").start();
			}
		}

	}

}
