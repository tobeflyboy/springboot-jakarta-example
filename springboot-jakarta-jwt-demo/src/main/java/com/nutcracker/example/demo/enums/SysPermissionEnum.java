package com.nutcracker.example.demo.enums;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.nutcracker.example.demo.entity.dataobject.auth.SysPermissionDo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 权限枚举
 * <a href="http://www.wapadd.cn/icons/awesome/index.htm">FontAwesome 4.3.0 icon 参考</a>
 *
 * @author 胡桃夹子
 * @date 2025/01/02 16:52:23
 */
@Getter
public enum SysPermissionEnum {

    /*
    SELECT
        upper(permission_code) || '(' ||
        '"' || permission_code || '",' ||
        '"' || permission_name || '",' ||
        -- 处理 parent_permission_code 为 NULL 的情况
        CASE WHEN parent_permission_code IS NULL THEN 'null' ELSE '"' || parent_permission_code || '"' END || ',' ||
        -- 处理 url 为 NULL 的情况
        CASE WHEN url IS NULL THEN 'null' ELSE '"' || url || '"' END || ',' ||
        -- 处理 icon 为 NULL 的情况
        CASE WHEN icon IS NULL THEN 'null' ELSE '"' || icon || '"' END || ',' ||
        -- hide 字段保持原逻辑（NULL 时默认 1）
        '' || IFNULL(hide, 1) || ',' ||
        '' || lev || ',' ||
        '' || sort || '' ||
        '),' AS SysPermissionEnum
    FROM sys_permission;
     */

    DASHBOARD("dashboard", "Dashboard", null, "/dashboard", "HomeFilled", 1, 1, 1),

    QXGL("qxgl", "权限管理", null, null, "Setting", 1, 1, 1000),
    QXGL_ZYGL("qxgl_zygl", "资源管理", "qxgl", "/auth/permission", "Files", 1, 2, 1001),
    QXGL_ZSGL("qxgl_zsgl", "角色管理", "qxgl", "/auth/role", "Finished", 1, 2, 1002),
    QXGL_YHGL("qxgl_yhgl", "用户管理", "qxgl", "/auth/user", "UserFilled", 1, 2, 1003),

    GUSHICI("gushici", "古诗词", null, null, "Notebook", 1, 1, 1100),
    GUSHICI_TENGWANGGEXU("gushici_tengwanggexu", "滕王阁序", "gushici", "/gushici/tengwanggexu", "Tickets", 1, 2, 1101),
    GUSHICI_JIANGJINJIU("gushici_jiangjinjiu", "将进酒", "gushici", "/gushici/jiangjinjiu", "Tickets", 1, 2, 1102),
    GUSHICI_MANJIANGHONG("gushici_manjianghong", "满江红·写怀", "gushici", "/gushici/manjianghong", "Tickets", 1, 2, 1103),
    GUSHICI_YUEYANGLOUJI("gushici_yueyanglouji", "岳阳楼记", "gushici", "/gushici/yueyanglouji", "Tickets", 1, 2, 1104),
    GUSHICI_CHUSHIBIAO("gushici_chushibiao", "出师表", "gushici", "/gushici/chushibiao", "Tickets", 1, 2, 1105),
    GUSHICI_XIAOYAOYOU("gushici_xiaoyaoyou", "逍遥游", "gushici", "/gushici/xiaoyaoyou", "Tickets", 1, 2, 1106),
    GUSHICI_XIAOYAOYOUJIEXUAN("gushici_xiaoyaoyoujiexuan", "逍遥游节选", "gushici", "/gushici/xiaoyaoyoujiexuan", "Tickets", 1, 2, 1107),
    GUSHICI_DUANGEXING("gushici_duangexing", "短歌行", "gushici", "/gushici/duangexing", "Tickets", 1, 2, 1108),
    GUSHICI_SONGDONGYANGMASHENGXU("gushici_songdongyangmashengxu", "送东阳马生序", "gushici", "/gushici/songdongyangmashengxu", "Tickets", 1, 2, 1109),
    GUSHICI_DINGFENGBO("gushici_dingfengbo", "定风波", "gushici", "/gushici/dingfengbo", "Tickets", 1, 2, 1110),
    GUSHICI_JIANGCHENGZIYIMAOZHENGYUEERSHIRIYEJIMENG("gushici_jiangchengziyimaozhengyueershiriyejimeng", "江城子·乙卯正月二十日夜记梦", "gushici", "/gushici/jiangchengziyimaozhengyueershiriyejimeng", "Tickets", 1, 2, 1111),
    GUSHICI_JIANGCHENGZIMIZHOUCHULIE("gushici_jiangchengzimizhouchulie", "江城子·密州出猎", "gushici", "/gushici/jiangchengzimizhouchulie", "Tickets", 1, 2, 1112),
    GUSHICI_TAOHUAANGE("gushici_taohuaange", "桃花庵歌", "gushici", "/gushici/taohuaange", "Tickets", 1, 2, 1113),

