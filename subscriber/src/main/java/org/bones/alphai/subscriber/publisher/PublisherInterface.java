package org.bones.alphai.subscriber.publisher;

import org.bones.alphai.subscriber.stream.TradeUpdate;

public interface PublisherInterface {

    void publish(TradeUpdate update);
}
