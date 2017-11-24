package edu.demon.MultiThread16;

public class YieldTest extends Thread {
	public YieldTest(String name) {
		super(name);
	}
	public void run() {
		for (int i = 0; i < 50; i++) {
			System.out.println(getName() + " " + i);
			if (i== 20) {
				Thread.yield();
			}
		}
	}
	//若没有设置优先级，两个线程优先级完全一样，一个线程yield后，另一个线程会开始执行
	//若如下设置了优先级，优先级高的线程调用yield以后，会再次被调度执行
	public static void main(String[] args) {
		YieldTest yt1 = new YieldTest("Top Level");
		yt1.setPriority(Thread.MAX_PRIORITY);
		yt1.start();
		
		YieldTest yt2 = new YieldTest("Top Level");
		yt2.setPriority(Thread.MIN_PRIORITY);
		yt2.start();
	}

}
