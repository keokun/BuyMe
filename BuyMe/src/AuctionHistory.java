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
@WebServlet("/AuctionHistory")
public class AuctionHistory extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public AuctionHistory() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username;
		String button = request.getParameter("button");
		
		String user=request.getParameter("user");
		
		//auctionhistory of the user currently logged in.
		/*if(("auctionH").equals(button))
		{
			username = (String) request.getSession().getAttribute("username");
			Connection conn = null;
			PreparedStatement stmt = null;
			
			try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
			      String sql;
			      
			      //Get the current timestamp
			      java.util.Date date = new java.util.Date();
			      Timestamp currTime = new Timestamp(date.getTime());	
			      
			      //get all auctions where i
			      sql = "SELECT * FROM Auction A, Bid B, Book C WHERE ((A.seller = '" + username + "' AND A.auctionid = C.auctionid) "
			      		+ "OR (B.username = '" + username + "' "
			      		+ " AND B.auctionid = A.auctionid AND B.auctionid = C.auctionid)) AND endtime < ?";
			      
			      //prep the sql statement
			      stmt = conn.prepareStatement(sql);
			      stmt.setTimestamp(1, currTime);
			      
			      ResultSet rs = stmt.executeQuery();
			      
			      List<AuctionObject> lao = new ArrayList<AuctionObject>();
			      List<Integer> aids = new ArrayList<Integer>();
			      
			      while (rs.next())
			      {
			    	  int auctionid = rs.getInt("auctionid");
			    	  //check if we are getting a unique result
			    	  //we only wanna count the first bid on another person's auction, rest don't matter
			    	  if (!aids.contains(auctionid))
			    	  {
			    		  //add that auctionid to the auctionid array
			    		  aids.add(auctionid);
			    		  
				    	  Timestamp posttime = rs.getTimestamp("posttime");
				    	  Timestamp endtime = rs.getTimestamp("endtime");
				    	  
				    	  String seller = rs.getString("seller");
				    	  String book = rs.getString("title");
				    	  
				    	  AuctionObject ao = new AuctionObject(posttime,endtime, auctionid, book, seller);
				    	  
				    	  lao.add(ao);
			    	  }
			    	  
			      }
			      
			      int newsize = lao.size();
			      
			     
				      
			      request.setAttribute("success", true);
			      request.setAttribute("ahtable", lao);
			      request.setAttribute("newsize", newsize);
			      request.setAttribute("username", username);
			      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/history.jsp");
		    	  dispatcher.forward(request,response);
			      
			      
			      
			   }//End try statement
			
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/history.jsp").forward(request, response);	
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
			
			
		}//end auctionH
		
		//else if we have the username of the person passed in through the URL
		else*/
		{
			Connection conn = null;
			PreparedStatement stmt = null;
			
			try{
				//username = request.getParameter("username");
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
			      String sql;
			      
			      //Get the current timestamp
			      java.util.Date date = new java.util.Date();
			      Timestamp currTime = new Timestamp(date.getTime());	
			      
			      //get all auctions where i
			      sql = "SELECT * FROM Auction A, Bid B, Book C WHERE ((A.seller = '" + user + "' AND A.auctionid = C.auctionid) "
			      		+ "OR (B.username = '" + user + "' "
			      		+ " AND B.auctionid = A.auctionid AND B.auctionid = C.auctionid)) AND endtime < ?";
			      
			      //prep the sql statement
			      stmt = conn.prepareStatement(sql);
			      stmt.setTimestamp(1, currTime);
			      
			      ResultSet rs = stmt.executeQuery();
			      
			      List<AuctionObject> lao = new ArrayList<AuctionObject>();
			      List<Integer> aids = new ArrayList<Integer>();
			      
			      while (rs.next())
			      {
			    	  int auctionid = rs.getInt("auctionid");
			    	  //check if we are getting a unique result
			    	  //we only wanna count the first bid on another person's auction, rest don't matter
			    	  if (!aids.contains(auctionid))
			    	  {
			    		  //add that auctionid to the auctionid array
			    		  aids.add(auctionid);
			    		  
				    	  Timestamp posttime = rs.getTimestamp("posttime");
				    	  Timestamp endtime = rs.getTimestamp("endtime");
				    	  
				    	  String seller = rs.getString("seller");
				    	  String book = rs.getString("title");
				    	  
				    	  AuctionObject ao = new AuctionObject(posttime,endtime, auctionid, book, seller);
				    	  
				    	  lao.add(ao);
			    	  }
			    	  
			      }
			      
			      int newsize = lao.size();
			      
			     
				      
			      request.setAttribute("success", true);
			      request.setAttribute("ahtable", lao);
			      request.setAttribute("newsize", newsize);
			      request.setAttribute("username", user);
			      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/history.jsp");
		    	  dispatcher.forward(request,response);
			      
			      
			      
			   }//End try statement
			
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/history.jsp").forward(request, response);	
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
			
			
		}
	}//End doGet
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
		
	}//End doPost
	
	
}//end AuctionHistory class