    XTPZ("xtpz", "系统配置", null, "/view/sysconfig/setconfig", "Setting", 1, 1, 1200),

    DXGL("dxgl", "电商管理", null, "/view/tenant/tenant-list", "ShoppingCart", 1, 1, 1300),

    DSPZ("dspz", "电商配置", null, "/view/tenantConfig/list", "Tools", 1, 1, 1400),

    DCCSGL("dccsgl", "地区城市管理", null, null, "Location", 1, 1, 1500),
    DCCSGL_TJCS("dccsgl_tjcs", "添加城市", "dccsgl", "/view/city/add2", "Plus", 1, 2, 1501),
    DCCSGL_CSLB("dccsgl_cslb", "城市列表", "dccsgl", "/view/city/list", "List", 1, 2, 1502),

    CHGL("chgl", "账号管理", null, "/view/user/user_list", "User", 1, 1, 1600),

    LPGL("lpgl", "楼盘管理", null, null, "OfficeBuilding", 1, 1, 1700),
    LPGL_KTLP("lpgl_ktlp", "开通楼盘", "lpgl", "/view/project/openProject", "Plus", 1, 2, 1701),
    LPGL_LPSH("lpgl_lpsh", "楼盘审核", "lpgl", "/view/project/list", "Check", 1, 2, 1702),
    LPGL_LPGL("lpgl_lpgl", "楼盘管理", "lpgl", "/view/project/manager_list", "OfficeBuilding", 1, 2, 1703),
    LPGL_HZLP("lpgl_hzlp", "合作楼盘", "lpgl", "/view/project/edit_list", "ChatDotRound", 1, 2, 1704),
    LPGL_TJLP("lpgl_tjlp", "添加楼盘", "lpgl", null, "CirclePlus", 1, 2, 1705),
    LPGL_TJLP_JBXX("lpgl_tjlp_jbxx", "基本信息", "lpgl_tjlp", "/view/project/add", "Document", 1, 3, 1706),
    LPGL_TJLP_HXGL("lpgl_tjlp_hxgl", "户型管理", "lpgl_tjlp", "/view/project/add2", "Grid", 1, 3, 1707),
    LPGL_TJLP_LPXC("lpgl_tjlp_lpxc", "楼盘相册", "lpgl_tjlp", "/view/project/add3", "Picture", 1, 3, 1708),
    LPGL_TJLP_XKGL("lpgl_tjlp_xkgl", "销控管理", "lpgl_tjlp", "/view/project/add4", "Sort", 1, 3, 1709),
    LPGL_TJLP_HZXX("lpgl_tjlp_hzxx", "合作信息", "lpgl_tjlp", "/view/project/add5", "ChatLineRound", 1, 3, 1710),
    LPGL_TJLP_CPWH("lpgl_tjlp_cpwh", "产品维护", "lpgl_tjlp", "/view/project/add6", "Tools", 1, 3, 1711),
    LPGL_TJLP_RJWH("lpgl_tjlp_rjwh", "拥金维护", "lpgl_tjlp", "/view/project/add7", "Money", 1, 3, 1712),
    LPGL_TJLP_LPDT("lpgl_tjlp_lpdt", "楼盘动态", "lpgl_tjlp", "/view/project/add8", "Shop", 1, 3, 1713),
    LPGL_TJLP_YHHD("lpgl_tjlp_yhhd", "优惠活动", "lpgl_tjlp", "/view/project/add9", "Present", 1, 3, 1714),

