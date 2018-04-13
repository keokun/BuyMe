import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manageaccount")
public class ManageAccount extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	
	public ManageAccount()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);
	}//End doGet
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String button = request.getParameter("button");
		
		if (button.equals("delAcc"))
		{
			String username = request.getParameter("username");
			Connection conn = null;
			PreparedStatement stmt = null; 
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				
				conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				String sql;
				
				sql = "SELECT * FROM Account WHERE username = '" + username + "' AND type <> 'admin'";
				
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				
				//if the account actually exists
				if (rs.next())
				{
				
					sql = "DELETE FROM Account WHERE username = '" + username + "' AND type <> 'admin'";
					
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					
					request.setAttribute("delsuccess", true);
					request.setAttribute("username", username);
					request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request,response);
				}
				else
				{
				      request.setAttribute("delsuccess",false);
				      request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);	
				}
				
				
			}
			
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("delsuccess",false);
			      request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);	
			   }catch(Exception e){
			      e.printStackTrace();
			   }
			
			finally{
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
			
			
			
			
		}//end delete account section 
		
		
		if (button.equals("resPass"))
		{
			String username = request.getParameter("username");
			String newpass = request.getParameter("password");
			Connection conn = null;
			PreparedStatement stmt = null; 
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				
				conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				String sql;
				
				sql = "SELECT * FROM Account WHERE username = '" + username + "' AND password = '" + newpass + "' AND type = 'regular'";
				
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				
				//if there already exists some account with that same password, fail
				if (rs.next())
				{
					request.setAttribute("resetsuccess", false);
					request.setAttribute("username", username);
					request.getRequestDispatcher("/jsps/rephome.jsp").forward(request,response);
				}
				
				//otherwise we update and succeed
				else
				{
					sql = "UPDATE Account SET password = '" + newpass + "' WHERE username = '" + username + "' AND type = 'regular'";
					
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					
					request.setAttribute("resetsuccess", true);
					request.setAttribute("username", username);
					request.getRequestDispatcher("/jsps/rephome.jsp").forward(request,response);
				}

				
				
			}
			
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("resetsuccess",false);
			      request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);	
			   }catch(Exception e){
			      e.printStackTrace();
			   }
			
			finally{
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
			
			
			
			
		}//end delete account section 
		
		
		
		
	}//end doPost

}//End deleteAccount
