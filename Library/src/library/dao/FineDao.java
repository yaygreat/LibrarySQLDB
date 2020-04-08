package library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import library.bean.Borrower;
import library.bean.Loan;
import library.bean.Fine;
import library.util.MySQLJDBC;

public class FineDao {
	private MySQLJDBC mySQLJDBC = new MySQLJDBC();
	
	/**
	 * refresh fines method.
	 * Creates new fines and updates existing fines with the rule:
	 * $0.25 per day for each day out after the due date
	 * 
	 * @param loan_id,ISBN, and/or borrower_id
	 * @return check in status, either (1) for success or (-1) for failure
	 */
	public int refreshFines() throws SQLException {
		//update existing fines
		int resupdate;
		mySQLJDBC.setPreparedSql("SELECT * FROM concatfinesborrowers;");
		ResultSet res = mySQLJDBC.excuteQuery();
		try{
			while(res.next()) {
				String duedate = res.getString("due_date");
				String datein = res.getString("date_in");
				int loanid = res.getInt("loan_id");
				if(datein != null) { //book was turned in
					mySQLJDBC.setPreparedSql("update fine set fine_amt = DATEDIFF(?,?)*0.25 where loan_id=?", datein, duedate, loanid);
					resupdate = mySQLJDBC.executeUpdate();
					if(resupdate != -1) {
						//mySQLJDBC.setPreparedSql("update ConcatFinesBorrowers set flag=2 where fine_amt>=5.00");
						//resupdate = mySQLJDBC.executeUpdate();
						mySQLJDBC.setPreparedSql("select borrower_id from concatfinesborrowers where paid=0 group by borrower_id having sum(fine_amt)>=5.00");
						res = mySQLJDBC.excuteQuery();
						while(res.next()) {
							mySQLJDBC.setPreparedSql("update borrower set flag=2 where borrower_id=?", res.getInt("borrower_id"));
							resupdate = mySQLJDBC.executeUpdate();
						}
						//if(resupdate != -1)
							//return 100;
						//else //update flag failed
							return 1;
					}
					else return -1; //update fine failed
				}
				//book is still out
				else {
					mySQLJDBC.setPreparedSql("update fine set fine_amt = DATEDIFF(SYSDATE(), ?)*0.25 where loan_id=?", duedate, loanid);
					resupdate = mySQLJDBC.executeUpdate();
					if(resupdate != -1) {
						mySQLJDBC.setPreparedSql("update ConcatFinesBorrowers set flag=2 where fine_amt>=5.00");
						resupdate = mySQLJDBC.executeUpdate();
						//if(resupdate != -1)
							//return 101;
						//else //update flag failed
							return 1;
					}
					else return -1; //update fine failed
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add new fines
		mySQLJDBC.setPreparedSql("SELECT * FROM book_loan where DATEDIFF(SYSDATE(), due_date) > 0;");
		res = mySQLJDBC.excuteQuery();
		try {
			int loanid;
			String duedate;
			while(res.next()) {
				loanid = res.getInt("loan_id");
				duedate = res.getString("due_date");
				mySQLJDBC.setPreparedSql("INSERT INTO fine (loan_id, fine_amt, paid) SELECT * FROM (SELECT ?, DATEDIFF(SYSDATE(), ?)*0.25, 0) AS tmp WHERE NOT EXISTS (SELECT loan_id FROM fine WHERE fine.loan_id=?);",  loanid, duedate, loanid);
				resupdate = mySQLJDBC.executeUpdate();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//mySQLJDBC.close();
		return 0;
	}
	
	/**
	 * display fines method.
	 * If searching for all fines in database,
	 * display the sum of each borrower's fines
	 * If searching for a specific borrower,
	 * display all unpaid fines for that borrower
	 * 
	 * @param borrower_id
	 * @return list of unpaid fines that match borrower_id
	 */
	public ArrayList<Fine> displayFines(int borrower_id){
		try {
			refreshFines();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<Fine> list = new ArrayList<>();
		
		//AND sql matching
		String selectClause = "";
		if(borrower_id == 0)
			selectClause = "SELECT *, sum(fine_amt) FROM concatfinesborrowers WHERE paid=0 group by borrower_id;";
		else
			selectClause = "SELECT * FROM concatfinesborrowers WHERE paid=0 and borrower_id="+ borrower_id;
		mySQLJDBC.setPreparedSql(selectClause);
		ResultSet resultSet = mySQLJDBC.excuteQuery();
		
//		response.getWriter().println("hello");
		try {
			while(resultSet.next()) {
				Fine fine = new Fine();
				fine.setLoanID(resultSet.getInt("loan_id"));
				if(borrower_id == 0)
					fine.setFineAmt(resultSet.getDouble("sum(fine_amt)"));
				else
					fine.setFineAmt(resultSet.getDouble("fine_amt"));
				fine.setPaid(resultSet.getInt("paid"));
				
				Loan lo = new Loan();
				lo.setISBN10(resultSet.getString("ISBN10"));
				lo.setDueDate(resultSet.getString("due_date"));
				lo.setDateIn(resultSet.getString("date_in"));
				fine.setLoan(lo);
				
				Borrower bow = new Borrower();
				bow.setBorrowerID(resultSet.getInt("borrower_id"));
				fine.setBorrower(bow);
				
				list.add(fine);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mySQLJDBC.close();
		
		return list;
	}
	
	/**
	 * pay fines method.
	 * Mark that a loan with given loan_id has been paid and unflag borrower
	 * 
	 * @param loan_id, borrower_id
	 * @return payment status, either (1) for success or (-1) for failure
	 */
	public int removeFine(int loan_id, int borrower_id) {
		try {
		String selectClause = "SELECT * FROM concatfinesborrowers where paid=0 and borrower_id="+ borrower_id;
		mySQLJDBC.setPreparedSql(selectClause);
		ResultSet res = mySQLJDBC.excuteQuery();
		String str;
		if(res.next()) {
			str = res.getString("date_in");
			if(str == null) {	//book is NOT turned in
				return -2;				//unable to pay
			}
			else {
				//mySQLJDBC.setPreparedSql("update borrower set flag=0 where borrower_id=1;");
				//int resupdate = mySQLJDBC.executeUpdate();
				mySQLJDBC.setPreparedSql("update fine set paid=1 where loan_id=?;", loan_id);
				int resupdate = mySQLJDBC.executeUpdate();
				if (resupdate != -1) {
					mySQLJDBC.setPreparedSql("SELECT *, sum(fine_amt) FROM concatfinesborrowers where borrower_id=? and paid=0;", borrower_id);
					res = mySQLJDBC.excuteQuery();
					double s=0;
					if(res.next())
						s = res.getDouble("sum(fine_amt)");
					//if sum < 5, change flag to 0
					if(s < 5.00) {
						mySQLJDBC.setPreparedSql("update borrower set flag=0 where borrower_id=?;", borrower_id);
						resupdate = mySQLJDBC.executeUpdate();
						if (resupdate != -1)
							return 1;
						else return -1; //update flag failed
					}
					else return 1; //borrower owes > $5.00, flag stays
				}
				else return -1; //update paid failed
			}
		}
		else return -1; //select failed
		
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//mySQLJDBC.close();
	return -1;
	}	
	
		
}
