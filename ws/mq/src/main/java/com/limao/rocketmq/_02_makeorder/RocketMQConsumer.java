package com.limao.rocketmq._02_makeorder;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class RocketMQConsumer {
	
	public static void start() {
		new Thread(new Runnable() {
			public void run() {
				try {
					//1.创建消费者对象
					DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_consumer_group");
					//2.为消费者对象设置路由中心，方便从路由中心确定具体消费哪台Broker实例上的数据
					consumer.setNamesrvAddr("localhost:8080");
					//3.确定消费者具体要消费哪个Top的消息
					consumer.subscribe("orderPaySucess", "*");
					//4.注册消息监听器,消费者拉取到消息后会回调该监听器
					consumer.setMessageListener(new MessageListenerConcurrently() {
						public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> arg0, ConsumeConcurrentlyContext arg1) {
							//消费消息位置
							return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
						}
					});
					//5.启动消费者
					consumer.start();
					
					while(true) {
						Thread.sleep(1000);//6.让消费者线程保持启动状态,一直消费消息
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
