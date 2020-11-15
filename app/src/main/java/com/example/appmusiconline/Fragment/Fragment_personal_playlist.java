package com.example.appmusiconline.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appmusiconline.Adapter.PersonalAlbumAdapter;
import com.example.appmusiconline.Adapter.PersonalPlaylistAdapter;
import com.example.appmusiconline.Model.PersonalAlbum;
import com.example.appmusiconline.Model.PersonalPlaylist;
import com.example.appmusiconline.R;
import com.example.appmusiconline.Service.APIService;
import com.example.appmusiconline.Service.DataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_personal_playlist extends Fragment {

    View view ;
    ListView lvPersonalPlaylist ;
    PersonalPlaylistAdapter adapter ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_playlist , container , false );
        mapping();
        getPersonalPlaylist();
        return view ;
    }

    private void getPersonalPlaylist() {
        DataService dataService = APIService.getService();
        Call<List<PersonalPlaylist>> callback = dataService.getPersonalPlaylist();

        callback.enqueue(new Callback<List<PersonalPlaylist>>() {
            @Override
            public void onResponse(Call<List<PersonalPlaylist>> call, Response<List<PersonalPlaylist>> response) {

                ArrayList<PersonalPlaylist> playlistArrayList = (ArrayList<PersonalPlaylist>) response.body();
                Collections.shuffle(playlistArrayList);
                adapter = new PersonalPlaylistAdapter(getActivity(), R.layout.dong_playlist_personal, playlistArrayList);
                lvPersonalPlaylist.setAdapter(adapter);
                Log.d("bbb", "Sucesss!");


            }

            @Override
            public void onFailure(Call<List<PersonalPlaylist>> call, Throwable t) {
                Log.d("bbb", "Loi !!!");

            }


        });
    }

    private void mapping() {
        lvPersonalPlaylist = view.findViewById(R.id.lvPersonalPlaylist);
    }
}
