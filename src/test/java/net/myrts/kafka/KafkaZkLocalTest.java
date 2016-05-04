package net.myrts.kafka;

import net.myrts.kafka.KafkaLocal;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.fail;

public class KafkaZkLocalTest {
    public static final String TEST_TOPIC = "test-topic";

    static KafkaLocal kafka;

    @BeforeClass
    public static void startKafka() {
        Properties kafkaProperties = new Properties();
        Properties zkProperties = new Properties();

        try {
            //load properties
            kafkaProperties.load(Class.class.getResourceAsStream("/kafkalocal.properties"));
            zkProperties.load(Class.class.getResourceAsStream("/zklocal.properties"));

            //start kafka
            kafka = new KafkaLocal(kafkaProperties, zkProperties);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            fail("Error running local Kafka broker");
            e.printStackTrace(System.out);
        }

        //do other things
    }

    @Test
    public void shouldProduceConsumeMessages() {
        //create kafka producer without zookeeper on localhost:
        Properties props = new Properties();
        //Use broker.list to bypass zookeeper:
        props.put("bootstrap.servers", "localhost:9092");
        props.put("metadata.broker.list", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        //send one message to local kafka server:
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TEST_TOPIC, "test-message" + i);
            producer.send(producerRecord);
        }
        producer.close();

        //consume one messages from Kafka:
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put("group.id", "test");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.commit.interval.ms", "1000");
        consumerProps.put("session.timeout.ms", "30000");
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


       /* KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(TEST_TOPIC));
        try {
            ConsumerRecords<String, String> records = consumer.poll(1000);

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Message consumed " + record.offset() +  ": key " + record.key()
                        + ": " + record.value());
            }
            assertFalse(records.isEmpty());
        } finally {
            //close the consumer
            //stop the kafka broker:
            consumer.close();
        }
         */
    }

    @AfterClass
    public static void stopKafka() {
        kafka.stop();
    }
}