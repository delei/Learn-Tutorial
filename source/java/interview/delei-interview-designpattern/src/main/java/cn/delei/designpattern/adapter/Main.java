package cn.delei.designpattern.adapter;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {

    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play("mp3", "a.mp3");
        audioPlayer.play("mp4", "b.mp4");
        audioPlayer.play("vlc", "c.vlc");
        audioPlayer.play("rmvb", "d.rmvb");
    }

}
