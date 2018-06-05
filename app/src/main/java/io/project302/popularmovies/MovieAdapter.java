package io.project302.popularmovies;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler{
//        void onClick(String movie);
        void onClick(Parcelable movie);
    }

    public MovieAdapter(/*List<Movie> movieList,*/ MovieAdapterOnClickHandler clickHandler) {
        //this.mMovieData = movieList;
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieImageView;

        MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = view.findViewById(R.id.iv_movie_preview);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
//            String movieDetail = mMovieData.get(adapterPosition).getTitle();
            Parcelable movieDetail = mMovieData.get(adapterPosition);
            mClickHandler.onClick(movieDetail);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_grid_item, parent, false);

        int gridColsNum = view.getContext().getResources().getInteger(R.integer.grid_number_cols);
        view.getLayoutParams().height = (int) (parent.getWidth() / gridColsNum *
                Movie.POSTER_ASPECT_RATIO);
        Log.d(TAG, "poster height: " + view.getLayoutParams().height);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String posterPath = mMovieData.get(position).getMoviePoster();
        Log.d(TAG, "poster path: " + posterPath);
        Context context = holder.mMovieImageView.getContext();
        Log.d(MovieAdapter.class.getSimpleName(), "context: " + context);
        Picasso.with(context).load(posterPath).into(holder.mMovieImageView);
        //holder.mMovieImageView.setText(animal);
    }

    @Override
    public int getItemCount() {
        //Log.d(MovieAdapter.class.getSimpleName(), "mMovieData: " + mMovieData.size());
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }

    String getItem(int id) {
        return mMovieData.get(id).getTitle();
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        Log.d(TAG, "mMovieData SIZE: " + mMovieData.size());
        notifyDataSetChanged();
        Log.d(TAG, "setMovieData called");
    }
}
