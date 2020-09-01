package priv.wxl.rocket.config;

/**
 * @author xueli.wang
 * @since 2020/07/24 15:31
 */

public class JmsConfig {
    /**
     * Name Server 地址，因为是集群部署 所以有多个用 分号 隔开
     */
    public static final String NAME_SERVER = "127.0.0.1:9876";

    /**
     * 发送者组
     */
    public static final String PRODUCER_GROUP = "test_producer_group";
    public static final String TRANSACTION_PRODUCER_GROUP = "transaction_producer_group";

    /**
     * 发送测试消息的默认数量
     */
    public static final int DEFAULT_MESSAGE_COUNT = 10;

    /**
     * 消费者组
     */
    public static final String CONSUMER_GROUP = "test_consumer_group";

    /**
     * 主题名称 主题一般是服务器设置好 而不能在代码里去新建topic（ 如果没有创建好，生产者往该主题发送消息 会报找不到topic错误）
     */
    public static final String BASIC_TOPIC = "basic_topic";
    public static final String ORDER_TOPIC = "order_topic";
    public static final String SCHEDULE_TOPIC = "schedule_topic";
    public static final String BATCH_TOPIC = "batch_topic";
    public static final String FILTER_TOPIC = "filter_topic";
    public static final String TRANSACTION_TOPIC = "transaction_topic";


    /**
     * tags
     */
    public static final String TAG_SYNC = "sync";
    public static final String TAG_ASYNC = "async";
    public static final String TAG_ONE_WAY = "one_way";
}
