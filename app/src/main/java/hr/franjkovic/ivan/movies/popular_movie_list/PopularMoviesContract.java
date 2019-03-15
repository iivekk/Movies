package hr.franjkovic.ivan.movies.popular_movie_list;


import java.util.List;

import hr.franjkovic.ivan.movies.model.MovieResult;

public interface PopularMoviesContract {

    interface Model {

        interface OnFinishedListener {

            void onFinished(List<MovieResult> movieList);

            void onFailure(Throwable t);
        }


        void getMovieList(OnFinishedListener onFinishedListener);

    }

    interface View {

        void setDataToRecyclerView(List<MovieResult> movieList);

        void onResponseFailure(Throwable t);

    }

    interface Presenter {

        void onDestroy();

        void requestDataFromServer();

    }

}