package priv.wxl.rocket.producer.batch;

import org.apache.rocketmq.common.message.Message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 复杂度只有当你发送大批量时才会增长，你可能不确定它是否超过了大小限制（4MB）。这时候你最好把你的消息列表分割一下：
 *
 * @author xueli.wang
 * @since 2020/08/30 23:08
 */
public class ListSplitter implements Iterator<List<Message>> {
    private final int SIZE_LIMIT = 1024 * 1024 * 4;
    private final List<Message> messages;
    private int curIndex;
    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return curIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        int startIndex = getStartIndex();
        int nextIndex = startIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);
            int tmpSize = calcMessageSize(message);
            if (tmpSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += tmpSize;
            }
        }
        List<Message> subList = messages.subList(startIndex, nextIndex);
        curIndex = nextIndex;
        return subList;
    }

    private int getStartIndex() {
        Message currMessage = messages.get(curIndex);
        int tmpSize = calcMessageSize(currMessage);
        while(tmpSize > SIZE_LIMIT) {
            curIndex += 1;
            Message message = messages.get(curIndex);
            tmpSize = calcMessageSize(message);
        }
        return curIndex;
    }

    private int calcMessageSize(Message message) {
        int tmpSize = message.getTopic().length() + message.getBody().length;
        Map<String, String> properties = message.getProperties();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            tmpSize += entry.getKey().length() + entry.getValue().length();
        }

        // 增加日志的开销20字节
        tmpSize = tmpSize + 20;
        return tmpSize;
    }
}