package hr.franjkovic.ivan.movies.popular_movie_list;

import java.util.List;

import hr.franjkovic.ivan.movies.model.MovieResult;

public class PopularMoviesPresenter implements PopularMoviesContract.Presenter, PopularMoviesContract.Model
        .OnFinishedListener {

    private PopularMoviesContract.View movieListView;
    private PopularMoviesContract.Model movieListModel;

    public PopularMoviesPresenter(PopularMoviesContract.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new PopularMoviesModel();
    }

    @Override
    public void onFinished(List<MovieResult> movieList) {
        movieListView.setDataToRecyclerView(movieList);
    }

    @Override
    public void onFailure(Throwable t) {
        movieListView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        this.movieListView = null;
    }

    @Override
    public void requestDataFromServer() {
        movieListModel.getMovieList(this);
    }
}
