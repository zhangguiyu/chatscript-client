package com.brtrndb.chatscript.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.brtrndb.chatscript.client.core.ChatscriptException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatScriptClient
{
	public static void main(final String[] args)
	{
		try
		{
			final Map<Options, CommandLine> cli = verifyCli(args);

			if (cli.get(ChatScriptOptions.OPTIONS_ALL).hasOption("h"))
				ChatScriptOptions.printHelp();
			else
			{
				final ClientManager clientManager = createClientManager(cli.get(ChatScriptOptions.OPTIONS_CLI));
				clientManager.start();
			}
		}
		catch (final ParseException e)
		{
			ChatScriptOptions.printHelp();
		}
		catch (final ChatscriptException e)
		{
			log.error("Chatscript client error", e);
			System.out.println("An error occurs: " + e.getLocalizedMessage() + ".");
		}
		catch (final Exception e)
		{
			log.error("Oups... Something unexpected went wrong: ", e);
			System.out.println("An unexpected error occurs: " + e.getLocalizedMessage() + ".");
		}
	}

	private static Map<Options, CommandLine> verifyCli(final String[] args) throws ParseException
	{
		final CommandLineParser parser = new DefaultParser();
		final CommandLine cliHelp = parser.parse(ChatScriptOptions.OPTIONS_ALL, args, true);
		final CommandLine cli = parser.parse(ChatScriptOptions.OPTIONS_CLI, args);
		final Map<Options, CommandLine> map = new HashMap<>();

		map.put(ChatScriptOptions.OPTIONS_ALL, cliHelp);
		map.put(ChatScriptOptions.OPTIONS_CLI, cli);

		return (map);
	}

	private static ClientManager createClientManager(final CommandLine cli) throws ChatscriptException
	{
		try
		{
			final String url = cli.getOptionValue("u", ChatScriptOptions.DEFAULT_SERVER_URL);
			final int port = Integer.parseInt(cli.getOptionValue("p", ChatScriptOptions.DEFAULT_SERVER_PORT));
			final String username = cli.getOptionValue("n", ChatScriptOptions.DEFAULT_USERNAME);
			final String botname = cli.getOptionValue("b", ChatScriptOptions.DEFAULT_BOTNAME);

			final ClientManager clientManager = new ClientManager(url, port, username, botname);
			return (clientManager);
		}
		catch (final NumberFormatException e)
		{
			throw (new ChatscriptException("Port parameter in not a number", e));
		}
	}
}
