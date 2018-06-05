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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movies);
        //int numOfColumns = Utilities.calculateNumberOfColumns(this);
        //Log.d(MainActivity.class.getSimpleName(), "numOfColumns: " + numOfColumns);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
*/
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovies("popular");
    }

    private void loadMovies(String sortingOrder) {
        String popular = "popular";
        new FetchMoviesTask().execute(sortingOrder);
    }

    @Override
    public void onClick(Parcelable movie) {
        //Toast.makeText(this, movie, Toast.LENGTH_SHORT).show();
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
                loadMovies("popular");
                return true;

            case R.id.top_rated:
                loadMovies("top_rated");
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
            Log.d(TAG, "sort: " + sort);

            URL movieRequestUrl = NetworkUtils.buildUrl(sort);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                mMovies = JsonUtils.getSimpleMovieStringsFromJson(MainActivity.this,
                        jsonMovieResponse);

                Log.d(TAG, "mMovies.size(): " + mMovies.size());

                Log.d(TAG, "doInBackground: FINISHED SUCCESSFULLY");
                return mMovies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List movieData) {
            if (movieData != null) {
                Log.d(TAG, "movieData SIZE: " + movieData.size());
                mMovieAdapter.setMovieData(movieData);
                Log.d(TAG, "onPostExecute: FINISHED SUCCESSFULLY");
            }
        }


    }
}
