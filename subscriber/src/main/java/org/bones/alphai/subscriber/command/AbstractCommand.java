package org.bones.alphai.subscriber.command;


import org.bones.alphai.subscriber.exception.SubscriberException;

import java.util.ArrayList;

abstract public class AbstractCommand
{
    abstract public String getCommandToken();
    abstract public Integer getArgumentCount();

    abstract public void execute() throws SubscriberException;

    public boolean supports(String commandName)
    {
        return commandName.equalsIgnoreCase(getCommandToken());
    }

    public boolean hasCorrectNumberOfParameters(ArrayList<String> commandList)
    {
        return commandList.size() == getArgumentCount();
    }
}
