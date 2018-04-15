import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

/* Kyle Created this page
 * 
 * Kristen added "view similar items" functionality */

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
		
		String username=(String) request.getSession().getAttribute("username");
		
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
		      
		      if(seller.equals(username)) {
		    	  request.setAttribute("amSeller", true);
		      }
		      

		      Date openDate=new Date(openTs.getTime());
		      Date closeDate=new Date(closeTs.getTime());
		      
		      if(openDate.after(new Date(System.currentTimeMillis()+ 3600000))) {
		    	  request.setAttribute("active", "before");
		      }
		      
		      else if (closeDate.before(new Date(System.currentTimeMillis()+ 3600000))) {
		    	  request.setAttribute("active", "after");
		      }
		      
		      else {
		    	  Date d=new Date(System.currentTimeMillis()+ 3600000);
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
		      String genre=null;
		      
		      if(rs.next()) {
		    	  title=rs.getString("title");
		    	  author=rs.getString("author");
		    	  format=rs.getString("format");
		    	  format=format.substring(0, 1).toUpperCase() + format.substring(1);
		    	  pageCount=rs.getString("pagect");
		    	  publisher=rs.getString("publisher");
		    	  fictionType=rs.getBoolean("fictype");
		    	  subcategory=rs.getString("subcat");
		    	  subcategory=subcategory.substring(0, 1).toUpperCase() + subcategory.substring(1);
		    	  isbn=rs.getString("isbn");
		    	  genre=rs.getString("genre");
		    	  
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
		      Date maxBidDate=null;
		      double maxBidAutoBid=0.0;
		      
		      sql= "SELECT * FROM Bid b1 WHERE b1.auctionId=" + auctionId + " AND amount IN (SELECT MAX(amount) FROM Bid b2 WHERE b2.auctionId=b1.auctionId)";
		      stmt=conn.prepareStatement(sql);
		      
		      rs=stmt.executeQuery();
		      
		      if(rs.next()) {
		    	 maxBid=rs.getDouble("amount");
		    	 maxBidUsername=rs.getString("username");
		    	 maxBidDate=new Date(rs.getTimestamp("time").getTime());
		    	 maxBidAutoBid=rs.getDouble("maxbid");
		      }
		      
		     
		      stmt.close();
		      
		      if (closeDate.getTime()<System.currentTimeMillis()) {
		    	  if(maxBid>=reservePrice&&maxBid>0.0) {
		    		  request.setAttribute("sold", true);
		    	  }
		    	  
		    	  else {
		    		  request.setAttribute("sold", false);
		    	  }
		      }
		      
		      
		      // view similar items: top 5 with same subcategory
		      
		      List<SearchResult> sr = new ArrayList<SearchResult>();
			  SearchResult r;
			    
		      PreparedStatement simst = null;
		      ResultSet simr = null;
		      String simq = "SELECT * FROM Auction A, Book B WHERE A.auctionid=B.auctionid AND B.subcat='" + subcategory + "' AND NOT A.auctionid=" + auctionId;
		      simst=conn.prepareStatement(simq);
		      simr = simst.executeQuery();
		      
		      String simtitle = null;
			  String simauthor = null;
			  String simseller = null;
			  float simprice = 0;
			  int simID = -1;
			  int count = 5;
			    
			  while(simr.next() && count > 0) {
					
				  simtitle = simr.getString("title");
				  simauthor = simr.getString("author");
				  simseller = simr.getString("seller");
				  simID = simr.getInt("auctionid");
					
				  String str2 = "SELECT MAX(B.amount) FROM Bid B WHERE B.auctionid=" + simID;
				  PreparedStatement stmt2 = conn.prepareStatement(str2);
				  ResultSet r2 = stmt2.executeQuery();
					if(r2 == null) {
						simprice = 0;
					} else {
						r2.next();
						simprice = r2.getFloat("MAX(B.amount)");
					}
					
					stmt2.close();
					r = new SearchResult(simtitle, simauthor, simseller, simprice, simID);
					sr.add(r);
					count--;
			  }
		      
			  simst.close();
		      // end similar results
		      
		      conn.close();
		      
		      //Set attributes for jsp
		      
		      request.setAttribute("openDate", new Date(openTs.getTime()));
		      request.setAttribute("closeDate", new Date(closeTs.getTime()));
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
		      request.setAttribute("genre", genre);
		      
		      request.setAttribute("maxBid", maxBid);
		      request.setAttribute("maxBidUsername", maxBidUsername);
		      request.setAttribute("maxBidDate", maxBidDate);
		      request.setAttribute("maxBidAutoBid", maxBidAutoBid);
		      request.setAttribute("minBid", maxBid+0.01);
		      
		      request.setAttribute("sr", sr); 
		      
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
			doGet(request,response);
			return;
		}
		String auctionId=(String)request.getParameter("auctionid");
		
		Timestamp currentTS=new Timestamp(System.currentTimeMillis());
		
		String username=(String)request.getSession().getAttribute("username");
		String bidAmount=(String)request.getParameter("bidAmount");
		String biddingType=(String)request.getParameter("Autobidding");
		String autoBidAmount=null;
		double autoBidDouble=0.0;
		if(biddingType.equals("autobidding")) {
			autoBidAmount=(String)request.getParameter("autoBidAmount");
			autoBidDouble=Double.parseDouble(autoBidAmount);
		}
		Connection conn=null;
		PreparedStatement stmt=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
		      
		      String sql;
		      
		      double maxBid=0.0;
		      String maxBidUsername=null;
		      String maxBidDate=null;
		      double maxBidAutoBid=0.0;
		      
		      sql= "SELECT * FROM Bid b1 WHERE b1.auctionId=" + auctionId + " AND amount IN (SELECT MAX(amount) FROM Bid b2 WHERE b2.auctionId=b1.auctionId)";
		      stmt=conn.prepareStatement(sql);
		      
		      ResultSet rs=stmt.executeQuery();
		      
		      if(rs.next()) {
		    	  maxBid=rs.getDouble("amount");
			    	 maxBidUsername=rs.getString("username");
			    	 maxBidDate=new Date(rs.getTimestamp("time").getTime()).toString();
			    	 maxBidAutoBid=rs.getDouble("maxbid");
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
		      
		      
		      if(maxBidUsername==null) {
		    	  
		      }
		      
		      //Case0 Max bid does not have autobidding
		      else if(maxBidAutoBid==0.0) {
		    	  if(!username.equals(maxBidUsername)) {
			    	  Timestamp sendTS=new Timestamp(System.currentTimeMillis());
			    	  Timestamp expiryTS=new Timestamp(System.currentTimeMillis()+ (long)7776000000L);
			    	  String message="Your bid on auction: " + auctionId + " for $" + maxBid + " was outbid by " + username + " who bid $" + bidAmount + ".";
			    	  sql="INSERT INTO Message(sender, receiver, sendtime, expirytime, contents) VALUES ('forum', '" + maxBidUsername + 
			    			  "', '" + sendTS + "', '" + expiryTS + "', '" + message + "')";
			    	  stmt=conn.prepareStatement(sql);
				      stmt.executeUpdate();
			      }
		      }
		      
		      //Case 1 Max bid has autobidding but its not enough
		      else if(maxBidAutoBid<=bidAmountDouble) {
		    	  if(!username.equals(maxBidUsername)) {
			    	  Timestamp sendTS=new Timestamp(System.currentTimeMillis());
			    	  Timestamp expiryTS=new Timestamp(System.currentTimeMillis()+ (long)7776000000L);
			    	  String message="Your max auto bid on auction: " + auctionId + " for $" + maxBidAutoBid + " was outbid by " + username + " who bid $" + bidAmount + ".";
			    	  sql="INSERT INTO Message(sender, receiver, sendtime, expirytime, contents) VALUES ('forum', '" + maxBidUsername + 
			    			  "', '" + sendTS + "', '" + expiryTS + "', '" + message + "')";
			    	  stmt=conn.prepareStatement(sql);
				      stmt.executeUpdate();
			      }
		      }
		      
		    //Case 2 Max bid has autobid higher than new bid and new bid doesnt have autobid
		      else if(maxBidAutoBid>bidAmountDouble&&autoBidAmount==null) {
		    	  double newBidAmount=bidAmountDouble+0.01;
		    	  
		    	  //Original max bidder wins
		    	  sql="INSERT INTO Bid(username,auctionid,time,amount,maxbid) VALUES ('" + maxBidUsername + "', " + auctionId + ", '" + currentTS
			    		  + "', " + newBidAmount + ", " + maxBidAutoBid + ")";
		    	  stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();
			      
		    	  if(!username.equals(maxBidUsername)) {
			    	  Timestamp sendTS=new Timestamp(System.currentTimeMillis());
			    	  Timestamp expiryTS=new Timestamp(System.currentTimeMillis()+ (long)7776000000L);
			    	  String message="Your bid on auction: " + auctionId + " for $" + bidAmount + " was outbid by " + maxBidUsername + " who bid $" + newBidAmount + ".";
			    	  sql="INSERT INTO Message(sender, receiver, sendtime, expirytime, contents) VALUES ('forum', '" + username + 
			    			  "', '" + sendTS + "', '" + expiryTS + "', '" + message + "')";
			    	  stmt=conn.prepareStatement(sql);
				      stmt.executeUpdate();
			      }
		    	  
		    	  
		      }
		      
		      //Case 3 Max bid has autobid higher than new bids autobid
		      else if(maxBidAutoBid>bidAmountDouble&&maxBidAutoBid>autoBidDouble) {
		    	  double newBidAmount=autoBidDouble+0.01;
		    	  sql="INSERT INTO Bid(username,auctionid,time,amount,maxbid) VALUES ('" + maxBidUsername + "', " + auctionId + ", '" + currentTS
			    		  + "', " + newBidAmount + ", " + maxBidAutoBid + ")";
		    	  stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();
			      
			      if(!username.equals(maxBidUsername)) {
			    	  Timestamp sendTS=new Timestamp(System.currentTimeMillis());
			    	  Timestamp expiryTS=new Timestamp(System.currentTimeMillis()+ (long)7776000000L);
			    	  String message="Your max auto bid on auction: " + auctionId + " for $" + autoBidAmount + " was outbid by " + maxBidUsername + " who bid $" + newBidAmount + ".";
			    	  sql="INSERT INTO Message(sender, receiver, sendtime, expirytime, contents) VALUES ('forum', '" + username + 
			    			  "', '" + sendTS + "', '" + expiryTS + "', '" + message + "')";
			    	  stmt=conn.prepareStatement(sql);
				      stmt.executeUpdate();
			      }
		    	  
		      }
		      
		      //Case 4 New bid has higher autobid
		      
		      else {
		    	  double newBidAmount=maxBidAutoBid+0.01;
		    	  sql = "INSERT INTO Bid(username,auctionid,time,amount,maxbid) VALUES ('" + username + "', " + auctionId + ", '" + currentTS
			    		  + "', " + newBidAmount + ", " + autoBidAmount + ")";
		    	  stmt=conn.prepareStatement(sql);
			      stmt.executeUpdate();
			      
			      if(!username.equals(maxBidUsername)) {
			    	  Timestamp sendTS=new Timestamp(System.currentTimeMillis());
			    	  Timestamp expiryTS=new Timestamp(System.currentTimeMillis()+ (long)7776000000L);
			    	  String message="Your max auto bid on auction: " + auctionId + " for $" + maxBidAutoBid + " was outbid by " + username + " who bid $" + newBidAmount + ".";
			    	  sql="INSERT INTO Message(sender, receiver, sendtime, expirytime, contents) VALUES ('forum', '" + maxBidUsername + 
			    			  "', '" + sendTS + "', '" + expiryTS + ", " + message + ")";
			    	  stmt=conn.prepareStatement(sql);
				      stmt.executeUpdate();
			      }
			      
		      }
		      
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