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

public class TopRatedPopularAdapter extends RecyclerView.Adapter<TopRatedPopularAdapter.MyViewHolder> {

    private List<MovieResult> movieResultMovies;
    private LayoutInflater layoutInflater;
    private MovieApiService apiService;
    private OnClickItemListener listener;

    public TopRatedPopularAdapter(Context context, List<MovieResult> movieResultMovies) {
        this.movieResultMovies = movieResultMovies;
        this.layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_card_view
                , viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        MovieResult r = movieResultMovies.get(position);

        String image_url = apiService.BASE_URL_FOR_IMAGE + movieResultMovies.get(position).getPosterPath();
        Picasso.get().load(image_url).into(myViewHolder.ivPosterImage);

        myViewHolder.tvTitle.setText(r.getTitle());


    }

    @Override
    public int getItemCount() {
        return movieResultMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivPosterImage;
        private TextView tvTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemCLick(view, getAdapterPosition());
            }
        }
    }

    public void setClickListener(OnClickItemListener l) {
        this.listener = l;
    }

    // return selected movie
    public MovieResult getSelectedMovie(int index) {
        return movieResultMovies.get(index);
    }


}
