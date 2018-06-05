package io.project302.popularmovies.utilites;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import io.project302.popularmovies.BuildConfig;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String POPULAR_MOVIE_URL = "http://api.themoviedb.org/3/movie/popular";
    private static final String TOP_RATED_MOVIE_URL = "http://api.themoviedb.org/3/movie/top_rated";

    private static final String MOVIE_BASE_URL = POPULAR_MOVIE_URL;
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p";

    private static String api_key = BuildConfig.MY_API_KEY;

    final static String API_PARAM = "api_key";


    public static URL buildUrl(String sortQuery) {
        Log.d(TAG, "SORT QUERY: " + sortQuery);
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(API_PARAM, api_key)
                    .build();

        if (sortQuery.equals("top_rated")) {
            builtUri = Uri.parse(TOP_RATED_MOVIE_URL).buildUpon()
                    .appendQueryParameter(API_PARAM, api_key)
                    .build();
        }

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URL: " + url);

        return url;
    }

    public static URL buildPosterUrl(String posterPath) {
        Log.d(TAG, "Movie Poster Path: " + posterPath);
        Uri builtUri = Uri.parse(POSTER_BASE_URL).buildUpon()
                .appendPath("w185")
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URL: " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
