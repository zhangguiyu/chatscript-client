/**
 * 
 */
package com.brtrndb.chatscript.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author bertrand
 *
 */
public class MessageTests
{
	@Test
	public void formatToCSTest()
	{
		Message msg = new Message("User", "Bot", "Msg");
		byte[] expected = { 'U', 's', 'e', 'r', 0, 'B', 'o', 't', 0, 'M', 's', 'g', 0 };
		assertThat(msg.toCSFormat()).containsExactly(expected);
	}
}