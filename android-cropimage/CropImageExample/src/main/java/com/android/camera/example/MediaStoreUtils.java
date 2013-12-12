package com.android.camera.example;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public final class MediaStoreUtils {
    private MediaStoreUtils() {
    }

    public static Intent getPickImageIntent(final Context context) {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        return Intent.createChooser(intent, "Select picture");
    }

    public static String getLocalMediaPath(final Context context, final Intent intent) {
        final Uri uri = intent.getData();
        final Cursor cursor = context.getContentResolver().query(uri,
                new String[]{MediaStore.MediaColumns.DATA}, null, null, null);

        if (cursor == null) {
            throw new RuntimeException(String.format("Could not resolve file name for url: {0}",
                    uri.toString()));
        }

        cursor.moveToFirst();

        final String ret = cursor.getString(0);

        cursor.close();

        return ret;
    }
}
