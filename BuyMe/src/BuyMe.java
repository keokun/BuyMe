
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Josh Kyle and Kristen
 * worked on this page together
 * 
 */

@WebServlet("/")
public class BuyMe extends HttpServlet {

private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://joshdb.cugqewczk5li.us-east-1.rds.amazonaws.com:3306/projdbcs336";
	
	static final String USER = "root";
	static final String PASS = "ScarletKnight";

    public BuyMe(){
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("username")!=null) {
			response.sendRedirect(request.getContextPath()+"/home");
		}
		else
			request.getRequestDispatcher("/jsps/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String button=request.getParameter("button");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		if("button1".equals(button)) {
			
			 Connection conn = null;
			 PreparedStatement stmt = null;
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			      String sql;
			      sql = "SELECT * FROM Account	 WHERE username='" + username + "'AND password='" + password + "'";
			      stmt=conn.prepareStatement(sql);
			      ResultSet rs=stmt.executeQuery();
			      
			      if(rs.next()) {
			    	  String fn = rs.getString("fname");
			    	  String ln = rs.getString("lname");
			    	  String type = rs.getString("type");
			    	  request.setAttribute("success",true);
			    	  request.setAttribute("firstname", fn);
			    	  request.getSession().setAttribute("username", username);
			    	  request.getSession().setAttribute("type", type);
			    	  //customer account
			    	  /*if (type.equals("regular"))
			    	  {
				    	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/welcome.jsp");
				    	  dispatcher.forward(request,response);

			    	  }
			    	  //customer rep account
			    	  else if (type.equals("rep"))
			    	  {
				    	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/rephome.jsp");
				    	  dispatcher.forward(request,response);

			    	  }
			    	  //admin account
			    	  else if (type.equals("admin"))
			    	  {
				    	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/adminpage.jsp");
				    	  dispatcher.forward(request,response);
			    	  }*/
			    	  
			    	  response.sendRedirect(request.getContextPath()+"/home");
			      } 
			      
			      else {
			    	  request.setAttribute("success",false);
			    	  request.getRequestDispatcher("/jsps/login.jsp").forward(request, response);
			      }
			      rs.close();
			      stmt.close();
			      conn.close();
			      
			      
			   }
			 	catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/login.jsp").forward(request, response);	
			   }
			 	catch(Exception e){
			      e.printStackTrace();
			   }
			 	finally{
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }
			      catch(SQLException se2){
			      }
			      try{
			         if(conn!=null)
			            conn.close();
			      }
			      
			      catch(SQLException se){
			         se.printStackTrace();
			      }
			   }
		}
	}

}
