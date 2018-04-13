import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Kristen created this page */

@WebServlet("/advsearch")

public class AdvSearch extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("username") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		Connection conn = null;
		
		// query
		PreparedStatement qstmt = null;
		String q = null;
		ResultSet qresult = null;
		List<SearchResult> sr = new ArrayList<SearchResult>();
	    SearchResult r;
	    
	    // query 2 (for alerts)
	    PreparedStatement qstmt2 = null;
	    String q2 = null;
	    ResultSet qresult2 = null;
	    
	    // number of search results
	    PreparedStatement cstmt = null;
	    String c = null;
	    int count = -1;
	    ResultSet cresult = null;
		
		// search or alert?
		String search = request.getParameter("Search");
		String setAlert = request.getParameter("Alert");
		
		// search terms
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");
		String format = request.getParameter("format");
		String minpg = request.getParameter("minpg");
		String maxpg = request.getParameter("maxpg");
		String fictype = request.getParameter("ficType");
		String genre = request.getParameter("genre");
		String fsubcat = request.getParameter("ficSubcat");
		String ed = request.getParameter("editors");
		String pStyle = request.getParameter("pStyle");
		String cVolume = request.getParameter("volume");
		String cIssue = request.getParameter("issue");
		String subj = request.getParameter("subject");
		String nfsubcat = request.getParameter("nonficSubcat");
		String era = request.getParameter("era");
		String mIssue = request.getParameter("issueMagazine");
		String minpr = request.getParameter("minpr");
		String maxpr = request.getParameter("maxpr");
				
		try{
		      
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(BuyMe.DB_URL,BuyMe.USER,BuyMe.PASS);
			
			// SEARCH
			if(search != null && search.equals("Search")) {
			
				q = "SELECT * FROM Auction A, Book B WHERE A.auctionid=B.auctionid";
			    c = "SELECT COUNT(*) FROM Auction A, Book B WHERE A.auctionid=B.auctionid";
			    
				// if statements to add to query string
				if(isbn.length() > 0) {
					q = q + " AND B.isbn='" + isbn + "'";
					c = c + " AND B.isbn='" + isbn + "'";
				}
				
				if(title.length() > 0) {
					q = q + " AND B.title='" + title + "'";
					c = c + " AND B.title='" + title + "'";
				}
				
				if(author.length() > 0) {
					q = q + " AND B.author='" + author + "'";
					c = c + " AND B.author='" + author + "'";
				}
				
				if(publisher.length() > 0) {
					q = q + " AND B.publisher='" + publisher + "'";
					c = c + " AND B.publisher='" + publisher + "'";
				}
				
				if(format.length() > 0) {
					if(!format.equals("ns")) {
						q = q + " AND B.format='" + format + "'";
						c = c + " AND B.format='" + format + "'";
					}
				}
				
				if(minpg.length() > 0) {
					q = q + " AND B.pagect>=" + minpg;
					c = c + " AND B.pagect>=" + minpg;
				}
				
				if(maxpg.length() > 0) {
					q = q + " AND B.pagect<=" + maxpg;
					c = c + " AND B.pagect<=" + maxpg;
				}
				
				if(fictype.length() > 0) {
					if(fictype.equals("fic")) {
						q = q + " AND B.fictype=1";
						c = c + " AND B.fictype=1";
					}
					else if(fictype.equals("nonfic")) {
						q = q + " AND B.fictype=0";
						c = c + " AND B.fictype=0";
					}
				}
				
				if(genre.length() > 0) {
					q = q + " AND B.genre='" + genre + "'";
					c = c + " AND B.genre='" + genre + "'";
				}
				
				if(fsubcat.length() > 0) {
					if(!fsubcat.equals("ns")) {
						q = q + " AND B.subcat='" + fsubcat + "'";
						c = c + " AND B.subcat='" + fsubcat + "'";
					}
				}
				
				if(ed.length() > 0) {
					q = q + " AND B.attr='" + ed + "'";
					c = c + " AND B.attr='" + ed + "'";
				}
				
				if(pStyle.length() > 0) {
					q = q + " AND B.attr='" + pStyle + "'";
					c = c + " AND B.attr='" + pStyle + "'";
				}
				
				if(cVolume.length() > 0) {
					q = q + " AND B.attr='" + cVolume + "'";
					c = c + " AND B.attr='" + cVolume + "'";
				}
				
				if(cIssue.length() > 0) {
					q = q + " AND B.attr2='" + cIssue + "'";
					c = c + " AND B.attr2='" + cIssue + "'";
				}
				
				if(subj.length() > 0) {
					q = q + " AND B.genre='" + subj + "'";
					c = c + " AND B.genre='" + subj + "'";
				}
				
				if(nfsubcat.length() > 0) {
					if(!nfsubcat.equals("ns")) {
						q = q + " AND B.subcat='" + nfsubcat + "'";
						c = c + " AND B.subcat='" + nfsubcat + "'";
					}
				}
				
				if(era.length() > 0) {
					q = q + " AND B.attr='" + era + "'";
					c = c + " AND B.attr='" + era + "'";
				}
				
				if(mIssue.length() > 0) {
					q = q + " AND B.attr='" + mIssue + "'";
					c = c + " AND B.attr='" + mIssue + "'";
				}
				
				cstmt = conn.prepareStatement(c);
				cresult = cstmt.executeQuery();
				cresult.next();
				count = cresult.getInt("COUNT(*)");
				
				qstmt = conn.prepareStatement(q);
			    qresult = qstmt.executeQuery();

			    String rtitle = null;
			    String rauthor = null;
			    String rseller = null;
			    float rprice = 0;
			    int rauctionID = -1;
				
			    while(qresult.next()) {
					
			    	rtitle = qresult.getString("title");
					rauthor = qresult.getString("author");
					rseller = qresult.getString("seller");
					rauctionID = qresult.getInt("auctionid");
					
					// get current highest bid for this auction
					String str = "SELECT MAX(B.amount) FROM Bid B WHERE B.auctionid=" + rauctionID;
					PreparedStatement stmt = conn.prepareStatement(str);
					ResultSet res = stmt.executeQuery();
					if(res == null) {
						rprice = 0;
					} else {
						res.next();
						rprice = res.getFloat("MAX(B.amount)");
					}
					
					boolean min = true;
					boolean max = true;
					Float minprice;
					Float maxprice;
					boolean gthan = true;
					boolean lthan = true;
					
					// check against minimum price
					if(minpr.length() > 0) {
						// check for valid input
						for(int i = 0; i < minpr.length(); i++) {
							if(!Character.isDigit(minpr.charAt(i)) && minpr.charAt(i) != '.') {
								min = false;
							}
						}
						if(min == true) {
							// convert to float
							minprice = Float.valueOf(minpr);
							if(rprice < minprice) {
								gthan = false;
							}
						}	
					}
					
					// check against maximum price
					if(maxpr.length() > 0) {
						// check for valid input
						for(int i = 0; i < maxpr.length(); i++) {
							if(!Character.isDigit(maxpr.charAt(i)) && maxpr.charAt(i) != '.') {
								max = false;
							}
						}
						if(max == true) {
							// convert to float
							maxprice = Float.valueOf(maxpr);
							if(rprice > maxprice) {
								gthan = false;
							}
						}	
					}
					
					// if it is within price bounds add to search results
					if(gthan == true && lthan == true) {
						r = new SearchResult(rtitle, rauthor, rseller, rprice, rauctionID);
						sr.add(r);
					} else {
						count--;
					}
					
			    }
			    
			    request.setAttribute("count", count);
			    request.setAttribute("sr", sr);   
				request.getRequestDispatcher("/jsps/searchresults.jsp").forward(request, response);
			
			}
			
			// SET ALERT
			else if(setAlert != null && setAlert.equals("Set an Alert")) {
				
				// create time - format 2018-4-30 23:59:59
				Date date = new Date();
				String createtime = "2018-" + date.getMonth() + "-" + date.getDay() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
				
				q = "INSERT INTO Alert (username, createtime";
				q2 = " VALUES ('" + request.getSession().getAttribute("username") + "', " + "'" + createtime + "'";
				
				// if statements to add to query string
				if(minpr.length() > 0) {
					q = q + ", minprice";
					q2 = q2 + ", " + minpr;
				}
				
				if(maxpr.length() > 0) {
					q = q + ", maxprice";
					q2 = q2 + ", " + maxpr;
				}
				
				if(isbn.length() > 0) {
					q = q + ", isbn";
					q2 = q2 + ", '" + isbn + "'";
				}
				
				if(title.length() > 0) {
					q = q + ", title";
					q2 = q2 + ", '" + title + "'";
				}
				
				if(author.length() > 0) {
					q = q + ", author";
					q2 = q2 + ", '" + author + "'";
				}
				
				if(publisher.length() > 0) {
					q = q + ", publisher";
					q2 = q2 + ", '" + publisher + "'";
				}
				
				if(format.length() > 0) {
					if(!format.equals("ns")) {
						q = q + ", format";
						q2 = q2 + ", '" + format + "'";
					}
				}
				
				if(fictype.length() > 0) {
					if(fictype.equals("fic")) {
						q = q + ", fictype";
						q2 = q2 + ", 1";
					}
					else if(fictype.equals("nonfic")) {
						q = q + ", fictype";
						q2 = q2 + ", 0";
					}
				}
				
				if(fsubcat.length() > 0) {
					if(!fsubcat.equals("ns")) {
						q = q + ", subcat";
						q2 = q2 + ", '" + fsubcat + "'";
					}
				}
				
				if(nfsubcat.length() > 0) {
					if(!nfsubcat.equals("ns")) {
						q = q + ", subcat";
						q2 = q2 + ", '" + nfsubcat + "'";
					}
				}
				
				if(genre.length() > 0) {
					q = q + ", genre";
					q2 = q2 + ", '" + genre + "'";
				}
				
				if(subj.length() > 0) {
					q = q + ", genre";
					q2 = q2 + ", '" + subj + "'";
				}
				
				if(ed.length() > 0) {
					q = q + ", attr";
					q2 = q2 + ", '" + ed + "'";
				}
				
				if(pStyle.length() > 0) {
					q = q + ", attr";
					q2 = q2 + ", '" + pStyle + "'";
				}
				
				if(cVolume.length() > 0) {
					q = q + ", attr";
					q2 = q2 + ", '" + cVolume + "'";
				}
				
				if(era.length() > 0) {
					q = q + ", attr";
					q2 = q2 + ", '" + era + "'";
				}
				
				if(mIssue.length() > 0) {
					q = q + ", attr";
					q2 = q2 + ", '" + mIssue + "'";
				}
				
				if(cIssue.length() > 0) {
					q = q + ", attr2";
					q2 = q2 + ", '" + cIssue + "'";
				}
				
				if(minpg.length() > 0) {
					q = q + ", minpg";
					q2 = q2 + ", " + minpg;
				}
				
				if(maxpg.length() > 0) {
					q = q + ", maxpg";
					q2 = q2 + ", " + maxpg;
				}
				
				q = q + ")";
				q2 = q2 + ")";
				
				String alert;
				if(q.equals("INSERT INTO Alert (username, createtime)")) {
					alert = "<i>No search terms were entered, so an alert could not be set.</i>";
				} else {
					String query = q + q2;
					qstmt = conn.prepareStatement(query);
				    qstmt.executeUpdate();
					alert = "<i>Alert has been set. You will receive a message when an item matching your search becomes available.</i>";
				}
				
				request.setAttribute("alert", alert);   
				request.getRequestDispatcher("/jsps/advsearch.jsp").forward(request, response);
				
			}
			
			if(qstmt!=null) {
				qstmt.close();
			}
			if(cstmt!=null) {
				cstmt.close();
			}
		    conn.close();
		      
		   }catch(SQLException se){
		      se.printStackTrace();
	
		   }catch(Exception e){
		      e.printStackTrace();
		   }finally{
		      try{
		         if(qstmt!=null)
		            qstmt.close();
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
		
		request.getRequestDispatcher("/jsps/advsearch.jsp").forward(request, response);
		
	}

}