    JJGSJJR("jjgsjjr", "经济公司/经纪人", null, null, "User", 1, 1, 1800),
    JJGSJJR_JJGSWH("jjgsjjr_jjgswh", "经纪公司维护", "jjgsjjr", "/view/firm/list", "OfficeBuilding", 1, 2, 1801),

    ZYGWGL("zygwgl", "置业顾问管理", null, null, "Avatar", 1, 1, 1900),
    ZYGWGL_ZYGWGKSH("zygwgl_zygwgksh", "置业顾问挂靠审核", "zygwgl", "/view/adviserProject/adviserAuditList", "Check", 1, 2, 1901),
    ZYGWGL_ZYGWGL("zygwgl_zygwgl", "置业顾问管理", "zygwgl", "/view/adviser/list", "Avatar", 1, 2, 1902),

    POSJYGL("posjygl", "POS交易管理", null, null, "CreditCard", 1, 1, 2100),
    POSJYGL_POSJBD("posjygl_posjbd", "POS机绑定", "posjygl", "/view/pos/bind", "Link", 1, 2, 2101),
    POSJYGL_POSBDXX("posjygl_posbdxx", "POS绑定信息", "posjygl", "/view/pos/list", "List", 1, 2, 2102),
    POSJYGL_POSJYXX("posjygl_posjyxx", "POS交易信息", "posjygl", "/view/pos/poslog", "Refresh", 1, 2, 2103),

    SJTJ("sjtj", "数据统计", null, null, "DataAnalysis", 1, 1, 2200),
    SJTJ_SYTJ("sjtj_sytj", "收入统计", "sjtj", "/view/data/statIncome_data", "TrendCharts", 1, 2, 2201),

    HTGL("htgl", "合同管理", null, null, "Document", 1, 1, 2300),
    HTGL_TJGLLB("htgl_tjgllb", "添加/管理列表", "htgl", "/view/agreement/list", "Plus", 1, 2, 2301),
    HTGL_ZRG("htgl_zrg", "转认购", "htgl", "/view/agreement/rengou", "Refresh", 1, 2, 2302),
    HTGL_QY("htgl_qy", "签约", "htgl", "/view/agreement/qianyue", "Checked", 1, 2, 2303),
    HTGL_HTMXBJ("htgl_htmxbj", "合同明细/编辑", "htgl", "/view/agreement/edit", "Edit", 1, 2, 2304),
    HTGL_HTXQ("htgl_htxq", "合同详情", "htgl", "/view/agreement/view", "DocumentCopy", 1, 2, 2305),

    JJGSGLY("jjgsgly", "经济公司管理员", null, null, "User", 1, 1, 2400),
    JJGSGLY_GHSH("jjgsgly_ghsh", "挂靠审核", "jjgsgly", "/view/firm/verify", "Check", 1, 2, 2401),
    JJGSGLY_DGZH("jjgsgly_dgzh", "对公账号", "jjgsgly", "/view/firm/view", "CreditCard", 1, 2, 2402),
    JJGSGLY_YJXX("jjgsgly_yjxx", "佣金信息", "jjgsgly", "/view/commission/agentCommission", "Money", 1, 2, 2403),
    JJGSGLY_EWYJ("jjgsgly_ewyj", "额外佣金", "jjgsgly", "/view/commission/commissionExt", "Coin", 1, 2, 2404),

    MDGLY("mdgly", "门店管理员", null, null, "User", 1, 1, 2500),
    MDGLY_JJRLB("mdgly_jjrlb", "经纪人列表", "mdgly", "/view/agent/list", "User", 1, 2, 2501),
    MDGLY_KHLB("mdgly_khlb", "客户列表", "mdgly", "/view/agent/custAgent", "User", 1, 2, 2502),

    ACJL("acjl", "案场经理", null, null, "User", 1, 1, 2600),
    ACJL_KHSH("acjl_khsh", "客户审核", "acjl", "/view/custintent/custAuditList", "Check", 1, 2, 2601),

    XMJL("xmjl", "项目经理", null, null, "User", 1, 1, 2700),
    XMJL_KFSBT("xmjl_kfsbt", "开发商补贴", "xmjl", "/view/devpSubsidy/list", "Coin", 1, 2, 2701),
    XMZL("xmzl", "项目助理", null, null, "User", 1, 1, 2800),
    XMZL_QRDF("xmzl_qrdf", "确认到访", "xmzl", "/view/code/code", "Check", 1, 2, 2801),
    XMZL_FSYYYZM("xmzl_fsyyyzm", "发送语音验证码", "xmzl", "/view/code/telcode", "Microphone", 1, 2, 2802),

