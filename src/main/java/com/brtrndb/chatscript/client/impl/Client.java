package com.brtrndb.chatscript.client.impl;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.brtrndb.chatscript.client.core.ChatscriptClient;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessage;
import com.brtrndb.chatscript.client.core.message.ChatscriptMessageService;

import lombok.Getter;

public class Client implements ChatscriptClient
{
	@Getter
	private final String					url;
	@Getter
	private final int						port;
	@Getter
	private final String					username;
	@Getter
	private final String					botname;
	@Getter
	private final ChatscriptMessageService	messageService;

	public Client(final String url, final int port, final String username, final String botname)
	{
		this.url = url;
		this.port = port;
		this.username = username;
		this.botname = botname;
		this.messageService = new MessageService();
	}

	@Override
	public Socket buildSocket() throws UnknownHostException, IOException
	{
		return (new Socket(this.url, this.port));
	}

	@Override
	public ChatscriptMessage buildMessage(final String message)
	{
		return (new Message(this.username, this.botname, message));
	}

	@Override
	public void userPrompt(final String str)
	{
		if (str.isEmpty())
			System.out.print(this.getUsername() + ": ");
		else
			System.out.println(this.getUsername() + ": " + str);
	}

	@Override
	public void botPrompt(final String str)
	{
		System.out.println(this.getBotname() + ": " + str);
	}

	@Override
	public void clientPrompt(final String str)
	{
		System.out.println("CLIENT: " + str);
	}
}
