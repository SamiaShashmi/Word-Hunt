package com.example.wordhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView dataList;
    List<String> letters;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = findViewById(R.id.dataList);

        letters = new ArrayList<>();
        Random r = new Random();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 64; i++) {
            char rand = alphabet.charAt(r.nextInt(alphabet.length()));
            letters.add("" + rand);
        }


        adapter = new Adapter(this, letters);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

    }

}