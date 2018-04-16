import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * Josh Kyle and Kristen contributed to this page
 */
@WebServlet("/home")
public class Home extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=(String) request.getSession().getAttribute("type");
		String username=(String)request.getSession().getAttribute("username");
		if (type != null)
		{
			if(type.equals("regular")) {
				response.sendRedirect(request.getContextPath()+"/AuctionHistory?user=" + username);
			}
			
			else if (type.equals("rep")) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/rephome.jsp");
		    	dispatcher.forward(request,response);
			}
			
			else if (type.equals("admin")){
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/adminpage.jsp");
		    	dispatcher.forward(request,response);
			}
			
			else
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/login.jsp");
				dispatcher.forward(request, response);
			}
		}
		//if the type is null, then we just have to go back to the login 
		else
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/login.jsp");
			dispatcher.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
