import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

@WebServlet("/auctionview")
public class Auctionview extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		//Get the auction id from the url
		String auctionId=(String)request.getParameter("auctionid");
		
		Connection conn = null;
		 PreparedStatement stmt = null;
		 
		 try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
		      
		      //Find the auction in the database and pull data from entry
		      String sql;
		      sql = "SELECT * FROM Auction WHERE auctionid=" + auctionId;
		      stmt=conn.prepareStatement(sql);
		      ResultSet rs=stmt.executeQuery();
		      
		      Timestamp openTs=null;
		      Timestamp closeTs=null;
		      
		      
		      double  reservePrice=0.0;
		      String seller=null;
		     
		      if(rs.next()) {
		    	  openTs=rs.getTimestamp("posttime");
		    	  closeTs=rs.getTimestamp("endtime");
		    	  reservePrice=rs.getDouble("reserve");
		    	  seller=rs.getString("seller");
		      }
		      

		      Date openDate=new Date(openTs.getTime());
		      Date closeDate=new Date(closeTs.getTime());
		      
		      if(openDate.getTime()>System.currentTimeMillis()) {
		    	  request.setAttribute("active", "before");
		      }
		      
		      else if (closeDate.getTime()<System.currentTimeMillis()) {
		    	  request.setAttribute("active", "after");
		      }
		      
		      else {
		    	  request.setAttribute("active", "active");
		      }
		      
		      //Find book in database and pull data from it
		      sql = "SELECT * FROM Book  WHERE auctionid=" + auctionId;
		      stmt=conn.prepareStatement(sql);
		      rs=stmt.executeQuery();
		      
		      String title=null;
		      String author=null;
		      String format=null;
		      String pageCount=null;
		      String publisher=null;
		      boolean fictionType=false;
		      String subcategory=null;
		      String attribute1=null;
		      String attribute2=null;
		      String isbn=null;
		      
		      if(rs.next()) {
		    	  title=rs.getString("title");
		    	  author=rs.getString("author");
		    	  format=rs.getString("format");
		    	  pageCount=rs.getString("pagect");
		    	  publisher=rs.getString("publisher");
		    	  fictionType=rs.getBoolean("fictype");
		    	  subcategory=rs.getString("subcat");
		    	  isbn=rs.getString("isbn");
		    	  
		    	  try {
		    		  attribute1=rs.getString("attr");
		    		  
		    	  } catch(Exception e) {
		    		  
		    	  }
		    	  
		    	  try {
		    		  attribute2=rs.getString("attr2");
		    		  
		    	  } catch(Exception e) {
		    		  
		    	  }
		    	  
		    	  
		      }
		      
		      double maxBid=0.0;
		      String maxBidUsername=null;
		      String maxBidDate=null;
		      double maxBidAutoBid=0.0;
		      
		      sql= "SELECT * FROM Bid b1 WHERE b1.auctionId=" + auctionId + " AND amount IN (SELECT MAX(amount) FROM Bid b2 WHERE b2.auctionId=b1.auctionId)";
		      stmt=conn.prepareStatement(sql);
		      
		      rs=stmt.executeQuery();
		      
		      if(rs.next()) {
		    	 maxBid=rs.getDouble("amount");
		    	 maxBidUsername=rs.getString("username");
		    	 maxBidDate=new Date(rs.getTimestamp("time").getTime()).toString();
		    	 maxBidAutoBid=rs.getDouble("maxbid");
		      }
		      
		      
		      //check max bid autobidding of bids. Increase bids if needed.
		      
		      //send alerts
		      
		      
		      
		      
		      stmt.close();
		      conn.close();
		      
		      if (closeDate.getTime()<System.currentTimeMillis()) {
		    	  if(maxBid>=reservePrice) {
		    		  request.setAttribute("sold", true);
		    	  }
		    	  
		    	  else {
		    		  request.setAttribute("sold", false);
		    	  }
		      }
		      
		      //Set attributes for jsp
		      
		      request.setAttribute("openDateString", openTs.toString());
		      request.setAttribute("closeDateString", closeTs.toString());
		      request.setAttribute("auctionId", auctionId);
		      request.setAttribute("reservePrice", reservePrice);
		      request.setAttribute("seller", seller);
		      
		      request.setAttribute("isbn", isbn);
		      request.setAttribute("title", title);
		      request.setAttribute("author", author);
		      request.setAttribute("format", format);
		      request.setAttribute("pageCount", pageCount);
		      request.setAttribute("publisher", publisher);
		      request.setAttribute("fictionType", fictionType);
		      request.setAttribute("subcategory", subcategory);
		      request.setAttribute("attribute1", attribute1);
		      request.setAttribute("attribute2", attribute2);
		      
		      request.setAttribute("maxBid", maxBid);
		      request.setAttribute("maxBidUsername", maxBidUsername);
		      request.setAttribute("maxBidDate", maxBidDate);
		      request.setAttribute("maxBidAutoBid", maxBidAutoBid);
		      request.setAttribute("minBid", maxBid+0.01);
		      
		      
		      
		      request.getRequestDispatcher("/jsps/auctionview.jsp").forward(request, response);
		      
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
		
		String button=request.getParameter("button");
		if(!button.equals("button1")) {
			System.out.println("Not button");
			doGet(request,response);
			return;
		}
		String auctionId=(String)request.getParameter("auctionid");
		
		Timestamp currentTS=new Timestamp(System.currentTimeMillis());
		
		String username=(String)request.getSession().getAttribute("username");
		String bidAmount=(String)request.getParameter("bidAmount");
		String biddingType=(String)request.getParameter("Autobidding");
		String autoBidAmount=null;
		if(biddingType.equals("autobidding"))
			autoBidAmount=(String)request.getParameter("autoBidAmount");
		
		Connection conn=null;
		PreparedStatement stmt=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
		      
		      String sql;
		      
		      double maxBid=0.0;
		      
		      sql= "SELECT * FROM Bid b1 WHERE b1.auctionId=" + auctionId + " AND amount IN (SELECT MAX(amount) FROM Bid b2 WHERE b2.auctionId=b1.auctionId)";
		      stmt=conn.prepareStatement(sql);
		      
		      ResultSet rs=stmt.executeQuery();
		      
		      if(rs.next()) {
		    	 maxBid=rs.getDouble("amount");
		      }
		      
		      Double bidAmountDouble=Double.parseDouble(bidAmount);
		      
		      //accidental refresh. This check prevents duplicate entries
		      if(maxBid==bidAmountDouble.doubleValue()) {
		    	  doGet(request,response);
		    	  return;
		      }
		      
		      
		      
		      if(autoBidAmount!=null)
		    	  sql = "INSERT INTO Bid(username,auctionid,time,amount,maxbid) VALUES ('" + username + "', " + auctionId + ", '" + currentTS
		    		  + "', " + bidAmount + ", " + autoBidAmount + ")";
		      else
		    	  sql="INSERT INTO Bid(username,auctionid,time,amount,maxbid) VALUES ('" + username + "', " + auctionId + ", '" + currentTS
			    		  + "', " + bidAmount + ", 0.0)";
		      stmt=conn.prepareStatement(sql);
		      stmt.executeUpdate();
		      
		}
		
		catch(SQLException se){
		      se.printStackTrace();
	
		}
		
		catch(Exception e){
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
		
		
		doGet(request,response);
	}

}
