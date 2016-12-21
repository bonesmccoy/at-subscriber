package co.alphai.atsubscriber.stream;

import at.shared.ATServerAPIDefines;
import at.utils.jlib.PrintfFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class TradeUpdate {

    private String symbol;
    private short hour;
    private short minute;
    private short seconds;
    private short milliseconds;
    private short pricePrecision;
    private double price;
    private long lastSize;

    public TradeUpdate(ATServerAPIDefines.ATQUOTESTREAM_TRADE_UPDATE update)
    {
        String strSymbol = new String(update.symbol.symbol);
        int plainSymbolIndex = strSymbol.indexOf((byte)0);
        symbol = strSymbol.substring(0, plainSymbolIndex);

        hour = update.lastDateTime.hour;
        minute = update.lastDateTime.minute;
        seconds = update.lastDateTime.second;
        milliseconds = update.lastDateTime.milliseconds;
        price = update.lastPrice.price;
        pricePrecision = update.lastPrice.precision;
        lastSize = update.lastSize;
    }

    public ObjectNode toJsonNode()
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payload = mapper.createObjectNode();

        payload.put("symbol", symbol);
        payload.put("hour", hour);
        payload.put("minute", minute);
        payload.put("seconds", seconds);
        payload.put("milliseconds", milliseconds);
        payload.put("price", price);
        payload.put("lastSize", lastSize);

        return payload;
    }

    public String toJsonString() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payload = mapper.createObjectNode();

        payload.put("symbol", symbol);
        payload.put("hour", hour);
        payload.put("minute", minute);
        payload.put("seconds", seconds);
        payload.put("milliseconds", milliseconds);
        payload.put("price", price);
        payload.put("lastSize", lastSize);

        return mapper.writeValueAsString(payload);
    }
}
