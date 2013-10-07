package no.benjoh.fragment;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import no.benjoh.IMDB.IMDBItem;
import no.benjoh.IMDB.IMDBParser;
import no.benjoh.adapter.IMDBAdapter;
import no.benjoh.myfirstandroidapp.MainActivity;
import no.benjoh.myfirstandroidapp.MovieDetails;
import no.benjoh.myfirstandroidapp.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class IMDBFragment extends Fragment {
	private static final String TAG = IMDBFragment.class.getSimpleName();
	private IMDBAdapter adapter;
	private Activity activity;
	
	static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";
    public static final String KEY_THUMB_URL = "thumb_url";
    
	public IMDBFragment(){
		
	}
	
	public void setContext(Activity mainActivity){
		activity = mainActivity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.imdb_search, container, false);
		
		EditText searchBox = (EditText)rootView.findViewById(R.id.searchbox);
        searchBox.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				switch (actionId) {
					case EditorInfo.IME_ACTION_SEARCH:
						Log.d(TAG, "Searching for: " + view.getText());
						search(view.getText().toString());
						break;
					default:
						Log.e(TAG, "Wtf.");
						break;
				}
				return true;
			}
		});
       
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        
        ListView searchResults = (ListView)rootView.findViewById(R.id.searchresults);
        adapter = new IMDBAdapter(activity, new ArrayList<Map<String, String>>());
        searchResults.setAdapter(adapter);
        
        searchResults.setClickable(true);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				TextView txtView = (TextView) view.findViewById(R.id.title);
				String movieTitle = txtView.getText().toString();
				Intent intent = new Intent(activity, MovieDetails.class);
				intent.putExtra("title", movieTitle);
				startActivity(intent);
			}
        	
        });
		return rootView;
	}
	
    private void search(final String search) {
    	if (search.trim().length() == 0) {
    		adapter.clear();
    		return;
    	}
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... v) {
				try {
					String encoded = URLEncoder.encode(search.trim(), "UTF-8");
					String searchUrl = getString(R.string.searchurl, encoded.substring(0,1), encoded);
					//String searchUrl = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yp34eysmp5tjqx9yny5sxdms&q=djan&page_limit=10";
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(searchUrl);
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					return httpClient.execute(httpGet, responseHandler);
				}
				catch(HttpResponseException e){
					return "error";
				}
				catch(Exception ex) {
					throw new RuntimeException(ex);
				}	
			}
			
			protected void onPostExecute(String response) {
				if(!response.equals("error")){
					List<IMDBItem> items = IMDBParser.parseMovieResults(response);
					
					if(items.size() == 0){
						showToast();
					}
					else {
						adapter.clear();
						adapter.addAll(IMDBParser.parseMovieResults(response));
					}
				}else {
					showToast();
				}
			}

			private void showToast() {
				Context context = activity.getApplicationContext();
				CharSequence text = "Fant ingen treff";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			};
		}.execute();
					
    }
}
