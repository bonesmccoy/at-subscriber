package co.alphai.atsubscriber.publisher;

import co.alphai.atsubscriber.stream.TradeUpdate;

public interface PublisherInterface {

    void publish(TradeUpdate update);
}
