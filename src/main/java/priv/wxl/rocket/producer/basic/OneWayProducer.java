package priv.wxl.rocket.producer.basic;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import priv.wxl.rocket.config.JmsConfig;

/**
 * 单向发送消息
 * 这种方式主要用在不特别关心发送结果的场景，例如日志发送。
 *
 * @author xueli.wang
 * @since 2020/08/05 14:53
 */

public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);

        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);

        producer.start();

        for (int i = 0; i < JmsConfig.DEFAULT_MESSAGE_COUNT; i++) {
            Message message = new Message(JmsConfig.BASIC_TOPIC,
                    JmsConfig.TAG_ONE_WAY,
                    ("Hello RocketMQ, OneWayProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 发送单向消息，没有任何返回结果
            producer.send(message);
        }

        producer.shutdown();
    }
}
