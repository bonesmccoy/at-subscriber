package org.bones.alphai.subscriber.command;

import java.util.ArrayList;

public class HelpCommand extends AbstractCommand {

    public void execute()
    {
        System.out.println("Trades Stream Subscriber");
        System.out.println("-------------------------------------------------");
        System.out.println("Avaliable commands:");
        System.out.println("");
        System.out.println("subscribe [symbol] : subscribe to update on trades for symbol(s)");
        System.out.println("unsubscribe [symbol] : unsubscribe from the udaates for that symbol(s)");
        System.out.println("quit : quits the app.");
        System.out.println("? : print this help.");
    }

    @Override
    public String getCommandToken() {
        return "?";
    }

    @Override
    public Integer getArgumentCount() {
        return 1;
    }
}
