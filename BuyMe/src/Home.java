import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class Home extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=(String) request.getSession().getAttribute("type");
		
		if(type.equals("regular")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/welcome.jsp");
	    	dispatcher.forward(request,response);
		}
		
		else if (type.equals("rep")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/rephome.jsp");
	    	dispatcher.forward(request,response);
		}
		
		else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/adminpage.jsp");
	    	dispatcher.forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
