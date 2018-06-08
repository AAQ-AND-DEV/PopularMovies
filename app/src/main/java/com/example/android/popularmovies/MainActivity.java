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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Movies>>, MoviesAdapter.ListItemClickListener {

    private TextView mEmptyView;
    RecyclerView mRecyclerView;
    MoviesAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Movies> mMoviesList;
    private static final int MOVIE_LOADER_0 = 0;
    private static final int MOVIE_LOADER_1 = 1;

    // URL to query the movie database
    private static final String MOVIE_REQUEST_URL =
            "http://api.themoviedb.org/3/movie/popular?&api_key=36bfad9d0ba02dad9b3c2c167b27d286";

    private static final String MOVIE_HIGH_RATED_URL =
            "http://api.themoviedb.org/3/movie/top_rated?&api_key=36bfad9d0ba02dad9b3c2c167b27d286";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data to populate the RecyclerView with
        // this needs to come from the json eventually
        /*mArrayList = new ArrayList<>();
        mArrayList.add(R.drawable.sample_0);
        mArrayList.add(R.drawable.sample_1);
        mArrayList.add(R.drawable.sample_2);
        mArrayList.add(R.drawable.sample_3);
        mArrayList.add(R.drawable.sample_4);
        mArrayList.add(R.drawable.sample_5);
        mArrayList.add(R.drawable.sample_6);
        mArrayList.add(R.drawable.sample_7);*/

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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.highest_rated_item:
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(MOVIE_LOADER_0, null, this);
                Toast toast = Toast.makeText(this, "You clicked highest rated", Toast.LENGTH_SHORT);
                toast.show();
                //newGame();
                return true;
            case R.id.most_popular_item:
                LoaderManager loaderManager2 = getLoaderManager();
                loaderManager2.initLoader(MOVIE_LOADER_1, null, this);
                Toast toast2 = Toast.makeText(this, "You clicked most popular", Toast.LENGTH_SHORT);
                toast2.show();
                //showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle bundle) {

        if (id == 0) {
            MoviesLoader movies = new MoviesLoader(this, MOVIE_REQUEST_URL);
        } else {
            MoviesLoader movies = new MoviesLoader(this, MOVIE_HIGH_RATED_URL);
        }
        // Create a new loader for the given URL
        return movies;
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No movies available!"
        mEmptyView.setText(R.string.no_movies);

        switch (loader.getId()) {
            case 0:
                if (movies != null && !movies.isEmpty()) {
                    mMoviesList.addAll(movies);
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (movies != null && !movies.isEmpty()) {
                    mMoviesList.addAll(movies);
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }

        // If there is a valid list of {@link Movies}, then add them to the adapter's
        // data set. This will trigger the RecyclerView to update.
        /*if (movies != null && !movies.isEmpty()) {
            mMoviesList.addAll(movies);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }*/

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        mMoviesList.clear();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        /*String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        toast.show();*/
    }
}
