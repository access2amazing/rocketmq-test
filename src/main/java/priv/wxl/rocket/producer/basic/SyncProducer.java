package priv.wxl.rocket.producer.basic;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import priv.wxl.rocket.config.JmsConfig;

/**
 * Producer端发送同步消息
 * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
 *
 * @author xueli.wang
 * @since 2020/07/24 15:33
 */

public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);
        // 设置NameServer的地址
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < JmsConfig.DEFAULT_MESSAGE_COUNT; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message message = new Message(JmsConfig.BASIC_TOPIC,
                    JmsConfig.TAG_SYNC,
                    ("Hello RocketMQ, SyncProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(message);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
