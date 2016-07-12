package cn.delei.designpattern.adapter;

import cn.delei.designpattern.adapter.impl.Mp4Player;
import cn.delei.designpattern.adapter.impl.VlcPlayer;

public class MediaAdapter implements MediaPlayer {
	
	private AdvancedMediaPlayer AdvancedMediaPlayer;
	
	public MediaAdapter(String playType) {
		if (playType.equalsIgnoreCase("vlc")) {
			AdvancedMediaPlayer = new VlcPlayer();
		} else if (playType.equalsIgnoreCase("mp4")) {
			AdvancedMediaPlayer = new Mp4Player();
		}
	}
	
	public void play(String playType, String fileName) {
		if (playType.equalsIgnoreCase("vlc")) {
			AdvancedMediaPlayer.playVlc(fileName);
		} else if (playType.equalsIgnoreCase("mp4")) {
			AdvancedMediaPlayer.playMp4(fileName);
		}
		
	}
	
}
