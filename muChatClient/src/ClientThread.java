import java.net.*;

import java.io.*;
import java.util.*;

class ClientThread extends Thread {
	Socket s;
	String name, host;
	int port;
	ClientApp app;
	String messagein, messageout;
	InputStream in;
	BufferedReader lineReader;
	OutputStream out;
	PrintStream lineWriter;
	boolean flag;

	//ChatRoom room;

	public ClientThread(String uname, String host, int port, ClientApp app) {
		name = uname;
		this.host = host;
		this.port = port;
		this.app = app;
		start();
	}

	public void run() {
		try {
			s = new Socket(host,port);
			flag = true;
			in = s.getInputStream();
			//Wrapping the InputStream in BufferedReader
			lineReader=new BufferedReader(new InputStreamReader(in));
			out=s.getOutputStream();
			//Wrapping the outputStream into PrintStream
			lineWriter=new PrintStream(out);
			messageout = "connect" + "$" + name;
			//writing connect message to the socket stream
			lineWriter.println(messageout);
			lineWriter.flush();
			
			while (flag) {
				messagein = lineReader.readLine();
				System.out.println(messagein);
				StringTokenizer st = new StringTokenizer(messagein ,"$");
				String command = st.nextToken();
				String u = st.nextToken();
				if (command.equals("adduser")) {
					app.messagingPanel.addUser(u);
				} else if (command.equals("removeuser")) {
					app.messagingPanel.removeUser(u);
				} else {
					app.messagingPanel.showMessage(command, u);
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public void post(String to, String msg) {
		String rep=to + "$" + name + "$" + msg;
		lineWriter.println(rep);
		lineWriter.flush();
	}

	public void disconnect() {
		flag=false;
	}
}
