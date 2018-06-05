package io.project302.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import io.project302.popularmovies.utilites.NetworkUtils;

public class Movie implements Parcelable {

    private static final String TAG = Movie.class.getSimpleName();

    public static final float POSTER_ASPECT_RATIO = 1.5f;

    private String title;
    private String moviePoster;
    private String synopsis;
    private String rating;
    private String releaseDate;

    public Movie(String title, String poster, String synopsis, String rating, String releaseDate) {
        this.title = title;
        setPosterPath(poster);
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        title = in.readString();
        moviePoster = in.readString();
        synopsis = in.readString();
        rating = in.readString();
        releaseDate = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private void setPosterPath(String posterPath) {
        moviePoster = NetworkUtils.buildPosterUrl(posterPath).toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(moviePoster);
        dest.writeString(synopsis);
        dest.writeString(rating);
        dest.writeString(releaseDate);
    }

    static public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}
