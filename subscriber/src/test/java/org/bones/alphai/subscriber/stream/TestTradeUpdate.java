package org.bones.alphai.subscriber.stream;

import at.feedapi.Helpers;
import at.shared.ATServerAPIDefines;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;


public class TestTradeUpdate
{
    @Test
    public void testToJsonNode()
    {
        ATServerAPIDefines defines = new ATServerAPIDefines();

        ATServerAPIDefines.SYSTEMTIME timeOfEvent = defines.new SYSTEMTIME();
        timeOfEvent.year = 2016;
        timeOfEvent.month = 12;
        timeOfEvent.day = 22;
        timeOfEvent.hour = 12;
        timeOfEvent.minute = 0;
        timeOfEvent.second = 0;
        timeOfEvent.milliseconds = 0;

        ATServerAPIDefines.ATPRICE price = defines.new ATPRICE();
        price.precision = 2;
        price.price = 123.4567;


        ATServerAPIDefines.ATQUOTESTREAM_TRADE_UPDATE updateFromServer = defines.new ATQUOTESTREAM_TRADE_UPDATE();
        updateFromServer.lastDateTime = timeOfEvent;
        updateFromServer.lastPrice = price;
        updateFromServer.symbol = Helpers.StringToSymbol("AAPL");
        updateFromServer.lastSize = 2000;

        TradeUpdate trade = new TradeUpdate(updateFromServer);

        Assert.assertTrue(trade.toJsonNode() instanceof ObjectNode);
        try {
            Assert.assertEquals(
                    "{\"symbol\":\"AAPL\",\"year\":2016,\"month\":12,\"day\":22,\"hour\":12,\"minute\":0," +
                            "\"seconds\":0,\"milliseconds\":0,\"timestamp\":\"2016-12-22T12:00:00Z\"," +
                            "\"price\":123.4567,\"price_precision\":2,\"last_size\":2000}",
                    trade.toJsonString()
            );
        } catch (JsonProcessingException e) {
            Assert.assertTrue(false);
        }
    }
}
