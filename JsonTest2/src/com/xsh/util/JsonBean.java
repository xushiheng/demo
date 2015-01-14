package com.xsh.util;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class JsonBean {//根据JSON视图结构写的类
	public String status;
	public int code;
	public Data data;

	public static class Data {//内部类要用static修饰，否则报错
		public List<Comment> comment;
		public List<Image> image;
		
		public static class Comment {
			@SerializedName("user_logo")
			public String userLogo;
			public String content;
			public String date;
			@SerializedName("cn_name")
			public String cnName;
			@SerializedName("user_name")
			public String userName;
			@SerializedName("overall_rating")
			public String overallRating;

			public void setUser_logo(String userLogo) {
				this.userLogo = userLogo;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public void setCn_name(String cnName) {
				this.cnName = cnName;
			}

			public void setUser_name(String userName) {
				this.userName = userName;
			}

			public void setOverall_rating(String overallRating) {
				this.overallRating = overallRating;
			}

			public String getUserLogo() {
				return userLogo;
			}

			public String getContent() {
				return content;
			}

			public String getDate() {
				return date;
			}

			public String getCnName() {
				return cnName;
			}

			public String getUserName() {
				return userName;
			}

			public String getOverallRating() {
				return overallRating;
			}
		}

		public static class Image {
			public String cn_name;
			public String img_url;
		}
	}
}
