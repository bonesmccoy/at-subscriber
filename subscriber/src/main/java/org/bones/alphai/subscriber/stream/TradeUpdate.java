package org.bones.alphai.subscriber.stream;

import at.feedapi.Helpers;
import at.shared.ATServerAPIDefines;
import at.shared.ActiveTick.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TradeUpdate {

    private String symbol;
    private short year;
    private short month;
    private short day;
    private short hour;
    private short minute;
    private short seconds;
    private short milliseconds;
    private short pricePrecision;
    private double price;
    private long lastSize;
    private Date timestamp;

    public TradeUpdate(ATServerAPIDefines.ATQUOTESTREAM_TRADE_UPDATE update)
    {
        String strSymbol = new String(update.symbol.symbol);
        int plainSymbolIndex = strSymbol.indexOf((byte)0);
        symbol = strSymbol.substring(0, plainSymbolIndex);

        year = update.lastDateTime.year;
        month = update.lastDateTime.month;
        day = update.lastDateTime.day;
        hour = update.lastDateTime.hour;
        minute = update.lastDateTime.minute;
        seconds = update.lastDateTime.second;
        milliseconds = update.lastDateTime.milliseconds;
        price = update.lastPrice.price;
        pricePrecision = update.lastPrice.precision;
        lastSize = update.lastSize;
        timestamp = (new GregorianCalendar(year, (month - 1), day, hour, minute, seconds)).getTime();
    }

    public ObjectNode toJsonNode()
    {
        ObjectMapper mapper = new ObjectMapper();

        return createObjectNode(mapper);
    }

    public String toJsonString() throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payload = createObjectNode(mapper);

        return mapper.writeValueAsString(payload);
    }

    private ObjectNode createObjectNode(ObjectMapper mapper) {

        ObjectNode payload = mapper.createObjectNode();

        payload.put("symbol", symbol);
        payload.put("year", year);
        payload.put("month", month);
        payload.put("day", day);
        payload.put("hour", hour);
        payload.put("minute", minute);
        payload.put("seconds", seconds);
        payload.put("milliseconds", milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        payload.put("timestamp", formatter.format(timestamp));
        payload.put("price", price);
        payload.put("price_precision", pricePrecision);
        payload.put("last_size", lastSize);

        return payload;
    }
}
