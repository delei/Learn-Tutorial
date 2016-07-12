package cn.delei.designpattern.adapter.impl;

import cn.delei.designpattern.adapter.AdvancedMediaPlayer;

public class Mp4Player implements AdvancedMediaPlayer {
	
	public void playVlc(String fileName) {
		// TODO Auto-generated method stub
		
	}
	
	public void playMp4(String fileName) {
		System.out.println("Playing mp4 file. File name:" + fileName);
		
	}
	
}
