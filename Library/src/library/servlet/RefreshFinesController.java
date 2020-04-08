package library.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.dao.FineDao;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/RefreshFinesController")
public class RefreshFinesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshFinesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FineDao fineDao = new FineDao();
		
		String pageid = request.getParameter("id");
				
		try {
			int r = fineDao.refreshFines();
			
			switch (r) {
			case 1: //successful remove
				request.setAttribute("message", "Database Refreshed!");
				break;
			case -1: //unsuccessful remove
				request.setAttribute("message", "Error, could not refresh database.");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/"+pageid+".jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
}
