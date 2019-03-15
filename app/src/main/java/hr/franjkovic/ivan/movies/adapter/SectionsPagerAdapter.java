package hr.franjkovic.ivan.movies.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hr.franjkovic.ivan.movies.R;
import hr.franjkovic.ivan.movies.popular_movie_list.fragment.PopularFragment;
import hr.franjkovic.ivan.movies.top_rated_movie_list.fragment.TopRatedFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    
    Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TopRatedFragment.newInstance();
        } else if (position == 1) {
            return PopularFragment.newInstance();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.top_rated);
        } else if (position == 1) {
            return context.getString(R.string.popular);
        } else {
            return null;
        }
    }
}
