package hr.franjkovic.ivan.movies.movie_details.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.franjkovic.ivan.movies.movie_details.MovieDetailsContract;
import hr.franjkovic.ivan.movies.movie_details.MovieDetailsPresenter;
import hr.franjkovic.ivan.movies.R;
import hr.franjkovic.ivan.movies.adapter.OnClickItemListener;
import hr.franjkovic.ivan.movies.adapter.SimilarMoviesAdapter;
import hr.franjkovic.ivan.movies.model.GenreResult;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.rest.MovieApiService;
import hr.franjkovic.ivan.movies.tools.Keys;

public class MovieDetails extends AppCompatActivity implements OnClickItemListener, MovieDetailsContract.View {

    @BindView(R.id.ivMoviePoster)
    ImageView ivPoster;

    @BindView(R.id.llContainer)
    LinearLayout llContainer;

    @BindView(R.id.ivCover)
    ImageView ivCover;

    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;

    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;

    @BindView(R.id.tvGenres)
    TextView tvGenres;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.rvSimilarMovies)
    RecyclerView rvSimilarMovies;

    private MovieResult selectedMovie;
    private List<GenreResult> genreList;
    private List<MovieResult> similarM;
    private SimilarMoviesAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private MovieDetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);
        genreList = new ArrayList<>();
        similarM = new ArrayList<>();

        // recycler view
        rvSimilarMovies.setHasFixedSize(true);
        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSimilarMovies.setLayoutManager(manager);

        // fetching MovieResult object who presents selected movie
        if (getIntent() != null) {
            selectedMovie = getIntent().getParcelableExtra(Keys.SELECTED_MOVIE);
        }

        // movie details presenter
        if (selectedMovie != null) {
            detailsPresenter = new MovieDetailsPresenter(this, selectedMovie.getId());
            detailsPresenter.requestDataFromServer();
        }

        setMovieInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imgShowHide:
                showHideImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setBackgroundMoviePoster() {
        if (selectedMovie != null) {
            Picasso.get().load(MovieApiService.BASE_URL_FOR_IMAGE + selectedMovie.getPosterPath())
                    .into(ivPoster);
        }

        ivPoster.setAlpha(0.2f);
    }

    // showing or hiding movie poster in MovieDetails background
    private void showHideImage() {
        if (llContainer.getVisibility() == View.VISIBLE) {
            llContainer.setVisibility(View.GONE);
            showWithAnim();

        } else {
            llContainer.setVisibility(View.VISIBLE);
            ivPoster.setAlpha(0.2f);

        }
    }

    // animation when showing background movie poster
    private void showWithAnim() {
        ivPoster.setAlpha(1f);
        AlphaAnimation animation1 = new AlphaAnimation(0.1f, 1.0f);
        animation1.setDuration(650);
        animation1.setFillAfter(true);
        ivPoster.startAnimation(animation1);
    }

    private void setCoverImage() {
        if (selectedMovie != null) {
            Picasso.get().load(MovieApiService.BASE_URL_FOR_IMAGE + selectedMovie.getBackdropPath())
                    .into(ivCover);
        }

    }

    // set genre according to the obtained genre id
    private String setMovieGenres(List<GenreResult> list) {
        List<String> genres = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        if (selectedMovie != null) {
            integerList = selectedMovie.getGenreIds();
        }

        if (genreList != null) {
            for (Integer i : integerList) {
                for (GenreResult g : list) {
                    if (i.equals(g.getId())) {
                        genres.add(g.getName());
                    }
                }
            }
        }

        return genres.toString().substring(1, genres.toString().length() - 1);
    }

    private void setMovieInfo() {
        if (selectedMovie != null) {
            setBackgroundMoviePoster();
            setCoverImage();
            tvMovieTitle.setText(selectedMovie.getTitle());
            tvReleaseDate.setText(selectedMovie.getReleaseDate());
            tvOverview.setText(selectedMovie.getOverview());
        }

    }


    // sending selected movie to MovieDetails.class
    @Override
    public void onItemCLick(View v, int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra(Keys.SELECTED_MOVIE, adapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void setDataToRecyclerView(List<MovieResult> movieResults) {
        similarM.addAll(movieResults);
        adapter = new SimilarMoviesAdapter(this, similarM);
        adapter.setClickListener(this);
        rvSimilarMovies.setAdapter(adapter);
    }

    @Override
    public void setDataToTextView(List<GenreResult> genreResults) {
        genreList.addAll(genreResults);
        tvGenres.setText(setMovieGenres(genreList));
    }

    @Override
    public void onResponseFailure(Throwable t) {
        Toast.makeText(this, getString(R.string.data_error_msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailsPresenter.onDestroy();
    }
}
