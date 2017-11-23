package me.rorykelly.shogi.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

public class Connection {

	private static final Pattern PORT_REGEX = Pattern
			.compile("^[0-9]$|^[1-9]{1}[0-9]{1,3}$|^[1-5][0-9]{4}$|^6[0-5][0-5][0-3][0-5]$");
	public static final int DEFAULT_PORT = 0x5C06; // unicode escape for "sho"
													// in japanese (\u5C06)

	private ServerSocket svr;
	private Socket cli;

	public Connection() {

	}

	public void host() throws IllegalArgumentException {
		host(DEFAULT_PORT);
	}

	public void host(int port) throws IllegalArgumentException {
		if (!PORT_REGEX.matcher(String.valueOf(port)).matches())
			throw new IllegalArgumentException();
		try {
			svr = new ServerSocket(port, 100);
			cli = svr.accept();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void connect(String ip) {
		connect(ip, DEFAULT_PORT);
	}

	public void connect(String ip, int port) {

	}

}
