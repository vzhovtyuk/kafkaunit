package net.myrts.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Encoder;
import org.apache.log4j.Logger;

public class JsonEncoder implements Encoder<DocumentMessage> {
    private static final Logger logger = Logger.getLogger(JsonEncoder.class);
    // instantiating ObjectMapper is expensive. In real life, prefer injecting the value.
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] toBytes(DocumentMessage documentMessage) {
        try {
            return objectMapper.writeValueAsString(documentMessage).getBytes();
        } catch (JsonProcessingException e) {
            logger.error(String.format("Json processing failed for object: %s", documentMessage.getClass().getName()), e);
        }
        return "".getBytes();
    }
}
