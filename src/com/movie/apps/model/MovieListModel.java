package com.movie.apps.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MovieListModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@SerializedName("Search") public ArrayList<MoviesObject> movieList  = new ArrayList<MoviesObject>();
}
