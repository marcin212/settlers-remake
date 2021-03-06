package jsettlers.network.infrastructure.channel.socket.standard;

import java.io.IOException;
import java.net.Socket;

import jsettlers.network.infrastructure.channel.socket.ISocket;
import jsettlers.network.infrastructure.channel.socket.ISocketFactory;
import jsettlers.network.infrastructure.channel.socket.SocketConnectException;

/**
 * Factory for {@link ISocket} implementation using the standard java {@link Socket}s.
 * 
 * @author Andreas Eberle
 * 
 */
public class JavaSocketFactory implements ISocketFactory {

	@Override
	public ISocket generateSocket(String host, int port) throws SocketConnectException {
		try {
			return new JavaSocketAdapter(new Socket(host, port));
		} catch (IOException e) {
			throw new SocketConnectException(e);
		}
	}

	@Override
	public ISocket generateSocket(Socket socket) {
		return new JavaSocketAdapter(socket);
	}

}
