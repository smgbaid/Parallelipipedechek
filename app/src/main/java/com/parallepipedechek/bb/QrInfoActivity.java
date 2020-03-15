package com.parallepipedechek.bb;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class QrInfoActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    Button startButton, pauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrinfo);

        mPlayer=MediaPlayer.create(this, R.raw.audio);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });

        startButton = findViewById(R.id.voiceBtn);
        pauseButton = findViewById(R.id.pause);

        pauseButton.setEnabled(false);

        (findViewById(R.id.backBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QrInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void stopPlay(){
        mPlayer.stop();
        pauseButton.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            startButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void play(View view){
        mPlayer.start();
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
    }

    public void pause(View view){
        mPlayer.pause();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    public void stop(View view){
        stopPlay();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
    }
}
