package org.bones.alphai.subscriber.activetick;

import at.feedapi.ActiveTickStreamListener;
import at.shared.ATServerAPIDefines;
import org.bones.alphai.subscriber.publisher.PublisherInterface;
import org.bones.alphai.subscriber.publisher.RestPublisher;
import org.bones.alphai.subscriber.stream.QuoteUpdate;
import org.bones.alphai.subscriber.stream.UpdateInterface;
import org.bones.alphai.subscriber.stream.TradeUpdate;

public class StreamListener extends ActiveTickStreamListener
{
    private final RestPublisher restPublisher;
    APISession session;
    String collectorUrl;

    public StreamListener(APISession session, String collectorUrl)
    {
        super(session.GetSession(), false);
        this.session = session;
        restPublisher = new RestPublisher(collectorUrl);
    }

    public void OnATStreamTradeUpdate(ATServerAPIDefines.ATQUOTESTREAM_TRADE_UPDATE update)
    {
        TradeUpdate tradeUpdate = new TradeUpdate(update);
        //System.out.print(".");
        restPublisher.publish(tradeUpdate);
    }

    public void OnATStreamQuoteUpdate(ATServerAPIDefines.ATQUOTESTREAM_QUOTE_UPDATE update)
    {
        QuoteUpdate quoteUpdate = new QuoteUpdate(update);
        restPublisher.publish(quoteUpdate);
    }

    public void OnATStreamTopMarketMoversUpdate(ATServerAPIDefines.ATMARKET_MOVERS_STREAM_UPDATE update)
    {
        System.out.println("OnATStreamTopMarketMoversUpdate not implemented");
    }
}
