package cn.delei.designpattern.adapter;

public class AudioPlayer implements MediaPlayer {
	
	private MediaAdapter mediaAdapter;
	
	public void play(String playType, String fileName) {
		if (playType.equalsIgnoreCase("mp3")) {
			System.out.println("Playing mp3 file.File name:" + fileName);
		} else if (playType.equalsIgnoreCase("vlc") || playType.equalsIgnoreCase("mp4")) {
			mediaAdapter = new MediaAdapter(playType);
			mediaAdapter.play(playType, fileName);
		} else {
			System.err.println("Invalid play type:" + playType + ".Sorry,not support this type");
		}
	}
	
}
