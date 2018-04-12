import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Kristen created this page */

@WebServlet("/sortby")

public class SortBy extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("username") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		Connection conn = null;
		PreparedStatement stmt = null; // for main query
		PreparedStatement stmt2 = null; // for price
		String str = null; // for main query
		String str2 = null; // for price
		ResultSet result = null; // for main query
		ResultSet result2 = null; // for price
	    
		String s = request.getParameter("sort");
		List<SearchResult> sr = new ArrayList<SearchResult>();
	    SearchResult r;
	    String sb = "";
		 
		try{
		      
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
			
			// sort by book type
			if(s.equals("btype")) {
				str = "SELECT * FROM Auction A, Book B WHERE A.auctionid=B.auctionid ORDER BY B.subcat";	
			}
			
			// sort by price -> low to high
			else if(s.equals("plh")){
				str = "SELECT B.title, B.author, A.seller, A.auctionid, MAX(P.amount) FROM Auction A, Book B, Bid P WHERE A.auctionid=B.auctionid AND B.auctionid=P.auctionid GROUP BY A.auctionid ORDER BY P.amount";
				str2 = "SELECT * FROM Auction A, Book B WHERE A.auctionid=B.auctionid AND A.auctionid NOT IN (SELECT A.auctionid FROM Auction A, Bid P WHERE A.auctionid=P.auctionid)";
			}
			
			// sort by price -> high to low
			else if(s.equals("phl")) {
				str = "SELECT B.title, B.author, A.seller, A.auctionid, P.amount FROM Auction A, Book B, Bid P WHERE A.auctionid=B.auctionid AND B.auctionid=P.auctionid AND (P.amount, P.auctionid) IN (SELECT MAX(P.amount), P.auctionid FROM Bid P GROUP BY P.auctionid) ORDER BY P.amount DESC";
				str2 = "SELECT * FROM Auction A, Book B WHERE A.auctionid=B.auctionid AND A.auctionid NOT IN (SELECT A.auctionid FROM Auction A, Bid P WHERE A.auctionid=P.auctionid)";
			}
			
			// no sorting
			else {
				str = "SELECT * FROM Auction A, Book B WHERE A.auctionid=B.auctionid";
			}
			
		    stmt = conn.prepareStatement(str);
		    result = stmt.executeQuery();
		    
		    String title = null;
		    String author = null;
		    String seller = null;
		    float price = 0;
		    int auctionID = -1;
		    
		    // if no sorting or sorting by book type
		    if(s.equals("no") || s.equals("btype")) {
		    	
		    	while(result.next()) {
					
			    	title = result.getString("title");
					author = result.getString("author");
					seller = result.getString("seller");
					auctionID = result.getInt("auctionid");
					
					str2 = "SELECT MAX(B.amount) FROM Bid B WHERE B.auctionid=" + auctionID;
					stmt2 = conn.prepareStatement(str2);
					result2 = stmt2.executeQuery();
					if(result2 == null) {
						price = 0;
					} else {
						result2.next();
						price = result2.getFloat("MAX(B.amount)");
					}
					
					r = new SearchResult(title, author, seller, price, auctionID);
					sr.add(r);
					
		    	}
		    	
		    	if(s.equals("btype")) {
		    		sb = "<i>Sorted By Book Type</i>";
		    	}
		    
		    }
		    
		    // sort by price -> low to high
	    	else if(s.equals("plh")) {
	    		
	    		stmt2 = conn.prepareStatement(str2);
			    result2 = stmt2.executeQuery();
	    		
	    		// add auctions with no bids
	    		while(result2.next()) {
			    	title = result2.getString("title");
					author = result2.getString("author");
					seller = result2.getString("seller");
					auctionID = result2.getInt("auctionid");
					price = 0;
					r = new SearchResult(title, author, seller, price, auctionID);
					sr.add(r);
	    		}
	    		
	    		// add auctions with bids
	    		while(result.next()) {
			    	title = result.getString("title");
					author = result.getString("author");
					seller = result.getString("seller");
					auctionID = result.getInt("auctionid");
					price = result.getFloat("MAX(P.amount)");
					r = new SearchResult(title, author, seller, price, auctionID);
					sr.add(r);
	    		}
	    		
	    		sb = "<i>Sorted By Price: Low to High</i>";
	    		
	    	}
		    
		    // sort by price -> high to low
	    	else if(s.equals("phl")) {
	    		
	    		stmt2 = conn.prepareStatement(str2);
			    result2 = stmt2.executeQuery();
	    	
	    		// add auctions with bids
	    		while(result.next()) {
			    	title = result.getString("title");
					author = result.getString("author");
					seller = result.getString("seller");
					auctionID = result.getInt("auctionid");
					price = result.getFloat("amount");
					r = new SearchResult(title, author, seller, price, auctionID);
					sr.add(r);
	    		}
	    		
	    		// add auctions with no bids
	    		while(result2.next()) {
			    	title = result2.getString("title");
					author = result2.getString("author");
					seller = result2.getString("seller");
					auctionID = result2.getInt("auctionid");
					price = 0;
					r = new SearchResult(title, author, seller, price, auctionID);
					sr.add(r);
	    		}
	    		
	    		sb = "<i>Sorted By Price: High to Low</i>";
	    		
	    	}
		    
			request.setAttribute("sr", sr);
			request.setAttribute("sb", sb);
			request.getRequestDispatcher("/jsps/sortby.jsp").forward(request, response);
			
			stmt.close();
		    conn.close();
		      
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
		
		request.getRequestDispatcher("/jsps/sortby.jsp").forward(request, response);
		
	}
	
}
