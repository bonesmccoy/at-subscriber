package org.bones.alphai.subscriber.activetick;

import at.feedapi.ActiveTickStreamListener;
import at.shared.ATServerAPIDefines;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bones.alphai.subscriber.publisher.RestPublisher;
import org.bones.alphai.subscriber.stream.QuoteUpdate;
import org.bones.alphai.subscriber.stream.TradeUpdate;

import java.util.logging.Logger;


public class StreamListener extends ActiveTickStreamListener
{
    APISession session;
    private final RestPublisher restPublisher;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public StreamListener(APISession session, String collectorUrl)
    {
        super(session.GetSession(), false);
        this.session = session;
        restPublisher = new RestPublisher(collectorUrl);
    }

    public void OnATStreamTradeUpdate(ATServerAPIDefines.ATQUOTESTREAM_TRADE_UPDATE update)
    {
        TradeUpdate tradeUpdate = new TradeUpdate(update);

        ObjectNode jsonNode = tradeUpdate.toJsonNode();
        LOGGER.info(String.format("trade-update: %s:%s\n", jsonNode.get("symbol"), jsonNode.get("timestamp")));

        restPublisher.publish(tradeUpdate);
    }

    public void OnATStreamQuoteUpdate(ATServerAPIDefines.ATQUOTESTREAM_QUOTE_UPDATE update)
    {
        QuoteUpdate quoteUpdate = new QuoteUpdate(update);

        ObjectNode jsonNode = quoteUpdate.toJsonNode();
        LOGGER.info(String.format("quote-update: %s:%s\n", jsonNode.get("symbol"), jsonNode.get("timestamp")));

        restPublisher.publish(quoteUpdate);
    }

    public void OnATStreamTopMarketMoversUpdate(ATServerAPIDefines.ATMARKET_MOVERS_STREAM_UPDATE update)
    {
        System.out.println("OnATStreamTopMarketMoversUpdate not implemented");
    }
}
