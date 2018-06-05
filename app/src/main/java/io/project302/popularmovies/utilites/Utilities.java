package io.project302.popularmovies.utilites;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class Utilities {
    public static int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.d(Utilities.class.getSimpleName(), "dpWidth: " + dpWidth);
        int numberOfColumns = (int) (dpWidth / 180);
        Log.d(Utilities.class.getSimpleName(), "numberOfColumns: " + numberOfColumns);
        return numberOfColumns;
    }
}
