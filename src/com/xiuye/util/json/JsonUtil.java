package com.xiuye.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	public static final int FORMAT_GSON = 1;
	public static final int GENERAL_GSON = 2;

	private static Gson formatterGson;
	private static Gson generalGson;
	static {
		formatterGson = new GsonBuilder().setPrettyPrinting().create();
		generalGson = new Gson();
	}

	public static Gson instance() {
		return generalGson;
	}

	public static Gson instance(int type) {

		if (FORMAT_GSON == type) {
			return formatterGson;
		}
		return generalGson;

	}

}
