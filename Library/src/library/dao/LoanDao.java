package library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import library.bean.Borrower;
import library.bean.Book;
import library.bean.Loan;
import library.util.MySQLJDBC;

public class LoanDao {
	private MySQLJDBC mySQLJDBC = new MySQLJDBC();
	
	/**
	 * save loan method.
	 * If borrower is not flagged and book is available, create book loan and set book as unavailabile
	 * Flag borrower if number of books out is 3
	 * 
	 * @param ISBN10 of book to be checked out & borrower_id of borrower checking out the book
	 * @return saving loan status code, 
	 * 			(1)	for success 
	 * 			(-1) for database failure
	 * 			(-2) book is already checked out
	 * 			(-3) borrower has more than 3 books checked out
	 * 			(-4) borrower owes more than $5
	 * 			(-5) erroneous
	 */
	public int saveLoan(String ISBN10, int borrower_id) throws SQLException {
		mySQLJDBC.setPreparedSql("select flag from borrower where borrower_id=?;", borrower_id);
		ResultSet res = mySQLJDBC.excuteQuery();
		int s = 90;
		if(res.next())
			s = res.getInt("flag");
		//check if borrower is NOT flagged
		if (s == 0) {
			mySQLJDBC.setPreparedSql("select isAvailable from book where ISBN10=?;", ISBN10);
			res = mySQLJDBC.excuteQuery();
			if(res.next())
				s = res.getInt("isAvailable");
			//check if book is available
			if(s != 0) {
				mySQLJDBC.setPreparedSql("insert into book_loan (ISBN10, borrower_id, date_out, due_date) values (?, ?, SYSDATE(), DATE_ADD(SYSDATE(), INTERVAL 14 DAY));", ISBN10,borrower_id);
				int resupdate = mySQLJDBC.executeUpdate();
				//if insert was successful, change book availability
				if (resupdate != -1) {
					mySQLJDBC.setPreparedSql("update book set isAvailable=0 where ISBN10=?;", ISBN10);
					resupdate = mySQLJDBC.executeUpdate();
					//if update availability was successful, count no. of loans
					if(resupdate == 1) {
						mySQLJDBC.setPreparedSql("select borrower_id, count(*) from book_loan where date_in is null and borrower_id=?;", borrower_id);
						res = mySQLJDBC.excuteQuery();
						if(res.next())
							s = res.getInt("count(*)");
						//if count >= 3, change flag to 1
						if(s >= 3) {
							mySQLJDBC.setPreparedSql("update borrower set flag = 1 where borrower_id=?;", borrower_id);
							resupdate = mySQLJDBC.executeUpdate();
							//if update was successful, return success
							if (resupdate != -1) {
								mySQLJDBC.close();
								return 1;
							}
							//update flag was not successful
							else {
								mySQLJDBC.close();
								return -1;
							}
						}
						//i count < 3, just return success
						else {
							mySQLJDBC.close();
							return 1;
						}
					}
					//update availability was not successful
					else {
						mySQLJDBC.close();
						return -1;
					}
				}
				//insert loan was not successful
				else {
					mySQLJDBC.close();
					return -1;
				}
			}
			//book is checked out
			else {
				mySQLJDBC.close();
				return -2;
			}
		}
		//borrower is flagged, return reason
		else {
			//borrower has more than 3 books checked out
			//mySQLJDBC.setPreparedSql("select flag from borrower where borrower_id=?;", borrower_id);
			//res = mySQLJDBC.excuteQuery();
			//if(res.next()) {
				//s = res.getInt("flag");
				//mySQLJDBC.close();
			//}
			mySQLJDBC.close();
			if(s == 1) { //s=1 --> more than 3 books checked out
				return -3; 
			}
			else if(s == 2){ //s=2 --> more than $5 owed
				return -4;
			}
			else {
				return -5;
			}
		}
	}
	
