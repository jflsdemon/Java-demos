package edu.demon.soundsystem;

import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc {

	@Override
	public void play() {
		System.out.println("SgtPeppers is Playing"); 
	}

	@Override
	public void playTrack(int trackNumber) {
		// TODO Auto-generated method stub
		
	}

}
