/*
 * Kristen created this page
 */
public class SearchResult{
	
	String title;
    String author;
    String seller;
    float price;
    int auctionID;
	
	public SearchResult(String title, String author, String seller, float price, int auctionID) {
		this.title = title;
		this.author = author;
		this.seller = seller;
		this.price = price;
		this.auctionID = auctionID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getSeller() {
		return seller;
	}
	
	public float getPrice() {
		return price;
	}
	
	public int getAuctionID() {
		return auctionID;
	}

}