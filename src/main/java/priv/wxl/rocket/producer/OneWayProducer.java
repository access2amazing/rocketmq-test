package priv.wxl.rocket.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import priv.wxl.rocket.config.JmsConfig;

/**
 * @author xueli.wang
 * @since 2020/08/05 14:53
 */

public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);

        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);

        producer.start();

        for (int i = 0; i < 100; i++) {
            Message message = new Message(JmsConfig.TOPIC,
                    JmsConfig.TAG_ONE_WAY,
                    ("Hello RocketMQ, OneWayProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            producer.send(message);
        }

        producer.shutdown();
    }
}
