package hr.franjkovic.ivan.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hr.franjkovic.ivan.movies.R;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.rest.MovieApiService;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.MyViewHolder> {

    private List<MovieResult> similarMovies;
    private LayoutInflater inflater;
    private MovieApiService movieApiService;
    private OnClickItemListener itemListener;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_similar_movies
                , viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        MovieResult r = similarMovies.get(i);

        // loading movie poster
        String image_url = movieApiService.BASE_URL_FOR_IMAGE + r.getPosterPath();
        myViewHolder.ivSMoviePoster.setMaxHeight(128);
        Picasso.get().load(image_url).into(myViewHolder.ivSMoviePoster);

        myViewHolder.tvSMovieTitle.setText(r.getTitle() + "\n" + r.getReleaseDate().substring(0, 4));
    }

    @Override
    public int getItemCount() {
        return similarMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivSMoviePoster;
        private TextView tvSMovieTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSMoviePoster = itemView.findViewById(R.id.ivSMoviePoster);
            tvSMovieTitle = itemView.findViewById(R.id.tvSMovieTitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemCLick(view, getAdapterPosition());
            }
        }
    }

    public void setClickListener(OnClickItemListener l) {
        this.itemListener = l;
    }

    public SimilarMoviesAdapter(Context context, List<MovieResult> similarMovies) {
        this.similarMovies = similarMovies;
        this.inflater = inflater.from(context);

    }

    public MovieResult getSelectedMovie(int index) {
        return similarMovies.get(index);
    }
}
