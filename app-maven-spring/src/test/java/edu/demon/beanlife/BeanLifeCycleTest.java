package edu.demon.beanlife;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLifeCycleTest {

	public static void main(String[] args) {
		ApplicationContext aContext = new ClassPathXmlApplicationContext("/configs/spring-beanlife.xml");
		System.out.println("容器初始化成功");
		Person person = aContext.getBean("person",Person.class);
		System.out.println(person);
		System.out.println("现在开始关闭容器");
		((ClassPathXmlApplicationContext)aContext).registerShutdownHook();
	}
}
