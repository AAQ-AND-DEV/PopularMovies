package com.example.android.popularmovies;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {

    private static final String LOG_TAG = MoviesLoader.class.getName();

    // Query URL
    private String mUrlTopRated;
    private String mUrl;


    public MoviesLoader(Context context, String urlTopRated, String url) {
        super(context);
        mUrlTopRated = urlTopRated;
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {

        String preferredSortOrder = MoviesPreferences.getPreferredSortOrder(getContext());

        switch (preferredSortOrder) {

            case "popular":
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

            case "top_rated":
                try {

                    List<Movies> movies = Utils.fetchMovieData(mUrlTopRated);

                    if (mUrlTopRated == null) {
                        return null;
                    }

                    return movies;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            default:
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
