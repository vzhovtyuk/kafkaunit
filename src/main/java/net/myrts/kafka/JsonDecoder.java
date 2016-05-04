package net.myrts.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Decoder;
import org.apache.log4j.Logger;

import java.io.IOException;

public class JsonDecoder implements
        Decoder<DocumentMessage> {
    private static final Logger LOGGER = Logger.getLogger(JsonDecoder.class);

    @Override
    public DocumentMessage fromBytes(byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(bytes, DocumentMessage.class);
        } catch (IOException e) {
            LOGGER.error(String.format("Json processing failed for object: %s", bytes.toString()), e);
        }
        return null;
    }
}
