package com.nutcracker.example.demo.web.controller;

import cn.hutool.core.date.DateUtil;
import com.nutcracker.example.demo.entity.dto.dashboard.PayDailySumTotalDto;
import com.nutcracker.example.demo.enums.BusiEnum;
import com.nutcracker.example.demo.util.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统级请求控制器
 *
 * @author 胡桃夹子
 * @date 2020-03-01 12:43
 */
@Slf4j
@Controller
public class DashboardController {
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    /**
     * 创建 PayDailySumTotalDto 对象列表
     *
     * @return PayDailySumTotalDto 对象列表
     */
    public static List<PayDailySumTotalDto> createPayDailySumTotalDtoList() {
        List<PayDailySumTotalDto> result = new ArrayList<>();
        // 第一条数据
        result.add(PayDailySumTotalDto.builder()
                .busiEnumCode("CCB_QR_CODE_PAY")
                .merchantCode("105000073929890")
                .snapshotDate(DateUtil.parse("2025-01-26"))
                .dailyCount(0)
                .dailyTotal(new BigDecimal("0.00"))
                .monthlyCount(588)
                .monthlyTotal(new BigDecimal("9578789.50"))
                .build());

        // 第二条数据
        result.add(PayDailySumTotalDto.builder()
                .busiEnumCode("CCB_QR_CODE_PAY")
                .merchantCode("105000270130745")
                .snapshotDate(DateUtil.parse("2025-01-26"))
                .dailyCount(16)
                .dailyTotal(new BigDecimal("289900.00"))
                .monthlyCount(253)
                .monthlyTotal(new BigDecimal("3943460.00"))
                .build());

        // 第三条数据
        result.add(PayDailySumTotalDto.builder()
                .busiEnumCode("CCB_QR_CODE_PAY")
                .merchantCode("105000270130749")
                .snapshotDate(DateUtil.parse("2025-01-26"))
                .dailyCount(0)
                .dailyTotal(new BigDecimal("0.00"))
                .monthlyCount(138)
                .monthlyTotal(new BigDecimal("2453640.00"))
                .build());

        // 第四条数据
        result.add(PayDailySumTotalDto.builder()
                .busiEnumCode("CCB_QR_CODE_PAY")
                .merchantCode("105000270130754")
                .snapshotDate(DateUtil.parse("2025-01-26"))
                .dailyCount(0)
                .dailyTotal(new BigDecimal("0.00"))
                .monthlyCount(225)
                .monthlyTotal(new BigDecimal("4149030.00"))
                .build());

        // 第五条数据
        result.add(PayDailySumTotalDto.builder()
                .busiEnumCode("ALIPAY_QRCODE_PAY")
                .merchantCode("2021005106694497")
                .snapshotDate(DateUtil.parse("2025-01-26"))
                .dailyCount(0)
                .dailyTotal(new BigDecimal("0.00"))
                .monthlyCount(0)
                .monthlyTotal(new BigDecimal("0.00"))
                .build());

        // 第六条数据
        result.add(PayDailySumTotalDto.builder()
                .busiEnumCode("ALIPAY_FACE_TO_FACE_PAY")
                .merchantCode("2021005106694497")
                .snapshotDate(DateUtil.parse("2025-01-26"))
                .dailyCount(0)
                .dailyTotal(new BigDecimal("0.00"))
                .monthlyCount(3)
                .monthlyTotal(new BigDecimal("3194.01"))
                .build());

        return result;
    }

    @GetMapping("dashboard")
    public String dashboard(ModelMap model) {
        log.info("dashboard page");
        // 当日0点0分0秒
        PayDailySumTotalDto total = PayDailySumTotalDto.builder().dailyTotal(BigDecimal.ZERO).dailyCount(0).monthlyTotal(BigDecimal.ZERO).monthlyCount(0).build();
        PayDailySumTotalDto ccbpayTotal = PayDailySumTotalDto.builder().dailyTotal(BigDecimal.ZERO).dailyCount(0).monthlyTotal(BigDecimal.ZERO).monthlyCount(0).build();
        PayDailySumTotalDto alipayTotal = PayDailySumTotalDto.builder().dailyTotal(BigDecimal.ZERO).dailyCount(0).monthlyTotal(BigDecimal.ZERO).monthlyCount(0).build();
        List<PayDailySumTotalDto> ccbpayList = new ArrayList<>();
        List<PayDailySumTotalDto> alipayList = new ArrayList<>();
        for (PayDailySumTotalDto dto : createPayDailySumTotalDtoList()) {
            if (dto.getBusiEnumCode().equals(BusiEnum.CCB_QR_CODE_PAY.getCode())) {
                // 建设银行汇总
                ccbpayTotal.setDailyTotal(BigDecimalUtil.add(ccbpayTotal.getDailyTotal(), dto.getDailyTotal()));
                ccbpayTotal.setDailyCount(ccbpayTotal.getDailyCount() + dto.getDailyCount());
                ccbpayTotal.setMonthlyTotal(BigDecimalUtil.add(ccbpayTotal.getMonthlyTotal(), dto.getMonthlyTotal()));
                ccbpayTotal.setMonthlyCount(ccbpayTotal.getMonthlyCount() + dto.getMonthlyCount());
                ccbpayList.add(dto);
            } else if (dto.getBusiEnumCode().equals(BusiEnum.ALIPAY_QRCODE_PAY.getCode())
                    || dto.getBusiEnumCode().equals(BusiEnum.ALIPAY_JSAPI_PAY.getCode())
                    || dto.getBusiEnumCode().equals(BusiEnum.ALIPAY_FACE_TO_FACE_PAY.getCode())) {
                // 支付宝汇总
                alipayTotal.setDailyTotal(BigDecimalUtil.add(alipayTotal.getDailyTotal(), dto.getDailyTotal()));
                alipayTotal.setDailyCount(alipayTotal.getDailyCount() + dto.getDailyCount());
                alipayTotal.setMonthlyTotal(BigDecimalUtil.add(alipayTotal.getMonthlyTotal(), dto.getMonthlyTotal()));
                alipayTotal.setMonthlyCount(alipayTotal.getMonthlyCount() + dto.getMonthlyCount());
                dto.setMerchantCode(BusiEnum.getBusiEnum(dto.getBusiEnumCode()).getMsg());
                alipayList.add(dto);
            }
            total.setDailyTotal(BigDecimalUtil.add(total.getDailyTotal(), dto.getDailyTotal()));
            total.setDailyCount(total.getDailyCount() + dto.getDailyCount());
            total.setMonthlyTotal(BigDecimalUtil.add(total.getMonthlyTotal(), dto.getMonthlyTotal()));
            total.setMonthlyCount(total.getMonthlyCount() + dto.getMonthlyCount());
        }
        // 创建一个包含所有业务编码的映射
        Map<String, List<PayDailySumTotalDto>> map = new HashMap<>();
        map.put("建设银行", ccbpayList);
        map.put("支付宝", alipayList);
        model.put("payDailySumTotalMap", map);
        model.put("total", total);
        model.put("ccbpayTotal", ccbpayTotal);
        model.put("alipayTotal", alipayTotal);
        log.info("dashboard page");
        return "dashboard";
    }
}
