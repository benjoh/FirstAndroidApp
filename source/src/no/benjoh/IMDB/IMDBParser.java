package no.benjoh.IMDB;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class IMDBParser {

	private static final String TAG = IMDBParser.class.getSimpleName();
	
	public static List<IMDBItem> parseMovieResults(String suggestString) {
		if (suggestString == null || suggestString.isEmpty()) return new ArrayList<IMDBItem>();
		// ^imdb\\$[^\\(]*\\((.*)\\)$, om man føler seg sprek.
		String jsonString = suggestString.substring(suggestString.indexOf("(")+1, suggestString.length()-1);
		List<IMDBItem> result = new ArrayList<IMDBItem>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("d");
			Log.d(TAG, jsonArray.length() + " items found");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				String type = item.optString("q");
				if(type != null && type.equals("feature"))
					result.add(new IMDBItem(item.getString("l"), item.getString("s")));
			}
		} 
		catch (JSONException e) { 
			throw new RuntimeException(e);
		} 
		return result;
	}
	
}
