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
import java.util.Random;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Josh created this page
 */

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
			    	  String sql2;
			    	  Random rand = new Random();
			    	  int tid = rand.nextInt(500000) + 200000;
			    	  if (head != null)
			    	  {
			    		  sql2 = 
					    		  	"INSERT INTO Message (sender, receiver, sendtime, expirytime,contents,forum,tid)"
					    		  	+ "VALUES ('" + username + "' , 'forum', ? , '9999-12-31' , '" + head + " : " + mess + "' , 1 , " + tid + " )";
			    	  }
			    	  else
			    	  {
			    		  sql2 = 
					    		  	"INSERT INTO Message (sender, receiver, sendtime, expirytime,contents,forum,tid)"
					    		  	+ "VALUES ('" + username + "' , 'forum', ? , '9999-12-31' , '" + mess + "' , 1 , " + tid + "  )";
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
		/*If we are getting all of the queries in the forum*/
		if ("aquery".equals(button))
		{
			Connection conn = null;
			PreparedStatement stmt = null;
			String type = (String) request.getSession().getAttribute("type");
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");

				 conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				 
				 String sql;
				 
				 sql = "SELECT * FROM Message ORDER BY tid ASC";
				 
				 stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery();
				 List<ForumItem> fl = new ArrayList <ForumItem>();
				 
				 while(rs.next())
				 {
					 
					 String name = rs.getString("sender");
					 String content = rs.getString("contents");
	
					 if (type.equals("rep"))
					 {
						 int tid = rs.getInt("tid");
						 
						 
						 ForumItem fi = new ForumItem(tid,name,content);
						 fl.add(fi);
					 }
					 else
					 {
						 ForumItem fi = new ForumItem(name, content);
						 fl.add(fi);
					 }
					 
				 }
				 
				 stmt.close();
				 conn.close();
				 
				 int length = fl.size();
				 
				 request.setAttribute("all",true);
				 request.setAttribute("forumList",fl);
				 request.setAttribute("newsize", length);
		
				 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/forum.jsp");
				 dispatcher.forward(request,response);
				 
				 
			 }
			 catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("all",false);
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
		/*If we are searching for a specific query*/
		if ("squery".equals(button))
		{
			Connection conn = null;
			PreparedStatement stmt = null;
			String keyword = request.getParameter("keyword");
			String keyword2 = request.getParameter("keyword2");
	
			
			String type = (String) request.getSession().getAttribute("type");
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");

				 conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				 
				 String sql;
				 
				 //retrieves the initial question
				 sql = "SELECT * FROM Message WHERE (contents LIKE '%" + keyword + "%'" +  "AND " 
						+"contents LIKE '%" + keyword2 + " %')";
				 
				
				 stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery();
				 
				 //Get the answers to these questions

				 List<ForumItem> fl = new ArrayList <ForumItem>();
				 
				 List<String> names = new ArrayList<String>();
				 List<String> messages = new ArrayList<String>();
				 List<Integer> tidList = new ArrayList<Integer>();
				 
				 
				 while(rs.next())
				 {
					 
					 
					 /* looking at the query we get */
					 
					 
					 String content = rs.getString("contents");
					 
					 if (!messages.contains(content))
					 {
						 messages.add(content);
						 
						 String name = rs.getString("sender");
						 names.add(name);
						 
						
						 
						 Timestamp ts = rs.getTimestamp("sendtime");
						 
						 int tid = rs.getInt("tid");
						 tidList.add(tid);
						 
						 /* Where we find the answer to this query, if there is one*/
						 String sql2;
						 
						 sql2 = "SELECT * FROM Message WHERE tid = " +  tid + " AND sendtime <> ?";
						 
						 stmt = conn.prepareStatement(sql2);
						 stmt.setTimestamp(1, ts);
						 
						 ResultSet rs2 = stmt.executeQuery();
						 
						 while(rs2.next())
						 {
							 names.add(rs2.getString("sender"));
							 messages.add(rs2.getString("contents"));
							 tidList.add(rs2.getInt("tid"));
							 
						 } 
					 }
					 
					 

					 
				 }
				 
				 //add the results from rs and rs2 (which are in the lists) to the ForumList array
				 for (int i = 0; i < tidList.size(); i++)
				 {
					 ForumItem fi = new ForumItem(tidList.get(i),names.get(i),messages.get(i));
					 fl.add(fi);
				 }
				
				 
				 stmt.close();
				 conn.close();
				 
				 int length = fl.size();
				 
				 request.setAttribute("search",true);
				 request.setAttribute("forumList",fl);
				 request.setAttribute("newsize", length);
		
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
		
		/*Deleting a query and all pertinent answers to the query*/
		if ("del".equals(button))
		{
			Connection conn = null;
			PreparedStatement stmt = null;
			int tid = Integer.parseInt(request.getParameter("tid"));
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");

				 conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				 
				 String sql;
				 
				 sql = "DELETE FROM Message WHERE tid = " + tid ;
				 
				 stmt = conn.prepareStatement(sql);
				 stmt.executeUpdate();
				 
				 stmt.close();
				 conn.close();
				 
				 //Successful delete attribute
				 request.setAttribute("delete",true);
				 
				 
				 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/forum.jsp");
				 dispatcher.forward(request,response);
				 
			 }
			 catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("delete",false);
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
		
		/*If we are answering a query*/
		if ("asubmit".equals(button))
		{
			Connection conn = null;
			PreparedStatement stmt = null;
			String tidq = request.getParameter("tidquery");
			String ansquery = request.getParameter("ansquery");
			
			 try{
				 Class.forName("com.mysql.jdbc.Driver");

				 conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				 
				 
				 /*  Query to check that the tid exists in the database*/
				 String sql;
				 
				 sql = "SELECT * FROM Message WHERE tid = " + tidq;
				 
				 stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery();
				 
				 /* if if does exist*/
				 if (rs.next())
				 {
					 String sql2;
					 
					 sql2 = 
				    		  	"INSERT INTO Message (sender, receiver, sendtime, expirytime,contents,forum,tid)"
				    		  	+ "VALUES ('" + username + "' , 'forum', ? , '9999-12-31' , 'CR ANSWER: " + ansquery + "' , 1 , " + tidq + " )";
					 
				      stmt = conn.prepareStatement(sql2);
				      stmt.setTimestamp(1, currTime);
				      stmt.executeUpdate();
				      
				      request.setAttribute("ansdone", true);
				      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/forum.jsp");
				      dispatcher.forward(request,response);
					 
				 }
				 else
				 {
					 request.setAttribute("ansdone", false);
					 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/forum.jsp");
					 dispatcher.forward(request,response);
				 }
				 
				 stmt.close();
				 conn.close();
				 
				 
				 
		

				 
				 
			 }
			 catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("ansdone",false);
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
		
		}//end answer query
		
		
	
	
	}//end doPost

}//End Forum Class
