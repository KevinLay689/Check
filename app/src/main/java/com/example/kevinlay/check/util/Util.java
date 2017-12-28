package com.example.kevinlay.check.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by kevinlay on 12/27/17.
 */

public class Util {

    public static Bitmap getProfileImage(int sampleSize, String image) {
//                    byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
//                    circleImageView.setImageBitmap(decodedByte);
//                    decodedByte = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        InputStream is = new ByteArrayInputStream(decodedString);
        Bitmap decodedByte = BitmapFactory.decodeStream(is, null, options);
        Bitmap.createScaledBitmap(decodedByte, 100, 100, false);

        return decodedByte;
    }
}
