package com.limao.rocketmq.proconsumer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {
	
	public static void main(String[] args) throws Exception {
		final DefaultMQProducer producer = new DefaultMQProducer("test_producer");
		//设置NameServer地址
		producer.setNamesrvAddr("192.168.43.51:9876");
		producer.start();
		//循环中启动多线程,使用producer不断发送消息
		for(int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					while(true) {
						try {
							Message msg = new Message("TopicTest","TagA", 
									"Test".getBytes(RemotingHelper.DEFAULT_CHARSET));
							SendResult sendResult = producer.send(msg);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		while(true) {
			Thread.sleep(1000);
		}
	}
	
}
