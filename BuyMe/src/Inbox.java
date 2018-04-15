import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * Kyle created this page
 */
@WebServlet("/inbox")
public class Inbox extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=(String)request.getSession().getAttribute("username");
		
		request.setAttribute("username", username);
		
		 Connection conn = null;
		 PreparedStatement stmt = null;
		 try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);

		      String sql;
		      sql = "SELECT * FROM Message WHERE receiver='" + username + "' OR sender='" + username + "' AND forum <> 1";
		      stmt=conn.prepareStatement(sql);
		      ResultSet rs=stmt.executeQuery();
		      
		      ArrayList<Message> msgList =new ArrayList<Message>();
		      while(rs.next()) {
		    	  String sender=rs.getString("sender");
		    	  String receiver=rs.getString("receiver");
		    	  String sendtime=rs.getString("sendtime");
		    	  String contents=rs.getString("contents");
		    	  Message msg=new Message(sender,receiver,sendtime,contents);
		    	  msgList.add(msg);
		    	  
		      }
		      stmt.close();
		      conn.close();
		      
		      request.setAttribute("messageList", msgList.toArray());
		      request.setAttribute("numMessages", msgList.size());
		      
		      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/inbox.jsp");
	    	  dispatcher.forward(request,response);
		      
	    	  /* On Failure*/
		      
		   }catch(SQLException se){
		      se.printStackTrace();	
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}