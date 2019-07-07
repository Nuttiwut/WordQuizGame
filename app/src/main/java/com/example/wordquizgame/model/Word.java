package com.example.wordquizgame.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class Word {

    private String mName;
    private String mPicture;

    public Word(String name, String picture) {
        this.mName = name;
        this.mPicture = picture;
    }

    public String getName() {
        return mName;
    }

    public String getPicture() {
        return mPicture;
    }

    public Drawable getPictureDrawable(Context context){
        AssetManager am = context.getAssets();
        try {
            InputStream stream = am.open(mPicture);
            return Drawable.createFromStream(stream, null);

        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
