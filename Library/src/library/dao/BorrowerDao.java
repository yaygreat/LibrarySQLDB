package library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import library.bean.Borrower;
import library.util.MySQLJDBC;


public class BorrowerDao {
	private MySQLJDBC mySQLJDBC = new MySQLJDBC();
	
	/**
	 * borrower registration method.
	 * If borrower ssn exists, borrower cannot be registered.
	 * 
	 * @param borrower to be registered
	 * @return register status, either true for success or false for failure
	 */
	public boolean register(Borrower borrower) {
		if (borrowerSSNExist(borrower)) {
			return false;
		} else {
			return insertBorrower(borrower);
		}
	}
	
	/**
	 * verify whether borrower ssn exists or not.
	 * 
	 * @param customer borrower id to be verified from ssn
	 * @return existence status, either true for success or false for failure
	 */
	public boolean borrowerSSNExist(Borrower borrower) {
		boolean borrowerSSNExist = false;
		mySQLJDBC.setPreparedSql("select borrower_id from borrower where ssn=?;", borrower.getSSN());
		ResultSet resultSet = mySQLJDBC.excuteQuery();
		try {
			borrowerSSNExist = resultSet.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrowerSSNExist;
	}

	/**
	 * insert borrower information into database.
	 * 
	 * @param borrower to be inserted
	 * @return insert status, either true for success or false for failure
	 */
	public boolean insertBorrower(Borrower borrower) {
		if (borrower.getSSN() != "") {
			mySQLJDBC.setPreparedSql("insert into borrower(ssn,first_name,last_name,email,address,city,state,phone) values(?,?,?,?,?,?,?,?);",
					borrower.getSSN(), borrower.getFirstName(), borrower.getLastName(), borrower.getEmail(),
					borrower.getAddress(), borrower.getCity(), borrower.getState(), borrower.getPhone());
			mySQLJDBC.executeUpdate();
			mySQLJDBC.close();
			return true;
		}
		mySQLJDBC.close();
		return false;
	}

	/**
	 * Validate that borrower exist given borrower_id
	 * If information matches in database, return a borrower object.
	 * Otherwise, return null.
	 * 
	 * @param borrower_id
	 * @return either (1) borrower object 
	 *         or (2) null.
	 */
	public Borrower validateBorrower(Borrower borrower) {
		mySQLJDBC.setPreparedSql("select * from borrower where borrower_id=?", borrower.getBorrowerID());
		ResultSet resultSet = mySQLJDBC.excuteQuery();
		try {
			if ((resultSet != null) && (resultSet.next())) {
				borrower.setSSN(resultSet.getString("ssn"));
				borrower.setFirstName(resultSet.getString("first_name"));
				borrower.setLastName(resultSet.getString("last_name"));
				borrower.setEmail(resultSet.getString("email"));
				borrower.setAddress(resultSet.getString("address"));
				borrower.setCity(resultSet.getString("city"));
				borrower.setState(resultSet.getString("state"));
				borrower.setPhone(resultSet.getString("phone"));
				return borrower;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Before releasing resources, execute mySQLJDBC.close() method to ensure
	 * this.preparedStatement and this.connection has been closed 
	 */
	//protected void finalize() throws Throwable {
		//mySQLJDBC.close();
		//super.finalize();
	//}

	/**
	 * Update Borrower information
	 * 
	 * @param borrower to be updated
	 * @return update status, either true for success or false for failure
	 */
	public boolean  updateBorrower(Borrower borrower) {
		mySQLJDBC.setPreparedSql("UPDATE borrower SET first_name=?, last_name=?, email=?, address=?, city=?, state=?, phone=? WHERE borrower_id=?", borrower.getFirstName(), borrower.getLastName(), borrower.getEmail(), borrower.getAddress(), borrower.getCity(), borrower.getState(), borrower.getPhone(), borrower.getBorrowerID());
		int resUpdate = mySQLJDBC.executeUpdate();
		if(resUpdate == 1) {
			mySQLJDBC.close();
			return true;
		}
		else {
			mySQLJDBC.close();
			return false;
		}
	}
	
}
