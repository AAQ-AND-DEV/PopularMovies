package com.example.android.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

public class MoviesPreferences {


    public static String getPreferredSortOrder(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForMostPopular = context.getString(R.string.pref_most_popular_url);
        String keyForHighestRated = context.getString(R.string.pref_highest_rated_url);

        return prefs.getString(keyForMostPopular, keyForHighestRated);
    }
}
