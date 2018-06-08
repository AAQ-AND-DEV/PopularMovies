package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Gson gson = new Gson();
        Intent intent = getIntent();
        String stringCurrentMovie = intent.getStringExtra("key");
        Movies currentMovie = gson.fromJson(stringCurrentMovie, Movies.class);

        // Find TextView with the ID "backdrop_iv"
        ImageView backdropView = (ImageView) findViewById(R.id.backdrop_iv);
        // Display text in TextView... samzies below
        Picasso.get().load("http://image.tmdb.org/t/p/w780/" + currentMovie.getBackdrop()).into(backdropView);

        TextView titleView = (TextView) findViewById(R.id.title_tv);
        titleView.setText(currentMovie.getOrigTitle());

        TextView releaseDateView = (TextView) findViewById(R.id.release_date_tv);
        releaseDateView.setText("Released: " + currentMovie.getReleaseDate());

        TextView ratingView = (TextView) findViewById(R.id.rating_tv);
        ratingView.setText("Viewer rating: " + currentMovie.getRating() + "/10");

        TextView synopsisView = (TextView) findViewById(R.id.synopsis_tv);
        synopsisView.setText(currentMovie.getSynopsis());
    }
}
