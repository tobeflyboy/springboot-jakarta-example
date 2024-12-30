package com.nutcracker.example.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 系统级请求控制器
 *
 * @author 胡桃夹子
 * @date 2020-03-01 12:43
 */
@Slf4j
@Controller
public class DashboardController  {

    //@Resource
    //private PayRecordTotalService payRecordTotalService;

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("dashboard")
    public String dashboard(ModelMap model) {
        //// 当日0点0分0秒
        //Date snaphostDate = DateUtil.parse(Calendar.getInstance().getTime());
        //List<PayDailySumTotalDto> list = payRecordTotalService.getPayRecordSum(snaphostDate);
        //PayDailySumTotalDto total = PayDailySumTotalDto.builder().dailyTotal(BigDecimal.ZERO).dailyCount(0).monthlyTotal(BigDecimal.ZERO).monthlyCount(0).build();
        //PayDailySumTotalDto ccbpayTotal = PayDailySumTotalDto.builder().dailyTotal(BigDecimal.ZERO).dailyCount(0).monthlyTotal(BigDecimal.ZERO).monthlyCount(0).build();
        //PayDailySumTotalDto alipayTotal = PayDailySumTotalDto.builder().dailyTotal(BigDecimal.ZERO).dailyCount(0).monthlyTotal(BigDecimal.ZERO).monthlyCount(0).build();
        //List<PayDailySumTotalDto> ccbpayList = new ArrayList<>();
        //List<PayDailySumTotalDto> alipayList = new ArrayList<>();
        //for (PayDailySumTotalDto dto : list) {
        //    if (dto.getBusiEnumCode().equals(BusiEnum.CCB_QR_CODE_PAY.getCode())) {
        //        ccbpayTotal.setDailyTotal(BigDecimalUtil.add(ccbpayTotal.getDailyTotal(), dto.getDailyTotal()));
        //        ccbpayTotal.setDailyCount(ccbpayTotal.getDailyCount() + dto.getDailyCount());
        //        ccbpayTotal.setMonthlyTotal(BigDecimalUtil.add(ccbpayTotal.getMonthlyTotal(), dto.getMonthlyTotal()));
        //        ccbpayTotal.setMonthlyCount(ccbpayTotal.getMonthlyCount() + dto.getMonthlyCount());
        //        ccbpayList.add(dto);
        //    } else if (dto.getBusiEnumCode().equals(BusiEnum.ALIPAY_QRCODE_PAY.getCode())) {
        //        alipayTotal.setDailyTotal(BigDecimalUtil.add(alipayTotal.getDailyTotal(), dto.getDailyTotal()));
        //        alipayTotal.setDailyCount(alipayTotal.getDailyCount() + dto.getDailyCount());
        //        alipayTotal.setMonthlyTotal(BigDecimalUtil.add(alipayTotal.getMonthlyTotal(), dto.getMonthlyTotal()));
        //        alipayTotal.setMonthlyCount(alipayTotal.getMonthlyCount() + dto.getMonthlyCount());
        //        dto.setMerchantCode("-");
        //        alipayList.add(dto);
        //    }
        //    total.setDailyTotal(BigDecimalUtil.add(total.getDailyTotal(), dto.getDailyTotal()));
        //    total.setDailyCount(total.getDailyCount() + dto.getDailyCount());
        //    total.setMonthlyTotal(BigDecimalUtil.add(total.getMonthlyTotal(), dto.getMonthlyTotal()));
        //    total.setMonthlyCount(total.getMonthlyCount() + dto.getMonthlyCount());
        //}
        //// 创建一个包含所有业务编码的映射
        //Map<String, List<PayDailySumTotalDto>> map = new HashMap<>();
        //map.put("建设银行", ccbpayList);
        //map.put("支付宝", alipayList);
        //model.put("payDailySumTotalMap", map);
        //model.put("total", total);
        //model.put("ccbpayTotal", ccbpayTotal);
        //model.put("alipayTotal", alipayTotal);
        log.info("dashboard page");
        return "dashboard";
    }
}
