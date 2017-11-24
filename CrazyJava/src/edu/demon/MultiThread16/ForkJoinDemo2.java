package edu.demon.MultiThread16;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinDemo2 extends RecursiveAction {
	private static final int THRESHOLD = 50;
	private int begin;
	private int end;
	public ForkJoinDemo2(int begin,int end) {
		this.begin = begin;
		this.end = end;
	}
	
	@Override
	protected void compute() {
		if (end - begin < THRESHOLD) {
			for (int i = begin; i < end; i++) {
				System.out.println(Thread.currentThread().getName() + "的i值: " + i);
			}
		} else {
			int middle = (begin + end) / 2;
			ForkJoinDemo2 left = new ForkJoinDemo2(begin, middle);
			ForkJoinDemo2 right = new ForkJoinDemo2(middle, end);
			left.fork();
			right.fork();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ForkJoinPool pool = new ForkJoinPool();
		pool.submit(new ForkJoinDemo2(0, 200));
		pool.awaitTermination(2, TimeUnit.SECONDS);
		pool.shutdown();
	}

}
