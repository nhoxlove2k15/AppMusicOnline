package com.example.appmusiconline.Service;

import com.example.appmusiconline.Model.Album;
import com.example.appmusiconline.Model.AlbumAndArtist;
import com.example.appmusiconline.Model.SongAndArtist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET ("album_hottrend.php")
    Call<List<AlbumAndArtist>> getDataAlbum();
    @GET("song_hottrend.php")
    Call<List<SongAndArtist>> getSong();
}
