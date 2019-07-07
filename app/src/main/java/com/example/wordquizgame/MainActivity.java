package com.example.wordquizgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    static final String KEY_DIFF = "name";
    private final String[] diff = {"Easy", "Medium", "Hard"};

    private Button mHightScoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playGameButton = findViewById(R.id.play_game_button);

        //เซ็ตการทำงานของปุ่ม
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT);
                t.show();
                //showPlainDialog();
                showCustomDialog();


                Log.i(TAG, "Hello");

            }
        });

        //เซ็ตการทำงานของปุ่ม High Score

        mHightScoreButton = findViewById(R.id.high_score_button);
        mHightScoreButton.setOnClickListener(this);


    }

    private void showCustomDialog() {
        DifficultyOptionsAdapter adapter = new DifficultyOptionsAdapter(
                this,
                R.layout.diff_row,
                diff
        );

        new AlertDialog.Builder(MainActivity.this)
//                .setTitle("Choose Difficulty Level")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra(KEY_DIFF, which);
                        startActivity(intent);
                        Log.i(TAG, "ระดับความยาก: " + diff[which]);
                    }
                })
                .show();
    }

    private void showPlainDialog() {
        //สร้างป๊อปอับ


        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Choose Difficulty Level")
                .setItems(diff, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra(KEY_DIFF, which);
                        startActivity(intent);
                        Log.i(TAG, "ระดับความยาก " + diff[which]);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 123:
                if (resultCode == RESULT_OK) {
                    int number = data.getIntExtra("number", 0);
                    Log.i(TAG, String.valueOf(number));
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_game_button:
                showCustomDialog();
                break;

            case R.id.high_score_button:
                Intent intent = new Intent(MainActivity.this, WordListActivity.class);
                startActivity(intent);
                break;

        }
    }

    private static class DifficultyOptionsAdapter extends ArrayAdapter<String> {

        private Context mContext;
        private int mItemLayoutId;
        private String[] mDifficulties;

        DifficultyOptionsAdapter(Context context, int itemLayoutId, String[] difficulties) {
            super(context, itemLayoutId, difficulties);

            this.mContext = context;
            this.mItemLayoutId = itemLayoutId;
            this.mDifficulties = difficulties;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = convertView;
            if (itemView == null) {
                assert inflater != null;
                itemView = inflater.inflate(mItemLayoutId, parent, false);
            }

            TextView difficultyTextView = itemView.findViewById(R.id.difficulty_text_view);
            ImageView difficultyImageView = itemView.findViewById(R.id.difficulty_image_view);

            String diff = mDifficulties[position];
            difficultyTextView.setText(diff);

            if (position == 0) {
                difficultyImageView.setImageResource(R.drawable.ic_easy);
            } else if (position == 1) {
                difficultyImageView.setImageResource(R.drawable.ic_medium);
            } else if (position == 2) {
                difficultyImageView.setImageResource(R.drawable.ic_hard);
            }

            return itemView;
        }
    }
}
