package edu.demon.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Audience {
	@Before("execution(** edu.demon.aop.Performance.perform(..))")
	public void silenceCellPhones() {
		System.out.println("Silence cell phone");
	}
	
	@Before("execution(** edu.demon.aop.Performance.perform(..))")
	public void takeSeats() {
		System.out.println("Taking seats");
	}
	
	@AfterReturning("execution(** edu.demon.aop.Performance.perform(..))")
	public void applause() {
		System.out.println("CLAP!!CLAP!!CLAP!!");
	}
	
	@AfterThrowing("execution(** edu.demon.aop.Performance.perform(..))")
	public void demandRefund() {
		System.out.println("Demanding a refund");
	}
	
	/*
	 * 表示重复定义的point cut，可以用@Pointcut注解，其中是一个切点表达式，之后使用，替换成了其下面的performance()标识
	@Pointcut("execution(** edu.demon.aop.Performance.perform(..))")
	public void performance() {}
	
	@Before("performance()")
	public void silenceCellPhones() {
		System.out.println("Silence cell phone");
	}
	
	@Before("performance()")
	public void takeSeats() {
		System.out.println("Taking seats");
	}
	
	@AfterReturning("performance()")
	public void applause() {
		System.out.println("CLAP!!CLAP!!CLAP!!");
	}
	
	@AfterThrowing("performance()")
	public void demandRefund() {
		System.out.println("Demanding a refund");
	}
	*/
}
