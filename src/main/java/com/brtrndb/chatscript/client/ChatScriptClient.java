package com.brtrndb.chatscript.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brtrndb.chatscript.client.impl.ClientManager;

public class ChatScriptClient
{
	private static final Logger log = LoggerFactory.getLogger(ChatScriptClient.class);

	public static void main(final String[] args)
	{
		try
		{
			verifyParams(args);
			final ClientManager clientManager = createClientManager(args);
			clientManager.start();
		}
		catch (final Exception e)
		{
			log.error("Oups... Something went wrong: ", e.getLocalizedMessage());
		}
	}

	private static void verifyParams(final String[] args)
	{
		if (args.length < 2)
		{
			log.error("Incorect number of parameters, found {} expected at least 2.", args.length);
			throw (new IllegalArgumentException("Wrong number of argument"));
		}
	}

	private static ClientManager createClientManager(final String[] args)
	{
		final String url = args[0];
		final int port = Integer.parseInt(args[1]);
		final String username = args.length == 3 ? args[2] : "MacClane";
		final String botname = args.length == 4 ? args[3] : "harry";

		final ClientManager clientManager = new ClientManager(url, port, username, botname);
		return (clientManager);
	}
}
