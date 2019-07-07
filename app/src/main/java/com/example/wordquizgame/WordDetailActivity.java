package com.example.wordquizgame;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class WordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Intent intent = getIntent();
        String personJson = intent.getStringExtra("person");
        WordListActivity.Person p = new Gson().fromJson(personJson, WordListActivity.Person.class);

        TextView nameTextView = findViewById(R.id.name_text_view);
        nameTextView.setText(p.getName());

        ActionBar ad = getSupportActionBar();
        ad.setTitle(p.getName());

        TextView phoneTextView = findViewById(R.id.phone_text_view);
        phoneTextView.setText(p.getPhoneNumber());

        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setImageResource(p.getPicture());

    }
}
