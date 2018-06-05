package io.project302.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.net.URL;
import java.util.List;

import io.project302.popularmovies.utilites.JsonUtils;
import io.project302.popularmovies.utilites.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovies;

    private final String DEFAULT_MOVIE_SORTING = "popular";
    private final String TOP_RATED = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovies(DEFAULT_MOVIE_SORTING);
    }

    private void loadMovies(String sortingOrder) {
        new FetchMoviesTask().execute(sortingOrder);
    }

    @Override
    public void onClick(Parcelable movie) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("io.project302.popularmovies.Movie", movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sorting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                loadMovies(DEFAULT_MOVIE_SORTING);
                return true;

            case R.id.top_rated:
                loadMovies(TOP_RATED);
                return true;

            default:
                return true;
        }
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, List> {

        private final String TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {

            if (params.length == 0) return null;

            String sort = params[0];

            URL movieRequestUrl = NetworkUtils.buildUrl(sort);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                mMovies = JsonUtils.getSimpleMovieStringsFromJson(MainActivity.this,
                        jsonMovieResponse);
                return mMovies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List movieData) {
            if (movieData != null) {
                mMovieAdapter.setMovieData(movieData);
            }
        }


    }
}
