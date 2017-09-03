// server port is 5000

import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

class ChatServer {

	Hashtable<String, ServerThread> list;

	public ChatServer() {
		list = new Hashtable<String, ServerThread>();
	}

	public void addClient(String name, ServerThread serverThread) {
		Set<Map.Entry<String, ServerThread>> s = list.entrySet();
		Iterator<Entry<String, ServerThread>> itr = s.iterator();
		String msgToAlreadyOnline, msgToNewOnline;
		while (itr.hasNext()) {
			Map.Entry<String, ServerThread> m = (Map.Entry<String, ServerThread>) itr.next();
			// Notify both the one who just came online and the one who is already online
			msgToAlreadyOnline = "adduser$" + name;
			ServerThread t = (ServerThread)m.getValue();
			t.sendFromServer(msgToAlreadyOnline);
			msgToNewOnline = "adduser$" + m.getKey();
			serverThread.sendFromServer(msgToNewOnline);
		}
		// Add the just online user in Hashtable
		list.put(name, serverThread);
	}

	public void removeClient(String name) {
		// Remove from the list
		list.remove(name);
		Set<Map.Entry<String, ServerThread>> s = list.entrySet();
		Iterator<Entry<String, ServerThread>> itr = s.iterator();
		String msgToEveryoneOnline = "removeuser$" + name;
		while (itr.hasNext()) {
			Map.Entry<String, ServerThread> m = (Map.Entry<String, ServerThread>) itr.next();
			// Notify all that a certain user has disconnected
			ServerThread t = (ServerThread)m.getValue();
			t.sendFromServer(msgToEveryoneOnline);
		}
	}

	public void deliverMessage(String to, String from, String message) {
		ServerThread reciever = (ServerThread)list.get(to);
		String msg = from + "$" + message;
		reciever.sendFromServer(msg);
	}

	public static void main(String[] args) {
		try {
			ChatServer server = new ChatServer();
			ServerSocket serverSocket = new ServerSocket(5000);
			System.out.println("Server is ready.............");
			System.out.println("Press Ctrl + C to shut down.");
			while (true) {
				Socket client = serverSocket.accept();
				ServerThread serverThread = new ServerThread(client, server);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ServerThread extends Thread {
	Socket client;
	ChatServer server;
	InputStream in;
	BufferedReader input;
	OutputStream out;
	PrintStream output;
	boolean disconnectFlag;
	String username;

	public ServerThread(Socket client, ChatServer server) {
		this.client = client;
		this.server = server;
		disconnectFlag = false;
		start();
	}

	public void run() {
		try {
			in = client.getInputStream();
			input = new BufferedReader(new InputStreamReader(in));
			out = client.getOutputStream();
			output = new PrintStream(out);
			String request, commandOrTo, message;
			while (!disconnectFlag) {
				request = input.readLine();
				System.out.println(request);
				StringTokenizer stringTokenizer = new StringTokenizer(request, "$");
				commandOrTo = stringTokenizer.nextToken();
				username = stringTokenizer.nextToken();
				if (commandOrTo.equals("connect")) {
					System.out.println(username + " connected");
					server.addClient(username, this);
				} else if (commandOrTo.equals("disconnect")) {
					disconnectFlag = true;
					server.removeClient(username);
				} else {
					message = stringTokenizer.nextToken();
					server.deliverMessage(commandOrTo, username, message);
				}
			}
			in.close();
			input.close();
			out.close();
			output.close();
			client.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void sendFromServer(String string) {
		output.println(string);
		output.flush();
	}
}