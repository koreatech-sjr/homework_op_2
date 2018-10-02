import java.util.HashMap;
import java.util.Map;

/**
 * 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * 2018년도 2학기 관찰자 패턴 실습
 * User 
 * 채팅 프로그램에서 사용자 역할을 함 
 * 가입된 채팅방마다 채팅메시지 목록 유지
 * 채팅방에 대해서는 관찰자, 채팅창에 대해서는 subject
 * @author 김상진
 *
 */
public class User{
	private String userID;
	private boolean isOnline = true;
	// Map<채팅방명 , 채팅메시지 목록>
	// 자신이 가입한 후 발생된 메시지만 유지
	private Map<String, ChatRoomLog> roomLogs = new HashMap<>();
	// 사용자 채팅 뷰 사용자마다 하나씩 가지고 있
	private UserChatWindow chatWindow;
	
	public User(String userID) {
		this.userID = userID;
	}
	public String getUserID() {
		return userID; 
	}
	public void setOnline(boolean flag) {
		isOnline = flag;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void joinRoom(String roomName) {
		roomLogs.put(roomName, new ChatRoomLog());
	}
	public void leaveRoom(String roomName) {
		roomLogs.remove(roomName);
	}
	public String[] getRooms() {
		String[] roomNames = new String[roomLogs.size()];
		roomLogs.keySet().toArray(roomNames);
		return roomNames;
	}
	public ChatRoomLog getRoomLog(String roomName) {
		return roomLogs.get(roomName);
	}
	public void setView(UserChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}
	public void notifyView(String roomName) {
		chatWindow.update(roomName);
	}
	public void update(String roomName, String senderID, String message) {
		roomLogs.get(roomName).addMessage(senderID, message);
		notifyView(roomName);
	}

}
