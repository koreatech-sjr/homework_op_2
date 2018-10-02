import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * 2018년도 2학기 관찰자 패턴 실습
 * UserChatWindow
 * 각 사용자의 채팅창 (View+Controller 클래스) 
 * 모델은 User
 * @author 김상진
 *
 */
public class UserChatWindow extends Stage{
	private User user;
	private CheckBox isOnline = new CheckBox("온라인");
	private ComboBox<String> roomChoice = new ComboBox<>();
	private TextArea chatArea = new TextArea();
	private TextArea sendArea = new TextArea();
	private Button sendButton = new Button("전송");
	public UserChatWindow(User user) {
		this.user = user;
		roomChoice.getItems().addAll(user.getRooms());
		roomChoice.getSelectionModel().select(0);
		roomChoice.getSelectionModel().selectedItemProperty().addListener((o,ov,nv)->changed(o,ov,nv));
		isOnline.setSelected(true);
		isOnline.setOnAction(e->onlineStatusChanged());
		doLayout();
	}
	public void doLayout() {
		BorderPane mainPane = new BorderPane();
		HBox choiceBox = new HBox();
		choiceBox.setPadding(new Insets(10));
		choiceBox.setSpacing(10);
		choiceBox.getChildren().addAll(roomChoice, isOnline);
		roomChoice.setMinWidth(200);
		
		chatArea.setPrefRowCount(6);
		
		HBox sendBox = new HBox();
		sendBox.setPadding(new Insets(10));
		sendBox.setSpacing(10);
		sendBox.getChildren().addAll(sendArea, sendButton);
		sendArea.setPrefRowCount(2);
		sendArea.setPrefColumnCount(40);
		sendButton.setMinWidth(60);
		sendButton.setMinHeight(60);
		sendButton.setOnAction(e->sendMessage());
		
		mainPane.setTop(choiceBox);
		mainPane.setCenter(chatArea);
		mainPane.setBottom(sendBox);
		
		setTitle(user.getUserID());
		setScene(new Scene(mainPane,300,300));
	}
	public void changed(
		ObservableValue<? extends String> observable, String oldValue, String roomName) {	
		chatArea.setText(prepareOutput(roomName));
	}
	public void onlineStatusChanged() {
		user.setOnline(isOnline.isSelected());
	}
	public void sendMessage() {
		String roomName = roomChoice.getSelectionModel().getSelectedItem();
		String message = sendArea.getText();
		// 예외처리를 추가하면
		ChatServer.getServer().sendMessage(user.getUserID(), roomName, message);
		sendArea.setText("");
	}
	public void update(String roomName) {
		roomChoice.getSelectionModel().select(roomName);
		chatArea.setText(prepareOutput(roomName));
	}
	// 여러 메시지를 동시에 출력하기 위한 보조 함수 (매번 모든 메시지를 다시 출력)
	private String prepareOutput(String roomName) {
		ArrayList<ChatMessage> chats = user.getRoomLog(roomName).getMessages();
		String output = "";
		for(ChatMessage chat: chats) {
			output += chat.getUserID()+": "+chat.getMessage()+"\n";
		}
		return output;
		/*
		return chats.stream().map(c->c.getUserID()+": "+c.getMessage()+"\n")
				.collect(Collectors.joining());
		*/
	}
}
