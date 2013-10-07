package no.benjoh.myfirstandroidapp;

import no.benjoh.fragment.SectionsPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

public class MovieDetails  extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_details);
		
		TextView txtProduct = (TextView) findViewById(R.id.movie_title);
        
        Intent i = getIntent();
        String product = i.getStringExtra("title");
        txtProduct.setText(product);
	}
}
