package library.servlet;

import java.io.IOException;
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
@WebServlet("/CheckinSearchController")
public class CheckinSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckinSearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		LoanDao loanDao = new LoanDao();
		
		int borrower_id;
		if(request.getParameter("borrower_id").equals(""))
			borrower_id = 0;
		else 
			borrower_id = Integer.parseInt(request.getParameter("borrower_id"));
		
		String ISBN;
		if(request.getParameter("ISBN").equals(""))
			ISBN = null;
		else
			ISBN = request.getParameter("ISBN");
		
		String borrower_name;
		if(request.getParameter("name").equals(""))
			borrower_name = null;
		else
			borrower_name = request.getParameter("name");

		List<Loan> loans = loanDao.displayLoans(ISBN, borrower_id, borrower_name);
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
