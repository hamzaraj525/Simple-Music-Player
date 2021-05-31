package com.learning.iplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class PlaySong extends AppCompatActivity {

    ImageView imageView, shuffle, previous, play, next, loop;
    TextView textView;
    SeekBar seekBar;
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    Thread thread;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        imageView = findViewById(R.id.imageView);
        imageView = findViewById(R.id.imageView);
        shuffle = findViewById(R.id.imageViewShuffle);
        previous = findViewById(R.id.imageViewPreviu);
        play = findViewById(R.id.imageViewPlay);
        next = findViewById(R.id.imageViewNext);
        loop = findViewById(R.id.imageViewLoop);
        textView = findViewById(R.id.textView2);
        seekBar = findViewById(R.id.seekBar);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        songs = (ArrayList) bundle.getParcelableArrayList("song List");
        final String[] currentSong = {bundle.getString("current Song")};
        final int[] position = {bundle.getInt("position", 0)};

        Uri uri = Uri.parse(songs.get(position[0]).toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();


        seekBar.setMax(mediaPlayer.getDuration());

        textView.setText("" + currentSong[0]);
        textView.setSelected(true);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        thread = new Thread() {
            @Override
            public void run() {

                int currentPosition = 0;
                try {
                    while (currentPosition < mediaPlayer.getDuration()) {
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        sleep(800);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


        play.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                mediaPlayer.pause();
            } else {
                play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                mediaPlayer.start();
            }
        });

        previous.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (position[0] != 0) {
                position[0] = position[0] - 1;
            } else {
                position[0] = songs.size() - 1;
            }
            Uri uri12 = Uri.parse(songs.get(position[0]).toString());
            mediaPlayer = MediaPlayer.create(PlaySong.this, uri12);
            mediaPlayer.start();
            play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            seekBar.setMax(mediaPlayer.getDuration());
            currentSong[0] = songs.get(position[0]).getName();
            textView.setText(currentSong[0]);
        });

        next.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (position[0] != songs.size() - 1) {
                position[0] = position[0] + 1;
            } else {
                position[0] = 0;
            }
            Uri uri1 = Uri.parse(songs.get(position[0]).toString());
            mediaPlayer = MediaPlayer.create(PlaySong.this, uri1);
            mediaPlayer.start();
            play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            seekBar.setMax(mediaPlayer.getDuration());
            currentSong[0] = songs.get(position[0]).getName();
            textView.setText(currentSong[0]);
        });

      /*  loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        
                mediaPlayer.setLooping(true);
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlaySong.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        thread.interrupt();
        super.onDestroy();
    }
}
