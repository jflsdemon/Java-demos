package edu.demon.soundsystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {TrackCoutConfig.class, CDPlayConfigure.class}) // to test Qualifier
public class TrackCounterAopTest {
	@Autowired

	private TrackCounter counter;
	
	@Autowired
	@Qualifier("blankCD")
	private CompactDisc cd;
	
	@Test
	public void testTrackCountAop() {
		cd.playTrack(1);
		cd.playTrack(1);
		cd.playTrack(2);
		cd.playTrack(2);
		cd.playTrack(2);
		cd.playTrack(2);
		
		
		assertEquals(2, counter.getPlayCount(1));
		assertEquals(4, counter.getPlayCount(2));
	}
}
