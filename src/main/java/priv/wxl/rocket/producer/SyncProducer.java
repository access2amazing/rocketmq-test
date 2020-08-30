package priv.wxl.rocket.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import priv.wxl.rocket.config.JmsConfig;

/**
 * @author xueli.wang
 * @since 2020/07/24 15:33
 */

public class SyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message(JmsConfig.TOPIC,
                    JmsConfig.TAG_SYNC,
                    ("Hello RocketMQ, SyncProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();
    }
}