	/**
	 * search loans method.
	 * search book loans given ISBN, borrower_id and/or borrower name
	 * 
	 * @param ISBN, borrower_id and/or borrower name
	 * @return list of loans matching an exact combination of ISBN, title, and/or Author(s)
	 */
	public ArrayList<Loan> displayLoans(String ISBN, int borrower_id, String name){
		ArrayList<Loan> list = new ArrayList<>();
		
		//AND sql matching
		String selectClause = "";
		if(borrower_id != 0 && ISBN != null && name != null)
			selectClause = "SELECT book_loan.*, borrower.first_name, borrower.last_name, concatauthorsbooks.ISBN13, concatauthorsbooks.title, concatauthorsbooks.`group_concat(distinct name order by name)` FROM book_loan JOIN borrower ON book_loan.borrower_id=borrower.borrower_id JOIN concatauthorsbooks ON book_loan.ISBN10=concatauthorsbooks.ISBN10 where date_in is null;";
		else {
			selectClause = "SELECT book_loan.*, borrower.first_name, borrower.last_name, concatauthorsbooks.ISBN13, concatauthorsbooks.title, concatauthorsbooks.`group_concat(distinct name order by name)` FROM book_loan JOIN borrower ON book_loan.borrower_id=borrower.borrower_id JOIN concatauthorsbooks ON book_loan.ISBN10=concatauthorsbooks.ISBN10 where date_in is null";
			if(borrower_id != 0)
				selectClause += " and borrower.borrower_id="+ borrower_id;
			if(ISBN != null) {
				if(ISBN.length() == 10)
					selectClause += " and concatauthorsbooks.ISBN10= '"+ ISBN +"'";
				else if(ISBN.length() == 13)
					selectClause += " and concatauthorsbooks.ISBN13= '"+ ISBN +"'";
			}
			if(name != null) {
				String delims = "[ ]";
				String[] FLnames = name.split(delims);
				String borrower_name = "";
				if(FLnames.length == 1) {
					borrower_name = FLnames[0];
					selectClause += " and (borrower.first_name= '"+ borrower_name +"' or borrower.last_name= '"+ borrower_name +"')";
				}
				else if(FLnames.length == 2){
					String Fname = FLnames[0];
					String Lname = FLnames[1];
					selectClause += " and borrower.first_name= '"+ Fname +"'";
					selectClause += " and borrower.last_name= '"+ Lname +"'";
				}
			}
		}
		mySQLJDBC.setPreparedSql(selectClause);
		ResultSet resultSet = mySQLJDBC.excuteQuery();
		
		try {
			while(resultSet.next()) {
				Loan loan = new Loan();
				loan.setloanID(resultSet.getInt("loan_id"));
				loan.setborrowerID(resultSet.getInt("borrower_id"));
				loan.setISBN10(resultSet.getString("ISBN10"));
				loan.setDateOut(resultSet.getString("date_out"));
				loan.setDueDate(resultSet.getString("due_date"));
				loan.setDateIn(resultSet.getString("date_in"));
				Borrower bow = new Borrower();
				bow.setFirstName(resultSet.getString("first_name"));
				bow.setLastName(resultSet.getString("last_name"));
				loan.setBorrower(bow);
				Book bok = new Book();
				bok.setISBN13(resultSet.getString("ISBN13"));
				bok.setTitle(resultSet.getString("title"));
				bok.setAuthorNames(resultSet.getString("group_concat(distinct name order by name)"));
				loan.setBook(bok);
				
				list.add(loan);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mySQLJDBC.close();
		
		return list;
	}
	
	/**
	 * check in loan method.
	 * Change the date in to today to mark a book loan as checked in,
	 * change book to available and remove flag on borrower
	 * 
	 * @param loan_id,ISBN, and/or borrower_id
	 * @return check in status, either (1) for success or (-1) for failure
	 */
	public int removeLoan(int loan_id, int borrower_id, String ISBN10) throws SQLException {
		mySQLJDBC.setPreparedSql("update book_loan set date_in=SYSDATE() where ISBN10=?;", ISBN10); //and date_out
		int resupdate = mySQLJDBC.executeUpdate();
		if (resupdate != -1) {
			mySQLJDBC.setPreparedSql("update book set isAvailable=1 where ISBN10=?;", ISBN10);
			resupdate = mySQLJDBC.executeUpdate();
			if (resupdate != -1) {
				mySQLJDBC.setPreparedSql("update borrower set flag=0 where borrower_id=?;", borrower_id);
				resupdate = mySQLJDBC.executeUpdate();
				if (resupdate != -1) {
					mySQLJDBC.close();
					return 1;
				}
				else {//update flag failed
					mySQLJDBC.close();
					return -1; 
				}
			}
			else {//update isAvailable failed
				mySQLJDBC.close();
				return -1; 
			}
		}
		else {//update date_in failed
			mySQLJDBC.close();
			return -1; 
		}
	}
}
