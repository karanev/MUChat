import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessagePanel extends JPanel {
	
	DefaultListModel<String> messages;
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
				String message = messageToSend.getText();
				messageToSend.setText("");
				app.ct.post(name, message);
				showMessage(message);
			}
		});
	}
	
	public void createMessageListsPanel() {
		messages = new DefaultListModel<>();
		JList<String> messagesList = new JList<>(messages);
		messagesListPanel = new JScrollPane(messagesList);
	}
	
	public void showMessage(String msg) {
		messages.addElement(msg);
	}
}
