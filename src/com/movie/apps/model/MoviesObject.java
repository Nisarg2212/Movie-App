package com.movie.apps.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class MoviesObject implements Serializable{

	private static final long serialVersionUID = 1L;
	@SerializedName("Title") public String title;
	@SerializedName("imdbID") public String id;
	public String description = "";
	public String image = "";
}
