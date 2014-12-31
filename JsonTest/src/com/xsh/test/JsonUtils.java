package com.xsh.test;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {
	public JsonBean parseUserFromJson(String jsonData) {
		Gson gson = new Gson();//使用了谷歌提供的Gson类对Json进行处理
		Type type = new TypeToken<JsonBean>() {}.getType();
		JsonBean jsonBean = gson.fromJson(jsonData, type);
		return jsonBean;
	}
		
	}

