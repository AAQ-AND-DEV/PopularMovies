package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;
import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {

    private static final String LOG_TAG = MoviesLoader.class.getName();

    // Query URL
    private String mUrl;
    private String mUrlTop;

    public MoviesLoader(Context context, String url, String urlTop) {
        super(context);
        mUrl = url;
        mUrlTop = urlTop;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {

        String preferredSortOrder = MoviesPreferences.getPreferredSortOrder(getContext());

        if (preferredSortOrder =) {

            try {

                List<Movies> movies = Utils.fetchMovieData(mUrlTop);

                if (mUrlTop == null) {
                    return null;
                }

                return movies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else {

            try {

                List<Movies> movies = Utils.fetchMovieData(mUrl);

                if (mUrl == null) {
                    return null;
                }

                return movies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
