package edu.demon.MultiThread16;

import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
	private final ReentrantLock lock = new ReentrantLock();
	
	public void draw() {
		lock.lock();
		try {
		} finally {
			lock.unlock();
		}
	}
	public static void main(String[] args) {

	}

}
