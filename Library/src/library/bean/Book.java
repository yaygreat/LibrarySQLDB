package library.bean;

public class Book {
	private String ISBN10 = null;
	private String ISBN13 = null;
	private String title = null;
	private String author_names = null;
	private int isAvailable = 0;
	private String cover= null;
	

	public Book() {}
	
	//Setters
		public void setISBN10(String ISBN10) {
			this.ISBN10 = ISBN10;
		}
		public void setISBN13(String ISBN13) {
			this.ISBN13 = ISBN13;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setAuthorNames(String author_names) {
			this.author_names = author_names;
		}
		public void setAvailability(int isAvailable) {
			this.isAvailable = isAvailable;
		}
		public void setCover(String cover) {
			this.cover = cover;
		}
	
	//Getters
	public String getISBN10() {
		return ISBN10;
	}
	public String getISBN13() {
		return ISBN13;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthorNames() {
		return author_names;
	}
	public int getAvailability() {
		return isAvailable;
	}
	public String getCover() {
		return cover;
	}
}
