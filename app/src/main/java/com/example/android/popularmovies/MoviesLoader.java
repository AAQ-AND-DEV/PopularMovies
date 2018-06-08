package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {

    private static final String LOG_TAG = MoviesLoader.class.getName();

    // Query URL
    private String mUrl;

    public MoviesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies.
        List<Movies> movies = Utils.fetchMovieData(mUrl);
        return movies;
    }
}
