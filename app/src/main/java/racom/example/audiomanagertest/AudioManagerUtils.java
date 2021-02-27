package racom.example.audiomanagertest;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;

/**
 * 音频管理器
 *
 * @author HB.yangzuozhe
 * @date 2021-02-27
 */
public class AudioManagerUtils {
    private AudioManager mManager;

    public AudioManagerUtils(Context context) {
        //获取系统的音频对象
        mManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
    }

    /**
     * 设置音量的大小和是否显示音量变化弹窗
     *
     * @param isAdd  音量增大还是增小 true: 音量增大，false: 音量变小
     * @param isShow 是否显示音量变化的toast
     */
    public void setAudio(boolean isAdd, boolean isShow) {
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
            //可调大一个单位
            direction = AudioManager.ADJUST_RAISE;
        } else {
            //可调小一个单位
            direction = AudioManager.ADJUST_LOWER;
        }
        if (isShow) {
            //显示进度条
            flag = AudioManager.FLAG_SHOW_UI;
        } else {
            //播放声音
            flag = AudioManager.FLAG_PLAY_SOUND;
        }
        mManager.adjustStreamVolume(stream, direction, flag);
    }

    /**
     * 手机的某个音效类型是否静音
     *
     * @param streamType 某个音效，如铃声，媒体声音
     *                   // STREAM_SYSTEAM：手机系统
     *                   // STREAM_RING：电话铃声
     *                   // STREAM_NOTIFICATION：系统提示
     *                   //STREAM_ALARM：手机闹铃
     *                   // STREAM_MUSIC：手机音乐
     *                   // STREAM_DTMF：音调
     *                   // STREAM_VOICE_CALL:语音电话
     * @param state      是否静音
     */
    public void setStreamMute(int streamType, boolean state) {

        int direction;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //android 5.0以上的使用
            //ADJUST_MUTE 静音
            //ADJUST_UNMUTE 不静音
            if (state) {
                direction = AudioManager.ADJUST_MUTE;
            } else {
                direction = AudioManager.ADJUST_UNMUTE;
            }
            mManager.adjustStreamVolume(streamType, direction, AudioManager.FLAG_PLAY_SOUND);
        } else {
            //Android 5.0以下的使用
            mManager.setStreamMute(streamType, state);
        }
    }

    /**
     * 设置是否让麦克风（就是我们讲话的声音）静音
     */
    private void setMicrophoneMute(boolean on) {
        mManager.setMicrophoneMute(on);
    }


    /**
     * @return 判断麦克风是否静音或是否打开
     */
    public boolean isMicrophoneMute() {
        return mManager.isMicrophoneMute();
    }

    /**
     * 判断是否有音乐处于活跃状态
     */
    public boolean isMusicActive() {
        return mManager.isMusicActive();
    }

    /**
     * 获取当前手机的音量，最大值为7,最小值为0,当设置为0的时候,会自动调整为震动模式
     *
     * @param streamType 要调整的音乐类型
     */
    public void getStreamVolume(int streamType) {
        mManager.getStreamVolume(streamType);
    }

    /**
     * 返回当前的音频模式
     * 设置声音模式 有下述几种模式: MODE_NORMAL(普通), MODE_RINGTONE(铃声), MODE_IN_CALL(打电话)，MODE_IN_COMMUNICATION(通话)
     *
     * @return 音频模式
     */
    public int getMode() {
        return mManager.getMode();
    }

    /**
     * @return 返回当前的铃声模式
     */
    public int getRingerMode() {
        return mManager.getRingerMode();
    }

    /**
     * 设置铃声模式 有下述几种模式:
     *
     * @param streamType 如 RINGER_MODE_NORMAL（普通）、RINGER_MODE_SILENT（静音）、RINGER_MODE_VIBRATE（震动）
     */
    public void setRingerMode(int streamType) {
        mManager.setRingerMode(streamType);
    }

    /**
     * 是否插入了耳机
     */
    @SuppressLint("WrongConstant")
    public boolean isWiredHeadsetOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Android 5.0以上用这样判断耳机是否插入
            AudioDeviceInfo[] audioDeviceInfos = mManager.getDevices(AudioManager.GET_DEVICES_ALL);
            if (audioDeviceInfos == null || audioDeviceInfos.length == 0) {
                return false;
            }
            for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfos) {
                if (audioDeviceInfo.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET) {
                    return true;
                }
            }
        } else {
            //Android 5.0以下用这样判断耳机是否插入
            return mManager.isWiredHeadsetOn();
        }
        return false;
    }

    /**
     * 判断当前手机里使用的设备，如有线耳机，扬声器，无线耳机等等
     *
     * @param flags  音频的输入流还是输出流还是两个流
     *               AudioManager.GET_DEVICES_OUTPUTS 输出流
     *               AudioManager.GET_DEVICES_INPUTS 输入流
     *               AudioManager.GET_DEVICES_ALL 所有的流
     * @param device 音频设备的类型比如，有线耳机，扬声器，无线耳机等等
     *               AudioDeviceInfo.TYPE_BUILTIN_SPEAKER 内置扬声器
     *               AudioDeviceInfo.TYPE_WIRED_HEADSET 有线耳机
     *               AudioDeviceInfo.TYPE_BLUETOOTH_SCO 蓝牙耳机
     *               等等
     */
    @SuppressLint("WrongConstant")
    public boolean checkDevices(int flags, int device) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //AudioManager.GET_DEVICES_ALL表示获取音频输入输出流的所有设备
            AudioDeviceInfo[] audioDeviceInfos = mManager.getDevices(flags);
            if (audioDeviceInfos == null || audioDeviceInfos.length == 0) {
                return false;
            }
            for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfos) {
                if (audioDeviceInfo.getType() == device) {
                    return true;
                }
            }
        }
        return false;
    }
}


