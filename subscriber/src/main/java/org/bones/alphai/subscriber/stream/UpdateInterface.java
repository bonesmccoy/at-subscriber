package org.bones.alphai.subscriber.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UpdateInterface {

    ObjectNode toJsonNode();

    String toJsonString() throws JsonProcessingException;
}
