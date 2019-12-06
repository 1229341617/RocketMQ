package com.limao.rocketmq._03_proorconsmessage.product;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ThreeWaysForMQProducer {
	
	private static DefaultMQProducer producer;
	
	static {
		try {
			producer = new DefaultMQProducer("order_producer_group");
			producer.setNamesrvAddr("localhost:8080");
			producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
	
	public static void send(String topic, String message) throws Exception {
		Message msg = new Message(topic,
									"",
									message.getBytes(RemotingHelper.DEFAULT_CHARSET));
		
		//1.同步发送消息：必须等待消息的返回,否则会阻塞到此处
		SendResult sendResult = producer.send(msg);
		//2.异步发送消息：send函数调用了就会执行过去
		////设置消息发送失败后，重试的次数
		producer.setRetryTimesWhenSendAsyncFailed(5);
		producer.send(msg, new SendCallback() {
			public void onSuccess(SendResult sendResult) {
				//消息发送成功时的处理
			}
			
			public void onException(Throwable e) {
				//发送消息异常时的处理
			}
		});
		//3.单向发送消息：不管是否成功发送与否，发送了就行
		producer.sendOneway(msg);
	}
	
	
	
}