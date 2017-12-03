package edu.demon.soundsystem;

import java.util.List;

public class BlankDisc implements CompactDisc {
	private List<String> tracks;
	private String title;
	private String artist;
	public List<String> getTracks() {
		return tracks;
	}
	public void setTracks(List<String> tracks) {
		this.tracks = tracks;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	@Override
	public void play() {
		
	}
	@Override
	public void playTrack(int trackNumber1) {
		if (tracks.size() < trackNumber1) {
			System.out.println("Do not have the track number");
			return;
		}
		System.out.println(tracks.get(trackNumber1));
	}

}
