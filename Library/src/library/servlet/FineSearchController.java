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
@WebServlet("/FineSearchController")
public class FineSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FineSearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FineDao fineDao = new FineDao();
		
		int borrower_id;
		if(request.getParameter("borrower_id").equals(""))
			borrower_id = 0;
		else
			borrower_id = Integer.parseInt(request.getParameter("borrower_id"));
		
		
		List<Fine> fineList = fineDao.displayFines(borrower_id);
		request.setAttribute("fineList", fineList);
		if(borrower_id == 0)
			request.getRequestDispatcher("/finesSearchResult.jsp").forward(request, response);
		else
			request.getRequestDispatcher("/fineResult.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
}
