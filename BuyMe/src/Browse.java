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

@WebServlet("/browse")

public class Browse extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("username") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String str = null;
		ResultSet result = null;
		
	    List<SearchResult> sr = new ArrayList<SearchResult>();
	    SearchResult r;
		 
		try{
		      
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
		    
		    str = "SELECT * FROM Auction A, Book B WHERE A.auctionid=B.auctionid";
		    stmt = conn.prepareStatement(str);
		    result = stmt.executeQuery();
		    
		    String title = null;
		    String author = null;
		    String seller = null;
		    float price = 0;
		    int auctionID = -1;
		    
		    while(result.next()) {
				
		    	title = result.getString("title");
				author = result.getString("author");
				seller = result.getString("seller");
				auctionID = result.getInt("auctionid");
				
				String str2 = "SELECT MAX(B.amount) FROM Bid B WHERE B.auctionid=" + auctionID;
				PreparedStatement stmt2 = conn.prepareStatement(str2);
				ResultSet r2 = stmt2.executeQuery();
				if(r2 == null) {
					price = 0;
				} else {
					r2.next();
					price = r2.getFloat("MAX(B.amount)");
				}
				
				r = new SearchResult(title, author, seller, price, auctionID);
				sr.add(r);
			
		    }
		    
			request.setAttribute("sr", sr);   
			request.getRequestDispatcher("/jsps/browse.jsp").forward(request, response);
			
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
		
		request.getRequestDispatcher("/jsps/browse.jsp").forward(request, response);
		
	}
}