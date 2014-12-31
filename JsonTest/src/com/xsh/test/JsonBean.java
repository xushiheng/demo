package com.xsh.test;

import java.util.List;

public class JsonBean {//根据JSON视图结构写的类
	public String status;
	public int code;
	public Data data;

	public static class Data {//内部类要用static修饰，否则报错
		public List<Comment> comment;
		public List<Image> image;

		public static class Comment {
			public String user_logo;
			public String content;
			public String date;
			public String cn_name;
			public String user_name;
			public String overall_rating;

			public void setUser_logo(String user_logo) {
				this.user_logo = user_logo;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public void setCn_name(String cn_name) {
				this.cn_name = cn_name;
			}

			public void setUser_name(String user_name) {
				this.user_name = user_name;
			}

			public void setOverall_rating(String overall_rating) {
				this.overall_rating = overall_rating;
			}

			public String getUser_logo() {
				return user_logo;
			}

			public String getContent() {
				return content;
			}

			public String getDate() {
				return date;
			}

			public String getCn_name() {
				return cn_name;
			}

			public String getUser_name() {
				return user_name;
			}

			public String getOverall_rating() {
				return overall_rating;
			}
		}

		public static class Image {
			public String cn_name;
			public String img_url;
		}
	}
}
