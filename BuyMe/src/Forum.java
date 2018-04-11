import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Forum")
public class Forum extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public Forum() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/forum.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String head = request.getParameter("heading");
		String button = request.getParameter("button");
		String mess = request.getParameter("query");
		String username = (String) request.getSession().getAttribute("username");
		java.util.Date date = new java.util.Date();
		Timestamp currTime = new Timestamp(date.getTime());
		
		
	
		
		/* If we are posting a query to db*/
		if ("qsubmit".equals(button))
		{
			Connection conn = null;
			PreparedStatement stmt = null;
			
			//cannot have a null query
			if (mess.equals(null))
			{
				request.setAttribute("body", false);
				request.getRequestDispatcher("/jsps/forum.jsp").forward(request, response);
			}
			
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
			      String email;
			      String sql;
			      sql = 
			    		  "SELECT email FROM Account WHERE username = '" + username + "'";
			      stmt=conn.prepareStatement(sql);
			      ResultSet rs = stmt.executeQuery();
			      
			      //if there is something in the 
			      if (rs.next())
			      {
			    	  email = rs.getString("email");
			    	  String sql2;
			    	  if (head != null)
			    	  {
			    		  sql2 = 
					    		  	"INSERT INTO Message (sender, receiver, sendtime, expirytime,contents,forum)"
					    		  	+ "VALUES ('" + email + "' , 'forum', ? , '9999-12-31' , '" + head + " : " + mess + "' , 1)";
			    	  }
			    	  else
			    	  {
			    		  sql2 = 
					    		  	"INSERT INTO Message (sender, receiver, sendtime, expirytime,contents,forum)"
					    		  	+ "VALUES ('" + email + "' , 'forum', ? , '9999-12-31' , '" + mess + "' , 1)";
			    	  }
				      
				      stmt = conn.prepareStatement(sql2);
				      stmt.setTimestamp(1, currTime);
				      stmt.executeUpdate();
				      
				      stmt.close();
				      conn.close();
				      
				      request.setAttribute("success", true);
				      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/forum.jsp");
			    	  dispatcher.forward(request,response);
			      }
			      
			      else
			      {
			    	  request.setAttribute("success", false);
			    	  request.getRequestDispatcher("/jsps/forum.jsp").forward(request, response);
			      }
			      
			   }catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/forum.jsp").forward(request, response);	
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
		
		if ("squery".equals(button))
		{
			Connection conn = null;
			PreparedStatement stmt = null;
			String keyword = request.getParameter("keyword");
			String keyword2 = request.getParameter("keyword2");
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");

				 conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				 
				 String sql;
				 
				 sql = "SELECT * FROM Message WHERE contents LIKE '%" + keyword + "%'" +  "OR " +"contents LIKE '%" + keyword2 + "%'";
				 
				 stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery();
				 List<String> nameList = new ArrayList<String>();
				 List<String> contentList= new ArrayList<String>();
				 
				 while(rs.next())
				 {
					 String name = rs.getString("sender");
					 String content = rs.getString("contents");
					 nameList.add(name);
					 contentList.add(content);
				 }
				 
				 stmt.close();
				 conn.close();
				 
				 int newsize = nameList.size();
				 
				 String [][] qtable	= new String[newsize][2];
				 for (int i = 0; i < newsize; i++)
				 {
					 qtable[i][0] = nameList.get(i);
					 qtable[i][1] = contentList.get(i);
				 }
				 
				 request.setAttribute("search",true);
				 request.setAttribute("qtable", qtable);
				 request.setAttribute("newsize", newsize);
				 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/forum.jsp");
				 dispatcher.forward(request,response);
				 
				 
			 }
			 catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("search",false);
			      request.getRequestDispatcher("/jsps/forum.jsp").forward(request, response);	
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
	
	
	}

}
