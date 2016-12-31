package org.bones.alphai.subscriber.command;

import org.bones.alphai.subscriber.activetick.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CommandParser
{
    HashMap<String, AbstractCommand> availableCommands;

    public CommandParser()
    {
        availableCommands = new HashMap<>();
        addCommand(new QuitCommand());
    }

    public void PrintHelp() {
        // make each command return its help.

        Helper.PrintOut("Trades Stream Subscriber");
        Helper.PrintOut("-------------------------------------------------");
        Helper.PrintOut("Avaliable commands:");
        Helper.PrintOut("");
        Helper.PrintOut("trade subscribe [symbol] : subscribe to update on trades for symbol(s)");
        Helper.PrintOut("trade unsubscribe [symbol] : unsubscribe from the udaates for that symbol(s)");
        Helper.PrintOut("quit : quits the app.");
        Helper.PrintOut("? : print this help.");
    }

    public void addCommand(AbstractCommand command) {
        availableCommands.put(command.getCommandToken(), command);
    }

    public AbstractCommand parse(String userInput)
    {
        if (!(userInput.length() > 0)) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(userInput);
        ArrayList<String> commandTokenList = new ArrayList<String>();

        while(st.hasMoreTokens())
            commandTokenList.add(st.nextToken());

        String commandName = (String)commandTokenList.remove(0);
        if (commandName.equals("?") || commandName.equals("help")) {
            PrintHelp();
        } else if(availableCommands.containsKey(commandName)) {
            AbstractCommand command = availableCommands.get(commandName);
            if (command.hasCorrectNumberOfParameters(commandTokenList)) {
                command.setArguments(commandTokenList);
                return command;
            }
        }

        Helper.PrintOut(String.format("Command '%s' unknown. Type 'help' for available commands", commandName));

        return null;
    }
}
