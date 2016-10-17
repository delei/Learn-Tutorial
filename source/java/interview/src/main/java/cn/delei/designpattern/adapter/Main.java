package cn.delei.designpattern.adapter;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AudioPlayer audioPlayer = new AudioPlayer();
		audioPlayer.play("mp3", "a.mp3");
		audioPlayer.play("mp4", "b.mp4");
		audioPlayer.play("vlc", "c.vlc");
		audioPlayer.play("rmvb", "d.rmvb");
	}
	
}
