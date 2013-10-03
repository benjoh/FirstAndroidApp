package no.benjoh.fragment;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import no.benjoh.IMDB.IMDBItem;
import no.benjoh.IMDB.IMDBParser;
import no.benjoh.myfirstandroidapp.MainActivity;
import no.benjoh.myfirstandroidapp.R;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class IMDBFragment extends Fragment {
	private static final String TAG = IMDBFragment.class.getSimpleName();

	private ArrayAdapter<IMDBItem> adapter;
	
	public IMDBFragment(){
		
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
        adapter = new ArrayAdapter<IMDBItem>(MainActivity.context, android.R.layout.simple_list_item_1, new ArrayList<IMDBItem>());
        searchResults.setAdapter(adapter);
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
					System.out.println(searchUrl);
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(searchUrl);
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					return httpClient.execute(httpGet, responseHandler);
				}
				catch(Exception ex) {
					throw new RuntimeException(ex);
				}	
			}
			
			protected void onPostExecute(String response) {
				adapter.clear();
				adapter.addAll(IMDBParser.parseMovieResults(response));
			};
		}.execute();
					
    }
}
