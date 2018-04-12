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

public class AuctionHistory extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public AuctionHistory() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/auctionhistory.jsp").forward(request, response);
	}//End doGet
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = (String) request.getSession().getAttribute("username");
		
		String button = request.getParameter("button");
		
		//auctionhistory of the user currently logged in.
		if(("auctionH").equals(button))
		{
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
			      sql = "SELECT * FROM Auction A, Bid B WHERE (A.seller = '" + username + "' OR ) AND endtime <= ?";
			      
			      //prep the sql statement
			      stmt = conn.prepareStatement(sql);
			      stmt.setTimestamp(1, currTime);
			      
			      ResultSet rs = stmt.executeQuery();
			      
			      List<AuctionObject> lao = new ArrayList<AuctionObject>();
			      
			      
			      while (rs.next())
			      {
			    	  Timestamp posttime = rs.getTimestamp("posttime");
			    	  Timestamp endtime = rs.getTimestamp("endtime");
			    	  int auctionid = rs.getInt("auctionid");
			    	  float reserve = rs.getFloat("reserve");
			    	  String seller = rs.getString("seller");
			    	  
			    	  AuctionObject ao = new AuctionObject(posttime,endtime, auctionid, reserve, seller);
			    	  
			    	  lao.add(ao);
			    	  
			      }
			      
			      
			     
				      
			      request.setAttribute("success", true);
			      request.setAttribute("ahtable", lao);
			      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsps/auctionhistory.jsp");
		    	  dispatcher.forward(request,response);
			      
			      
			      
			   }//End try statement
			
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/auctionhistory.jsp").forward(request, response);	
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
		
		
	}//End doPost
	
	
}//end AuctionHistory class
