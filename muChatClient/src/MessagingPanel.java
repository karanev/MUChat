import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// TODO:
// 1) Check why main panel is not in proportion to gridbaglayout

public class MessagingPanel extends JPanel {
	public final String friends = "Friends";
	
	public DefaultListModel<String> online = new DefaultListModel<String>();
	
	HashSet<String> tabsOpened = new HashSet<>();
	
	JTabbedPane contactsTabbedPane;
	
	JScrollPane friendsScrollPane;
	
	JTabbedPane messagingTabbedPane;
	
	ClientApp app;

	public MessagingPanel(ClientApp app) {
		
		this.app = app;
		createFriendsPanel();
		
		createMessagingTabbedPane();
		
		addMainPanel();
	}
	
	private void createFriendsPanel() {
		JPanel friendsPanel = new JPanel();
		friendsPanel.setLayout(new BorderLayout());

		JLabel friendsText = new JLabel("<html><b>Friends: </b></html>", JLabel.CENTER);
		JList<String> friendsList = new JList<String>(online);
		
		// Adding a mouse listener to open a tab by double clicking it. 
		friendsList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					@SuppressWarnings("unchecked")
					JList<String> source = (JList<String>) evt.getSource();
					String nameSelected = (String) source.getSelectedValue();
					openMessagingTab(nameSelected);
				}
			}
		});
		
		// Setting single selection mode
		friendsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		friendsPanel.add(friendsText, BorderLayout.NORTH);
		friendsPanel.add(friendsList);
		
		friendsScrollPane = new JScrollPane(friendsPanel);
	}
	
	public void addUser(String uname) {
		online.addElement(uname);
	}
	
	public void removeUser(String uname) {
		online.removeElement(uname);
	}
	
	public void showMessage(String from, String msg) {
		openMessagingTab(from);
		MessagePanel panel = (MessagePanel)messagingTabbedPane.getComponentAt(messagingTabbedPane.indexOfTab(from));
		panel.messages.addElement(msg);
	}
	
	private void createMessagingTabbedPane() {
		messagingTabbedPane = new JTabbedPane();
		messagingTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	private void createTab(String title) {
		MessagePanel messagePanel = new MessagePanel(app, title);
		messagingTabbedPane.addTab(title, messagePanel);

		int tabIndex = messagingTabbedPane.indexOfTab(title);
		messagingTabbedPane.setSelectedIndex(tabIndex);

		JPanel closeTabPanel = new JPanel();
		JLabel tabName = new JLabel(title);
		ImageComponent closeTabImage = new ImageComponent("G:\\GUI\\muChatClient\\src\\close.gif");
		closeTabImage.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				// Index would change I close some tabs so cannot use the same index that at
				// time of creation It is working correctly how is that correct friendsName passed
				int closingTabIndex = messagingTabbedPane.indexOfTab(title);
				messagingTabbedPane.remove(closingTabIndex);
				tabsOpened.remove(title);
			}
		});
		closeTabPanel.add(tabName);
		closeTabPanel.add(closeTabImage);
		
		closeTabPanel.setOpaque(false);

		messagingTabbedPane.setTabComponentAt(tabIndex, closeTabPanel);
	}
	
	private void openMessagingTab(String name) {
		// First Check whether the tab is already opened
		if (tabsOpened.contains(name)) {
			messagingTabbedPane.setSelectedIndex(messagingTabbedPane.indexOfTab(name));
		} else {
			createTab(name);
			tabsOpened.add(name);
		}
	}
	
	private void addMainPanel() {
		this.setLayout(new GridBagLayout());
		
		contactsTabbedPane = new JTabbedPane();
		
		contactsTabbedPane.addTab(friends, friendsScrollPane);

		this.add(contactsTabbedPane, new GBC(0, 0).setWeight(0.1, 1).setFill(GBC.BOTH));
		this.add(messagingTabbedPane, new GBC(1, 0).setWeight(0.9, 1).setFill(GBC.BOTH));
	}
}