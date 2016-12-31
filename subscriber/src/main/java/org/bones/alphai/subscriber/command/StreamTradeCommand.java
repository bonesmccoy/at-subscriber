package org.bones.alphai.subscriber.command;

import at.feedapi.ActiveTickServerAPI;
import at.feedapi.Helpers;
import at.shared.ATServerAPIDefines;
import at.utils.jlib.Errors;
import org.bones.alphai.subscriber.activetick.APISession;
import org.bones.alphai.subscriber.activetick.Helper;
import org.bones.alphai.subscriber.activetick.ServerRequester;
import org.bones.alphai.subscriber.exception.SubscriberException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StreamTradeCommand extends AbstractCommand {

    private APISession apiSession;

    private static final String SUBSCRIBE_SWITCH = "subscribe";
    private static final String UNSUBSCRIBE_SWITCH = "unsubscribe";

    public StreamTradeCommand(APISession apiSession)
    {
        this.apiSession = apiSession;
    }

    @Override
    public String getCommandToken() {
        return "trade";
    }

    @Override
    public Integer getArgumentCount() {
        return 3;
    }

    @Override
    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute()
    {
        if (!checkSession()) { return; }
        String commaSeparatedSymbolsList = this.arguments.get(1);
        String command = this.arguments.get(0);

        List<ATServerAPIDefines.ATSYMBOL> lstSymbols = parseInputSymbolList(commaSeparatedSymbolsList);

        ATServerAPIDefines.ATStreamRequestType requestType = (new ATServerAPIDefines()).new ATStreamRequestType();

        switch (command) {
            case UNSUBSCRIBE_SWITCH:
                requestType.m_streamRequestType =  ATServerAPIDefines.ATStreamRequestType.StreamRequestUnsubscribeTradesOnly;
                break;
            case SUBSCRIBE_SWITCH:
                requestType.m_streamRequestType =  ATServerAPIDefines.ATStreamRequestType.StreamRequestSubscribeTradesOnly;
                break;
            default:
                Helper.PrintOut("Invalid Command " + command);
                return;
        }
         ;

        ServerRequester requester = apiSession.getServerRequester();
        long request = requester.SendATQuoteStreamRequest(
                lstSymbols,
                requestType,
                ActiveTickServerAPI.DEFAULT_REQUEST_TIMEOUT
        );

        if(request < 0){
            Helper.PrintOut("Error = " + Errors.GetStringFromError((int)request));
        } else {
            Helper.PrintOut("SEND " + request + ": request <subscribe-trade> for symbols [" + commaSeparatedSymbolsList + "]");
        }
    }

    public boolean checkSession()
    {
        if (!apiSession.GetSession().IsConnected()) {
            Helper.PrintOut("Session is not connected. Retry.");

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
}
