package priv.wxl.rocket.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import priv.wxl.rocket.config.JmsConfig;

import java.util.concurrent.TimeUnit;

/**
 * @author xueli.wang
 * @since 2020/07/25 18:24
 */

public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(JmsConfig.PRODUCER_GROUP);
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);

        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);

        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            Message message = new Message(JmsConfig.TOPIC,
                    JmsConfig.TAG_ASYNC,
                    "OrderID188",
                    ("Hello RocketMQ, AsyncProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.printf("%-10d Exception %s %n", index, throwable);
                    throwable.printStackTrace();
                }
            });
        }

        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
