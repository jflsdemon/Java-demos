package edu.demon;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

//这组是新出的API，用异步的方式发送消息
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;




public class ProducerDemo extends Thread {
	public static Producer<Integer, String> initKafkaProducer(String brokerList){
	    Properties props = new Properties();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList.substring(1));//格式：host1:port1,host2:port2,....
	    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);//a batch size of zero will disable batching entirely
	    props.put(ProducerConfig.LINGER_MS_CONFIG, 0);//send message without delay
	    props.put(ProducerConfig.ACKS_CONFIG, "1");//对应partition的leader写到本地后即返回成功。极端情况下，可能导致失败
	    props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
	    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    Producer<Integer, String> kafkaProducer = new KafkaProducer<Integer, String>(props);
	    return kafkaProducer;
	}
	private final String brokerList = new String("localhost:9092");
	private final Producer<Integer, String> producer;
	private final String topic;
	public ProducerDemo(String topic) {
		producer = initKafkaProducer(brokerList);
		this.topic = topic;
	}
	@Override
	public void run() {
		int messageNo = 1;
		while (true) {
			String messageStr = new String("Message_" + messageNo);
			System.out.println("Send: " + messageStr);
			producer.send(new ProducerRecord<Integer, String>(topic, messageStr));
			messageNo++;
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
