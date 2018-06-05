package io.project302.popularmovies.utilites;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.project302.popularmovies.Movie;

public class JsonUtils {
    /**
     * This method parses JSON from a web response and returns an array of Strings describing
     * movie information.
     *
     * @param movieJsonStr JSON response from the server
     *
     * @return Array of Strings describing the movie data
     *
     * @throws JSONException if JSON data cannot be properly parsed
     */

    public static List<Movie> getSimpleMovieStringsFromJson(Context context, String movieJsonStr) throws JSONException {

        final String TAG = JsonUtils.class.getSimpleName();

        /* Movie information. */
        final String TMDB_RESULTS = "results";

        final String TMDB_TITLE = "title";
        final String TMDB_POSTER = "poster_path";
        final String TMDB_SYNOPSIS = "overview";
        final String TMDB_RATING = "vote_average";
        final String TMDB_RELEASE_DATE = "release_date";


        /* String array to hold each movies data String */
        List<Movie> parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray movieArray = movieJson.getJSONArray(TMDB_RESULTS);
        Log.d(TAG, "movieArray.length(): " + movieArray.length());

        parsedMovieData = new ArrayList<>();

        for (int i = 0; i < movieArray.length(); i++) {
            Log.d(TAG, " ");
            Log.d(TAG, "-== NEW MOVIE ==-");
            String title;
            String moviePoster;
            String synopsis;
            String rating;
            String releaseDate;

            JSONObject movieDetail = movieArray.getJSONObject(i);

            title = movieDetail.getString(TMDB_TITLE);
            moviePoster = movieDetail.getString(TMDB_POSTER);
            synopsis = movieDetail.getString(TMDB_SYNOPSIS);
            Log.d(TAG, "synopsis: " + synopsis);
            rating = movieDetail.getString(TMDB_RATING);
            releaseDate = movieDetail.getString(TMDB_RELEASE_DATE);

            String jsonMovieDescription = title + " :: " + moviePoster + " :: " + synopsis + " :: " +
                    rating + " :: " + releaseDate;
            Log.d(TAG, jsonMovieDescription);
            Log.d(TAG, "-== END ==-");
            Log.d(TAG, " ");

            parsedMovieData.add(new Movie(title, moviePoster, synopsis, rating, releaseDate));
        }
        return parsedMovieData;
    }
}
