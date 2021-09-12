package cn.delei.designpattern.adapter;

import cn.hutool.core.lang.Assert;

/**
 * 音频播放接口
 * <p>默认情况下，AudioPlayer 可以播放 mp3 格式的音频文件</p>
 * <p>
 * 想要让 AudioPlayer 播放其他格式的音频文件,创建一个实现了 MediaPlayer 接口的适配器类 MediaAdapter，
 * 并使用 AdvancedMediaPlayer 对象来播放所需的格式
 * </p>
 *
 * @author deleiguo
 */
public class AudioPlayer implements MediaPlayer {

    private MediaAdapter mediaAdapter;

    @Override
    public void play(String playType, String fileName) {
        Assert.notEmpty(playType, "play type is null or empty");
        Assert.notEmpty(fileName, "file name is null or empty");
        
        switch (playType) {
            case MediaPlayer.MEDIA_MP3:
                System.out.println("Playing mp3 file.File name:" + fileName);
                break;
            case MediaPlayer.MEDIA_MP4:
            case MediaPlayer.MEDIA_VLC:
                // 适配使用高级播放接口进行处理
                mediaAdapter = new MediaAdapter(playType);
                mediaAdapter.play(playType, fileName);
                break;
            default:
                System.err.println("Invalid play type:" + playType + ".Sorry,not support this type");
                break;
        }
    }

}
