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

    private static final String SUBSCRIBE_COMMAND = "subscribe";
    private static final String UNSUBSCRIBE_COMMAND = "unsubscribe";
    private static final String HELP_COMMAND = "?";
    private static final String QUIT_COMMAND = "quit";

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MainClass(Configuration configuration)
    {
        this.configuration = configuration;

        start();
    }

    public void run()
    {
        serverapi = new ActiveTickServerAPI();
        serverapi.ATInitAPI();
        apiSession = new APISession(serverapi, this.configuration);

        if (! apiSession.connect() ) {
            System.out.println("Error connecting. Quit.");

            return;
        }
        PrintUsage();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                String line = br.readLine();
                if (line.length() > 0) {
                    if (line.startsWith("quit")) {
                        Helper.PrintOut("Bye");
                        break;
                    }
                    parseInput(line);
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

    private boolean parseInput(String userInput)
    {
        if (!(userInput.length() > 0)) {
            return true;
        }

        StringTokenizer st = new StringTokenizer(userInput);
        List ls = new ArrayList<String>();

        while(st.hasMoreTokens())
            ls.add(st.nextToken());
        int count = ls.size();

        if(count > 0 && ((String)ls.get(0)).equalsIgnoreCase(HELP_COMMAND)) {
            PrintUsage();
        }

        String command = (String) ls.get(0);
        boolean goOnWithExecution = true;
        switch (command) {
            case HELP_COMMAND:
                PrintUsage();
                break;
            case SUBSCRIBE_COMMAND:
                if (count != 2) {
                    PrintUsage();
                } else {
                    String symbolsList = (String) ls.get(1);
                    subscribeTradesOnly(symbolsList);
                }
                break;
            case UNSUBSCRIBE_COMMAND:
                if (count != 2) {
                    PrintUsage();
                } else {
                    String symbolsList = (String) ls.get(1);
                    unSubscribeTradesOnly(symbolsList);
                }
                break;
            case QUIT_COMMAND:
                goOnWithExecution = false;
            default:
                System.out.println("Command " + command + " not found\n");
                PrintUsage();
                break;
        }

        return goOnWithExecution;
    }

    private void PrintUsage()
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

    public void subscribeTradesOnly(String commaSeparatedSymbolsList)
    {
        if (!checkSession()) { return; }
        List<ATServerAPIDefines.ATSYMBOL> lstSymbols = parseInputSymbolList(commaSeparatedSymbolsList);

        ATServerAPIDefines.ATStreamRequestType requestType = (new ATServerAPIDefines()).new ATStreamRequestType();
        requestType.m_streamRequestType =  ATServerAPIDefines.ATStreamRequestType.StreamRequestSubscribeTradesOnly ;

        doRequest(commaSeparatedSymbolsList, lstSymbols, requestType);
    }

    public void unSubscribeTradesOnly(String commaSeparatedSymbolsList)
    {
        if (!checkSession()) { return; }

        List<ATServerAPIDefines.ATSYMBOL> lstSymbols = parseInputSymbolList(commaSeparatedSymbolsList);

        ATServerAPIDefines.ATStreamRequestType requestType = (new ATServerAPIDefines()).new ATStreamRequestType();
        requestType.m_streamRequestType =  ATServerAPIDefines.ATStreamRequestType.StreamRequestUnsubscribeTradesOnly ;

        doRequest(commaSeparatedSymbolsList, lstSymbols, requestType);
    }

    public boolean checkSession()
    {
        if (!apiSession.GetSession().IsConnected()) {
            System.out.println("Session is not connected. Retry.");

            return false;
        }

        return true;
    }

    private List<ATServerAPIDefines.ATSYMBOL> parseInputSymbolList(String commaSeparatedSymbolsList) {

        List<ATServerAPIDefines.ATSYMBOL> lstSymbols = new ArrayList<ATServerAPIDefines.ATSYMBOL>();
        if(!commaSeparatedSymbolsList.isEmpty() && !commaSeparatedSymbolsList.contains(",")) {
            ATServerAPIDefines.ATSYMBOL atSymbol = Helpers.StringToSymbol(commaSeparatedSymbolsList);
            lstSymbols.add(atSymbol);
        } else {
            StringTokenizer symbolTokenizer = new StringTokenizer(commaSeparatedSymbolsList, ",");
            while(symbolTokenizer.hasMoreTokens()) {
                ATServerAPIDefines.ATSYMBOL atSymbol = Helpers.StringToSymbol(symbolTokenizer.nextToken());
                lstSymbols.add(atSymbol);
            }
        }
        return lstSymbols;
    }

    private void doRequest(String commaSeparatedSymbolsList, List<ATServerAPIDefines.ATSYMBOL> lstSymbols, ATServerAPIDefines.ATStreamRequestType requestType) {
        ServerRequester requester = apiSession.getServerRequester();
        long request = requester.SendATQuoteStreamRequest(
                lstSymbols,
                requestType,
                ActiveTickServerAPI.DEFAULT_REQUEST_TIMEOUT
        );

        String requestName = (requestType.m_streamRequestType == ATServerAPIDefines.ATStreamRequestType.StreamRequestUnsubscribeTradesOnly) ? "unsubscribe" : "subscribe";

        System.out.println("SEND " + request + ": request " + requestName + " [" + commaSeparatedSymbolsList + "]");
        if(request < 0){
            System.out.println("Error = " + Errors.GetStringFromError((int)request));
        }
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
