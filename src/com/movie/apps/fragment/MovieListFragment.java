package com.movie.apps.fragment;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.movie.apps.R;
import com.movie.apps.model.MovieListModel;
import com.movie.apps.model.MoviesObject;
import com.movie.apps.utils.AppConstatnts;

public class MovieListFragment extends Fragment implements TextWatcher {

	private Callbacks mCallbacks;
	private EditText edtMovieTitle;
	private ListView listViewMovie;
	private ListitemAdapter listitemAdapter;
	private AQuery aq;
	private Gson gson;
	private MovieListModel movieListModel = new MovieListModel();

	public interface Callbacks {
		public void onItemSelected(MoviesObject c);
	}

	public MovieListFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aq = new AQuery(getActivity());
		gson = new Gson();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		edtMovieTitle = (EditText) view.findViewById(R.id.editText1);
		listViewMovie = (ListView) view.findViewById(R.id.list);
		listViewMovie.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCallbacks.onItemSelected((MoviesObject) listViewMovie.getItemAtPosition(position));
			}
		});
		edtMovieTitle.addTextChangedListener(this);
		if (savedInstanceState != null && savedInstanceState.containsKey("movieListModel")) {
			movieListModel = (MovieListModel) savedInstanceState.getSerializable("movieListModel");
			listitemAdapter = new ListitemAdapter(movieListModel.movieList);
			listViewMovie.setAdapter(listitemAdapter);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("movieListModel", movieListModel);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (s.length() >= 2) {
			searchApiCall(s.toString());
		} else {
			if (listitemAdapter != null)
				listitemAdapter.clear();
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	private void searchApiCall(String s) {
		String url = AppConstatnts.searchApi + s;
		aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				// TODO Auto-generated method stub
				super.callback(url, object, status);
				if (object != null) {
					movieListModel = gson.fromJson(object.toString(), MovieListModel.class);
					listitemAdapter = new ListitemAdapter(movieListModel.movieList);
					listViewMovie.setAdapter(listitemAdapter);
				} else {
					Toast.makeText(getActivity(), status.getError(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public class ListitemAdapter extends ArrayAdapter<MoviesObject> {

		ViewHolder holder;

		public ListitemAdapter(ArrayList<MoviesObject> movieList) {
			super(getActivity(), android.R.layout.simple_list_item_1, movieList);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (getActivity()).getLayoutInflater();
			if (convertView == null) {
				convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
				holder = new ViewHolder();
				holder.txtname = (TextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txtname.setText(movieListModel.movieList.get(position).title);
			return convertView;
		}

		private class ViewHolder {
			TextView txtname;
		}
	}
}
