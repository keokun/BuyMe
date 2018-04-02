import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Kyle and Josh
 * worked on this together
 * 
 */
@WebServlet("/createaccount")
public class CreateAccount extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CreateAccount(){
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/createaccount.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String button=request.getParameter("button");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		
		
		//creation of a regular account
		if("button1".equals(button)) {
			
			 Connection conn = null;
			 PreparedStatement stmt = null;
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);

			      String sql;
			      sql = "INSERT INTO Account (type, username, pass, email, fname, lname)"
			      		+ "VALUES ('regular', '" + username + "', '" + password + "', '" + email + "', '" + fname + "', '" + lname + "')";
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();

			      stmt.close();
			      conn.close();
			      
		    	  request.setAttribute("success",true);
		    	  request.setAttribute("firstname", fname);
		    	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/welcome.jsp");
		    	  dispatcher.forward(request,response);
			      
			      
			   }catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/createaccount.jsp").forward(request, response);	
			   }catch(Exception e){
			      e.printStackTrace();
			   }finally{
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			   }
		}
		
		//for creation of a customer rep
		if("CR".equals(button)) {
			
			
			 Connection conn = null;
			 PreparedStatement stmt = null;
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);

			      String sql;
			      sql = "INSERT INTO Account (type, username, pass, email, fname, lname)"
			      		+ "VALUES ('rep', '" + username + "', '" + password + "', '" + email + "', '" + fname + "', '" + lname + "')";
			      stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();

			      stmt.close();
			      conn.close();
			      
		    	  request.setAttribute("success",true);
		    	  request.setAttribute("firstname", fname);
		    	  request.setAttribute("lastname",lname);
		    	  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/adminpage.jsp");
		    	  dispatcher.forward(request,response);
			      
			      
			   }catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);	
			   }catch(Exception e){
			      e.printStackTrace();
			   }finally{
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			   }
			
			
		}//end create customer rep
		
		
		
		
	}
}
