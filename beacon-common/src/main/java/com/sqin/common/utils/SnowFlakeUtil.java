package com.sqin.common.utils;

import com.sqin.common.enums.ExceptionEnums;
import com.sqin.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author Qin
 * @Date 2025/5/7 10:29
 * @Description 雪花算法生成全局唯一ID
 * 64个bit位的long类型的值
 * 第一位，占1个bit位，就是0，
 * 第二位，占41个bit位，代表时间戳
 * 第三位，占5个bit位，代表机器 id
 * 第四位，占5个bit位，服务id
 * 第五位，占12个bit位，序列，自增的数字
 **/
@Component
public class SnowFlakeUtil {

    private long timeStart = 1746547200000l;

    @Value("${snowflake.machineId:0}")
    private long machineId = 0;

    @Value("${snowflake.serviceId:0}")
    private long serviceId = 0;

    private long sequence;

    /**
     * 序列占用的bit位数
     */
    private long sequenceBits = 12L;

    /**
     * 机器id占用的bit位数
     */
    private long machineIdBits = 5L;

    /**
     * 服务id占用的bit位数
     */
    private long serviceIdBits = 5L;

    /**
     * 计算出机器id的最大值
     */
    private long maxMachineId = -1 ^ (-1 << machineIdBits);
    // 11111111 11111111 11111111 11111111 11111111 11111111
    // 11111111 11111111 11111111 11111111 11111111 11100000
    // 00000000 00000000 00000000 00000000 00000000 00011111

    /**
     * 计算出服务id的最大值
     */
    private long maxServiceId = -1 ^ (-1 << serviceIdBits);

    @PostConstruct
    public void init() {
        if (machineId > maxMachineId || serviceId > maxServiceId) {
            System.out.println("机器ID或服务ID超最大范围值");
            throw new ApiException(ExceptionEnums.SNOWFLAKE_OUT_OF_RANGE);
        }
    }

    /**
     * 服务id需要位移的位数
     */
    private long serviceIdShift = sequenceBits;

    /**
     * 机器id需要位移的位数
     */
    private long machineIdShift = sequenceBits + machineIdBits;

    /**
     * 时间戳需要位移的位数
     */
    private long timestampShift = sequenceBits + machineIdBits + serviceIdBits;

    /**
     * 序列的最大值
     */
    private long maxSequenceId = -1 ^ (-1 << sequenceBits);

    /**
     * 记录最近一次获取id的时间
     *
     * @return
     */
    private long lastTimestamp = -1;


    public synchronized long nextId() {
        // 1, 拿到当前时间的毫秒值
        long timestamp = timeGen();

        // 避免时间回拨造成重复出现的id
        if(timestamp < lastTimestamp) {
            System.out.println("当前时间出现时间回拨。");
            throw new ApiException(ExceptionEnums.SNOWFLAKE_TIME_BACK);
        }

        // 2, 41个bit的时间知道存什么了，但是序列也需要计算一下
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequenceId;

            if (sequence == 0) {
                // 进到这里，说明已经是最大值了，只能等下一个毫秒了
                timestamp = timeGen();
                while (timestamp == lastTimestamp) {
                    timestamp = timeGen();
                }
            }
        } else {
            sequence = 0;
        }

        // 重新给lastTimestamp赋值
        lastTimestamp = timestamp;

        // 计算id
        return (timestamp - timeStart) << timestampShift |
                machineId << machineIdShift |
                serviceId << serviceIdShift |
                sequence & Long.MAX_VALUE;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowFlakeUtil util = new SnowFlakeUtil();
        long id = util.nextId();
        System.out.println(id);

        System.out.println(Long.toBinaryString(id));

        System.out.println("100110011010011111100001100000000000000000000000".length());
    }

//    用来计算timeStart，这里从2025年5月7号开始计算
//    public static void main(String[] args) {
//        LocalDate localDate = LocalDate.of(2025, 05, 07);
//        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
//        System.out.println(instant.toEpochMilli());
//
//        System.out.println(new Date(1746547200000l));
//    }

}
