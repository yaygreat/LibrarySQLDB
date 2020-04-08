package library.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.dao.LoanDao;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/LoanController")
public class LoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LoanDao loanDao = new LoanDao();
		
		int borrower_id = Integer.parseInt(request.getParameter("borrower_id"));
		String ISBN10 = request.getParameter("ISBN10");
		
		int saved = 40;
		try {
			saved = loanDao.saveLoan(ISBN10, borrower_id);
			request.setAttribute("message", "Test1");
			
			switch (saved) {
			case 1: //successful insert
				request.setAttribute("message", "Book successfully checked out!");
				break;
			case -1: //unsuccessful insert
				request.setAttribute("message", "Error, could not update database");
				break;
			case -2: //book is checked out
				request.setAttribute("message", "Book is already checked out");
				break;
			case -3: //borrower has > 3 books out
				request.setAttribute("message", "Borrower already has 3 books out");
				break;
			case -4: //borrower owes > $5
				request.setAttribute("message", "Borrower owes more than $5.00");
				break;
			case -5: //error code for flag does not hold meaning
				request.setAttribute("message", "Not valid error code");
				break;
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
}
