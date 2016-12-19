package co.alphai.atsubscriber;

import java.io.*;
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
    public static at.feedapi.ActiveTickServerAPI serverapi;
    public static co.alphai.at.APISession apiSession;

    public StreamSubscriber(Configuration configuration)
    {
        this.configuration = configuration;

        start();
    }

    public void run()
    {
        serverapi = new ActiveTickServerAPI();
        apiSession = new APISession(serverapi);
        serverapi.ATInitAPI();

        System.out.println("I'm running");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            try {
                String line = br.readLine();
                if (line.length() > 0) {
                    if (line.startsWith("quit"))
                        System.out.println("Bye");
                        apiSession.UnInit();
                        serverapi.ATShutdownAPI();
                        break;
                }
            } catch (IOException e) {
                System.out.println("IO error trying to read your input!");
            }
        }
    }

    public void subscribeTradesOnly(String commaSeparatedSymbolsList)
    {
        List<ATServerAPIDefines.ATSYMBOL> lstSymbols = parseInputSymbolList(commaSeparatedSymbolsList);

        ATServerAPIDefines.ATStreamRequestType requestType = (new ATServerAPIDefines()).new ATStreamRequestType();
        requestType.m_streamRequestType =  ATServerAPIDefines.ATStreamRequestType.StreamRequestSubscribeTradesOnly ;

        doRequest(commaSeparatedSymbolsList, lstSymbols, requestType);
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

        System.out.println("SEND " + request + ": subscribeTradesOnly request [" + commaSeparatedSymbolsList + "]");
        if(request < 0){
            System.out.println("Error = " + Errors.GetStringFromError((int)request));
        }
    }

    public static void main(String args[])
    {
        String configurationFilePath = args[0];
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get(configurationFilePath)));
            Configuration appConfiguration = new Configuration(fileContents);

            System.out.println(appConfiguration.toString());

            StreamSubscriber subscriber = new StreamSubscriber(appConfiguration);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
