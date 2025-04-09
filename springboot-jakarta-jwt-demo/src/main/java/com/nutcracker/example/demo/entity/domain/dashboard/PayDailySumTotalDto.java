package com.nutcracker.example.demo.entity.domain.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付每日总金额
 *
 * @author 胡桃夹子
 * @date 2024/12/16 16:46:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayDailySumTotalDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1816316229033847444L;
    /**
     * 供应商通道表ID
     */
    private String channelBranchId;

    /**
     * 业务枚举编码
     */
    private String busiEnumCode;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 日交易笔数
     */
    private Integer dailyCount;

    /**
     * 日交易汇总(元)
     */
    private BigDecimal dailyTotal;

    /**
     * 月交易笔数
     */
    private Integer monthlyCount;

    /**
     * 月交易汇总(元)
     */
    private BigDecimal monthlyTotal;

    /**
     * 统计日期
     */
    private Date snapshotDate;

}
