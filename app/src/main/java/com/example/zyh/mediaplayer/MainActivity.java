package com.example.zyh.mediaplayer;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//通过Mediaplayer访问raw,assets中的视屏，音频
//通过Movie播放本地gif
//打开本地任意的视频，音频
//获取assets中的文件
//探究enviroment.get方法


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     public  PowerImageView imageView;
    public Button end,start,pause,resume_video,start_video,pause_video;
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private VideoView videoView;

    AssetFileDescriptor assetFileDescriptor= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        end=(Button)findViewById(R.id.end);
        pause=(Button)findViewById(R.id.pause);
        start=(Button)findViewById(R.id.start);
        resume_video=(Button)findViewById(R.id.resume_video);
        pause_video=(Button)findViewById(R.id.pause_video);
        start_video=(Button)findViewById(R.id.start_video);
        textView=(TextView)findViewById(R.id.textview);
        videoView=(VideoView)findViewById(R.id.video);

        mediaPlayer=new MediaPlayer();
        AssetManager assetManager=this.getAssets();


        try {
            assetFileDescriptor = assetManager.openFd("music.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        initVoiceMediaplayer();
        initVideoPath();
        textView.setText(Environment.getDataDirectory()+"\n");
        textView.append(Environment.getExternalStorageDirectory()+"\n");
        textView.append(Environment.getExternalStorageState()+"\n");
        textView.append(this.getExternalCacheDir()+"\n");
        textView.append(this.getExternalMediaDirs()+"");
        /*try {
            textView.append(TypedValue.class.getDeclaredField("width")+"");
            textView.append(View.class.getField("width")+"");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            textView.append("hahahha");
        }
        */
        textView.setBackgroundColor(Color.BLUE);


        pause.setOnClickListener(this);
        end.setOnClickListener(this);
        start.setOnClickListener(this);
        pause_video.setOnClickListener(this);
        resume_video.setOnClickListener(this);
        start_video.setOnClickListener(this);
    }

    public void initVoiceMediaplayer(){
      /*  try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor
            .getStartOffset(),assetFileDescriptor.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        try {
            mediaPlayer.setDataSource(MainActivity.this,Uri.parse("android.resource://com.example.zyh.mediaplayer/"+R.raw.music));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public  void initVideoPath(){
        videoView.setVideoURI(Uri.parse("android.resource://com.example.zyh.mediaplayer/"+R.raw.shipin));
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.start :
                if (!mediaPlayer.isPlaying());
                mediaPlayer.start();
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying());
                mediaPlayer.pause();
                break;
            case R.id.end:
                if (mediaPlayer.isPlaying());
                mediaPlayer.reset();
               initVoiceMediaplayer();
                break;
            case R.id.start_video :
                if (!videoView.isPlaying())
                {
                    videoView.start();
                }
                break;
            case R.id.pause_video :
                if (videoView.isPlaying())
                {
                    videoView.pause();
                }
                break;
            case R.id.resume_video:
                if (videoView.isPlaying())
                {
                    videoView.resume();
                }
                break;
            default:
                break;
        }
    }

    public  void onDestory(){
        super.onDestroy();
        if (mediaPlayer.isPlaying());
        mediaPlayer.stop();
        mediaPlayer.release();
        if (videoView!=null){
            videoView.suspend();
        }

    }


}
