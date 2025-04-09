package com.nutcracker.example.demo.enums;


import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 收款产品枚举
 *
 * @author 胡桃夹子
 * @date 2020/2/20 17:52
 */
@Getter
@ToString
public enum BusiEnum {

    // 注意：编码长度不能超过30个字符

    CCB_QR_CODE_PAY("CCB_QR_CODE_PAY", "建设银行聚合二维码支付", "15分钟", "ccb.png"),
    ALIPAY_QRCODE_PAY("ALIPAY_QRCODE_PAY", "支付宝订单码支付", "2小时", "alipay.png"),
    ALIPAY_JSAPI_PAY("ALIPAY_JSAPI_PAY", "支付宝JSAPI支付", "3小时", "alipay.png"),
    ALIPAY_FACE_TO_FACE_PAY("ALIPAY_FACE_TO_FACE_PAY", "支付宝当面付", "3小时", "alipay.png"),
    ;

    private static final Map<String, BusiEnum> BUSI_ENUM_MAP = new LinkedHashMap<>();

    private static final Map<String, BusiEnum> ALIPAY_BUSI_ENUM_MAP = new LinkedHashMap<>();

    static {
        for (BusiEnum obj : BusiEnum.values()) {
            BUSI_ENUM_MAP.put(obj.code, obj);
        }
        ALIPAY_BUSI_ENUM_MAP.put(ALIPAY_QRCODE_PAY.code, ALIPAY_QRCODE_PAY);
        ALIPAY_BUSI_ENUM_MAP.put(ALIPAY_JSAPI_PAY.code, ALIPAY_JSAPI_PAY);
        ALIPAY_BUSI_ENUM_MAP.put(ALIPAY_FACE_TO_FACE_PAY.code, ALIPAY_FACE_TO_FACE_PAY);
    }

    private final String code;
    private final String msg;
    /**
     * 多长时间内有效
     */
    private final String effectiveTime;
    /**
     * 二维码图LOGO
     */
    private final String logo;

    public static BusiEnum getBusiEnum(String code) {
        return BUSI_ENUM_MAP.get(code);
    }

    public static Map<String, BusiEnum> getBusiEnumMap() {
        return BUSI_ENUM_MAP;
    }

    public static Map<String, BusiEnum> getAlipayBusiEnumMap() {
        return BUSI_ENUM_MAP;
    }

    private BusiEnum(String code, String msg, String effectiveTime, String logo) {
        this.code = code;
        this.msg = msg;
        this.effectiveTime = effectiveTime;
        this.logo = logo;
    }

}
