/**
 * 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * 2018년도 2학기 관찰자 패턴 실습
 * ChatMessage
 * 채팅 메시지 클래스 
 * 전송자와 전송 메시지 유지
 * @author 김상진
 *
 */
public class ChatMessage {
	private String userID;
	private String message;
	public ChatMessage(String userID, String message) {
		this.userID = userID;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public String getUserID() {
		return userID;
	}
}
