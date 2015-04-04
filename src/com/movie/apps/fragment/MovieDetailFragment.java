package com.movie.apps.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.movie.apps.MovieFullPosterActivity;
import com.movie.apps.R;
import com.movie.apps.model.MoviesObject;
import com.movie.apps.utils.AppConstatnts;

public class MovieDetailFragment extends Fragment {

	private MoviesObject movieObject;

	private ImageView img;
	private TextView title;
	private TextView description;
	private AQuery aq;

	public MovieDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aq = new AQuery(getActivity());
		try {
			if (getArguments().containsKey(AppConstatnts.movieObjectTag)) {
				movieObject = (MoviesObject) getArguments().getSerializable(AppConstatnts.movieObjectTag);
			}
		} catch (Exception e) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
		img = (ImageView) rootView.findViewById(R.id.img);
		title = (TextView) rootView.findViewById(R.id.title);
		description = (TextView) rootView.findViewById(R.id.desc);

		if (movieObject != null) {
			title.setText(movieObject.title);
			description.setText(movieObject.description);
			if (!movieObject.image.equalsIgnoreCase("N/A") && !movieObject.image.equalsIgnoreCase("")) {
				aq.id(img).progress(R.id.progressBar1).image(movieObject.image);
			}
			apiCall(movieObject.id);
		}
		
		rootView.findViewById(R.id.progressBar1).setVisibility(View.GONE);
		
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(movieObject != null && (!movieObject.image.equalsIgnoreCase("N/A") && !movieObject.image.equalsIgnoreCase(""))){
					Intent i = new Intent(getActivity(), MovieFullPosterActivity.class);
					i.putExtra(AppConstatnts.movieObjectTag, movieObject);
					getActivity().startActivity(i);
				}
			}
		});
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(AppConstatnts.movieObjectTag, movieObject);
	}

	private void apiCall(String id) {
		String url = AppConstatnts.movieDetail + id;
		aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				super.callback(url, object, status);
				if (object != null) {
					try {
						movieObject.description = object.getString("Genre") + "\n" + object.getString("Plot");
						movieObject.image = object.getString("Poster");
						description.setText(movieObject.description);
						if (!movieObject.image.equalsIgnoreCase("N/A") && !movieObject.image.equalsIgnoreCase("")) {
							aq.id(img).progress(R.id.progressBar1).image(movieObject.image);
						} else {
							getView().findViewById(R.id.textView1).setVisibility(View.VISIBLE);
						}
					} catch (JSONException e) {
					}
				} else {
					Toast.makeText(getActivity(), status.getError(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
