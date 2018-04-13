import java.sql.Timestamp;

public class BidObject {
	Timestamp time;
	float amount;
	float maxbid;
	String seller;
	String book;
	public BidObject(Timestamp time, float amount, float maxbid, String seller, String book) {
		super();
		this.time = time;
		this.amount = amount;
		this.maxbid = maxbid;
		this.seller = seller;
		this.book = book;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getMaxbid() {
		return maxbid;
	}
	public void setMaxbid(float maxbid) {
		this.maxbid = maxbid;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	

}//End BidObject class
