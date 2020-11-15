package com.example.appmusiconline.Service;

import com.example.appmusiconline.Model.Album;
import com.example.appmusiconline.Model.AlbumAndArtist;
import com.example.appmusiconline.Model.PersonalAlbum;
import com.example.appmusiconline.Model.PersonalPlaylist;
import com.example.appmusiconline.Model.PersonalSong;
import com.example.appmusiconline.Model.SongAndArtist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET ("album_hottrend.php")
    Call<List<AlbumAndArtist>> getDataAlbum();
    @GET("song_hottrend.php")
    Call<List<SongAndArtist>> getSong();
    @GET("personal_song.php")
    Call<List<PersonalSong>> getPersonalSong();

    @GET("personal_album.php")
    Call<List<PersonalAlbum>> getPersonalAlbum();

    @GET ("personal_playlist.php")
    Call<List<PersonalPlaylist>> getPersonalPlaylist();

    @GET("explorer_playlist.php")
    Call<List<PersonalPlaylist>> getExplorerPlaylist();


    @GET("explorer_song.php")
    Call<List<PersonalSong>> getExplorerSong();


}
