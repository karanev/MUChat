import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessagePanel extends JPanel {
	
	DefaultListModel<Message> messages;
	JScrollPane messagesListPanel;
	JTextField messageToSend;
	String name;
	ClientApp app;

	public MessagePanel(ClientApp app, String name) {
		this.app = app;
		setLayout(new GridBagLayout());
		
		createMessageListsPanel();
		messageToSend = new JTextField();
		this.name = name;
		
		this.add(messagesListPanel, new GBC(0, 0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(2));
		this.add(messageToSend, new GBC(0, 1).setFill(GBC.HORIZONTAL).setAnchor(GBC.SOUTH).setInsets(2));
		this.messageToSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String messageString = messageToSend.getText();
				messageToSend.setText("");
				app.ct.post(name, messageString);
				Message myMsg = new Message("me", messageString);
				showMessage(myMsg);
			}
		});
	}
	
	public void createMessageListsPanel() {
		messages = new DefaultListModel<>();
		JList<Message> messagesList = new JList<>(messages);
		messagesList.setCellRenderer(new MessageRenderer());
		messagesListPanel = new JScrollPane(messagesList);
	}
	
	public void showMessage(Message myMessage) {
		messages.addElement(myMessage);
	}
}
