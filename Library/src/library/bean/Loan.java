package library.bean;


public class Loan {
	private int loan_id = 0;
	private int borrower_id = 0;
	private String ISBN10 = null;
	private Borrower borrower = new Borrower();
	private Book book = new Book();
	private String date_out = null;
	private String due_date = null;
	private String date_in = null;

	
	//Setters
	public void setloanID(int loan_id) {
		this.loan_id = loan_id;
	}
	
	public void setborrowerID(int borrower_id) {
		this.borrower_id = borrower_id;
	}
	
	public void setISBN10(String ISBN10) {
		this.ISBN10 = ISBN10;
	}
	
	public void setBorrower(Borrower bow) {
		this.borrower = bow;
	}
	
	public void setBook(Book bok) {
		this.book = bok;
	}
	
	public void setDateOut(String date_out) {
		this.date_out = date_out;
	}
	
	public void setDueDate(String due_date) {
		this.due_date = due_date;
	}
	
	public void setDateIn(String date_in) {
		this.date_in = date_in;
	}
	
	
	//Getters
	public int getLoanID() {
		return loan_id;
	}

	public int getBorrowerID() {
		return borrower_id;
	}
		
	public String getISBN10() {
		return ISBN10;
	}
	
	public Borrower getBorrower() {
		return borrower;
	}

	public Book getBook() {
		return book;
	}
	
	public String getDateOut() {
		return date_out;
	}
	
	public String getDueDate() {
		return due_date;
	}
	
	public String getDateIn() {
		return date_in;
	}
}
