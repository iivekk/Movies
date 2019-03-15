package hr.franjkovic.ivan.movies.rest;

import hr.franjkovic.ivan.movies.model.GenreList;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.model.PopularMovies;
import hr.franjkovic.ivan.movies.model.SimilarMovies;
import hr.franjkovic.ivan.movies.model.TopRatedMovies;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    String API_KEY = "fe3b8cf16d78a0e23f0c509d8c37caad";
    String BASE_URL_FOR_MOVIE = "https://api.themoviedb.org/3/";
    String BASE_URL_FOR_IMAGE = "https://image.tmdb.org/t/p/w400";

    @GET("movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<PopularMovies> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/similar")
    Call<SimilarMovies> getSimilarMovies(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenreList> getMovieGenres(@Query("api_key") String apiKey);

}
