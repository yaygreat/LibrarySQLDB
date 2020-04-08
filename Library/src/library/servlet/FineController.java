package library.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.bean.Fine;
import library.dao.FineDao;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/FineController")
public class FineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FineController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FineDao fineDao = new FineDao();
		
		int borrower_id = Integer.parseInt(request.getParameter("borrower_id"));
		int loan_id = Integer.parseInt(request.getParameter("loan_id"));
		
		int rem = 40;
		rem = fineDao.removeFine(loan_id, borrower_id);
		
		switch (rem) {
		case 1: //successful remove
			request.setAttribute("message", "Fine successfully paid!");
			break;
		case -1: //unsuccessful remove
			request.setAttribute("message", "Error, could not update database.");
			break;
		case -2: //unsuccessful remove
			request.setAttribute("message", "Borrower has not checked in this book.");
			break;
		}

		fineDao = new FineDao();
		List<Fine> fineList = fineDao.displayFines(borrower_id);
		request.setAttribute("fineList", fineList);
		request.getRequestDispatcher("fineResult.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
}
