package hr.franjkovic.ivan.movies.popular_movie_list.fragment;

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
import hr.franjkovic.ivan.movies.popular_movie_list.PopularMoviesContract;
import hr.franjkovic.ivan.movies.popular_movie_list.PopularMoviesPresenter;
import hr.franjkovic.ivan.movies.R;
import hr.franjkovic.ivan.movies.movie_details.activity.MovieDetails;
import hr.franjkovic.ivan.movies.adapter.OnClickItemListener;
import hr.franjkovic.ivan.movies.adapter.TopRatedPopularAdapter;
import hr.franjkovic.ivan.movies.model.MovieResult;
import hr.franjkovic.ivan.movies.tools.Keys;

public class PopularFragment extends Fragment implements OnClickItemListener, PopularMoviesContract.View {

    @BindView(R.id.rvPopular)
    RecyclerView rvPopular;

    private List<MovieResult> movieList1;
    private GridLayoutManager layoutManager;
    private TopRatedPopularAdapter mAdapter1;
    private PopularMoviesPresenter presenter;

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);

        ButterKnife.bind(this, rootView);

        movieList1 = new ArrayList<>();

        layoutManager = new GridLayoutManager(getActivity(), 2);
        rvPopular.setLayoutManager(layoutManager);

        presenter = new PopularMoviesPresenter(this);
        presenter.requestDataFromServer();

        return rootView;
    }

    @Override
    public void onItemCLick(View v, int position) {
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra(Keys.SELECTED_MOVIE, mAdapter1.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void setDataToRecyclerView(List<MovieResult> movieList) {
        movieList1.addAll(movieList);
        mAdapter1 = new TopRatedPopularAdapter(getActivity(), movieList1);
        mAdapter1.setClickListener(this);
        rvPopular.setAdapter(mAdapter1);
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
