package no.benjoh.fragment;

import no.benjoh.myfirstandroidapp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CollectionFragment extends Fragment {

	public CollectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.my_collection, container, false);
		return rootView;
	}
}
