package hr.franjkovic.ivan.movies.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSetup {

    private static Retrofit retrofit = null;

    public static Retrofit setup() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(MovieApiService.BASE_URL_FOR_MOVIE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
