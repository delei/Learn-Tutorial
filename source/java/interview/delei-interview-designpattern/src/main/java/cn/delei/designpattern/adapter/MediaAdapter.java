package cn.delei.designpattern.adapter;

import cn.hutool.core.lang.Assert;

/**
 * MediaPlayer接口的适配器类
 * <p>使用 AdvancedMediaPlayer 对象来播放所需的格式</p>
 *
 * @author deleiguo
 */
public class MediaAdapter implements MediaPlayer {

    private AdvancedMediaPlayer advancedMediaPlayer;

    public MediaAdapter(String playType) {
        Assert.notEmpty(playType, "play type is null or empty");
        switch (playType) {
            case MediaPlayer.MEDIA_MP4:
                advancedMediaPlayer = new Mp4Player();
                break;
            case MediaPlayer.MEDIA_VLC:
                advancedMediaPlayer = new VlcPlayer();
                break;
            default:
                System.err.println("Invalid play type:" + playType + ".Sorry,not support this type");
                break;

        }
    }

    @Override
    public void play(String playType, String fileName) {
        switch (playType) {
            case MediaPlayer.MEDIA_MP4:
                advancedMediaPlayer.playMp4(fileName);
                break;
            case MediaPlayer.MEDIA_VLC:
                advancedMediaPlayer.playVlc(fileName);
                break;
            default:
                System.err.println("Invalid play type:" + playType + ".Sorry,not support this type");
                break;

        }

    }

}
