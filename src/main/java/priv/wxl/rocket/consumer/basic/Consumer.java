package priv.wxl.rocket.consumer.basic;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import priv.wxl.rocket.config.JmsConfig;

import java.util.List;

/**
 * @author xueli.wang
 * @since 2020/08/05 14:58
 */

public class Consumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(JmsConfig.CONSUMER_GROUP);
        consumer.setNamesrvAddr(JmsConfig.NAME_SERVER);

        consumer.subscribe(JmsConfig.BASIC_TOPIC, "*");

        consumer.registerMessageListener(
                (List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) -> {
                    System.out.printf("%s Receive New Message: %s %n", Thread.currentThread().getName(), list);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                });

        consumer.start();
        System.out.println("Consumer Started...");
    }
}
