package library.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.bean.Borrower;
import library.dao.BorrowerDao;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/BorrowerController")
public class BorrowerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BorrowerDao borrowerDao = new BorrowerDao();
		
		Borrower borrower = new Borrower();
		Borrower verifiedBorrower = new Borrower();

		//update
		if(request.getParameter("update") != null) {
			borrower.setBorrowerID(Integer.parseInt(request.getParameter("borrower_id")));
			verifiedBorrower = borrowerDao.validateBorrower(borrower);
			if(request.getParameter("firstname") != null)
				verifiedBorrower.setFirstName(request.getParameter("firstname"));
			if(request.getParameter("lastname") != null)
				verifiedBorrower.setLastName(request.getParameter("lastname"));
			if(request.getParameter("email") != null)
				verifiedBorrower.setEmail(request.getParameter("email"));
			if(request.getParameter("address") != null)
				verifiedBorrower.setAddress(request.getParameter("address"));
			if(request.getParameter("city") != null)
				verifiedBorrower.setCity(request.getParameter("city"));
			if(request.getParameter("state") != null)
				verifiedBorrower.setState(request.getParameter("state"));
			if(request.getParameter("phone") != null)
				verifiedBorrower.setPhone(request.getParameter("phone"));
			boolean updated = borrowerDao.updateBorrower(verifiedBorrower);
			if(updated) {
				request.setAttribute("message", "Borrower successfully updated!");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
			else {
				request.setAttribute("message", "Borrower could not be updated at this time");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		} //display
		else if (request.getParameter("borrower_id") != null) { //validate borrower with borrowerdao.validateborrower
			borrower.setBorrowerID(Integer.parseInt(request.getParameter("borrower_id")));
			verifiedBorrower = borrowerDao.validateBorrower(borrower);
			if(verifiedBorrower != null) { //if validated, go to updateBorrower.jsp
				request.setAttribute("borrower", verifiedBorrower);
				String msg = "Borrower with ID: " + verifiedBorrower.getBorrowerID() + ", " +verifiedBorrower.getFirstName() + " " + verifiedBorrower.getLastName() + "\n"
						+ verifiedBorrower.getAddress() + " " + verifiedBorrower.getCity() + ", " + verifiedBorrower.getState() + "\n"
						+ verifiedBorrower.getPhone() + "\n"
						+ verifiedBorrower.getEmail();
				request.setAttribute("message", msg);
				request.getRequestDispatcher("/updateBorrower.jsp").forward(request, response);
			}
			else { //send error msg to register
				request.setAttribute("message", "Could not find Borrower with that ID!");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		
		} else { //register
			borrower.setSSN(request.getParameter("ssn"));
			borrower.setFirstName(request.getParameter("firstname"));
			borrower.setLastName(request.getParameter("lastname"));
			borrower.setEmail(request.getParameter("email"));
			borrower.setAddress(request.getParameter("address"));
			borrower.setCity(request.getParameter("city"));
			borrower.setState(request.getParameter("state"));
			borrower.setPhone(request.getParameter("phone"));
			boolean inserted = borrowerDao.register(borrower);
			if(inserted) {
				request.setAttribute("message", "Registration successful!");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			} else {
				if(request.getParameter("ssn").equals(""))
					request.setAttribute("message", "Please provide an SSN!");
				else
					request.setAttribute("message", "Borrower already exists with that same SSN!");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		}
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
