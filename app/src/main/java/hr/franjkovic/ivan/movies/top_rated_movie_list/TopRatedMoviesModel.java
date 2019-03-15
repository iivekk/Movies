package hr.franjkovic.ivan.movies.top_rated_movie_list;

import java.util.List;

import hr.franjkovic.ivan.movies.rest.RetrofitSetup;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.model.TopRatedMovies;
import hr.franjkovic.ivan.movies.rest.MovieApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedMoviesModel implements TopRatedMoviesContract.Model {

    @Override
    public void getMovieList(final OnFinishedListener onFinishedListener) {

        MovieApiService service = RetrofitSetup.setup().create(MovieApiService.class);

        Call<TopRatedMovies> topRatedCall = service.getTopRatedMovies(MovieApiService.API_KEY);
        topRatedCall.enqueue(new Callback<TopRatedMovies>() {
            @Override
            public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {
                List<MovieResult> list = response.body().getResults();
                onFinishedListener.onFinished(list);
            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
