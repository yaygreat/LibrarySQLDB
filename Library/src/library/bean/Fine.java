package library.bean;


public class Fine {
	private int loan_id = 0;
	private double fine_amt = 0.00;
	private int paid = 0;
	private Borrower borrower = new Borrower();
	private Book book = new Book();
	private Loan loan = new Loan();

	
	//Setters
	public void setLoanID(int loan_id) {
		this.loan_id = loan_id;
	}
	
	public void setFineAmt(double fine_amt) {
		this.fine_amt = fine_amt;
	}
	
	public void setPaid(int paid) {
		this.paid = paid;
	}
	
	public void setBorrower(Borrower bow) {
		this.borrower = bow;
	}
	
	public void setBook(Book bok) {
		this.book = bok;
	}
	
	public void setLoan(Loan lo) {
		this.loan = lo;
	}
	
	
	//Getters
	public int getLoanID() {
		return loan_id;
	}

	public double getFineAmt() {
		return fine_amt;
	}

	public int getPaid() {
		return paid;
	}
	
	public Borrower getBorrower() {
		return borrower;
	}
	
	public Book getBook() {
		return book;
	}
	
	public Loan getLoan() {
		return loan;
	}
}
