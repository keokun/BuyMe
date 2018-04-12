import java.sql.Timestamp;

public class AuctionObject {
	
	
	Timestamp posttime;
	Timestamp endtime;
	int auctionid;
	float reserve;
	String seller;
	
	
	public AuctionObject(Timestamp posttime, Timestamp endtime, int auctionid, float reserve, String seller) {
		super();
		this.posttime = posttime;
		this.endtime = endtime;
		this.auctionid = auctionid;
		this.reserve = reserve;
		this.seller = seller;
	}


	public Timestamp getPosttime() {
		return posttime;
	}


	public void setPosttime(Timestamp posttime) {
		this.posttime = posttime;
	}


	public Timestamp getEndtime() {
		return endtime;
	}


	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}


	public int getAuctionid() {
		return auctionid;
	}


	public void setAuctionid(int auctionid) {
		this.auctionid = auctionid;
	}


	public float getReserve() {
		return reserve;
	}


	public void setReserve(float reserve) {
		this.reserve = reserve;
	}


	public String getSeller() {
		return seller;
	}


	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	
	
	


}//end AuctionObject Class
