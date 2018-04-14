
public class Message {
	
	public String sender;
	public String receiver;
	public String sendtime;
	public String contents;
	
	public Message(String sender, String receiver, String sendtime, String contents) {
		this.sender=sender;
		this.receiver=receiver;
		this.sendtime=sendtime;
		this.contents=contents;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public String getSendtime() {
		return sendtime;
	}
	
	public String getContents() {
		return contents;
	}
}
