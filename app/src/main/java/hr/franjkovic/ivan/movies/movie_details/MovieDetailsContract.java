package hr.franjkovic.ivan.movies.movie_details;

import java.util.List;

import hr.franjkovic.ivan.movies.model.GenreResult;
import hr.franjkovic.ivan.movies.model.MovieResult;

public interface MovieDetailsContract {

    interface Model {

        interface SimilarMoviesModel {

            interface OnFinishedListener {

                void onFinishedSimilar(List<MovieResult> list);

                void onFailureSimilar(Throwable t);

            }

            void getSimilarMovies(OnFinishedListener onFinishedListener, int movieId);

        }

        interface Genres {

            interface OnFinishedListener {

                void onFinishedGenres(List<GenreResult> list);

                void onFailureGenres(Throwable t);

            }

            void getGenreList(OnFinishedListener onFinishedListener);

        }


    }

    interface View {

        void setDataToRecyclerView(List<MovieResult> movieResults);

        void setDataToTextView(List<GenreResult> genreResults);

        void onResponseFailure(Throwable t);

    }

    interface Presenter {

        void onDestroy();

        void requestDataFromServer();

    }

}
