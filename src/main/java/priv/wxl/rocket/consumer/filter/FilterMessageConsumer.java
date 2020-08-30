package priv.wxl.rocket.consumer.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import priv.wxl.rocket.config.JmsConfig;

import java.util.List;

/**
 * 用MessageSelector.bySql来使用sql筛选消息
 *
 * @author xueli.wang
 * @since 2020/08/30 23:25
 */

public class FilterMessageConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(JmsConfig.CONSUMER_GROUP);

        // 只有订阅的消息有这个属性a, a >=0 and a <= 3
        consumer.subscribe(JmsConfig.FILTER_TOPIC, MessageSelector.bySql("a between 0 and 3"));
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
