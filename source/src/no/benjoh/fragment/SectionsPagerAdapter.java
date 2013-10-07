package no.benjoh.fragment;

import java.util.Locale;

import no.benjoh.myfirstandroidapp.MainActivity;
import no.benjoh.myfirstandroidapp.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	MainActivity mainActivity;
	
	public SectionsPagerAdapter(FragmentManager fm, MainActivity mainActivity) {
		super(fm);
		this.mainActivity = mainActivity;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = null;
		switch(position){
		case 0 : 
			fragment = new IMDBFragment();
			((IMDBFragment) fragment).setContext(mainActivity);
			break;
		case 1 :
			fragment = new CollectionFragment();
			break;	
		}
		return fragment;
		
		
		/*Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;*/
	}

	@Override
	public int getCount() {
		return 2;
	}

	public CharSequence getPageTitle(int position) {
		
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return mainActivity.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return mainActivity.getString(R.string.title_section2).toUpperCase(l);
		}
		return null;
	}
}