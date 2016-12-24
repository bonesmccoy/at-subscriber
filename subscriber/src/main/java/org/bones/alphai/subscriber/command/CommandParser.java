package org.bones.alphai.subscriber.command;

import org.bones.alphai.subscriber.exception.QuitApplicationException;
import org.bones.alphai.subscriber.exception.SubscriberException;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CommandParser
{
    ArrayList<AbstractCommand> availableCommands;

    public CommandParser()
    {
        availableCommands = new ArrayList<AbstractCommand>();
        availableCommands.add(new HelpCommand());
        availableCommands.add(new QuitCommand());
    }

    public boolean parse(String userInput) throws SubscriberException
    {
        if (!(userInput.length() > 0)) {
            return true;
        }

        StringTokenizer st = new StringTokenizer(userInput);
        ArrayList<String> commandTokenList = new ArrayList<String>();

        while(st.hasMoreTokens())
            commandTokenList.add(st.nextToken());

        String commandName = (String)commandTokenList.get(0);
        for(AbstractCommand command : availableCommands) {
            if (command.supports(commandName) && command.hasCorrectNumberOfParameters(commandTokenList)) {
                try {
                    command.execute();
                    return false;
                } catch(QuitApplicationException e) {
                    return true;
                }
            }
        }

        return false;
    }

}
