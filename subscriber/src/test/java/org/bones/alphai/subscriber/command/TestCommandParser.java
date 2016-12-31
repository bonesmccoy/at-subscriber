package org.bones.alphai.subscriber.command;

import org.junit.Assert;
import org.junit.Test;

public class TestCommandParser
{
    @Test
    public void UnknownCommand()
    {
        CommandParser parser = new CommandParser();
        parser.addCommand(new HelpCommand());
        AbstractCommand command = parser.parse("unknown");
        Assert.assertTrue( command instanceof HelpCommand);
    }
}
