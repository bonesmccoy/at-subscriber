package org.bones.alphai.subscriber.stream;

import at.shared.ATServerAPIDefines;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bones.alphai.subscriber.activetick.Dictionaries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class QuoteUpdate implements UpdateInterface
{
    public final String symbol;
    public final short year;
    public final short month;
    public final short day;
    public final short hour;
    public final short minute;
    public final short seconds;
    public final short milliseconds;
    public final Date timestamp;
    public final double bidPrice;
    public final short bidPricePrecision;
    public final long bidSize;
    public final double askPrice;
    public final byte askPricePrecision;
    public final long askSize;
    public final byte askExchangeType;

    public QuoteUpdate(ATServerAPIDefines.ATQUOTESTREAM_QUOTE_UPDATE update)
    {
        String strSymbol = new String(update.symbol.symbol);
        int plainSymbolIndex = strSymbol.indexOf((byte)0);
        symbol = strSymbol.substring(0, plainSymbolIndex);

        year = update.quoteDateTime.year;
        month = update.quoteDateTime.month;
        day = update.quoteDateTime.day;
        hour = update.quoteDateTime.hour;
        minute = update.quoteDateTime.minute;
        seconds = update.quoteDateTime.second;
        milliseconds = update.quoteDateTime.milliseconds;

        timestamp = (new GregorianCalendar(year, (month - 1), day, hour, minute, seconds)).getTime();

        bidPrice = update.bidPrice.price;
        bidPricePrecision = update.bidPrice.precision;
        bidSize = update.bidSize;

        askPrice = update.askPrice.price;
        askPricePrecision = update.askPrice.precision;
        askSize = update.askSize;

        askExchangeType = update.askExchange.m_atExchangeType;
    }

    @Override
    public ObjectNode toJsonNode()
    {
        ObjectMapper mapper = new ObjectMapper();

        return createObjectNode(mapper);
    }

    @Override
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

        payload.put("bid_price", bidPrice);
        payload.put("bid_price_precision", bidPricePrecision);
        payload.put("bid_size_size", bidSize);

        payload.put("ask_price", askPrice);
        payload.put("ask_price_precision", askPricePrecision);
        payload.put("ask_size_size", askSize);

        String exchangeName = Dictionaries.EXCHANGE_DICTIONARY.containsKey(Byte.toUnsignedInt(askExchangeType)) ?
                Dictionaries.EXCHANGE_DICTIONARY.get(Byte.toUnsignedInt(askExchangeType)) :
                String.format("%d", askExchangeType);
            payload.put("ask_exchange_type", exchangeName);

        return payload;
    }
}
