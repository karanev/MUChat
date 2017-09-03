import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {

		public LoginPanel(ClientApp appMainWindow) {
			setLayout(new GridBagLayout());
			
			JLabel usernameLabel = new JLabel("Username:");
			add(usernameLabel, new GBC(0, 0).setInsets(5));
			JTextField usernameField = new JTextField(12);
			add(usernameField, new GBC(1, 0, 2, 1).setInsets(5));
			JLabel serverLabel = new JLabel("Server Host:");
			add(serverLabel, new GBC(0, 1).setInsets(5));
			JTextField server = new JTextField(12);
			add(server, new GBC(1, 1, 2, 1).setInsets(5));
			JLabel portLabel = new JLabel("Port No.:");
			add(portLabel, new GBC(0, 2).setInsets(5));
			JTextField port= new JTextField(12);
			add(port, new GBC(1, 2, 2, 1).setInsets(5));
			JButton login = new JButton("Log In");
			add(login, new GBC(1, 3).setInsets(5));
			login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String userName=usernameField.getText();
					String host = server.getText();
					int portNo = Integer.parseInt(port.getText());
					ClientThread ct = new ClientThread(userName, host, portNo, appMainWindow);
					appMainWindow.ct = ct;
					CardLayout cl = (CardLayout)appMainWindow.getContentPane().getLayout();
					cl.show(appMainWindow.getContentPane(), appMainWindow.MESSAGINGPANELNAME);
				}
			});
		}
}
