package com.example.wordquizgame;

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

    private List<Word> mWordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        }

        for (Word w : mWordList) {
            Log.i(TAG, "name: " + w.getName() + ", picture: " + w.getPicture());
        }

        Random random = new Random();
        int randomIndex = random.nextInt(mWordList.size());

        final Word answerWord = mWordList.get(randomIndex);
        Drawable drawable = answerWord.getPictureDrawable(GameActivity.this);

        ImageView questionImageView = findViewById(R.id.question_image_view);
        questionImageView.setImageDrawable(drawable);

        TableLayout table = findViewById(R.id.choices_table_layout);
//        TableRow tr =(TableRow) table.getChildAt(0);

        for (int row = 0; row < 3; row++){
            TableRow tr = (TableRow) table.getChildAt(row);

            for (int i = 0; i < 2; i++) {
                randomIndex = random.nextInt(mWordList.size());
                Word choiceWord = mWordList.get(randomIndex);

                Button choiceButton = new Button(GameActivity.this);
                choiceButton.setText(choiceWord.getName());
                tr.addView(choiceButton);

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
                                    //วนกลับไปสุ่มคำถามใหม่
                                    

                                }
                            }, 2000);
                        } else {
                            feedbackTextView.setText("ผิดนะครับ");
                        }

                    }
                });
            }
        }




    }
}
