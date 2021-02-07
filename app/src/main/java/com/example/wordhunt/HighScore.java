package com.example.wordhunt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class HighScore extends AppCompatActivity {

    TextView n1, n2, n3, n4, n5, s1, s2, s3, s4, s5;
    FirebaseDatabase db;
    DatabaseReference ref;
    public Dialog highScoreWindow;
    public List<Score>scoreList;
    private SortedMap<Integer, String> map = new TreeMap<Integer, String>(Collections.reverseOrder());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        highScoreWindow = new Dialog(this);
        highScoreWindow.setContentView(R.layout.highscore_pop_up);

        n1 = findViewById(R.id.name1);
        n2 = findViewById(R.id.name2);
        n3 = findViewById(R.id.name3);
        n4 = findViewById(R.id.name4);
        n5 = findViewById(R.id.name5);
        s1 = findViewById(R.id.score1);
        s2 = findViewById(R.id.score2);
        s3 = findViewById(R.id.score3);
        s4 = findViewById(R.id.score4);
        s5 = findViewById(R.id.score5);
        scoreList = new ArrayList<>();

        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Score");

        ref.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for(DataSnapshot dataSnapshot : snapshot.getChildren())
                  {
                      try{

                          scoreList.add(dataSnapshot.getValue(Score.class));
                      }catch (Exception e){
                          System.out.println(e);
                      }
                  }


                  int x = scoreList.size();

                  for(int i =0; i < x ; i++)
                  {
                      int s = Integer.parseInt(scoreList.get(i).getScore());
                      map.put(s, scoreList.get(i).getName());
                      //Toast.makeText(getApplicationContext(),scoreList.get(i).getName(),Toast.LENGTH_SHORT).show();
                  }


                  int j = -1;
                  TextView namet;
                  TextView scoret;
                  Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
                  while (iterator.hasNext())
                  {
                      Map.Entry<Integer, String> entry = iterator.next();
                      j++;
                      if(j == 0)
                      {
                          namet = highScoreWindow.findViewById(R.id.name1);
                          scoret = highScoreWindow.findViewById(R.id.score1);
                          String n = entry.getValue();
                          String s = entry.getKey().toString();
                          namet.setText(n);
                          scoret.setText(s);
                      }
                      if(j == 1)
                      {
                          namet = highScoreWindow.findViewById(R.id.name2);
                          scoret = highScoreWindow.findViewById(R.id.score2);
                          String n = entry.getValue();
                          String s = entry.getKey().toString();
                          namet.setText(n);
                          scoret.setText(s);
                      }
                      if(j == 2)
                      {
                          namet = highScoreWindow.findViewById(R.id.name3);
                          scoret = highScoreWindow.findViewById(R.id.score3);
                          String n = entry.getValue();
                          String s = entry.getKey().toString();
                          namet.setText(n);
                          scoret.setText(s);
                      }
                      if(j == 3)
                      {
                          namet = highScoreWindow.findViewById(R.id.name4);
                          scoret = highScoreWindow.findViewById(R.id.score4);
                          String n = entry.getValue();
                          String s = entry.getKey().toString();
                          namet.setText(n);
                          scoret.setText(s);
                      }
                      if(j == 4)
                      {
                          namet = highScoreWindow.findViewById(R.id.name5);
                          scoret = highScoreWindow.findViewById(R.id.score5);
                          String n = entry.getValue();
                          String s = entry.getKey().toString();
                          namet.setText(n);
                          scoret.setText(s);
                      }
                      if(j == 4)
                      {
                          break;
                      }
                  }
                  String ga = "ga";
                  System.out.println(ga);
                  highScoreWindow.show();


              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });
                //Toast.makeText(getApplicationContext(), Integer.toString(scoreList.size()), Toast.LENGTH_SHORT).show();


        /**/


        /*Map.Entry<Integer,String> entry = map.entrySet().stream().findFirst().get();
        namet.setText(entry.getValue());
        scoret.setText(entry.getKey());
       */


    }


}