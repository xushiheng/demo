package com.xsh.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Words {
	public static final String AUTHORITY = "com.xsh.provider.contactprovider";

	public static final class Word implements BaseColumns {
		public static final String KEY_ID = "_id";
		public static final String KEY_NAME = "name";
		public static final String KEY_MOBILE = "mobile";
		public static final String KEY_TELEPHONE = "telephone";
		public static final String KEY_EMAIL = "email";
		public static final String KEY_REMARKS = "remarks";
		public static final Uri CONTACTS_URI = Uri.parse("content://"
				+ AUTHORITY + "/contacts");
		public static final Uri CONTACT_URI = Uri.parse("content://"
				+ AUTHORITY + "/contact");
	}
}
