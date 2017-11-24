package edu.demon;

public class KafkaProducerConsumerDemo {

	public static void main(String[] args) {
		ProducerDemo producer = new ProducerDemo(KafkaProperties.topic);
		producer.start();
		ConsumerDemo consumer = new ConsumerDemo(KafkaProperties.topic);
		consumer.start();

	}

}
