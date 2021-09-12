package cn.delei.designpattern.adapter;

/**
 * 先进的媒体格式播放器接口
 *
 * @author deleiguo
 */
public interface AdvancedMediaPlayer {
    /**
     * 播放VLC格式文件
     *
     * @param fileName VLC格式文件名
     */
    void playVlc(String fileName);

    /**
     * 播放MP4格式文件
     *
     * @param fileName MP4格式文件名
     */
    void playMp4(String fileName);
}
