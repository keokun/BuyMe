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
		if (request.getParameter("rtype") != null)
		{
			
			String reportType = request.getParameter("rtype");
			String sql = "";
			Connection conn = null;
			PreparedStatement stmt = null;
			String timeStamp;
			String stat = "error";
			 
			 try{
			      Class.forName("com.mysql.jdbc.Driver");

			      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
			  
			      
			      /* Obtain every auction whose endpoint is before the current timestamp*/
			      java.util.Date date = new java.util.Date();
			      Timestamp currTime = new Timestamp(date.getTime());
			      
			      
			     
			      switch (reportType)
					{
						case "TotEarn":
							
							
							sql="SELECT SUM(B.amount) AS res" + 
									" FROM Bid B, Auction A" + 
									" WHERE B.auctionid = A.auctionid AND B.amount > A.reserve AND A.endtime < '" + currTime + "' AND B.amount IN (SELECT MAX(b2.amount)"
									+ " FROM Bid b2 WHERE b2.auctionid=A.auctionid)";
							
							stmt=conn.prepareStatement(sql);
							
							stat = null;
							
							break;
							
						case "EpItemType":
							
							sql="SELECT sum(amount) AS res, J.subcat " + 
									"FROM Bid B, Auction A, Book J " + 
									"WHERE B.auctionid = A.auctionid AND B.auctionid=J.auctionid AND B.amount > A.reserve AND A.endtime < '" + currTime + "' AND B.amount IN (SELECT MAX(b2.amount) FROM Bid b2 WHERE b2.auctionid=A.auctionid) " + 
									"GROUP BY J.subcat Order BY Res DESC";
							
							
							
							stat = "subcat";
							
							break;
							
							
						case "EpItem":
							sql="SELECT sum(amount) AS res, J.isbn, J.title " + 
									"FROM Bid B, Auction A, Book J " + 
									"WHERE B.auctionid = A.auctionid AND B.auctionid=J.auctionid AND B.amount > A.reserve AND A.endtime < '" + currTime + "' AND B.amount IN (SELECT MAX(b2.amount) FROM Bid b2 WHERE b2.auctionid=A.auctionid) " + 
									"GROUP BY J.isbn Order BY Res DESC";
							
							stat = "isbn";
							
							break;
						case "EpEndUser":
							sql="SELECT sum(amount) AS res, A.seller " + 
									"FROM Bid B, Auction A, Book J " + 
									"WHERE B.auctionid = A.auctionid AND B.auctionid=J.auctionid AND B.amount > A.reserve AND A.endtime < '" + currTime + "' AND B.amount IN (SELECT MAX(b2.amount) FROM Bid b2 WHERE b2.auctionid=A.auctionid) " + 
									"GROUP BY A.seller Order BY Res DESC";
							stat = "seller";

							break;
							
						case "BBuyers":
							sql="SELECT sum(amount) AS res, B.username " + 
									"FROM Bid B, Auction A, Book J " + 
									"WHERE B.auctionid = A.auctionid AND B.auctionid=J.auctionid AND B.amount > A.reserve AND A.endtime < '" + currTime + "' AND B.amount IN (SELECT MAX(b2.amount) FROM Bid b2 WHERE b2.auctionid=A.auctionid) " + 
									"GROUP BY B.username Order BY Res DESC";
							stat = "username";
							
							break;
							
						case "BSitems":
							sql="SELECT count(*) AS res, J.isbn " + 
									"FROM Bid B, Auction A, Book J " + 
									"WHERE B.auctionid = A.auctionid AND B.auctionid=J.auctionid AND B.amount > A.reserve AND A.endtime < '" + currTime + "' AND B.amount IN (SELECT MAX(b2.amount) FROM Bid b2 WHERE b2.auctionid=A.auctionid) " + 
									"GROUP BY J.isbn Order BY Res DESC";
							stat = "isbn";
							
							
							break;
							

						default:
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
							return;
							
					}
			      
			      stmt=conn.prepareStatement(sql);
			      ResultSet rs = stmt.executeQuery();
			      

			      List<ReportItem> reps = new ArrayList<ReportItem>();
			      
			      
			      if (stat!=null&&stat.equals("error"))
			      {
			    	  request.setAttribute("sres", false);
			    	  request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);
			      }
			      
			      /* iterate over the result set and store into a list*/
			      while(rs.next())
			      {
			    	  //Each index gets a 'stat', which is the category we're organizing by
			    	  //and a sum of sales for that stat
			    	  
			    	  float result = rs.getFloat("res");
			    	  String str=null;
			    	  if(stat!=null) {
			    		  str=rs.getString(stat);
			    	  }
			    	  
			    	  ReportItem ri = new ReportItem(result,str);
			    	  reps.add(ri);
			    	  
			      }
			      
			  

			      stmt.close();
			      conn.close();
			      
			      /* On Success*/
			      
			      request.setAttribute("sres", true);
			      request.setAttribute("salestable", reps);
			      
			      //If we are getting earnings of a particular category
			      if (!(reportType.equals("TotEarn")))
		    	  {
		    	  	request.setAttribute("cat", stat);
		    	  	request.setAttribute("justSum", false);
		    	  }
			      
			      //If we are just getting total earnings
			      else
			      {
			    	  request.setAttribute("justSum",true);
			      }
			      
			      request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);	
		    	
			      
		    	  /* On Failure*/
			      
			   }
			 
			 
			 catch(SQLException se){
			      se.printStackTrace();
			      request.setAttribute("sres",false);
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
		
		else
		{
			request.setAttribute("sres", false);
			request.getRequestDispatcher("/jsps/adminpage.jsp").forward(request, response);
		}
		
	
			
		
	}//end doPost method
	
	

}//End SalesReport.java
