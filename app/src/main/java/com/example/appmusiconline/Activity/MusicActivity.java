package com.example.appmusiconline.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.example.appmusiconline.Model.PersonalSong;
import com.example.appmusiconline.R;
import com.squareup.picasso.Picasso;

public class MusicActivity extends AppCompatActivity {
    TextView txtTitle, txtArtist, txtElapsedTime, txtTotalTime;
    SeekBar progressBar, volumeBar;
    ImageView coverArt;
    ImageButton btnVolumeOn, btnTimer, btnLyrics;
    ImageButton btnPrevSong, btnNextSong, btnPrev10s, btnNext10s, btnPlay;
    ImageButton btnLike, btnShuffle, btnRepeat, btnShare;
    ImageButton btnClose;
    public MediaPlayer mediaPlayer;
    AudioManager audioManager;
    Handler handler = new Handler();

    Intent intent;
    Bundle bundle ;
    PersonalSong arraySong ;

    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mapping();
        progressBar.setMax(100);

        intent =   getIntent() ;
        bundle = intent.getBundleExtra("darkwa1");
        arraySong = (PersonalSong) bundle.getSerializable("darkwa");
        txtTitle.setSelected(true);
        txtArtist.setSelected(true);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        initMediaPlayer();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        prepareMusic();
        //nhấn nút play
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    //đang phát -> chuyển sang hình play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                } else {
                    //đang ngừng -> chuyển sang hình pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause_);
                }
//                //updateTime();
            }
        });

        //nhấn nút close
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicActivity.this, TrangchuActivity.class);
                startActivity(intent);
            }
        });
        //nhấn nút next song
//        btnNextSong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                position++;
//                if (position > arraySong.size() - 1) {
//                    position = 0;
//                }
//                if (mediaPlayer.isPlaying()) {
//                    //nếu đang phát nhạc -> chuyển bài sẽ tự động phát
//                    mediaPlayer.stop();
//                    MusicActivity.this.initMediaPlayer();
//                    mediaPlayer.start();
//                    btnPlay.setImageResource(R.drawable.pause_);
//                } else {
//                    //nếu đang không phát nhạc -> chuyển bài sẽ không tự động phát
//                    MusicActivity.this.initMediaPlayer();
//                    btnPlay.setImageResource(R.drawable.play);
//                }
//                //updateTime();
//            }
//        });

        //nhấn nút previous song
//        btnPrevSong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                position--;
//                if (position < 0) {
//                    position = arraySong.size() - 1;
//                }
//                if (mediaPlayer.isPlaying()) {
//                    //nếu đang phát nhạc -> chuyển bài sẽ tự động phát
//                    mediaPlayer.stop();
//                    MusicActivity.this.initMediaPlayer();
//                    mediaPlayer.start();
//                    btnPlay.setImageResource(R.drawable.pause_);
//                } else {
//                    //nếu đang không phát nhạc -> chuyển bài sẽ không tự động phát
//                    MusicActivity.this.initMediaPlayer();
//                    btnPlay.setImageResource(R.drawable.play);
//                }
//                //updateTime();
//            }
//        });

        //tua tới 10s
//        btnNext10s.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000); //cộng thêm 10000ms (10s)
//                updateTime();
//            }
//        });

        //tua lui 10s
//        btnPrev10s.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000); //trừ đi 10000ms (10s)
//                updateTime();
//            }
//        });

        //nhấn nút volume
        btnVolumeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (volumeBar.getProgress() != 0) {
                    MusicActivity.this.mute();
                } else {    //thanh âm lượng đang ở mức 0 thì unmute
                    MusicActivity.this.unMute();
                }
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

    public void prepareMusic() {
        String url = arraySong.getLinkSong();
        Toast.makeText(this, "Loading song...", Toast.LENGTH_LONG).show();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Picasso.with(MusicActivity.this ).load(arraySong.getImageSong()).into(coverArt);
                    txtArtist.setText(arraySong.getArtistSong());
                    txtTitle.setText(arraySong.getNameSong());
                    txtTotalTime.setText(arraySong.getTimeSong());
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause_);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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

//    private void updateTime() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
//                txtElapsedTime.setText(timeFormat.format(mediaPlayer.getCurrentPosition()));
//                progressBar.setProgress(mediaPlayer.getCurrentPosition());
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        position++;
//                        if (position > arraySong.size() - 1) {
//                            position = 0;
//                        }
//                        if (mediaPlayer.isPlaying()) {
//                            mediaPlayer.stop();
//                        }
//                        initMediaPlayer();
//                        mediaPlayer.start();
//                        btnPlay.setImageResource(R.drawable.pause_);
//                        updateTime();
//                    }
//                });
//                handler.postDelayed(this, 100);
//            }
//        }, 100);
//    }

    private void setTotalTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        txtTotalTime.setText(timeFormat.format(mediaPlayer.getDuration()));
        progressBar.setMax(mediaPlayer.getDuration());
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void mapping() {
        coverArt = findViewById(R.id.thumbnail);
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