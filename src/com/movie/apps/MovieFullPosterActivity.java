package com.movie.apps;

import com.androidquery.AQuery;
import com.movie.apps.model.MoviesObject;
import com.movie.apps.utils.AppConstatnts;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MovieFullPosterActivity extends FragmentActivity {

	AQuery aq;
	private MoviesObject moviesObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_full_poster);
		
		aq = new AQuery(this);
		moviesObject = (MoviesObject) getIntent().getSerializableExtra(AppConstatnts.movieObjectTag);
		aq.id(findViewById(R.id.img)).image(moviesObject.image);
	}
}
