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

import com.example.appmusiconline.Adapter.PersonalSongAdapter;
import com.example.appmusiconline.Model.PersonalSong;
import com.example.appmusiconline.R;
import com.example.appmusiconline.Service.APIService;
import com.example.appmusiconline.Service.DataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_personal_song extends Fragment {
    View view ;
    ListView lvPersonalSong ;

    PersonalSongAdapter adapter ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_song , container , false ) ;
        mapping () ;
        getPersonalSong ();
        return view ;
    }
    private void getPersonalSong() {
        DataService dataService = APIService.getService();
        Call<List<PersonalSong>> callback = dataService.getPersonalSong();

        callback.enqueue(new Callback<List<PersonalSong>>() {
            @Override
            public void onResponse(Call<List<PersonalSong>> call, Response<List<PersonalSong>> response) {

                ArrayList<PersonalSong> songArrayList = (ArrayList<PersonalSong>) response.body();
                Collections.shuffle(songArrayList);
                adapter = new PersonalSongAdapter(getActivity(), R.layout.dong_song_personal, songArrayList);
                lvPersonalSong.setAdapter(adapter);
                Log.d("bbb", "Sucesss!");


            }

            @Override
            public void onFailure(Call<List<PersonalSong>> call, Throwable t) {
                Log.d("bbb", "Loi !!!");

            }


        });
    }

    private void mapping() {
       lvPersonalSong = (ListView) view.findViewById(R.id.lvPersonalSong) ;
    }
}
