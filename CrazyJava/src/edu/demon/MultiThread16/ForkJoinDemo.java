package edu.demon.MultiThread16;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;


public class ForkJoinDemo extends RecursiveTask<Integer> {
	private static final int THRESHOLD = 20;
	private int[] arr;
	private int begin;
	private int end;
	public ForkJoinDemo(int[] arr, int begin,int end) {
		this.arr = arr;
		this.begin = begin;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		if (end - begin < THRESHOLD) {
			for (int i = begin; i < end; i++) {
				sum += arr[i];
			}
			return sum;
		} else {
			int middle = (begin + end) / 2;
			ForkJoinDemo left = new ForkJoinDemo(arr, begin, middle);
			ForkJoinDemo right = new ForkJoinDemo(arr, middle, end);
			left.fork();
			right.fork();
			return left.join() + right.join();
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int[] arr = new int[100];
		Random random = new Random();
		int total = 0;
		for (int i = 0; i < arr.length; i++) {
			int tmp = random.nextInt(20);
			total += (arr[i] = tmp);
		}
		System.out.println("total: " + total);
		ForkJoinPool pool = ForkJoinPool.commonPool();
		Future<Integer> future = pool.submit(new ForkJoinDemo(arr, 0, arr.length));
		System.out.println(future.get());

	}
}
