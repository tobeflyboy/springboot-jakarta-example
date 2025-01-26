package com.nutcracker.example.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 视图控制器
 *
 * @author 胡桃夹子
 * @date 2024/12/23 09:47:13
 */
@Slf4j
@Controller
public class ViewController {

    @GetMapping("leftnav")
    public String leftNav() {
        log.debug("leftnav");
        return "leftnav";
    }

    @GetMapping("topnav")
    public String topNav() {
        log.debug("topNav");
        return "topnav";
    }


    @GetMapping("view/sysconfig/setconfig")
    public String setConfig() {
        log.info("loading view/sysconfig/setconfig ");
        return "view/sysconfig/setconfig";
    }

    @GetMapping("view/sysconfig/signin")
    public String signIn() {
        log.info("loading view/sysconfig/signin ");
        return "view/sysconfig/signin";
    }

    @GetMapping("view/tenant/tenant-list")
    public String tenantList() {
        log.info("loading view/tenant/tenant-list ");
        return "view/tenant/tenant-list";
    }

    @GetMapping("view/tenant/tenant-add")
    public String tenantAdd() {
        log.info("loading view/tenant/tenant-add ");
        return "view/tenant/tenant-add";
    }

    @GetMapping("view/tenantConfig/list")
    public String tenantConfigList() {
        log.info("loading view/tenantConfig/list ");
        return "view/tenantConfig/list";
    }

    @GetMapping("view/city/add2")
    public String cityAdd2() {
        log.info("loading view/city/add2 ");
        return "view/city/add2";
    }

    @GetMapping("view/city/list")
    public String cityList() {
        log.info("loading view/city/list ");
        return "view/city/list";
    }

    @GetMapping("view/user/user_list")
    public String userList() {
        log.info("loading view/user/user_list");
        return "view/user/user_list";
    }

    @GetMapping("view/project/openProject")
    public String openProject() {
        log.info("loading view/project/openProject");
        return "view/project/openProject";
    }

    @GetMapping("view/project/list")
    public String projectList() {
        log.info("loading view/project/list");
        return "view/project/list";
    }

    @GetMapping("view/project/view")
    public String projectView() {
        log.info("loading view/project/view");
        return "view/project/view";
    }

    @GetMapping("view/project/manager_list")
    public String managerList() {
        log.info("loading view/project/manager_list");
        return "view/project/manager_list";
    }

    @GetMapping("view/project/add")
    public String projectAdd() {
        log.info("loading view/project/add");
        return "view/project/add";
    }

    @GetMapping("view/project/add2")
    public String projectAdd2() {
        log.info("loading view/project/add2");
        return "view/project/add2";
    }

    @GetMapping("view/project/add3")
    public String projectAdd3() {
        log.info("loading view/project/add3");
        return "view/project/add3";
    }

    @GetMapping("view/project/add4")
    public String projectAdd4() {
        log.info("loading view/project/add4");
        return "view/project/add4";
    }

    @GetMapping("view/project/add5")
    public String projectAdd5() {
        log.info("loading view/project/add5");
        return "view/project/add5";
    }

    @GetMapping("view/project/add6")
    public String projectAdd6() {
        log.info("loading view/project/add6");
        return "view/project/add6";
    }

    @GetMapping("view/project/add7")
    public String projectAdd7() {
        log.info("loading view/project/add7");
        return "view/project/add7";
    }

    @GetMapping("view/project/add8")
    public String projectAdd8() {
        log.info("loading view/project/add8");
        return "view/project/add8";
    }

    @GetMapping("view/project/add9")
    public String projectAdd9() {
        log.info("loading view/project/add9");
        return "view/project/add9";
    }

    @GetMapping("view/project/edit_list")
    public String projectEditList() {
        log.info("loading view/project/edit_list");
        return "view/project/edit_list";
    }

    @GetMapping("view/project/add_hezuo")
    public String projectAddHezuo() {
        log.info("loading view/project/add_hezuo");
        return "view/project/add_hezuo";
    }

    @GetMapping("view/agent/list")
    public String agentList() {
        log.info("loading view/agent/list");
        return "view/agent/list";
    }

    @GetMapping("view/agent/custAgent")
    public String agentCustAgent() {
        log.info("loading view/agent/custAgent");
        return "view/agent/custAgent";
    }

    @GetMapping("view/agent/com_list")
    public String agentComList() {
        log.info("loading view/agent/com_list");
        return "view/agent/com_list";
    }

    @GetMapping("view/agent/namelist")
    public String agentNamelist() {
        log.info("loading view/agent/namelist");
        return "view/agent/namelist";
    }

    @GetMapping("view/agent/card")
    public String agentCard() {
        log.info("loading view/agent/card");
        return "view/agent/card";
    }

    @GetMapping("view/agent/grain")
    public String agentGrain() {
        log.info("loading view/agent/grain");
        return "view/agent/grain";
    }

    @GetMapping("view/agent/grain_list")
    public String grainList() {
        log.info("loading view/agent/grain_list");
        return "view/agent/grain_list";
    }

    @GetMapping("view/adviserProject/adviserAuditList")
    public String adviserAuditList() {
        log.info("loading view/adviserProject/adviserAuditList");
        return "view/adviserProject/adviserAuditList";
    }

    @GetMapping("view/adviser/list")
    public String adviserList() {
        log.info("loading view/adviser/list");
        return "view/adviser/list";
    }

