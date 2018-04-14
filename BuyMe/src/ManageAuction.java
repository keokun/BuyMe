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
/*
 * Josh created this page
 */
@WebServlet("/manageauction")
public class ManageAuction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public ManageAuction()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);
	}//End doGet
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String button = request.getParameter("button");
		java.util.Date date = new java.util.Date();
		Timestamp currTime = new Timestamp(date.getTime());
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		
		if (button.equals("delBid"))
		{
			String username = request.getParameter("username");
			int auctionid = Integer.parseInt(request.getParameter("auctionid"));
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				
				conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				String sql;
				
				//get all the auctions and bids that are still going on now and match the username inputted.

				
				sql = "SELECT * FROM Auction A, Bid B WHERE A.auctionid = B.auctionid AND B.auctionid = " + auctionid + 
						" AND B.username = '" + username + "' AND A.endtime > ?";
				stmt = conn.prepareStatement(sql);
				stmt.setTimestamp(1, currTime);
				ResultSet rs = stmt.executeQuery();
				
				//if there exists a bid, we delete it
				if (rs.next())
				{
					sql = "DELETE FROM Bid  WHERE auctionid = " + auctionid + " AND username = '" + username + "' "
							+ "AND time < ? " ;
					stmt = conn.prepareStatement(sql);
					stmt.setTimestamp(1, currTime);
					stmt.executeUpdate();
					
					request.setAttribute("delbsuccess", true);
					request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);
				}
				
				//if there does not exist such a bid we can't delete
				else
				{
				      request.setAttribute("delbsuccess",false);
				      request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);
				}
				
			}
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("delbsuccess",false);
			      request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);	
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
			
			
		}// end delBid
		
		if (button.equals("delAuc"))
		{
			int auctionid = Integer.parseInt(request.getParameter("auctionid"));
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				
				conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
				String sql;
				
				//get all the auctions and bids that are still going on now and match the username inputted.

				
				sql = "SELECT * FROM Auction A WHERE A.auctionid = " + auctionid;
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				
				//if there exists an auction with this auctionid, we delete it
				if (rs.next())
				{
					sql = "DELETE FROM Auction WHERE auctionid = " + auctionid;
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					
					request.setAttribute("delasuccess", true);
					request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);
				}
				
				//if there does not exist such a bid we can't delete
				else
				{
				      request.setAttribute("delasuccess",false);
				      request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);
				}
				
			}
			
			catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("delasuccess",false);
			      request.getRequestDispatcher("/jsps/rephome.jsp").forward(request, response);	
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
			
			
		}// end delBid
		
		
		
	}//end doPost
	
	
}//End manageauction
