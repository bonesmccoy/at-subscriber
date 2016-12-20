package co.alphai.atsubscriber;

import at.feedapi.Helpers;
import at.shared.ATServerAPIDefines;
import at.shared.ActiveTick.DateTime;
import at.shared.ActiveTick.UInt64;
import at.utils.jlib.PrintfFormat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.Vector;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.Vector;

import at.feedapi.Helpers;
import at.utils.jlib.PrintfFormat;
import at.shared.ATServerAPIDefines;
import at.shared.ATServerAPIDefines.ATBarHistoryResponseType;
import at.shared.ATServerAPIDefines.ATDataType;
import at.shared.ATServerAPIDefines.ATMarketMoversDbResponseType;
import at.shared.ATServerAPIDefines.ATQuoteDbResponseType;
import at.shared.ATServerAPIDefines.ATStreamResponseType;
import at.shared.ATServerAPIDefines.ATSymbolStatus;
import at.shared.ATServerAPIDefines.ATTICKHISTORY_QUOTE_RECORD;
import at.shared.ATServerAPIDefines.ATTICKHISTORY_TRADE_RECORD;
import at.shared.ATServerAPIDefines.ATTickHistoryRecordType;
import at.shared.ATServerAPIDefines.ATTickHistoryResponseType;
import at.shared.ATServerAPIDefines.QuoteDbDataItem;
import at.shared.ATServerAPIDefines.QuoteDbResponseItem;
import at.shared.ATServerAPIDefines.SYSTEMTIME;
import at.shared.ActiveTick.*;

public class ServerRequester extends at.feedapi.ActiveTickServerRequester
{
    public ServerRequester(APISession apiSession, Streamer streamer)
    {
        super(apiSession.GetServerAPI(), apiSession.GetSession(), streamer);
    }

    public void OnRequestTimeoutCallback(long origRequest)
    {
        System.out.println("(" + origRequest + "): Request timed-out");
    }

    public void OnQuoteStreamResponse(long origRequest, ATServerAPIDefines.ATStreamResponseType responseType, Vector<ATServerAPIDefines.ATQUOTESTREAM_DATA_ITEM> vecData)
    {
        String strResponseType = "";
        switch(responseType.m_responseType)
        {
            case ATServerAPIDefines.ATStreamResponseType.StreamResponseSuccess: strResponseType = "StreamResponseSuccess"; break;
            case ATServerAPIDefines.ATStreamResponseType.StreamResponseInvalidRequest: strResponseType = "StreamResponseInvalidRequest"; break;
            case ATServerAPIDefines.ATStreamResponseType.StreamResponseDenied: strResponseType = "StreamResponseDenied"; break;
            default: break;
        }

        System.out.println("RECV (" + origRequest +"): Quote stream response [" + strResponseType + "]\n--------------------------------------------------------------");

        if(responseType.m_responseType == ATServerAPIDefines.ATStreamResponseType.StreamResponseSuccess)
        {
            String strSymbolStatus = "";
            Iterator<ATServerAPIDefines.ATQUOTESTREAM_DATA_ITEM> itrDataItems = vecData.iterator();
            while(itrDataItems.hasNext())
            {
                ATServerAPIDefines.ATQUOTESTREAM_DATA_ITEM atDataItem = (ATServerAPIDefines.ATQUOTESTREAM_DATA_ITEM)itrDataItems.next();
                switch(atDataItem.symbolStatus.m_atSymbolStatus)
                {
                    case ATServerAPIDefines.ATSymbolStatus.SymbolStatusSuccess: strSymbolStatus = "SymbolStatusSuccess"; break;
                    case ATServerAPIDefines.ATSymbolStatus.SymbolStatusInvalid: strSymbolStatus = "SymbolStatusInvalid"; break;
                    case ATServerAPIDefines.ATSymbolStatus.SymbolStatusUnavailable: strSymbolStatus = "SymbolStatusUnavailable"; break;
                    case ATServerAPIDefines.ATSymbolStatus.SymbolStatusNoPermission: strSymbolStatus = "SymbolStatusNoPermission"; break;
                    default: break;
                }

                System.out.println("\tsymbol:" + strSymbolStatus + " symbolStatus: " + strSymbolStatus);
            }
        }
    }

    public void OnConstituentListResponse(long origRequest, Vector<ATServerAPIDefines.ATSYMBOL> vecData)
    {
        String strResponseType = "";

        for(int i = 0; i < vecData.size(); ++i)
        {
            String symbol = Helpers.SymbolToString(vecData.elementAt(i));
            strResponseType += symbol +"\n";
        }

        System.out.println("RECV (" + origRequest +"): Constituent list response [ " + strResponseType + "]\n--------------------------------------------------------------");
    }

    public void OnQuoteDbResponse(long origRequest, ATServerAPIDefines.ATQuoteDbResponseType responseType, Vector<ATServerAPIDefines.QuoteDbResponseItem> vecData)
    {
        System.out.println("OnQuoteDbResponse not implemented");
    }

    public void OnBarHistoryDbResponse(long origRequest, ATServerAPIDefines.ATBarHistoryResponseType responseType, Vector<ATServerAPIDefines.ATBARHISTORY_RECORD> vecData)
    {
        System.out.println("OnBarHistoryDbResponse not implemented");
    }

    public void OnTickHistoryDbResponse(long origRequest, ATServerAPIDefines.ATTickHistoryResponseType responseType, Vector<ATServerAPIDefines.ATTICKHISTORY_RECORD> vecData)
    {
        System.out.println("OnTickHistoryDbResponse not implemented");
    }

    public void OnMarketHolidaysResponse(long origRequest, Vector<ATServerAPIDefines.ATMARKET_HOLIDAYSLIST_ITEM> vecData)
    {
        System.out.println("OnMarketHolidaysResponse not implemented");
    }

    public void OnMarketMoversDbResponse(long origRequest, ATServerAPIDefines.ATMarketMoversDbResponseType responseType, Vector<ATServerAPIDefines.ATMARKET_MOVERS_RECORD> vecData)
    {
        System.out.println("OnMarketMoversDbResponse not implemented");
    }

    public void OnMarketMoversStreamResponse(long origRequest, ATServerAPIDefines.ATStreamResponseType responseType, ATServerAPIDefines.ATMARKET_MOVERS_STREAM_RESPONSE response)
    {
        System.out.println("OnMarketMoversStreamResponse not implemented");
    }
}