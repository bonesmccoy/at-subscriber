package org.bones.alphai.subscriber.command;


import org.bones.alphai.subscriber.exception.QuitApplicationException;
import org.bones.alphai.subscriber.exception.SubscriberException;

public class QuitCommand extends AbstractCommand
{
    @Override
    public String getCommandToken() {
        return null;
    }

    @Override
    public Integer getArgumentCount() {
        return null;
    }

    @Override
    public void execute() throws SubscriberException {
        throw new QuitApplicationException();
    }
}
