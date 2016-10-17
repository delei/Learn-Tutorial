package cn.delei.designpattern.adapter.impl;

import cn.delei.designpattern.adapter.AdvancedMediaPlayer;

public class VlcPlayer implements AdvancedMediaPlayer {
	
	public void playVlc(String fileName) {
		System.out.println("Playing vls file. File name:" + fileName);
	}
	
	public void playMp4(String fileName) {
		// TODO Auto-generated method stub
		
	}
	
}
