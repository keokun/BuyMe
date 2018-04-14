/*
 * Josh created this page
 */
public class ReportItem{
	public float getRes() {
		return res;
	}
	public void setRes(float res) {
		this.res = res;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	float res;
	String stat;
	public ReportItem(float res,String stat)
	{
		this.res = res;
		this.stat = stat;
	}
	
}