    YYZY("yyzy", "运营专员", null, null, "User", 1, 1, 2900),
    YYZY_QDGZPZ("yyzy_qdgzpz", "签到规则配置", "yyzy", "/view/sysconfig/signin", "Calendar", 1, 2, 2901),
    YYZY_TSGL("yyzy_tsgl", "推送管理", "yyzy", "/view/push/list", "Bell", 1, 2, 2902),
    YYZY_GGWGL("yyzy_ggwgl", "广告位管理", "yyzy", "/view/adv/list", "Promotion", 1, 2, 2903),
    YJZY("yjzy", "佣金专员", null, null, "User", 1, 1, 3000),
    YJZY_YJGZSH("yjzy_yjgzsh", "佣金规则审核", "yjzy", "/view/commission/auditList", "Check", 1, 2, 3001),
    YJZY_JJRYBTX("yjzy_jjrybtx", "经纪人元宝提现", "yjzy", "/view/agent/grain", "Wallet", 1, 2, 3002),
    YJZY_DGZHSH("yjzy_dgzhsh", "对公账号审核", "yjzy", "/view/firm/account", "CreditCard", 1, 2, 3003),
    YJZY_EWYJSH("yjzy_ewyjsh", "额外佣金审核", "yjzy", "/view/commission/Ext", "Coin", 1, 2, 3004),

    CWZY("cwzy", "财务专员", null, null, "User", 1, 1, 3100),
    CWZY_KFSBT("cwzy_kfsbt", "开发商补贴确认", "cwzy", "/view/devpSubsidy/list2", "Coin", 1, 2, 3101),
    CWZY_TKSH("cwzy_tksh", "退款审核", "cwzy", "/view/refund/list", "Check", 1, 2, 3102),
    CWZY_YJSH("cwzy_yjsh", "佣金审核", "cwzy", "/view/commission/list", "Check", 1, 2, 3103),

    JJFWZYCS("jjfwzycs", "经纪服务专员测试", null, null, "User", 1, 1, 3200),
    JJFWZYCS_JJGSWH("jjfwzycs_jjgswh", "经纪公司维护", "jjfwzycs", "/view/firm/list2", "OfficeBuilding", 1, 2, 3201),

    KFZY("kfzy", "客服专员", null, null, "User", 1, 1, 3300),
    KFZY_KHYXSH("kfzy_khyxsh", "客户意向审核", "kfzy", "/view/custintent/custSupport", "Check", 1, 2, 3301),

    ABOUT("about", "关于系统", null, "/about", "InfoFilled", 1, 1, 9000);

    private static final List<SysPermissionDo> INIT_SYS_PERMISSION_LIST = new ArrayList<>();

    private SysPermissionEnum(String permissionCode, String permissionName, String parentPermissionCode, String url, String icon, Integer hide, Integer lev, Integer sort) {
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
        this.parentPermissionCode = parentPermissionCode;
        this.url = url;
        this.icon = icon;
        this.hide = hide;
        this.lev = lev;
        this.sort = sort;
    }

    static {
        for (SysPermissionEnum p : SysPermissionEnum.values()) {
            SysPermissionDo per = SysPermissionDo.builder()
                    .id(String.valueOf(IdWorker.getId("sys_permission")))
                    .permissionCode(p.getPermissionCode())
                    .permissionName(p.getPermissionName())
                    .parentPermissionCode(p.getParentPermissionCode())
                    .url(p.getUrl())
                    .icon(p.getIcon())
                    .hide(p.getHide())
                    .lev(p.getLev())
                    .sort(p.getSort())
                    .createTime(Calendar.getInstance().getTime())
                    .build();
            INIT_SYS_PERMISSION_LIST.add(per);
        }
    }

    public static List<SysPermissionDo> getPermissionList() {
        return INIT_SYS_PERMISSION_LIST;
    }

    private final String permissionName;
    private final String icon;
    private final String permissionCode;
    private final Integer hide;
    private final String url;
    private final Integer lev;
    private final Integer sort;
    private final String parentPermissionCode;
}