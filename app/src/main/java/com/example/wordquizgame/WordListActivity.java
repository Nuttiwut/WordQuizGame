package com.example.wordquizgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {

    private List<Person> mPersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        ListView lv = findViewById(R.id.list_view);

        Person p = new Person("Name","000-000-000",R.drawable.ic_easy);
        mPersonList.add(p);

        mPersonList.add(
                new Person("Name222111","111-222-333", R.drawable.ic_hard)
        );

        PersonAdapter adapter = new PersonAdapter(
                WordListActivity.this,
                R.layout.item_word,
                mPersonList
        );

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = mPersonList.get(position).getName();
                Toast.makeText(WordListActivity.this, name, Toast.LENGTH_SHORT).show();

                Person p = mPersonList.get(position);
                String personJson = new Gson().toJson(p);

                Intent intent = new Intent(WordListActivity.this, WordDetailActivity.class);
                intent.putExtra("person",personJson);
                startActivity(intent);
            }
        });
    }

    private static class PersonAdapter extends ArrayAdapter<Person> {

        private Context mContext;
        private int mResource;
        private List<Person> mPersonList;

        public PersonAdapter(Context context, int resource, List<Person> objects) {
            super(context, resource, objects);

            this.mContext = context;
            this.mResource = resource;
            this.mPersonList = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

            View view = convertView;
            if (view == null) {
                assert inflater != null;
                inflater.inflate( mResource,parent,false);
            }

            Person p = mPersonList.get(position);

            TextView nameTextView = view.findViewById(R.id.name_text_view);
            nameTextView.setText(p.getName());

            TextView phoneTextView = view.findViewById(R.id.phone_number_text_view);
            phoneTextView.setText(p.getPhoneNumber());

            ImageView imageView = view.findViewById(R.id.image_view);
            imageView.setImageResource(p.getPicture());

            return view;
        }
    }


    public static class Person {
        private String mName;
        private String mPhoneNumber;
        private int mPicture;

        public Person(String name, String phoneNumber, int picture) {
            this.mName = name;
            this.mPhoneNumber = phoneNumber;
            this.mPicture = picture;
        }

        public String getName() {
            return mName;
        }

        public String getPhoneNumber() {
            return mPhoneNumber;
        }

        public int getPicture() {
            return mPicture;
        }
    }
}
