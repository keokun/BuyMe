
public class ForumItem{
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	int tid;
	String name;
	String content;
	public ForumItem(int tid, String name, String content)
	{
		this.tid = tid;
		this.name = name;
		this.content = content;
	}
	public ForumItem( String name, String content)
	{
		this.name = name;
		this.content = content;
	}
}
