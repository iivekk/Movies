
package hr.franjkovic.ivan.movies.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreList {

    @SerializedName("genres")
    @Expose
    private List<GenreResult> genres = null;

    public List<GenreResult> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResult> genres) {
        this.genres = genres;
    }

}
