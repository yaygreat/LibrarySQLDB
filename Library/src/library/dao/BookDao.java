package library.dao;

import library.util.MySQLJDBC;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import library.bean.Book;



public class BookDao {
	private MySQLJDBC mySQLJDBC = new MySQLJDBC();
	
	/**
	 * search book method.
	 * 
	 * 
	 * @param search field
	 * @return list of books matching any combination of ISBN, title, and/or Author(s)
	 * 			with substring matching
	 */
	public List<Book> selectBook(String search) {
		List<Book> list = new ArrayList<Book>();
		
		String selectClause = "";
		if(search == null) {
			selectClause = "SELECT * FROM concatauthorsbooks";
		}else{
			selectClause = "SELECT * FROM concatauthorsbooks where 1=2";
			String[] searchSplit = search.split("[|]");
			for(int i=0; i<searchSplit.length;i++) {
				selectClause += " or ISBN10='" + searchSplit[i] + "' or ISBN13='" + searchSplit[i] + "' or title like '%" + searchSplit[i] + "%' or `group_concat(distinct name order by name)` like '%" + searchSplit[i] + "%'";
			}
		}
		mySQLJDBC.setPreparedSql(selectClause);
		ResultSet rs = mySQLJDBC.excuteQuery();
		try {
			while(rs.next()) {
				Book book = new Book();
				book.setISBN10(rs.getString("ISBN10"));
				book.setISBN13(rs.getString("ISBN13"));
				book.setTitle(rs.getString("title"));
				book.setAuthorNames(rs.getString("group_concat(distinct name order by name)"));
				book.setAvailability(rs.getInt("isAvailable"));
				book.setCover(rs.getString("cover"));
				list.add(book);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		mySQLJDBC.close();
		return list;
	}
	
}
