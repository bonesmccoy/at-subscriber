package org.bones.alphai.subscriber.activetick;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Dictionaries {

    public static final Map<Integer, String> EXCHANGE_DICTIONARY;

    static {

        Map<Integer, String> exchangeDictionary = new HashMap<Integer, String>();

        exchangeDictionary.put(65,"AMEX");
        exchangeDictionary.put(66,"NasdaqOmxBx");
        exchangeDictionary.put(67,"NationalStockExchange");
        exchangeDictionary.put(68,"FinraAdf");
        exchangeDictionary.put(69,"CQS");
        exchangeDictionary.put(70,"Forex");
        exchangeDictionary.put(73,"InternationalSecuritiesExchange");
        exchangeDictionary.put(74,"EdgaExchange");
        exchangeDictionary.put(75,"EdgxExchange");
        exchangeDictionary.put(77,"ChicagoStockExchange");
        exchangeDictionary.put(78,"NyseEuronext");
        exchangeDictionary.put(80,"NyseArcaExchange");
        exchangeDictionary.put(81,"NasdaqOmx");
        exchangeDictionary.put(83,"CTS");
        exchangeDictionary.put(84,"CTANasdaqOMX");
        exchangeDictionary.put(85,"OTCBB");
        exchangeDictionary.put(117,"NNOTC");
        exchangeDictionary.put(87,"ChicagoBoardOptionsExchange");
        exchangeDictionary.put(88,"NasdaqOmxPhlx");
        exchangeDictionary.put(89,"BatsYExchange");
        exchangeDictionary.put(90,"BatsExchange");
        exchangeDictionary.put(84,"CanadaToronto");
        exchangeDictionary.put(86,"CanadaVenture");
        exchangeDictionary.put(79,"ExchangeOptionOpra");
        exchangeDictionary.put(66,"ExchangeOptionBoston");
        exchangeDictionary.put(67,"ExchangeOptionCboe");
        exchangeDictionary.put(78,"ExchangeOptionNyseArca");
        exchangeDictionary.put(87,"ExchangeOptionC2");
        exchangeDictionary.put(84,"ExchangeOptionNasdaqOmxBx");
        exchangeDictionary.put(32,"Composite");

        EXCHANGE_DICTIONARY = Collections.unmodifiableMap(exchangeDictionary);
    }
}
