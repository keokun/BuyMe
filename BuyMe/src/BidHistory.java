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

@WebServlet("/BidHistory")

public class BidHistory extends HttpServlet {
	
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	public BidHistory()
	{
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/history.jsp").forward(request, response);
	}//End doGet
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String button = request.getParameter("button");
		
	
			int auction = Integer.parseInt(request.getParameter("auction"));
			
			Connection conn = null;
			PreparedStatement stmt = null;
			
			try{
				
				Class.forName("com.mysql.jdbc.Driver");
	
				conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				String sql;
			
				/* Get auction and books where the bid is made by the user*/
				sql = "SELECT * FROM Bid B , Auction A, Book C WHERE A.auctionid = '" + auction + "' , "
						+ " AND A.auctionid = B.auctionid AND B.auctionid = C.auctionid ";
				
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				
				List<BidObject> bol = new ArrayList<BidObject>();
				List<Timestamp> tsl = new ArrayList<Timestamp>();
				
				while(rs.next())
				{
					Timestamp ts = rs.getTimestamp("time");
			    	
					//just trying to filter out the duplicates
					if (!tsl.contains(ts))
					{
						tsl.add(ts);
						float amount = rs.getFloat("amount");
						float maxbid = rs.getFloat("maxbid");
						String seller = rs.getString("seller");
						String book = rs.getString("title");
						
						BidObject element = new BidObject(ts,amount,maxbid,seller,book);
						
						bol.add(element);
					}
			    
				}
				
				/* On SUCCESS */
				
				int newsize = bol.size();
				
				
				request.setAttribute("bidsuccess", true);
				request.setAttribute("newsize", newsize);
				request.setAttribute("bidtable",bol);
				
			}
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("bidsuccess",false);
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
		
		
		
		
	}//End doPost
	
	
}//End BidHistory
