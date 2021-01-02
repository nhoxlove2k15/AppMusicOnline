package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MusicActivity extends AppCompatActivity {
    TextView txtTitle, txtArtist, txtElapsedTime, txtTotalTime;
    SeekBar progressBar, volumeBar;
    ImageButton btnVolumeOn, btnTimer, btnLyrics;
    ImageButton btnPrevSong, btnNextSong, btnPrev10s, btnNext10s, btnPlay;
    ImageButton btnLike, btnShuffle, btnRepeat, btnShare;
    ImageButton btnClose;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    ArrayList<Song> arraySong;
    Random rnd = new Random();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();
        addSong();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        position = rnd.nextInt(arraySong.size());
        initMediaPlayer();

        //nhấn nút play
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                //đang phát -> chuyển sang hình play
                mediaPlayer.pause();
                btnPlay.setImageResource(R.drawable.play);
            } else {
                //đang ngừng -> chuyển sang hình pause
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause_);
            }
            //updateTime();
        });

        //nhấn nút next song
        btnNextSong.setOnClickListener(v -> {
            position++;
            if (position > arraySong.size() - 1) {
                position = 0;
            }
            if (mediaPlayer.isPlaying()) {
                //nếu đang phát nhạc -> chuyển bài sẽ tự động phát
                mediaPlayer.stop();
                initMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause_);
            } else {
                //nếu đang không phát nhạc -> chuyển bài sẽ không tự động phát
                initMediaPlayer();
                btnPlay.setImageResource(R.drawable.play);
            }
            //updateTime();
        });

        //nhấn nút previous song
        btnPrevSong.setOnClickListener(v -> {
            position--;
            if (position < 0) {
                position = arraySong.size() - 1;
            }
            if (mediaPlayer.isPlaying()) {
                //nếu đang phát nhạc -> chuyển bài sẽ tự động phát
                mediaPlayer.stop();
                initMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause_);
            } else {
                //nếu đang không phát nhạc -> chuyển bài sẽ không tự động phát
                initMediaPlayer();
                btnPlay.setImageResource(R.drawable.play);
            }
            //updateTime();
        });

        //tua tới 10s
        btnNext10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000); //cộng thêm 10000ms (10s)
                updateTime();
            }
        });

        //tua lui 10s
        btnPrev10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000); //trừ đi 10000ms (10s)
                updateTime();
            }
        });

        //nhấn nút volume
        btnVolumeOn.setOnClickListener(v -> {
            if (volumeBar.getProgress() != 0) {
                mute();
            } else {    //thanh âm lượng đang ở mức 0 thì unmute
                unMute();
            }
        });

        //VolumeBar
        volumeBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        if (volumeBar.getProgress() == 0) {
            btnVolumeOn.setImageResource(R.drawable.mute);
        } else if (volumeBar.getProgress() > 0 && volumeBar.getProgress() <= 5) {
            btnVolumeOn.setImageResource(R.drawable.volume_small);
        } else if (volumeBar.getProgress() > 5 && volumeBar.getProgress() <= 10) {
            btnVolumeOn.setImageResource(R.drawable.volume_medium);
        } else {
            btnVolumeOn.setImageResource(R.drawable.volume_max);
        }
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                        if (progress == 0) {
                            btnVolumeOn.setImageResource(R.drawable.mute);
                        } else if (progress > 0 && progress <= 5) {
                            btnVolumeOn.setImageResource(R.drawable.volume_small);
                        } else if (progress > 5 && progress <= 10) {
                            btnVolumeOn.setImageResource(R.drawable.volume_medium);
                        } else {
                            btnVolumeOn.setImageResource(R.drawable.volume_max);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(progressBar.getProgress());
            }
        });
    }

    private void mute() {
        audioManager = (AudioManager) MusicActivity.this.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
        } else {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
        btnVolumeOn.setImageResource(R.drawable.mute);
        volumeBar.setProgress(0);
    }

    private void unMute() {
        audioManager = (AudioManager) MusicActivity.this.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
        } else {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        }
        btnVolumeOn.setImageResource(R.drawable.volume_max);
        volumeBar.setProgress(10);
    }

    private void updateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                txtElapsedTime.setText(timeFormat.format(mediaPlayer.getCurrentPosition()));
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(mp -> {
                    position++;
                    if (position > arraySong.size() - 1) {
                        position = 0;
                    }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    initMediaPlayer();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause_);
                    updateTime();
                });
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    private void setTotalTime() {
        java.text.SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        txtTotalTime.setText(timeFormat.format(mediaPlayer.getDuration()));
        progressBar.setMax(mediaPlayer.getDuration());
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(MusicActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
        txtArtist.setText(arraySong.get(position).getArtist());
        setTotalTime();
        updateTime();
    }

    private void addSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Sweet Dreams", "Eurythmics, Annie Lennox, Dave Stewart", R.raw.sweet_dreams));
        arraySong.add(new Song("Pumped Up Kicks", "Foster The People", R.raw.pumped_up_kicks));
        arraySong.add(new Song("Caramelldansen", "Caramella Girls", R.raw.caramelldansen));
        arraySong.add(new Song("Renai Circulation", "Hanazawa Kana", R.raw.renai_circulation));
    }

    public void mapping() {
        txtTitle = findViewById(R.id.songTitle);
        txtArtist = findViewById(R.id.artist);
        txtElapsedTime = findViewById(R.id.elapsed_time);
        txtTotalTime = findViewById(R.id.remaining_time);
        progressBar = findViewById(R.id.progressBar);
        volumeBar = findViewById(R.id.volumeBar);
        btnVolumeOn = findViewById(R.id.speakerMax);
        btnTimer = findViewById(R.id.timer);
        btnLyrics = findViewById(R.id.lyrics);
        btnPrevSong = findViewById(R.id.prevSong);
        btnNextSong = findViewById(R.id.nextSong);
        btnPrev10s = findViewById(R.id.prev10);
        btnNext10s = findViewById(R.id.next10);
        btnPlay = findViewById(R.id.playBtn);
        btnLike = findViewById(R.id.likeBtn);
        btnShuffle = findViewById(R.id.shuffleBtn);
        btnRepeat = findViewById(R.id.repeatBtn);
        btnShare = findViewById(R.id.shareBtn);
        btnClose = findViewById(R.id.btnClose);
    }
}