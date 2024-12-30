package com.nutcracker.example.demo.strategy.secret;

import com.nutcracker.example.demo.constant.Constants;
import com.nutcracker.example.demo.enums.SecretStrategyEnum;
import com.nutcracker.example.demo.strategy.StrategyFactory;
import com.nutcracker.example.demo.util.FackNoUtil;
import com.nutcracker.example.demo.util.ListUtil;
import com.nutcracker.example.demo.util.TimeUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 加解密操作
 *
 * @author 胡桃夹子
 * @date 2021-11-17 18:20
 */
public abstract class BaseStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(BaseStrategy.class);

    /**
     * 获取加解密策略枚举
     *
     * @return 加解密策略枚举
     */
    public abstract SecretStrategyEnum getSecretStrategyEnum();

    /**
     * 线程池开太大,会导致加密系统抗不住
     */
    @Value("${app.secret.pool-size}")
    protected Integer poolSize;

    @Autowired
    protected StrategyFactory strategyFactory;

    /**
     * 自定义线程池
     */
    @Autowired
    @Qualifier(value = "taskExecutor")
    protected ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void init() {
        // 注册策略
        strategyFactory.registerSecretStrategy(this);
    }

    /**
     * 加密
     *
     * @param param 明文号码
     * @return 密文号码
     */
    public abstract String encrypt(String param);

    /**
     * 解密
     *
     * @param param 密文号码
     * @return 明文号码
     */
    public abstract String decrypt(String param);

    /**
     * 多线程批量加密或解密操作
     *
     * @param list   源数据
     * @param action true代表加密,false代表解密
     * @return 操作结果文档流
     */
    public String execute(List<String> list, boolean action) {
        long begin = System.currentTimeMillis();
        String result = null;
        try {
            if (null == list || list.isEmpty()) {
                LOG.warn("# body is empty.");
                return null;
            }

            // 按照份数切割
            List<List<String>> batchList = ListUtil.splitListForNum(list, poolSize);
            int size = batchList.size();
            LOG.info("# total batch={}\n\n\n\n", size);

            // 线程计数器
            CountDownLatch countDownLatch = new CountDownLatch(size);

            // start executor
            List<Future<String>> futureList = new ArrayList<>();
            AtomicInteger page = new AtomicInteger(1);
            for (List<String> currentBatch : batchList) {
                LOG.info("# action={},currentBatch.size={}", action, currentBatch.size());
                Future<String> call = taskExecutor.submit(new JobTask(countDownLatch, page.getAndIncrement(), currentBatch, action));
                futureList.add(call);
            }
            LOG.info("\n\n================等待线程操作==========================\n\n");
            // 等待所有线程结束
            countDownLatch.await();

            // 执行其他操作
            LOG.info("******************* 操作结束");

            StringJoiner joiner = new StringJoiner(Constants.BR);
            for (Future<String> future : futureList) {
                joiner.add(future.get());
            }
            result = joiner.toString();

            // 执行其他操作
            LOG.info("------it's over");
        } catch (Exception e) {
            LOG.error("# error {}", e.getLocalizedMessage());
        } finally {
            LOG.info("# execute:{}s", TimeUtil.getSecond(begin));
        }
        return result;
    }


    private class JobTask implements Callable<String> {
        private CountDownLatch latch;
        private Integer batchNo;
        private List<String> list;

        /**
         * true代表加密,false代表解密
         */
        private final boolean action;

        public JobTask(CountDownLatch latch, Integer batchNo, List<String> list, boolean action) {
            this.latch = latch;
            this.batchNo = batchNo;
            this.list = list;
            this.action = action;
        }

        /**
         * 加密
         */
        private String en() {
            StringJoiner joiner = new StringJoiner(Constants.BR);
            AtomicInteger atomic = new AtomicInteger(1);
            int size = list.size();
            for (String temp : list) {
                String info = temp + "," + FackNoUtil.getFackNo(temp) + "," + encrypt(temp);
                joiner.add(info);
                LOG.info("# {} batchNo={},list.size={},index={},info=[{}]", getSecretStrategyEnum(), batchNo, size, atomic.getAndIncrement(), info);
            }
            return joiner.toString();
        }

        /**
         * 解密
         */
        private String de() {
            StringJoiner joiner = new StringJoiner(Constants.BR);
            AtomicInteger atomic = new AtomicInteger(1);
            int size = list.size();
            for (String temp : list) {
                String resp = decrypt(temp);
                String info = temp + "," + FackNoUtil.getFackNo(resp) + "," + resp;
                joiner.add(info);
                LOG.info("# {} batchNo={},list.size={},index={},info=[{}]", getSecretStrategyEnum(), batchNo, size, atomic.getAndIncrement(), info);
            }
            return joiner.toString();
        }


        @Override
        public String call() throws Exception {
            LOG.info("# pageNo={}, begin-----------------------------------------------------------------", batchNo);
            String resp;
            if (action) {
                resp = en();
            } else {
                resp = de();
            }
            // countDown自减
            latch.countDown();
            LOG.info("# pageNo={}, end -----------------------------------------------------------------\n\n", batchNo);
            return resp;
        }
    }

}
