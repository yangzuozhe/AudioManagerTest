package racom.example.audiomanagertest;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import racom.example.audiomanagertest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    MediaPlayer mMediaPlayer;
    AudioManagerUtils mAudioManagerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = mBinding.getRoot();
        setContentView(rootView);
        initMusic();
        mAudioManagerUtils = new AudioManagerUtils(this);
        mBinding.btnStart.setOnClickListener(v ->{
            mMediaPlayer.start();
            mBinding.btnStop.setEnabled(true);
        });
        mBinding.btnStop.setOnClickListener(v -> {
            mMediaPlayer.pause();
            mBinding.btnStop.setEnabled(false);
        });
        mBinding.btnHigher.setOnClickListener(v -> mAudioManagerUtils.setAudio(true, true));
        mBinding.btnLower.setOnClickListener(v -> mAudioManagerUtils.setAudio(false, false));
        mBinding.btnQuite.setOnClickListener(v -> mAudioManagerUtils.setStreamMute(AudioManager.STREAM_MUSIC, true));
        mBinding.btnIsPlay.setOnClickListener(v -> {
            if (mAudioManagerUtils.isMusicActive()) {
                Toast.makeText(v.getContext(), "有音乐", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "没有音乐", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}