package cn.delei.designpattern.adapter;

/**
 * MP4格式
 *
 * @author deleiguo
 */
public class Mp4Player implements AdvancedMediaPlayer {

    @Override
    public void playVlc(String fileName) {
        // TODO Auto-generated method stub
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file. File name:" + fileName);
    }

}
