package hr.franjkovic.ivan.movies.top_rated_movie_list.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.franjkovic.ivan.movies.top_rated_movie_list.TopRatedMoviesContract;
import hr.franjkovic.ivan.movies.top_rated_movie_list.TopRatedMoviesPresenter;
import hr.franjkovic.ivan.movies.R;
import hr.franjkovic.ivan.movies.adapter.OnClickItemListener;
import hr.franjkovic.ivan.movies.adapter.TopRatedPopularAdapter;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.movie_details.activity.MovieDetails;
import hr.franjkovic.ivan.movies.tools.Keys;

public class TopRatedFragment extends Fragment implements OnClickItemListener, TopRatedMoviesContract.View {

    @BindView(R.id.rvTopRated)
    RecyclerView rvTopRated;

    private List<MovieResult> listOfMovies;
    private GridLayoutManager gridLayoutManager;
    private TopRatedPopularAdapter mAdapter;
    private TopRatedMoviesPresenter presenter;


    public static TopRatedFragment newInstance() {
        return new TopRatedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);

        ButterKnife.bind(this, rootView);

        listOfMovies = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvTopRated.setLayoutManager(gridLayoutManager);

        presenter = new TopRatedMoviesPresenter(this);
        presenter.requestDataFromServer();

        return rootView;
    }

    @Override
    public void onItemCLick(View v, int position) {
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra(Keys.SELECTED_MOVIE, mAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void setDataToRecyclerView(List<MovieResult> movieList) {
        listOfMovies.addAll(movieList);
        mAdapter = new TopRatedPopularAdapter(getActivity(), listOfMovies);
        mAdapter.setClickListener(this);
        rvTopRated.setAdapter(mAdapter);
    }

    @Override
    public void onResponseFailure(Throwable t) {
        Toast.makeText(getActivity(), getString(R.string.data_error_msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
