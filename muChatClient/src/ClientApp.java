import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClientApp extends JFrame {
	
	public final String LOGINPANELNAME = "Login";
	public final String MESSAGINGPANELNAME = "Message";
	
	LoginPanel loginPanel;
	MessagingPanel messagingPanel;
	ClientThread ct;
	
	public ClientApp() {
		addMenuBar();
		
		CardLayout layoutManger = new CardLayout();
		setLayout(layoutManger);
		loginPanel = new LoginPanel(this);
		messagingPanel = new MessagingPanel(this);
		add(loginPanel, LOGINPANELNAME);
		add(messagingPanel, MESSAGINGPANELNAME);
		CardLayout cl = (CardLayout)this.getContentPane().getLayout();
		cl.show(this.getContentPane(), LOGINPANELNAME);
	}
	
	public void addMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = fileMenu.add("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);  
			}
		});

		menuBar.add(fileMenu);

		this.setJMenuBar(menuBar);
	}
	
	public static void main(String[] args) {
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		ClientApp app = new ClientApp();
		app.setMinimumSize(new Dimension(300, 200));
		app.setTitle("MUChat");
		app.setLocationByPlatform(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(800, 600);
		app.setVisible(true);
	}
}
