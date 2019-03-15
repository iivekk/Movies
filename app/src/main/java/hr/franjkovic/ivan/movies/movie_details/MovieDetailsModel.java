package hr.franjkovic.ivan.movies.movie_details;

import java.util.List;

import hr.franjkovic.ivan.movies.rest.RetrofitSetup;
import hr.franjkovic.ivan.movies.model.GenreList;
import hr.franjkovic.ivan.movies.model.GenreResult;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.model.SimilarMovies;
import hr.franjkovic.ivan.movies.rest.MovieApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsModel implements MovieDetailsContract.Model.SimilarMoviesModel, MovieDetailsContract.Model.Genres {

    MovieApiService service = RetrofitSetup.setup().create(MovieApiService.class);

    // fetching the similar movie list
    @Override
    public void getSimilarMovies(final MovieDetailsContract.Model.SimilarMoviesModel.OnFinishedListener onFinishedListener, int movieId) {

        Call<SimilarMovies> similarCall = service.getSimilarMovies(movieId, MovieApiService.API_KEY);
        similarCall.enqueue(new Callback<SimilarMovies>() {
            @Override
            public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                if (response.body() != null) {
                    List<MovieResult> list = response.body().getResults();
                    onFinishedListener.onFinishedSimilar(list);
                }
            }

            @Override
            public void onFailure(Call<SimilarMovies> call, Throwable t) {
                onFinishedListener.onFailureSimilar(t);
            }
        });
    }


    // fetching the genre list
    @Override
    public void getGenreList(final MovieDetailsContract.Model.Genres.OnFinishedListener onFinishedListener) {

        Call<GenreList> genresCall = service.getMovieGenres(MovieApiService.API_KEY);
        genresCall.enqueue(new Callback<GenreList>() {
            @Override
            public void onResponse(Call<GenreList> call, Response<GenreList> response) {
                if (response.body() != null) {
                    List<GenreResult> list = response.body().getGenres();
                    onFinishedListener.onFinishedGenres(list);
                }
            }

            @Override
            public void onFailure(Call<GenreList> call, Throwable t) {
                onFinishedListener.onFailureGenres(t);
            }
        });
    }
}
