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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SalesReport")
public class SalesReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SalesReport() {
		super();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* If we are looking for sales report*/
		if (request.getParameter("SReport") != null)
		{
			
			String reportType = request.getParameter("SReport");
			String sql = "";
			Connection conn = null;
			PreparedStatement stmt = null;
			String timeStamp;
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
			      
			      /* Obtain every auction whose endpoint is before the current timestamp*/
			      java.util.Date date = new java.util.Date();
			      Timestamp currTime = new Timestamp(date.getTime());
			      
			      String stat = null;
			     
			      switch (reportType)
					{
						case "TotEarn":
							sql = 
								//the sum of the amounts sold
								"SELECT SUM(ps.sold) AS res"
									+ "FROM ("
										+ "SELECT max(amount) AS sold,endtime"
										+ "FROM Bid B, Auction A "
										+ "WHERE B.auctionid = A.auctionid AND endtime < ? AND sold > A.reserve"
										+ "GROUP BY auctionid"
									+ ")ps";
							
							stmt.setTimestamp(1, currTime);
							
							stat = "sum";
							
							break;
							
						case "EpItemType":
							sql = 
								//getting the sum of amounts sold for each genre 
								"SELECT SUM(ps.sold) AS res, genre "
									//getting the price sold of every book that's been on auction
									//organized by different auctions
									+ "FROM ("
										+ "SELECT MAX(amount) AS sold,endtime genre"
										+ "FROM Bid B, Auction A "
										+ "WHERE B.auctionid = A.auctionid AND endtime < ? AND sold > A.reserve"
										+ "GROUP BY auctionid"
									+ ") ps"

								+ "GROUP BY ps.genre";
							stmt.setTimestamp(1, currTime);
							
							stat = "genre";
							
							break;
							
							
						case "EpItem":
							sql =  
									//getting the sum of amounts sold for each book 
									"SELECT SUM(ps.sold) AS res, ISBN "
										//getting the price sold of every book that's been on auction
										//organized by different auctions
										+ "FROM ("
											+ "SELECT MAX(amount) AS sold,endtime, ISBN"
											+ "FROM Bid B, Auction A "
											+ "WHERE B.auctionid = A.auctionid AND endtime < ? AND sold > A.reserve"
											+ "GROUP BY auctionid"
										+ ") ps"

									+ "GROUP BY ps.ISBN";
							stmt.setTimestamp(1, currTime);
							
							stat = "ISBN";
							
							break;
						case "EpEndUser":
							sql = 
								//getting the sum of amounts sold for each genre 
								"SELECT SUM(ps.sold) AS res,seller "
									//getting the price sold of every book that's been on auction
									//organized by different auctions
									+ "FROM ("
										+ "SELECT MAX(amount) AS sold,endtime, seller"
										+ "FROM Bid B, Auction A "
										+ "WHERE B.auctionid = A.auctionid AND endtime < ? AND sold > A.reserve"
										+ "GROUP BY auctionid"
									+ ") ps"
	
								+ "GROUP BY ps.seller";
							stmt.setTimestamp(1, currTime);
							stat = "seller";

							break;
							
						case "BBuyers":
							sql = 
									//getting the sum of amounts sold for each genre 
									"SELECT SUM(ps.sold) AS res,username "
										//getting the price sold of every book that's been on auction
										//organized by different auctions
										+ "FROM ("
											+ "SELECT MAX(amount) AS sold,endtime, username"
											+ "FROM Bid B, Auction A "
											+ "WHERE B.auctionid = A.auctionid AND endtime < ? AND sold > A.reserve"
											+ "GROUP BY auctionid"
										+ ") ps"
		
									+ "GROUP BY ps.username";
							stat = "username";
							
							stmt.setTimestamp(1, currTime);
							break;
							

						default:
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
							break;
					}
			      
			      stmt=conn.prepareStatement(sql);
			      ResultSet rs = stmt.executeQuery();
			      
			      List<Float> sumarr = new ArrayList<Float>();
			      List<String> cat = new ArrayList<String>();
			      
			      
			      /* iterate over the result set and store into a list*/
			      while(rs.next())
			      {
			    	  sumarr.add(rs.getFloat("res"));
			    	  cat.add(rs.getString(stat));
			    	  
			    	  
			      }
			      

			      stmt.close();
			      conn.close();
			      
			      /* On Success*/
			      request.setAttribute("success", true);
			      request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);	
		    	
			      
		    	  /* On Failure*/
			      
			   }catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("success",false);
			      request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);	
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
		
	
			
		
	}//end doPost method
	
	

}//End SalesReport.java
