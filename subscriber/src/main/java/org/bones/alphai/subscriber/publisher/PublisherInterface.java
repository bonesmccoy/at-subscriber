package org.bones.alphai.subscriber.publisher;

import org.bones.alphai.subscriber.stream.UpdateInterface;

public interface PublisherInterface {

    void publish(UpdateInterface update);
}
