package org.bones.alphai.subscriber.command;


import org.bones.alphai.subscriber.exception.QuitApplicationException;
import org.bones.alphai.subscriber.exception.SubscriberException;

import java.util.ArrayList;

public class QuitCommand extends AbstractCommand
{
    @Override
    public String getCommandToken() {
        return "quit";
    }

    @Override
    public Integer getArgumentCount() {
        return 0;
    }

    @Override
    public void setArguments(ArrayList<String> arguments) {}

    @Override
    public void execute() throws SubscriberException{
        throw new QuitApplicationException();
    }
}
