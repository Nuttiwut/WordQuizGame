package com.example.wordquizgame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wordquizgame.db.MyDatabaseHelper;
import com.example.wordquizgame.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.wordquizgame.db.MyDatabaseHelper.COL_NAME;
import static com.example.wordquizgame.db.MyDatabaseHelper.COL_PICTURE;
import static com.example.wordquizgame.db.MyDatabaseHelper.TABLE_NAME_WORD;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getName();
    static final String KEY_DIFF = "name";
    private int randomImage;



    private List<Word> mWordList = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //final Intent intent = getIntent();
        //String name = intent.getStringExtra(KEY_DIFF);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(GameActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query(TABLE_NAME_WORD, null, null, null, null, null, null, null);
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex(COL_NAME));
            String picture = c.getString(c.getColumnIndex(COL_PICTURE));

            Word word = new Word(name, picture);
            mWordList.add(word);
            Log.i(TAG,"Number of word: " + mWordList.size());

        }

        for (Word w : mWordList) {
            Log.i(TAG, "name: " + w.getName() + ", picture: " + w.getPicture());
        }
        Random random = new Random();
        int randomIndex;

        Word[] choiceWord = new Word[mWordList.size()];
        Log.i(TAG,"mWordList Size : "+ mWordList.size());

        int countRandom = mWordList.size();

        //Random
        for (int i = 0; i < countRandom; i++) {
            randomIndex = random.nextInt(mWordList.size());
            choiceWord[i] = mWordList.get(randomIndex);
            Log.i(TAG,"Word random : "+ choiceWord[i]);
            mWordList.remove(randomIndex);
        }
        //รับค่าจากหน้าแรก
        int levelDifficult = 0;
        Intent intent = getIntent();
        String difficultLevel = intent.getStringExtra(KEY_DIFF);
        Log.i(TAG,""+ difficultLevel);


        assert difficultLevel != null;
        switch (difficultLevel) {
            case "Easy": levelDifficult = 1;
                randomImage = random.nextInt(1);
                break;
            case "Medium": levelDifficult = 2;
                randomImage = random.nextInt(3);
                break;
            case "Hard": levelDifficult = 3;
                randomImage = random.nextInt(3);
        }

        final Word answerWord = choiceWord[randomImage];
        Drawable drawable = answerWord.getPictureDrawable(GameActivity.this);

        ImageView questionImageView = findViewById(R.id.question_image_view);
        questionImageView.setImageDrawable(drawable);

        TableLayout table = findViewById(R.id.choices_table_layout);
//        TableRow tr =(TableRow) table.getChildAt(0);


        //แถวของคำตอบ
        int rowAnswer = 0;
        for (int row = 0; row < levelDifficult; row++){
            TableRow tr = (TableRow) table.getChildAt(row);
            //คอลั่มของคำตอบ
            for (int i = 0; i < 2; i++) {

//                randomIndex = random.nextInt(mWordList.size());
//                Word choiceWord = mWordList.get(randomIndex);
//

                Button choiceButton = new Button(GameActivity.this);
                choiceButton.setText(choiceWord[rowAnswer].getName());
                tr.addView(choiceButton);

                Log.i(TAG,"" +rowAnswer);
                rowAnswer++;

                choiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button button = (Button) view;
                        String choiceWordName = button.getText().toString();

                        TextView feedbackTextView = findViewById(R.id.feedback_text_view);

                        if (answerWord.getName().equals(choiceWordName)) {
                            feedbackTextView.setText("ถูกต้องนะครับ");

                            //หน่วงเวลา
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();

                                    startActivity(intent);


                                    //วนกลับไปสุ่มคำถามใหม่


                                }
                            }, 2000);
                        } else {
                            feedbackTextView.setText("ผิดนะครับ");
                        }

                    }
                });
//                mWordList.remove(randomIndex);
            }
        }




    }
}
