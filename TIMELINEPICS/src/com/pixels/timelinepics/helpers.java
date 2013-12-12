package com.pixels.timelinepics;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class helpers {

    public static String getDlDir(Context c) {
        String configFolder = getResourceString(c, R.string.config_wallpaper_download_loc);
        if (configFolder != null && !configFolder.isEmpty()) {
            return new File(Environment.getExternalStorageDirectory(), configFolder)
                    .getAbsolutePath() + "/";
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }

    public static String getSvDir(Context c) {
        String configFolder = getResourceString(c, R.string.config_wallpaper_sdcard_dl_location);
        if (configFolder != null && !configFolder.isEmpty()) {
            return new File(Environment.getExternalStorageDirectory(), configFolder)
                    .getAbsolutePath() + "/";
        } else {
            return null;
        }
    }

    public String getWallpaperDestinationPath(Context c) {
        String configFolder = getResourceString(c, R.string.config_wallpaper_sdcard_dl_location);
        if (configFolder != null && !configFolder.isEmpty()) {
            return new File(Environment.getExternalStorageDirectory(), configFolder)
                    .getAbsolutePath();
        }
        // couldn't find resource?
        return null;
    }

    public static String getResourceString(Context c, int id) {
        return c.getResources().getString(id);
    }

}