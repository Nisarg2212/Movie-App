package com.movie.apps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.movie.apps.fragment.MovieDetailFragment;
import com.movie.apps.fragment.MovieListFragment;
import com.movie.apps.model.MoviesObject;
import com.movie.apps.utils.AppConstatnts;

public class MovieListActivity extends FragmentActivity implements MovieListFragment.Callbacks {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_list);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		MovieDetailFragment movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentByTag(AppConstatnts.movieDetailFragmentTag);
		if (movieDetailFragment == null) {
			movieDetailFragment = new MovieDetailFragment();
			fragmentManager.beginTransaction().replace(R.id.movie_detail_container, movieDetailFragment, AppConstatnts.movieDetailFragmentTag).commit();
		}

		MovieListFragment movieListFragment = (MovieListFragment) fragmentManager.findFragmentByTag(AppConstatnts.movieListFragmentTag);
		if (movieListFragment == null) {
			movieListFragment = new MovieListFragment();
			fragmentManager.beginTransaction().replace(R.id.movie_list, movieListFragment, AppConstatnts.movieListFragmentTag).commit();
		}
	}

	@Override
	public void onItemSelected(MoviesObject moviesObject) {
		Bundle arguments = new Bundle();
		arguments.putSerializable(AppConstatnts.movieObjectTag, moviesObject);
		MovieDetailFragment fragment = new MovieDetailFragment();
		fragment.setArguments(arguments);
		getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment, AppConstatnts.movieDetailFragmentTag).commit();
	}
}
