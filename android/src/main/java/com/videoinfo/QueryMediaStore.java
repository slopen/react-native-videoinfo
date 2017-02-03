package com.videoinfo;

import android.content.Context;
import android.util.Pair;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.StringWriter;
import java.io.PrintWriter;

import java.util.ArrayList;



public class QueryMediaStore {

	private Context context;

	private String[] columns = {
		MediaStore.Video.Media._ID,
		MediaStore.Video.Media.DATA,
		MediaStore.Video.Media.TITLE,
		MediaStore.Video.Media.MIME_TYPE,
		MediaStore.Video.Media.DURATION,
		MediaStore.Video.Media.DATE_TAKEN,
		MediaStore.Video.Media.RESOLUTION,
		MediaStore.Video.Media.SIZE
	};

	public QueryMediaStore (Context context) {
		this.context = context;
	}

	public WritableMap requestInfo (
		String uri
	) {
		WritableMap result = Arguments.createMap();

		String id = uri.split (":") [1];

		try {

			Cursor cursor = context.getContentResolver ()
				.query (MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, columns [0] + "=" + id, null, null);


			if (cursor != null) {
				cursor.moveToFirst();

				result.putString ("path", cursor.getString (cursor.getColumnIndexOrThrow (columns [1])));
				result.putString ("title", cursor.getString (cursor.getColumnIndexOrThrow (columns [2])));
				result.putString ("mimeType", cursor.getString (cursor.getColumnIndexOrThrow (columns [3])));
				result.putInt ("duration", cursor.getInt (cursor.getColumnIndexOrThrow (columns [4])));
				result.putInt ("dateTaken", cursor.getInt (cursor.getColumnIndexOrThrow (columns [5])));
				result.putString ("resolution", cursor.getString (cursor.getColumnIndexOrThrow (columns [6])));
				result.putInt ("size", cursor.getInt (cursor.getColumnIndexOrThrow (columns [7])));
			}

			result.putString ("uri", uri);

		} catch (Exception e) {
			result.putString ("error", printExeption (e));
		}

		return result;
	}

	private String printExeption (Exception e) {
		StringWriter sw = new StringWriter ();
		e.printStackTrace (new PrintWriter (sw));

		return sw.toString ();
	}
}