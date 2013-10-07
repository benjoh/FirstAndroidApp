package no.benjoh.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.benjoh.IMDB.IMDBItem;
import no.benjoh.fragment.IMDBFragment;
import no.benjoh.myfirstandroidapp.R;
import no.benjoh.utils.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IMDBAdapter extends BaseAdapter {
	private Activity activity;
	private List<Map<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	
	public IMDBAdapter(Activity activity, List<Map<String, String>> data){
		this.activity = activity;
		this.data = data;
		
		inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(this.activity.getApplicationContext());
		
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			view = inflater.inflate(R.layout.list_row, null);
		}
		TextView title = (TextView)view.findViewById(R.id.title); // title
        TextView artist = (TextView)view.findViewById(R.id.artist); // artist name
        ImageView thumb_image=(ImageView)view.findViewById(R.id.list_image); // thumb image
 
        Map<String, String> movie = new HashMap<String, String>();
        movie = data.get(position);
 
        // Setting all values in listview
        title.setText(movie.get(IMDBFragment.KEY_TITLE));
        artist.setText(movie.get(IMDBFragment.KEY_ARTIST));
        imageLoader.DisplayImage(movie.get(IMDBFragment.KEY_THUMB_URL), thumb_image);
        return view;
	}

	public void clear() {
		data.clear();
	}

	public void addAll(List<IMDBItem> parseMovieResults) {
		for(IMDBItem item : parseMovieResults){
			Map<String, String> map = new HashMap<String, String>();
			map.put(IMDBFragment.KEY_TITLE, item.getTitle());
			data.add(map);
		}
		
		notifyDataSetChanged();
		
	}

}
