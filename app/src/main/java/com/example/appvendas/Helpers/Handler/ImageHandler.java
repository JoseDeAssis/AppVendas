package com.example.appvendas.Helpers.Handler;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHandler {
    Context context;

    public ImageHandler(Context context){
        this.context = context;
    }

    public RoundedBitmapDrawable getRoundPicture(Long id) throws IOException {
        Bitmap picture = getProductPic(id);
        RoundedBitmapDrawable roundedBitmapDrawable = setRoundPicture(picture);
        return roundedBitmapDrawable;
    }

    public Bitmap getProductPic (Long id){
        Bitmap photo = null;
        File picture = new File (context.getFilesDir(), id.toString());
        if (picture.exists()){
            photo = BitmapFactory.decodeFile(picture.toString());
        }
        return photo;
    }

    public static RoundedBitmapDrawable setRoundPicture(Bitmap picture){
        RoundedBitmapDrawable roundedBitmapDrawable;
        Bitmap bitmapAux = Bitmap.createScaledBitmap(picture, picture.getWidth(), picture.getHeight(), false);
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), bitmapAux);
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }

    public void savePicture(Bitmap picture, String picName) throws IOException{
        File file = new File( context.getFilesDir(), picName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        picture.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
