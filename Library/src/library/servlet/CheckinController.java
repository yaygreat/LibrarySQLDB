package library.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.bean.Loan;
import library.dao.LoanDao;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/CheckinController")
public class CheckinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckinController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		LoanDao loanDao = new LoanDao();
		
		int loan_id = Integer.parseInt(request.getParameter("loan_id"));
		int borrower_id = Integer.parseInt(request.getParameter("borrower_id"));
		String ISBN10 = request.getParameter("ISBN10");
		String borrower_name;
		borrower_name = request.getParameter("first_name");
		borrower_name += " " + request.getParameter("last_name");
		
		int rem = 40;
		try {
			rem = loanDao.removeLoan(loan_id, borrower_id, ISBN10);
			
			switch (rem) {
			case 1: //unsuccessful insert
				request.setAttribute("message", "Book successfully checked in!");
				break;
			case -1: //unsuccessful insert
				request.setAttribute("message", "Error, could not update database");
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loanDao = new LoanDao();
		List<Loan> loans = loanDao.displayLoans(null, borrower_id, borrower_name);
		request.setAttribute("loanList", loans);
		request.getRequestDispatcher("/checkinSearchResult.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
}
