package org.bones.alphai.subscriber;

import java.nio.file.Files;
import java.nio.file.Paths;
import at.feedapi.ActiveTickServerAPI;
import at.feedapi.Helpers;
import at.shared.ATServerAPIDefines;
import at.utils.jlib.Errors;
import org.bones.alphai.subscriber.activetick.APISession;
import org.bones.alphai.subscriber.activetick.Helper;
import org.bones.alphai.subscriber.activetick.ServerRequester;
import org.bones.alphai.subscriber.command.*;
import org.bones.alphai.subscriber.exception.QuitApplicationException;
import org.bones.alphai.subscriber.exception.SubscriberException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;


public class MainClass extends Thread
{

    private Configuration configuration;
    private static at.feedapi.ActiveTickServerAPI serverapi;
    private static APISession apiSession;
    private static CommandParser commandParser;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MainClass(Configuration configuration)
    {
        this.configuration = configuration;
        this.commandParser = new CommandParser();
        start();
    }

    public void run()
    {
        serverapi = new ActiveTickServerAPI();
        serverapi.ATInitAPI();
        apiSession = new APISession(serverapi, this.configuration);
        loadCommands();

        if (! apiSession.connect() ) {
            Helper.PrintOut("Error connecting. Quit.");

            return;
        }

        commandParser.PrintHelp();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                String line = br.readLine();
                if (line.length() > 0) {
                    commandParser.parse(line).execute();
                }
            } catch (SubscriberException e) {
                if (e instanceof QuitApplicationException) {
                    Helper.PrintOut("Bye!");
                    break;
                } else {
                    Helper.PrintOut(e.getMessage());
                }
            } catch (IOException e) {
                Helper.PrintOut("IO error trying to read your input!");
                LOGGER.info(e.getStackTrace().toString());
            } catch (Exception e) {
                LOGGER.info(e.getStackTrace().toString());
            }
        }

        apiSession.UnInit();
        serverapi.ATShutdownAPI();
    }

    private void loadCommands()
    {
        StreamTradeCommand streamTradeCommand = new StreamTradeCommand(apiSession);
        commandParser.addCommand(streamTradeCommand);
    }

    public static void main(String args[])
    {
        if (args.length != 1) {

            System.out.println("Error: configuration file missing!\n");
            System.out.println("Usage:\n\tsubscriber path/to/config.yml");
            return;
        }

        try {
            String configurationFilePath = args[0];
            String fileContents = new String(Files.readAllBytes(Paths.get(configurationFilePath)));
            Configuration appConfiguration = new Configuration(fileContents);

            MainLogger.setup(appConfiguration.getLogFilePath(), appConfiguration.getLogLevel());

            MainClass subscriber = new MainClass(appConfiguration);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
