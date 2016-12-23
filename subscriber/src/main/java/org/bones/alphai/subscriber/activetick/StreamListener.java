package org.bones.alphai.subscriber.activetick;

import at.feedapi.ActiveTickStreamListener;
import at.shared.ATServerAPIDefines;
import at.utils.jlib.PrintfFormat;
import org.bones.alphai.subscriber.publisher.PublisherInterface;
import org.bones.alphai.subscriber.publisher.RestPublisher;
import org.bones.alphai.subscriber.stream.TradeUpdate;

public class StreamListener extends ActiveTickStreamListener
{
    APISession session;
    String collectorUrl;

    public StreamListener(APISession session, String collectorUrl)
    {
        super(session.GetSession(), false);
        this.session = session;
        this.collectorUrl = collectorUrl;
    }

    public void OnATStreamTradeUpdate(ATServerAPIDefines.ATQUOTESTREAM_TRADE_UPDATE update)
    {
        TradeUpdate tradeUpdate = new TradeUpdate(update);
        //System.out.print(".");
        if (collectorUrl != null)
        {
            PublisherInterface publisher = new RestPublisher(collectorUrl);

            publisher.publish(tradeUpdate);
        }
    }

    public void OnATStreamQuoteUpdate(ATServerAPIDefines.ATQUOTESTREAM_QUOTE_UPDATE update)
    {
        String strSymbol = new String(update.symbol.symbol);
        int plainSymbolIndex = strSymbol.indexOf((byte)0);
        strSymbol = strSymbol.substring(0, plainSymbolIndex);
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(update.quoteDateTime.hour);
        sb.append(":");
        sb.append(update.quoteDateTime.minute);
        sb.append(":");
        sb.append(update.quoteDateTime.second);
        sb.append(":");
        sb.append(update.quoteDateTime.milliseconds);
        sb.append("] STREAMQUOTE [symbol:");
        sb.append(strSymbol);
        sb.append(" bid:");
        String strFormat = "%0." + update.bidPrice.precision + "f";
        sb.append(new PrintfFormat(strFormat).sprintf(update.bidPrice.price));
        sb.append(" ask:");
        strFormat = "%0." + update.askPrice.precision + "f";
        sb.append(new PrintfFormat(strFormat).sprintf(update.askPrice.price));
        sb.append(" bidSize:");
        sb.append(update.bidSize);
        sb.append(" askSize:");
        sb.append(update.askSize);
        sb.append("]");
        System.out.println(sb.toString());
    }

    public void OnATStreamTopMarketMoversUpdate(ATServerAPIDefines.ATMARKET_MOVERS_STREAM_UPDATE update)
    {
        System.out.println("OnATStreamTopMarketMoversUpdate not implemented");
    }
}
