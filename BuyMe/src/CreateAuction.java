import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * Kyle created this page
 *
 * Kristen added alerts functionality
 */
@WebServlet("/createauction")
public class CreateAuction extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public CreateAuction() {
		super();
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		if(request.getSession().getAttribute("username")==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		request.getRequestDispatcher("/jsps/createauction.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username=(String) request.getSession().getAttribute("username");
		
		String title=(String) request.getParameter("title");
		String author=(String) request.getParameter("author");
		String isbn=(String) request.getParameter("isbn");
		String publisher=(String) request.getParameter("publisher");
		String format=(String) request.getParameter("format");
		String fic=(String) request.getParameter("fictionType");
		boolean fictionType=false;
		if(fic.equals("fiction")) {
			fictionType=true;;
		}
		String reservePrice=(String) request.getParameter("reservePrice");
		
		String openDate=(String) request.getParameter("openDate");
		String closeDate=(String) request.getParameter("closeDate");
		String openHour=(String) request.getParameter("openHour");
		String closeHour=(String) request.getParameter("closeHour");
		String openMinutes=(String) request.getParameter("openMinutes");
		String closeMinutes=(String) request.getParameter("closeMinutes");
		
		
		String numPages=null;
		String genre=null;
		String subType=null;
		String param1=null;
		String param2=null;
		
		
		//Not an audiobook and thus has pages
		if(format.equals("physical")) {
			numPages=(String) request.getParameter("numPages");
		}
		
		//fiction
		if(fic.equals("fiction")) {
			subType=(String) request.getParameter("fictionSubType");
			genre=(String)request.getParameter("genre");
			
			if(subType.equals("anthology")) {
				param1=(String)request.getParameter("editors");
			}
			
			else if(subType.equals("poetry")) {
				param1=(String)request.getParameter("poetryStyle");
			}
			
			else if(subType.equals("comics")) {
				param1=(String)request.getParameter("volume");
				param2=(String)request.getParameter("issueComic");
			}
		}
		
		//non fiction
		else {
			subType=(String) request.getParameter("nonfictionSubType");
			genre=(String)request.getParameter("subject");
			
			if(subType.equals("biography")) {
				param1=(String)request.getParameter("era");
			}
			
			else if(subType.equals("magazine")) {
				param1=(String)request.getParameter("issueMagazine");
			}
			
		}
		
		//random int for auctionid
		Random random=new Random();
		int auctionId=random.nextInt(1000000);
		
		
		 Connection conn = null;
		 PreparedStatement stmt = null;
		 
		 try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
		      
		      //see if auction id exists
		      String sql;
		      sql = "SELECT * FROM Auction WHERE auctionid=" + auctionId;
		      stmt=conn.prepareStatement(sql);
		      ResultSet rs=stmt.executeQuery();
		      
		      //auction id needs to be unique
		      while(rs.next()) {
		    	  auctionId=random.nextInt(1000000);
		    	  sql = "SELECT * FROM Auction WHERE auctionid=" + auctionId;
			      stmt=conn.prepareStatement(sql);
			      rs=stmt.executeQuery();
		      }
		      
		      //parse open and close dates using simple date format and then create timestamps from them
		      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		      sdf.setTimeZone(TimeZone.getTimeZone("EST"));
		      
		      Date dateOpen=sdf.parse(openDate);
		      
		      dateOpen.setTime(dateOpen.getTime());
		      
		      //System.out.println("Before:" + dateOpen);
		      
		      dateOpen.setHours(Integer.parseInt(openHour));
		      dateOpen.setMinutes(Integer.parseInt(openMinutes));
		      
		      //System.out.println("After:" + dateOpen);
		      
		      Timestamp openTS= new Timestamp(dateOpen.getTime());
		      
		      Date dateClose=sdf.parse(closeDate);
		      
		      dateClose.setTime(dateClose.getTime());
		      
		      dateClose.setHours(Integer.parseInt(closeHour));
		      dateClose.setMinutes(Integer.parseInt(closeMinutes));
		      
		      Timestamp closeTS=new Timestamp(dateClose.getTime());
		      
		      //insert new auctin entry
		      sql="INSERT INTO Auction(posttime, endtime, auctionid, reserve, seller) VALUES ('" + openTS + "', '" + closeTS + "', " + auctionId
		    		  + ", " + reservePrice + ", '" + username + "')";
		      
		      //System.out.println(sql);
		      
		      stmt=conn.prepareStatement(sql);
		      stmt.executeUpdate();
		      
		      
		      //insert new book entry
		      if(numPages!=null&&param1!=null&&param2!=null)
			      sql="INSERT INTO Book(isbn,title,author,format,pagect,publisher,fictype,subcat,attr,attr2,auctionid, genre) VALUES ('" +
			    		  isbn + "', '" + title + "', '" + author + "', '" + format + "', " + numPages + ", '" + publisher + "', "
			    		  + fictionType + ", '" + subType + "', '" + param1 + "', '" + param2 + "', " + auctionId + ", '" + genre + "')";
		      else if (numPages!=null &&param1!=null)
		    	  sql="INSERT INTO Book(isbn,title,author,format,pagect,publisher,fictype,subcat,attr,auctionid, genre) VALUES ('" +
			    		  isbn + "', '" + title + "', '" + author + "', '" + format + "', " + numPages + ", '" + publisher + "', "
			    		  + fictionType + ", '" + subType + "', '" + param1 + "', " + auctionId + ", '" + genre + "')";
		      else if (numPages!=null)
		    	  sql="INSERT INTO Book(isbn,title,author,format,pagect,publisher,fictype,subcat,auctionid, genre) VALUES ('" +
			    		  isbn + "', '" + title + "', '" + author + "', '" + format + "', " + numPages + ", '" + publisher + "', "
			    		  + fictionType + ", '" + subType + "', " + auctionId + ", '" + genre + "')";
		      else if (param2!=null)
		    	  sql="INSERT INTO Book(isbn,title,author,format,publisher,fictype,subcat,attr,attr2,auctionid, genre) VALUES ('" +
			    		  isbn + "', '" + title + "', '" + author + "', '" + format + "', '" + publisher + "', "
			    		  + fictionType + ", '" + subType + "', '" + param1 + "', '" + param2 + "', " + auctionId + ", '" + genre + "')";
		      else if (param1!=null)
		    	  sql="INSERT INTO Book(isbn,title,author,format,publisher,fictype,subcat,attr,auctionid, genre) VALUES ('" +
			    		  isbn + "', '" + title + "', '" + author + "', '" + format + "', '" + publisher + "', "
			    		  + fictionType + ", '" + subType + "', '" + param1 + "', " +   auctionId + ", '" + genre + "')";
		      else 
		    	  sql="INSERT INTO Book(isbn,title,author,format,publisher,fictype,subcat,auctionid, genre) VALUES ('" +
			    		  isbn + "', '" + title + "', '" + author + "', '" + format + "', '" + publisher + "', "
			    		  + fictionType + ", '" + subType + "', " +  auctionId + ", '" + genre + "')";
		      
		      stmt=conn.prepareStatement(sql);
		      //System.out.println(stmt.toString());
		      stmt.executeUpdate();
		      
		      
		      // CHECK ALERTS
		      String q = "SELECT A.username FROM Alert A WHERE (A.title IS NULL OR A.title='" + title + "') AND (A.author IS NULL OR A.author='" + author + "') AND (A.isbn IS NULL OR A.isbn='" + isbn + "') AND (A.publisher IS NULL OR A.publisher='" + publisher + "') AND (A.format IS NULL OR A.format='" + format + "') AND (A.fictype IS NULL OR A.fictype=" + fictionType + ") AND (A.subcat IS NULL OR A.subcat='" + subType + "') AND (A.genre IS NULL OR A.genre='" + genre + "')";
		      if(param1 != null) {
		    	  q = q + " AND (A.attr IS NULL OR A.attr='" + param1 + "')";
		      }			
		      if(param2 != null) {
		    	  q = q + " AND (A.attr2 IS NULL OR A.attr2='" + param2 + "')";
		      }
		      if(numPages != null) {
		    	  q = q + " AND (A.minpg IS NULL OR A.minpg<=" + numPages + ") AND (A.maxpg IS NULL OR A.maxpg>=" + numPages + ")";
		      }
		      
		      // get alerts
		      PreparedStatement astmt = null;
		      ResultSet aresult = null;
		      astmt = conn.prepareStatement(q);
		      aresult = astmt.executeQuery();
		      
		      String receiver = null;
		      String sender = "alert@buyme.com";
		      String contents = null;
		      String sendtime = null;
		      String expirytime = null;
		      Date date;
		      String msg = null;
		      PreparedStatement mstmt = null;
		      ResultSet mresult = null;
		      
		      // send alerts
		      while(aresult.next()) {
			    	receiver = aresult.getString("username");
					// create time - format 2018-4-30 23:59:59
					Date adate = new Date();
					int year = adate.getYear() - 100 + 2000;
					int month = adate.getMonth() + 1;
					int day = adate.getDate();
					sendtime = year + "-" + month + "-" + day + " " + adate.getHours() + ":" + adate.getMinutes() + ":" + adate.getSeconds();
					adate = new Date();
					if(month == 12) {
						month = 1;
					} else {
						month++;
					}
					adate.setMonth(month);
					expirytime = year + "-" + month + "-" + day + " " + adate.getHours() + ":" + adate.getMinutes() + ":" + adate.getSeconds();
			    	contents = "[ALERT] Auction ID #" + auctionId +", sold by " + username + ", matches your search criteria.";
			    	
			    	msg = "INSERT INTO Message (sender, receiver, sendtime, expirytime, contents) VALUES ('" + sender + "', '" + receiver + "', '" + sendtime + "', '" + expirytime + "', '" + contents + "')";
			    	mstmt = conn.prepareStatement(msg);
				    mstmt.executeUpdate();
				    
		      }
		      

		      stmt.close();
		      conn.close();
		      
		      //add auction id as paramter and redirect to the auctionview page for the newly created auction
		      response.sendRedirect(request.getContextPath() + "/auctionview?auctionid=" + auctionId);
		      
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


}