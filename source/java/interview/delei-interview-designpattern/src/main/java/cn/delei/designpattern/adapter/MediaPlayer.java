package cn.delei.designpattern.adapter;

/**
 * 媒体播放器接口
 *
 * @author deleguo
 */
public interface MediaPlayer {

    String MEDIA_MP3 = "mp3";
    String MEDIA_MP4 = "mp4";
    String MEDIA_VLC = "vlc";


    void play(String playType, String fileName);
}
