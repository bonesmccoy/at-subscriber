package co.alphai.atsubscriber;

import java.nio.file.Files;
import java.nio.file.Paths;
import at.feedapi.ActiveTickServerAPI;
import at.feedapi.Helpers;
import at.shared.ATServerAPIDefines;
import at.utils.jlib.Errors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class StreamSubscriber extends Thread
{
    Configuration configuration;
    private static at.feedapi.ActiveTickServerAPI serverapi;
    private static co.alphai.atsubscriber.APISession apiSession;

    private static final String SUBSCRIBE_COMMAND = "subscribe";
    private static final String UNSUBSCRIBE_COMMAND = "unsubscribe";

    public StreamSubscriber(Configuration configuration)
    {
        this.configuration = configuration;

        start();
    }

    public void run()
    {
        serverapi = new ActiveTickServerAPI();
        apiSession = new APISession(serverapi, this.configuration);
        serverapi.ATInitAPI();

        if (! connect() ) {
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
                        System.out.println("Bye");
                        break;
                    }
                    parseInput(line);
                }
            } catch (IOException e) {
                System.out.println("IO error trying to read your input!");
            }
        }

        apiSession.UnInit();
        serverapi.ATShutdownAPI();
    }

    private boolean connect()
    {
        if(configuration.getApiKey().length() != 32) {
            System.out.println("Warning! \n\tApiUserIdGuid should be 32 characters long and alphanumeric only.");

            return false;
        }

        ATServerAPIDefines.ATGUID atguid = (new ATServerAPIDefines()).new ATGUID();
        atguid.SetGuid(configuration.getApiKey());

        boolean rc = apiSession.Init(atguid,
                configuration.getAtHostName(),
                configuration.getAtPort(),
                configuration.getUsername(),
                configuration.getPassword()
        );

        System.out.println("\nConnection: " + (rc == true ? "ok" : "failed"));

        return rc;
    }

    private void parseInput(String userInput)
    {
        StringTokenizer st = new StringTokenizer(userInput);
        List ls = new ArrayList<String>();
        while(st.hasMoreTokens())
            ls.add(st.nextToken());
        int count = ls.size();

        if(count > 0 && ((String)ls.get(0)).equalsIgnoreCase("?")) {
            PrintUsage();
        }

        String command = (String) ls.get(0);
        switch (command) {
            case "?":
                PrintUsage();
                break;
            case SUBSCRIBE_COMMAND:
                if (count != 2) {
                    PrintUsage();
                } else {
                    subscribeTradesOnly((String)ls.get(1));
                }
                break;
            case UNSUBSCRIBE_COMMAND:
                if (count != 2) {
                    PrintUsage();
                } else {
                    unSubscribeTradesOnly((String)ls.get(1));
                }
                break;
            default:
                System.out.println("Command " + command + " not found\n");
                PrintUsage();
                break;
        }
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
        if (!apiSession.session.IsConnected()) {
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

            System.out.println(appConfiguration.toString());

            StreamSubscriber subscriber = new StreamSubscriber(appConfiguration);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
