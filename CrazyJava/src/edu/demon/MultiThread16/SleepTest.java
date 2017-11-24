package edu.demon.MultiThread16;

import java.util.Date;

public class SleepTest {
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			System.out.println("当前时间： " + new Date());
			Thread.sleep(1000);
		}
	}
}
