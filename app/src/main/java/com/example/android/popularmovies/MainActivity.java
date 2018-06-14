package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Movies>>, MoviesAdapter.ListItemClickListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private TextView mEmptyView;
    RecyclerView mRecyclerView;
    MoviesAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Movies> mMoviesList;
    private static final int MOVIE_LOADER_0 = 0;
    private static final int MOVIE_LOADER_1 = 1;
    android.support.v4.app.LoaderManager.LoaderCallbacks<Movies> myLoaderCallbacks;

    // URL to query the movie database
    private static final String MOVIE_MOST_POPULAR_URL =
            "[most popular endpoint]";

    private static final String MOVIE_HIGH_RATED_URL =
            "[highest-rated endpoint]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Then initialize the empty state
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mRecyclerView.setHasFixedSize(true);

        int numberOfColumns = 2;
        mLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMoviesList = new ArrayList<>();
        mAdapter = new MoviesAdapter(this, mMoviesList, this);

        mRecyclerView.setAdapter(mAdapter);

        // Instead of just a loader manager we check for network connectivity
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIE_LOADER_0, null, this);
            loaderManager.initLoader(MOVIE_LOADER_1, null, this);
            mEmptyView.setVisibility(View.GONE);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.highest_rated_item:
                item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        getSupportLoaderManager().restartLoader(MOVIE_LOADER_1, null, myLoaderCallbacks);
                        return true;
                    }
                });

            case R.id.most_popular_item:

                item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        getSupportLoaderManager().restartLoader(MOVIE_LOADER_0, null, myLoaderCallbacks);
                        return true;
                    }
                });

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle bundle) {

        MoviesLoader movies = null;
        movies = new MoviesLoader(this, MOVIE_MOST_POPULAR_URL);
        return movies;
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (movies != null && !movies.isEmpty()) {
            mMoviesList.addAll(movies);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }

        // Set empty state text to display "No movies available!"
        mEmptyView.setText(R.string.no_movies);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        mMoviesList.clear();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        // do nothing? Handled by Adapter.
    }
}
