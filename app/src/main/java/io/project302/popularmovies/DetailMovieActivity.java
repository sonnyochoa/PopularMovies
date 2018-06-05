package io.project302.popularmovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends AppCompatActivity {

    ImageView mDetailActivityIV;
    TextView mDetailActivityReleaseDate;
    TextView mDetailActivityTV;
    TextView mDetailActivitySynopsis;

    RatingBar mDetailActivityRatings;

    Movie movie;

    static private final String TAG = DetailMovieActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            movie = bundle.getParcelable("io.project302.popularmovies.Movie");

        // Movie Poster
        mDetailActivityIV = findViewById(R.id.detail_activity_iv);
        Context context = getApplicationContext();
        Picasso.with(context).load(movie.getMoviePoster()).into(mDetailActivityIV);

        // Release Date
        mDetailActivityReleaseDate = findViewById(R.id.detail_activity_release_date);
        mDetailActivityReleaseDate.setText(movie.getReleaseDate());

        // Ratings
        mDetailActivityRatings = findViewById(R.id.detail_activity_ratings);
        mDetailActivityRatings.setRating(Float.valueOf(movie.getRating())/2);

        // Movie Title
        mDetailActivityTV = findViewById(R.id.detail_activity_title);
        mDetailActivityTV.setText(movie.getTitle());

        // Movie Synopsis
        mDetailActivitySynopsis = findViewById(R.id.detail_activity_synopsis);
        mDetailActivitySynopsis.setText(movie.getSynopsis());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
