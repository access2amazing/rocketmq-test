package priv.wxl.rocket.producer.schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import priv.wxl.rocket.config.JmsConfig;

/**
 * 发送延时消息
 * 比如电商里，提交了一个订单就可以发送一个延时消息，1h后去检查这个订单的状态，如果还是未付款就取消订单释放库存。
 *
 * @author xueli.wang
 * @since 2020/08/30 22:59
 *
 * 现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18
 * "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
 * 消息消费失败会进入延时消息队列，消息发送时间与设置的延时等级和重试次数有关，详见代码SendMessageProcessor.java
 */

public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        // 启动生产者
        producer.start();
        int totalMessagesToSend = JmsConfig.DEFAULT_MESSAGE_COUNT;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message(JmsConfig.SCHEDULE_TOPIC, ("Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(5);
            // 发送消息
            producer.send(message);
        }
        // 关闭生产者
        producer.shutdown();
    }
}
