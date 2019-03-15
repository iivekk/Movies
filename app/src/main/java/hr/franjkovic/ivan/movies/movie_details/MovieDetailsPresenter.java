package hr.franjkovic.ivan.movies.movie_details;

import java.util.List;

import hr.franjkovic.ivan.movies.model.GenreResult;
import hr.franjkovic.ivan.movies.model.MovieResult;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter, MovieDetailsContract.Model.SimilarMoviesModel
        .OnFinishedListener, MovieDetailsContract.Model.Genres.OnFinishedListener {

    private MovieDetailsContract.View view;
    private MovieDetailsContract.Model.SimilarMoviesModel modelSimilar;
    private MovieDetailsContract.Model.Genres modelGenres;
    private int movieId;

    public MovieDetailsPresenter(MovieDetailsContract.View view, int movieId) {
        this.view = view;
        this.movieId = movieId;
        modelSimilar = new MovieDetailsModel();
        modelGenres = new MovieDetailsModel();
    }

    @Override
    public void onFinishedSimilar(List<MovieResult> list) {
        view.setDataToRecyclerView(list);
    }

    @Override
    public void onFailureSimilar(Throwable t) {
        view.onResponseFailure(t);
    }

    @Override
    public void onFinishedGenres(List<GenreResult> list) {
        view.setDataToTextView(list);
    }

    @Override
    public void onFailureGenres(Throwable t) {
        view.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void requestDataFromServer() {
        modelSimilar.getSimilarMovies(this, movieId);
        modelGenres.getGenreList(this);
    }
}
