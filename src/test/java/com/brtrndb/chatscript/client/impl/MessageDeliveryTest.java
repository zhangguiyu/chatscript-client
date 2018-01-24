package com.brtrndb.chatscript.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.testng.annotations.Test;

import com.brtrndb.chatscript.client.core.ChatscriptMessage;
import com.brtrndb.chatscript.client.core.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDeliveryTest
{
	private static final String	URL			= "localhost";
	private static final int	PORT		= 12123;
	private static final String	MESSAGE		= "Msg";
	private static final String	RESPONSE	= "Ok";

	private MessageService		service		= new MessageDelivery();

	@Test
	public void sendAndReceiveTest() throws UnknownHostException, IOException
	{
		Thread r = new Thread(this::simulateClient);

		try (ServerSocket serverSocket = new ServerSocket(PORT))
		{
			r.start();

			try (Socket socket = serverSocket.accept())
			{
				byte[] expected = { 'U', 's', 'e', 'r', 0, 'B', 'o', 't', 0, 'M', 's', 'g', 0 };
				final byte[] buffer = new byte[expected.length];
				final InputStream is = socket.getInputStream();
				is.read(buffer, 0, expected.length);

				assertThat(buffer).isEqualTo(expected);
				socket.getOutputStream().write(RESPONSE.getBytes());
			}
		}
	}

	private void simulateClient()
	{
		try
		{
			Thread.sleep(1000);
			try (Socket socket = new Socket(URL, PORT))
			{
				ChatscriptMessage msg = new Message("User", "Bot", MESSAGE);
				this.service.sendMessage(socket, msg);
				String res = this.service.receiveMessage(socket);
				assertThat(res).isEqualTo(RESPONSE);
			}
		}
		catch (InterruptedException | IOException e)
		{
			log.error("Error", e);
		}
	}
}
