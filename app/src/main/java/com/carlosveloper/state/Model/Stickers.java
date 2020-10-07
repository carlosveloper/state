package com.carlosveloper.state.Model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class Stickers {
    String urlImage;
    String tituloImage;
    public Bitmap bitmapImage;

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public Stickers(String urlImage, String tituloImage) {
        this.urlImage = urlImage;
        this.tituloImage = tituloImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTituloImage() {
        return tituloImage;
    }

    public void setTituloImage(String tituloImage) {
        this.tituloImage = tituloImage;
    }
}
