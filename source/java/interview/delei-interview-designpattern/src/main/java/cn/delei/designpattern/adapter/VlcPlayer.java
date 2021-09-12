package cn.delei.designpattern.adapter;

/**
 * VLC格式播放
 *
 * @author deleiguo
 */
public class VlcPlayer implements AdvancedMediaPlayer {

    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vls file. File name:" + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        // TODO Auto-generated method stub
    }

}
