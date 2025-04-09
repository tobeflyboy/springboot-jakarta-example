package com.nutcracker.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * BigDecimal 工具类
 * 提供数值格式化、四舍五入、加减乘除等常用操作
 *
 * @author 胡桃夹子
 * @date 2020-02-24 09:57
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {

    }

    /** 货币保留两位小数 */
    public static final int MONEY_POINT = 2;

    /** 默认除法运算精度 */
    private static final int DEF_DIV_SCALE = 4;

    /**
     * 数值格式化精度
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的数值
     */
    public static Double format(double decimal, int point) {
        return BigDecimal.valueOf(decimal).setScale(point, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 四舍五入格式化数值
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的数值
     */
    public static Double formatRoundUp(double decimal, int point) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setMinimumFractionDigits(point);
        nf.setMaximumFractionDigits(point);
        return Double.valueOf(nf.format(decimal));
    }

    /**
     * 四舍五入格式化数值为字符串
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的字符串
     */
    public static String formatStringHalfUp(BigDecimal decimal, int point) {
        return decimal.setScale(point, RoundingMode.HALF_UP).toString();
    }

    /**
     * 四舍五入格式化数值为字符串，遇0自动补全
     *
     * @param decimal 数值
     * @param scale   小数位数
     * @param df      DecimalFormat
     * @return 格式化后的字符串
     */
    public static String formatStringHalfUp(BigDecimal decimal, int scale, DecimalFormat df) {
        return df.format(decimal.setScale(scale, RoundingMode.HALF_UP));
    }

    /**
     * 四舍五入格式化相乘结果为字符串，遇0自动补全
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @param scale    小数位数
     * @param df       DecimalFormat
     * @return 格式化后的字符串
     */
    public static String formatStringMultiplyHalfUp(BigDecimal decimal1, BigDecimal decimal2, int scale, DecimalFormat df) {
        return df.format(multiply(decimal1, decimal2, scale));
    }

    /**
     * 四舍五入格式化相除结果为字符串，遇0自动补全
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @param scale    小数位数
     * @param df       DecimalFormat
     * @return 格式化后的字符串
     */
    public static String formatStringDivideHalfUp(BigDecimal decimal1, BigDecimal decimal2, int scale, DecimalFormat df) {
        return df.format(decimal1.divide(decimal2, scale, RoundingMode.HALF_UP));
    }

    /**
     * 四舍五入格式化数值
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的数值
     */
    public static BigDecimal formatHalfUp(BigDecimal decimal, int point) {
        return decimal == null ? BigDecimal.ZERO.setScale(point, RoundingMode.HALF_UP) : decimal.setScale(point, RoundingMode.HALF_UP);
    }

    /**
     * 向下取整格式化数值为字符串
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的字符串
     */
    public static String formatStringHalfDown(BigDecimal decimal, int point) {
        return decimal.setScale(point, RoundingMode.HALF_DOWN).toString();
    }

    /**
     * 向上取整格式化数值为字符串
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的字符串
     */
    public static String formatStringUp(BigDecimal decimal, int point) {
        return decimal.setScale(point, RoundingMode.UP).toString();
    }

    /**
     * 向下取整格式化数值为字符串
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的字符串
     */
    public static String formatStringDown(BigDecimal decimal, int point) {
        return decimal.setScale(point, RoundingMode.DOWN).toString();
    }

    /**
     * 向下取整格式化数值
     *
     * @param decimal 数值
     * @param point   小数位数
     * @return 格式化后的数值
     */
    public static BigDecimal formatDown(BigDecimal decimal, int point) {
        return decimal.setScale(point, RoundingMode.DOWN);
    }

    /**
     * 格式化金额，带千位符
     *
     * @param money 金额
     * @return 格式化后的金额字符串
     */
    public static String moneyFormat(Double money) {
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingSize(3);
        formatter.setRoundingMode(RoundingMode.FLOOR);
        return formatter.format(money);
    }

    /**
     * 带小数的显示小数，不带小数的显示整数
     *
     * @param decimal 数值
     * @return 格式化后的字符串
     */
    public static String doubleTrans(Double decimal) {
        return Math.round(decimal) - decimal == 0 ? String.valueOf(decimal.longValue()) : String.valueOf(decimal);
    }

    /**
     * BigDecimal 相加
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @return 相加后的数值
     */
    public static Double add(double decimal1, double decimal2) {
        if (decimal1 == 0) {
            return decimal2;
        }
        if (decimal2 == 0) {
            return decimal1;
        }
        return BigDecimal.valueOf(decimal1).add(BigDecimal.valueOf(decimal2)).doubleValue();
    }

    /**
     * BigDecimal 相加
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @return 相加后的数值
     */
    public static BigDecimal add(BigDecimal decimal1, BigDecimal decimal2) {
        if (decimal1 == null) {
            decimal1 = BigDecimal.ZERO;
        }
        if (decimal2 == null) {
            decimal2 = BigDecimal.ZERO;
        }
        return decimal1.add(decimal2);
    }

    /**
     * BigDecimal 相减
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @return 相减后的数值
     */
    public static Double subtract(double decimal1, double decimal2) {
        return BigDecimal.valueOf(decimal1).subtract(BigDecimal.valueOf(decimal2)).doubleValue();
    }

    /**
     * BigDecimal 相减
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @return 相减后的数值
     */
    public static BigDecimal subtract(BigDecimal decimal1, BigDecimal decimal2) {
        return (decimal1 == null ? BigDecimal.ZERO : decimal1).subtract(decimal2 == null ? BigDecimal.ZERO : decimal2);
    }

    /**
     * BigDecimal 相乘
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @return 相乘后的数值
     */
    public static Double multiply(double decimal1, double decimal2) {
        return BigDecimal.valueOf(decimal1).multiply(BigDecimal.valueOf(decimal2)).doubleValue();
    }

    /**
     * BigDecimal 相乘
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @param scale    保留小数位
     * @return 相乘后的数值
     */
    public static BigDecimal multiply(BigDecimal decimal1, BigDecimal decimal2, int scale) {
        return decimal1.multiply(decimal2).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal 相乘并格式化
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @param scale    保留小数位
     * @param df       DecimalFormat
     * @return 格式化后的字符串
     */
    public static String multiply(BigDecimal decimal1, BigDecimal decimal2, int scale, DecimalFormat df) {
        return df.format(multiply(decimal1, decimal2, scale));
    }

    /**
     * 比较两个数值的大小
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @return 比较结果：小于0（decimal1 < decimal2），大于0（decimal1 > decimal2），等于0（decimal1 = decimal2）
     */
    public static int compare(double decimal1, double decimal2) {
        return BigDecimal.valueOf(decimal1).compareTo(BigDecimal.valueOf(decimal2));
    }

    /**
     * 比较两个数值的大小
     *
     * @param decimal1 数值1
     * @param decimal2 数值2
     * @return 比较结果：小于0（decimal1 < decimal2），大于0（decimal1 > decimal2），等于0（decimal1 = decimal2）
     */
    public static int compare(BigDecimal decimal1, BigDecimal decimal2) {
        return decimal1.compareTo(decimal2);
    }

    /**
     * 提供（相对）精确的除法运算，默认保留4位小数
     *
     * @param decimal1 被除数
     * @param decimal2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(double decimal1, double decimal2) {
        return div(decimal1, decimal2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算
     *
     * @param decimal1 被除数
     * @param decimal2 除数
     * @param scale    保留小数位
     * @return 两个参数的商
     */
    public static BigDecimal div(double decimal1, double decimal2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (decimal2 == 0) {
            throw new IllegalArgumentException("The divisor cannot be zero");
        }
        return BigDecimal.valueOf(decimal1).divide(BigDecimal.valueOf(decimal2), scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算
     *
     * @param decimal1 被除数
     * @param decimal2 除数
     * @param scale    保留小数位
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal decimal1, BigDecimal decimal2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (decimal2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("The divisor cannot be zero");
        }
        return decimal1.divide(decimal2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算
     *
     * @param decimal1 被除数
     * @param decimal2 除数
     * @param scale    保留小数位
     * @return 两个参数的商
     */
    public static BigDecimal div(String decimal1, String decimal2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (decimal2 == null || "0".equals(decimal2)) {
            throw new IllegalArgumentException("The divisor cannot be zero");
        }
        return new BigDecimal(decimal1).divide(new BigDecimal(decimal2), scale, RoundingMode.HALF_UP);
    }

    /**
     * 等额本息计算：根据贷款金额、年限、LPR 和基点计算月供
     *
     * @param totalLoan 贷款金额（单位：万）
     * @param loanYears 贷款年限
     * @param lpr       LPR（贷款市场报价利率，单位：%）
     * @param bp        基点（单位：BP，1BP = 0.01%）
     * @return 月供金额（四舍五入保留两位小数）
     */
    public static BigDecimal getMonthPay(BigDecimal totalLoan, int loanYears, BigDecimal lpr, int bp) {
        // 1. 计算实际利率（LPR + 基点）
        // 实际利率 = LPR + (基点 / 100)
        // 基点单位是 BP，1BP = 0.01%，因此需要将基点除以 100 转换为百分比
        BigDecimal actualRate = lpr.add(BigDecimal.valueOf(bp).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));

        // 2. 计算总月数
        // 总月数 = 贷款年限 × 12
        int totalMonth = loanYears * 12;

        // 3. 计算月利率
        // 月利率 = 实际利率 / (100 × 12)
        // 实际利率是百分比形式，因此需要除以 100 转换为小数形式
        BigDecimal monthRate = actualRate.divide(BigDecimal.valueOf(100 * 12), 10, RoundingMode.HALF_UP);

        // 4. 计算 (1 + 月利率)^还款月数
        // 这是等额本息公式中的幂运算部分
        // 1 + 月利率
        BigDecimal onePlusMonthRate = monthRate.add(BigDecimal.ONE);
        // (1 + 月利率)^还款月数
        BigDecimal powResult = BigDecimal.valueOf(Math.pow(onePlusMonthRate.doubleValue(), totalMonth));

        // 5. 计算分子：贷款金额 × 月利率 × (1 + 月利率)^还款月数
        // 贷款金额单位是万，需要乘以 10000 转换为元
        BigDecimal numerator = totalLoan
                // 万转换为元
                .multiply(BigDecimal.valueOf(10000))
                // 乘以月利率
                .multiply(monthRate)
                // 乘以 (1 + 月利率)^还款月数
                .multiply(powResult);

        // 6. 计算分母：(1 + 月利率)^还款月数 - 1
        BigDecimal denominator = powResult.subtract(BigDecimal.ONE);

        // 7. 计算月供
        // 月供 = 分子 / 分母
        // 结果保留两位小数，四舍五入
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }


    //public static void main(String[] args) {
    //    // 商业贷款总额 650 万元，贷款年限 25 年，LPR 3.95%，基点 -45
    //    // 贷款金额（单位：万）
    //    BigDecimal totalLoan = new BigDecimal("650");
    //    // 贷款年限
    //    int loanYears = 25;
    //    // LPR
    //    BigDecimal lpr = new BigDecimal("3.95");
    //    // 基点
    //    int bp = -45;
    //    // 计算月供
    //    BigDecimal monthPay = getMonthPay(totalLoan, loanYears, lpr, bp);
    //    // 输出结果
    //    // 月供: 32540.53 元才是正确的
    //    System.out.println("月供: " + monthPay + " 元");
    //}
}