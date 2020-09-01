package priv.wxl.rocket.producer.filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import priv.wxl.rocket.config.JmsConfig;

/**
 * @author xueli.wang
 * @since 2020/08/30 23:21
 */

public class FilterMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message(JmsConfig.FILTER_TOPIC,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 设置一些属性
            msg.putUserProperty("a", String.valueOf(i));
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();
    }
}
