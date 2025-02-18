package com.limao.rocketmq._03_proorconsmessage.product;

import java.util.List;
import java.util.Set;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

public class PushPullForMQConsumer {
	
	/**
	 * push方式获取消息，即Broker主动将消息发送过来
	 * @param topic
	 * @throws Exception
	 */
	public static void consumerPush(String topic) throws Exception {
		DefaultMQPushConsumer pushMQConsumer = new DefaultMQPushConsumer("order_consumer_group");
		pushMQConsumer.setNamesrvAddr("localhost:8080");
		pushMQConsumer.subscribe(topic, "*");
		
		pushMQConsumer.setMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		pushMQConsumer.start();
	}
	
	/**
	 * pull获取消息，即消费者主动去Broker获取消息
	 * @param topic
	 * @throws Exception
	 */
	public static void consumerPull(String topic) throws Exception {
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("order_consumer_group");
		consumer.setNamesrvAddr("localhost:8080");
		consumer.start();
		
		Set<MessageQueue> msgs = consumer.fetchSubscribeMessageQueues(topic);
		for (MessageQueue mq : msgs) {
			while(true) {
				PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, 1111, 32);
				switch (pullResult.getPullStatus()) {
				case FOUND:
					break;
				case NO_MATCHED_MSG:
					break;
				case NO_NEW_MSG:
					break;
				case OFFSET_ILLEGAL:
					break;
				default:
					break;
				}
			}
		}
	}
	
	
	
	
}
