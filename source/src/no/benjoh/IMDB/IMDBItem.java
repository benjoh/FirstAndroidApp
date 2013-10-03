package no.benjoh.IMDB;


public class IMDBItem {

	private String title;
	private String description;

	public IMDBItem(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
}
