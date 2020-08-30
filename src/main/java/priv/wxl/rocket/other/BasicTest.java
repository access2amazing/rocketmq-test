package priv.wxl.rocket.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author xueli.wang
 * @since 2020/07/31 11:42
 */

public class BasicTest {
    private static final long ONE_DAY_TIMESTAMP = 24 * 60 * 60 * 1000;

    private static final long BEIJING_OFFSET_MILLS = 8 * 60 * 60 * 1000;

    public static void main(String[] args) {
        long currentTimestamp = System.currentTimeMillis();
        System.out.println(currentTimestamp - (currentTimestamp + BEIJING_OFFSET_MILLS) % ONE_DAY_TIMESTAMP);

        String[] tests = new String[]{"abc", "bcds", "acb", "abcd"};

        List<String> f = Arrays.asList(tests);
        Collections.sort(f);
        System.out.println(f);
        Arrays.sort(tests);
        System.out.println(Arrays.toString(tests));
    }
}
