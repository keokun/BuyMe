import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/createauction")
public class CreateAuction extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public CreateAuction() {
		super();
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		request.getRequestDispatcher("/jsps/createauction.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username=(String) request.getSession().getAttribute("username");
		
		String title=(String) request.getParameter("title");
		String author=(String) request.getParameter("author");
		String isbn=(String) request.getParameter("isbn");
		String publisher=(String) request.getParameter("publisher");
		String format=(String) request.getParameter("format");
		String fictionType=(String) request.getParameter("fictionType");
		
		String openDate=(String) request.getParameter("openDate");
		String closeDate=(String) request.getParameter("closeDate");
		String openHour=(String) request.getParameter("openHour");
		String closeHour=(String) request.getParameter("closeHour");
		String openMinutes=(String) request.getParameter("openMinutes");
		String closeMinutes=(String) request.getParameter("closeMinutes");
		
		
		String numPages=null;
		String genre=null;
		String subType=null;
		String param1=null;
		String param2=null;
		
		
		//Not an audiobook
		if(format.equals("physical")) {
			numPages=(String) request.getParameter("numPages");
		}
		
		if(fictionType.equals("fiction")) {
			subType=(String) request.getParameter("fictionSubType");
			
			if(subType.equals("anthology")) {
				param1=(String)request.getParameter("editors");
			}
			
			else if(subType.equals("poetry")) {
				param1=(String)request.getParameter("poetryStyle");
			}
			
			else if(subType.equals("comics")) {
				param1=(String)request.getParameter("volume");
				param2=(String)request.getParameter("issueComic");
			}
		}
		
		else {
			subType=(String) request.getParameter("nonfictionSubType");
			
			if(subType.equals("biography")) {
				param1=(String)request.getParameter("era");
			}
			
			else if(subType.equals("magazine")) {
				param1=(String)request.getParameter("issueMagazine");
			}
			
		}
		
		
		

		
	}


}
