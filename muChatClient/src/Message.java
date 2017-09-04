public class Message {
	private String from;
	private String message;
	
	public Message(String from, String message) {
		this.from = from;
		this.message = message;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getMessage() {
		return this.message;
	}
}
