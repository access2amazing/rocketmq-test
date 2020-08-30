package priv.wxl.rocket.producer.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import priv.wxl.rocket.config.JmsConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量发送消息能显著提高传递小消息的性能。
 * 限制是这些批量消息应该有相同的topic，相同的waitStoreMsgOK，而且不能是延时消息。
 * 此外，这一批消息的总大小不应超过4M
 *
 * @author xueli.wang
 * @since 2020/08/30 23:08
 */

public class BatchMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        producer.start();

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(JmsConfig.BATCH_TOPIC, "TagA", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(JmsConfig.BATCH_TOPIC, "TagA", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(JmsConfig.BATCH_TOPIC, "TagA", "OrderID003", "Hello world 2".getBytes()));
        try {
            producer.send(messages);
        } catch (Exception e) {
            e.printStackTrace();
            //处理error
        }

        producer.shutdown();
    }
}