    @GetMapping("view/adviser/namelist")
    public String adviserNameList() {
        log.info("loading view/adviser/namelist");
        return "view/adviser/namelist";
    }

    @GetMapping("view/adviser/card")
    public String adviserCard() {
        log.info("loading view/adviser/card");
        return "view/adviser/card";
    }

    @GetMapping("view/adviser/grain")
    public String adviserGrain() {
        log.info("loading view/adviser/grain");
        return "view/adviser/grain";
    }

    @GetMapping("view/pos/bind")
    public String posBind() {
        log.info("loading view/pos/bind");
        return "view/pos/bind";
    }

    @GetMapping("view/pos/list")
    public String posList() {
        log.info("loading view/pos/list");
        return "view/pos/list";
    }

    @GetMapping("view/pos/poslog")
    public String posPosLog() {
        log.info("loading view/pos/poslog");
        return "view/pos/poslog";
    }

    @GetMapping("view/data/statIncome_data")
    public String statIncomeData() {
        log.info("loading view/data/statIncome_data");
        return "view/data/statIncome_data";
    }

    @GetMapping("view/agreement/list")
    public String agreementList() {
        log.info("loading view/agreement/list");
        return "view/agreement/list";
    }

    @GetMapping("view/agreement/rengou")
    public String agreementRengou() {
        log.info("loading view/agreement/rengou");
        return "view/agreement/rengou";
    }

    @GetMapping("view/agreement/qianyue")
    public String agreementQianyue() {
        log.info("loading view/agreement/qianyue");
        return "view/agreement/qianyue";
    }

    @GetMapping("view/agreement/edit")
    public String agreementEdit() {
        log.info("loading view/agreement/edit");
        return "view/agreement/edit";
    }

    @GetMapping("view/agreement/view")
    public String agreementView() {
        log.info("loading view/agreement/view");
        return "view/agreement/view";
    }

    @GetMapping("view/firm/list")
    public String firmList() {
        log.info("loading view/firm/list");
        return "view/firm/list";
    }

    @GetMapping("view/firm/edit")
    public String firmEdit() {
        log.info("loading view/firm/edit");
        return "view/firm/edit";
    }

    @GetMapping("view/firm/verify")
    public String firmVerify() {
        log.info("loading view/firm/verify");
        return "view/firm/verify";
    }

    @GetMapping("view/firm/list2")
    public String firmList2() {
        log.info("loading view/firm/list2");
        return "view/firm/list2";
    }

    @GetMapping("view/firm/view")
    public String firmView() {
        log.info("loading view/firm/view");
        return "view/firm/view";
    }

    @GetMapping("view/firm/account")
    public String firmAccount() {
        log.info("loading view/firm/account");
        return "view/firm/account";
    }

    @GetMapping("view/commission/agentCommission")
    public String agentCommission() {
        log.info("loading view/commission/agentCommission");
        return "view/commission/agentCommission";
    }

    @GetMapping("view/commission/commissionExt")
    public String commissionExt() {
        log.info("loading view/commission/commissionExt");
        return "view/commission/commissionExt";
    }

    @GetMapping("view/commission/list")
    public String commissionList() {
        log.info("loading view/commission/list");
        return "view/commission/list";
    }

    @GetMapping("view/commission/auditList")
    public String commissionAuditList() {
        log.info("loading view/commission/auditList");
        return "view/commission/auditList";
    }

    @GetMapping("view/commission/she")
    public String commissionShe() {
        log.info("loading view/commission/she");
        return "view/commission/she";
    }

    @GetMapping("view/commission/Ext")
    public String ext() {
        log.info("loading view/commission/Ext");
        return "view/commission/Ext";
    }

    @GetMapping("view/custintent/custAuditList")
    public String custAuditList() {
        log.info("loading view/custintent/custAuditList");
        return "view/custintent/custAuditList";
    }

    @GetMapping("view/custintent/custSupport")
    public String custSupport() {
        log.info("loading view/custintent/custSupport");
        return "view/custintent/custSupport";
    }

    @GetMapping("view/custintent/view")
    public String custintentView() {
        log.info("loading view/custintent/view");
        return "view/custintent/view";
    }

    @GetMapping("view/devpSubsidy/list")
    public String devpSubsidyList() {
        log.info("loading view/devpSubsidy/list");
        return "view/devpSubsidy/list";
    }

    @GetMapping("view/devpSubsidy/list2")
    public String devpSubsidyList2() {
        log.info("loading view/devpSubsidy/list2");
        return "view/devpSubsidy/list2";
    }

    @GetMapping("view/push/list")
    public String pushList() {
        log.info("loading view/push/list");
        return "view/push/list";
    }

    @GetMapping("view/code/code")
    public String codeCode() {
        log.info("loading view/code/code");
        return "view/code/code";
    }

    @GetMapping("view/code/telcode")
    public String codeTelcode() {
        log.info("loading view/code/telcode");
        return "view/code/telcode";
    }

    @GetMapping("view/adv/list")
    public String advList() {
        log.info("loading view/adv/list");
        return "view/adv/list";
    }

    @GetMapping("view/refund/list")
    public String refundList() {
        log.info("loading view/refund/list");
        return "view/refund/list";
    }

    @GetMapping("view/refund/view")
    public String refundView() {
        log.info("loading view/refund/view");
        return "view/refund/view";
    }

}
