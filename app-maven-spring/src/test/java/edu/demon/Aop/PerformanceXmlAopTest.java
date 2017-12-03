package edu.demon.Aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.demon.aop.Performance;

public class PerformanceXmlAopTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/configs/spring-aop-audience.xml");
		System.out.println("容器初始化成功");
		Performance perform = context.getBean("danceperformance",Performance.class);
		perform.perform();
		System.out.println("现在开始关闭容器");
		((ClassPathXmlApplicationContext)context).registerShutdownHook();
	}
}
