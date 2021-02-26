package racom.example.audiomanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import racom.example.audiomanagertest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    MediaPlayer mMediaPlayer;
    AudioManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = mBinding.getRoot();
        setContentView(rootView);
        //获取系统的音频对象
        mManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        initMusic();
        mBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
            }
        });
        mBinding.btnHigher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAudio(true, true);
            }
        });
        mBinding.btnLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAudio(false, false);
            }
        });
        mBinding.myProgress.setProgress(7,1);
    }

    /**
     * 设置音量的大小和显示
     *
     * @param isAdd  音量增大还是增小 true: 音量增大，false: 音量变小
     * @param isShow 是否显示音量变化的toast
     */
    private void setAudio(boolean isAdd, boolean isShow) {
        // STREAM_SYSTEAM：手机系统
        // STREAM_RING：电话铃声
        // STREAM_NOTIFICATION：系统提示
        /*****上面这三种都是同一类铃声******/
        //STREAM_ALARM：手机闹铃
        // STREAM_MUSIC：手机音乐
        // STREAM_DTMF：音调
        // STREAM_VOICE_CALL:语音电话
        int stream = AudioManager.STREAM_DTMF;
        int direction;
        int flag;
        if (isAdd) {
            direction = AudioManager.ADJUST_RAISE;
        } else {
            direction = AudioManager.ADJUST_LOWER;
        }
        if (isShow) {
            flag = AudioManager.FLAG_SHOW_UI;
        } else {
            flag = AudioManager.FLAG_PLAY_SOUND;
        }
        mManager.adjustStreamVolume(stream, direction, flag);
    }


    private void initMusic() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource("http://fm111.img.xiaonei.com/tribe/20070613/10/52/A314269027058MUS.mp3");
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //首先准备一个 AudioAttributes 实例
        AudioAttributes attributes;
        //AudioAttributes 音频属性取代了 AudioManager 音频流
        attributes = new AudioAttributes.Builder()
                //设置描述音频信号的预期用途的属性
                .setUsage(AudioAttributes.USAGE_MEDIA)
                //设置描述音频信号(如语音)的内容类型的属性
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        //设置音乐属性
        mMediaPlayer.setAudioAttributes(attributes);
        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i("Demo", "onBufferingUpdate: " + percent);
            }
        });

    }
}