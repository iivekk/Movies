package hr.franjkovic.ivan.movies.top_rated_movie_list;

import java.util.List;

import hr.franjkovic.ivan.movies.model.MovieResult;

public class TopRatedMoviesPresenter implements TopRatedMoviesContract.Presenter, TopRatedMoviesContract.Model
        .OnFinishedListener {

    private TopRatedMoviesContract.View movieListView;
    private TopRatedMoviesContract.Model movieListModel;

    public TopRatedMoviesPresenter(TopRatedMoviesContract.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new TopRatedMoviesModel();
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
