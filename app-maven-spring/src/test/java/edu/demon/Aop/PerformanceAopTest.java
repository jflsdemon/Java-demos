package edu.demon.Aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.demon.aop.AopConfig;
import edu.demon.aop.Performance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AopConfig.class)
public class PerformanceAopTest {
	@Autowired
	private Performance performance;
	
	@Test
	public void testAop() {
		performance.perform();
	}
}
