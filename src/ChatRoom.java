import java.util.HashMap;
import java.util.Map;

/**
 * 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 2018년도 2학기 관찰자 패턴 실습 ChatRoom 사용자 목록과 채팅 메시지 목록
 * 유지 채팅룸 목록, 사용자 목록 유지
 * 
 * @author 김상진
 *
 */
public class ChatRoom {
	private String roomName;
	// 이 채팅방에서 교환된 모든 메시지를 유지함
	private ChatRoomLog roomLog = new ChatRoomLog();
	// Map<사용자ID,마지막 받은 메시지 색인>
	private Map<String, Integer> lastMessageIndex = new HashMap<>();

	public ChatRoom(String name) {
		roomName = name;
	}

	public String getRoomName() {
		return roomName;
	}

	public ChatRoomLog getRoomLog() {
		return roomLog;
	}

	// 채팅 서버가 채팅방에 새 메시지가 생길 때마다 사용함
	public void newMessage(String userID, String message) {
		// 만약 여기에 예외 처리를 추가한다면...
		roomLog.addMessage(userID, message);
		updateUsers();
	}

	public void deleteUser(String userID) {
		lastMessageIndex.remove(userID);
	}

	// TODO : 사용자가 가입할 때 사용함
	// 사용자가 가입된 이후 발생한 메시지만 받음
	// LastMessageIndex Map에 사용자를 추가해야 함
	public void addUser(String userID) {
		lastMessageIndex.put(userID, roomLog.size());
	}

	// TODO : 채팅방에 있는 모든 사용자들에게 최신 메시지를 전달한다.
	// 이전에 받은 메시지부터 최신 메시지까지 전달해야 함.
	// 즉, 사용자마다 전달해야 하는 메시지 수가 다를 수 있음
	// 특정 사용자는 현재 오프라인일 수 있음
	public void updateUsers() {
		ChatServer chatServer = ChatServer.getServer();
		for (String key : lastMessageIndex.keySet()) {
			if(chatServer.getUser(key).isOnline()) {
				chatServer.forwardMessage(key, roomName, roomLog.getMessages().get(roomLog.size()-1).getUserID(), roomLog.getMessages().get(roomLog.size()-1).getMessage());
				lastMessageIndex.put(key, roomLog.size()-1);
			}
				
			
		}

	}
}
