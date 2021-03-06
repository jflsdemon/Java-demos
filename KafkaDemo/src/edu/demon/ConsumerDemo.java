package edu.demon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

public class ConsumerDemo extends Thread {
	private final KafkaConsumer<Integer, String> consumer;
	private final String topic;
	public ConsumerDemo(String topic) {
		consumer = new KafkaConsumer<Integer, String>(createConsumerProperty());
		this.topic = topic;
	}
	private static Properties createConsumerProperty() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	    props.put("group.id", "Only");
	    props.put("enable.auto.commit", "true");
	    props.put("auto.commit.interval.ms", "1000");
	    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
	}
	
	@Override
	public void run() {
		consumer.subscribe(Arrays.asList("TestTopic001"));
	    while (true) {
	        ConsumerRecords<Integer, String> records = consumer.poll(100);
	        for (ConsumerRecord<Integer, String> record : records) {
	             System.out.printf("offset = %d, key = %d, value = %s%n", record.offset(), record.key(), record.value());
	        }
	    }
	}
}
