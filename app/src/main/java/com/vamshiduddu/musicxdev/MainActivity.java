package com.vamshiduddu.musicxdev;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer player;
    private Button frwd,revrs;
    private SeekBar seekBar;
    private Runnable runnable;
    private Handler handler;

    public void play(View view){
        player.start();
    }
    public void pause(View view){
        player.pause();
    }
    public void stop(View view){
        player.stop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player= MediaPlayer.create(this,R.raw.music);
        frwd = findViewById(R.id.frwd);
        revrs = findViewById(R.id.revrs);
        handler =new Handler();
        seekBar = findViewById(R.id.seekBar);

        frwd.setOnClickListener(this);
        revrs.setOnClickListener(this);

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(player.getDuration());
                player.start();
                changeseekbar();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    player.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        }
        private void changeseekbar(){
        seekBar.setProgress(player.getCurrentPosition());
            if(player.isPlaying())
            {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        changeseekbar();
                    }
                };
                handler.postDelayed(runnable,1000);



            }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.frwd:
                player.seekTo(player.getCurrentPosition()+5000);
                break;
            case R.id.revrs:
                player.seekTo(player.getCurrentPosition()-5000);
                break;
        }
    }
}
