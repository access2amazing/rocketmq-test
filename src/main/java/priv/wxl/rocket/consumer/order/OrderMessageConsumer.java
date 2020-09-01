package priv.wxl.rocket.consumer.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import priv.wxl.rocket.config.JmsConfig;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 顺序消息消费，带事务方式（应用可控制Offset什么时候提交）
 *
 * @author xueli.wang
 * @since 2020/08/30 22:47
 */

public class OrderMessageConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(JmsConfig.CONSUMER_GROUP);
        consumer.setNamesrvAddr(JmsConfig.NAME_SERVER);

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe(JmsConfig.ORDER_TOPIC, "TagA || TagB || TagC");

        consumer.registerMessageListener(new MessageListenerOrderly() {

            Random random = new Random();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt messageExt : messageExtList) {
                    // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                    System.out.println("consumeThread=" + Thread.currentThread().getName() +
                            ", queueId=" + messageExt.getQueueId() +
                            ", content:" + new String(messageExt.getBody()));
                }

                try {
                    // 模拟业务逻辑处理中...
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();

        System.out.println("Consumer Started.");
    }
}
