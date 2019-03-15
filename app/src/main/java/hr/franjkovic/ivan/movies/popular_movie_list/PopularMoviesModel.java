package hr.franjkovic.ivan.movies.popular_movie_list;

import java.util.List;

import hr.franjkovic.ivan.movies.rest.RetrofitSetup;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.model.PopularMovies;
import hr.franjkovic.ivan.movies.rest.MovieApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesModel implements PopularMoviesContract.Model {

    @Override
    public void getMovieList(final OnFinishedListener onFinishedListener) {

        MovieApiService service = RetrofitSetup.setup().create(MovieApiService.class);

        Call<PopularMovies> topRatedCall = service.getPopularMovies(MovieApiService.API_KEY);
        topRatedCall.enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                List<MovieResult> list = response.body().getResults();
                onFinishedListener.onFinished(list);
            }

            @Override
            public void onFailure(Call<PopularMovies> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
