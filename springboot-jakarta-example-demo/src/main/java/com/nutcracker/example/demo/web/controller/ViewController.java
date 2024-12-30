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
    String leftNav() {
        log.debug("leftnav");
        return "leftnav_static";
        //return "leftnav";
    }

    @GetMapping("topnav")
    String topNav() {
        log.debug("leftNav");
        return "topnav";
    }


    @GetMapping("view/sysconfig/setconfig")
    String setConfig() {
        log.info("loading view/sysconfig/setconfig ");
        return "view/sysconfig/setconfig";
    }

    @GetMapping("view/sysconfig/signin")
    String signIn() {
        log.info("loading view/sysconfig/signin ");
        return "view/sysconfig/signin";
    }

    @GetMapping("view/tenant/tenant-list")
    String tenantList() {
        log.info("loading view/tenant/tenant-list ");
        return "view/tenant/tenant-list";
    }

    @GetMapping("view/tenant/tenant-add")
    String tenantAdd() {
        log.info("loading view/tenant/tenant-add ");
        return "view/tenant/tenant-add";
    }

    @GetMapping("view/tenantConfig/list")
    String tenantConfigList() {
        log.info("loading view/tenantConfig/list ");
        return "view/tenantConfig/list";
    }

    @GetMapping("view/city/add2")
    String cityAdd2() {
        log.info("loading view/city/add2 ");
        return "view/city/add2";
    }

    @GetMapping("view/city/list")
    String cityList() {
        log.info("loading view/city/list ");
        return "view/city/list";
    }

    @GetMapping("view/user/user_list")
    String userList() {
        log.info("loading view/user/user_list");
        return "view/user/user_list";
    }

    @GetMapping("view/project/openProject")
    String openProject() {
        log.info("loading view/project/openProject");
        return "view/project/openProject";
    }

    @GetMapping("view/project/list")
    String projectList() {
        log.info("loading view/project/list");
        return "view/project/list";
    }

    @GetMapping("view/project/view")
    String projectView() {
        log.info("loading view/project/view");
        return "view/project/view";
    }

    @GetMapping("view/project/manager_list")
    String managerList() {
        log.info("loading view/project/manager_list");
        return "view/project/manager_list";
    }

    @GetMapping("view/project/add")
    String projectAdd() {
        log.info("loading view/project/add");
        return "view/project/add";
    }

    @GetMapping("view/project/add2")
    String projectAdd2() {
        log.info("loading view/project/add2");
        return "view/project/add2";
    }

    @GetMapping("view/project/add3")
    String projectAdd3() {
        log.info("loading view/project/add3");
        return "view/project/add3";
    }

    @GetMapping("view/project/add4")
    String projectAdd4() {
        log.info("loading view/project/add4");
        return "view/project/add4";
    }

    @GetMapping("view/project/add5")
    String projectAdd5() {
        log.info("loading view/project/add5");
        return "view/project/add5";
    }

    @GetMapping("view/project/add6")
    String projectAdd6() {
        log.info("loading view/project/add6");
        return "view/project/add6";
    }

    @GetMapping("view/project/add7")
    String projectAdd7() {
        log.info("loading view/project/add7");
        return "view/project/add7";
    }

    @GetMapping("view/project/add8")
    String projectAdd8() {
        log.info("loading view/project/add8");
        return "view/project/add8";
    }

    @GetMapping("view/project/add9")
    String projectAdd9() {
        log.info("loading view/project/add9");
        return "view/project/add9";
    }

    @GetMapping("view/project/edit_list")
    String projectEditList() {
        log.info("loading view/project/edit_list");
        return "view/project/edit_list";
    }

    @GetMapping("view/project/add_hezuo")
    String projectAddHezuo() {
        log.info("loading view/project/add_hezuo");
        return "view/project/add_hezuo";
    }

    @GetMapping("view/agent/list")
    String agentList() {
        log.info("loading view/agent/list");
        return "view/agent/list";
    }

    @GetMapping("view/agent/custAgent")
    String agentCustAgent() {
        log.info("loading view/agent/custAgent");
        return "view/agent/custAgent";
    }

    @GetMapping("view/agent/com_list")
    String agentComList() {
        log.info("loading view/agent/com_list");
        return "view/agent/com_list";
    }

    @GetMapping("view/agent/namelist")
    String agentNamelist() {
        log.info("loading view/agent/namelist");
        return "view/agent/namelist";
    }

    @GetMapping("view/agent/card")
    String agentCard() {
        log.info("loading view/agent/card");
        return "view/agent/card";
    }

    @GetMapping("view/agent/grain")
    String agentGrain() {
        log.info("loading view/agent/grain");
        return "view/agent/grain";
    }

    @GetMapping("view/agent/grain_list")
    String grainList() {
        log.info("loading view/agent/grain_list");
        return "view/agent/grain_list";
    }

    @GetMapping("view/adviserProject/adviserAuditList")
    String adviserAuditList() {
        log.info("loading view/adviserProject/adviserAuditList");
        return "view/adviserProject/adviserAuditList";
    }

    @GetMapping("view/adviser/list")
    String adviserList() {
        log.info("loading view/adviser/list");
        return "view/adviser/list";
    }

    @GetMapping("view/adviser/namelist")
    String adviserNameList() {
        log.info("loading view/adviser/namelist");
        return "view/adviser/namelist";
    }

    @GetMapping("view/adviser/card")
    String adviserCard() {
        log.info("loading view/adviser/card");
        return "view/adviser/card";
    }

    @GetMapping("view/adviser/grain")
    String adviserGrain() {
        log.info("loading view/adviser/grain");
        return "view/adviser/grain";
    }

    @GetMapping("view/pos/bind")
    String posBind() {
        log.info("loading view/pos/bind");
        return "view/pos/bind";
    }

    @GetMapping("view/pos/list")
    String posList() {
        log.info("loading view/pos/list");
        return "view/pos/list";
    }

    @GetMapping("view/pos/poslog")
    String posPosLog() {
        log.info("loading view/pos/poslog");
        return "view/pos/poslog";
    }

    @GetMapping("view/data/statIncome_data")
    String statIncomeData() {
        log.info("loading view/data/statIncome_data");
        return "view/data/statIncome_data";
    }

    @GetMapping("view/agreement/list")
    String agreementList() {
        log.info("loading view/agreement/list");
        return "view/agreement/list";
    }

    @GetMapping("view/agreement/rengou")
    String agreementRengou() {
        log.info("loading view/agreement/rengou");
        return "view/agreement/rengou";
    }

    @GetMapping("view/agreement/qianyue")
    String agreementQianyue() {
        log.info("loading view/agreement/qianyue");
        return "view/agreement/qianyue";
    }

    @GetMapping("view/agreement/edit")
    String agreementEdit() {
        log.info("loading view/agreement/edit");
        return "view/agreement/edit";
    }

    @GetMapping("view/agreement/view")
    String agreementView() {
        log.info("loading view/agreement/view");
        return "view/agreement/view";
    }

    @GetMapping("view/firm/list")
    String firmList() {
        log.info("loading view/firm/list");
        return "view/firm/list";
    }

    @GetMapping("view/firm/edit")
    String firmEdit() {
        log.info("loading view/firm/edit");
        return "view/firm/edit";
    }

    @GetMapping("view/firm/verify")
    String firmVerify() {
        log.info("loading view/firm/verify");
        return "view/firm/verify";
    }

    @GetMapping("view/firm/list2")
    String firmList2() {
        log.info("loading view/firm/list2");
        return "view/firm/list2";
    }

    @GetMapping("view/firm/view")
    String firmView() {
        log.info("loading view/firm/view");
        return "view/firm/view";
    }

    @GetMapping("view/firm/account")
    String firmAccount() {
        log.info("loading view/firm/account");
        return "view/firm/account";
    }

    @GetMapping("view/commission/agentCommission")
    String agentCommission() {
        log.info("loading view/commission/agentCommission");
        return "view/commission/agentCommission";
    }

    @GetMapping("view/commission/commissionExt")
    String commissionExt() {
        log.info("loading view/commission/commissionExt");
        return "view/commission/commissionExt";
    }

    @GetMapping("view/commission/list")
    String commissionList() {
        log.info("loading view/commission/list");
        return "view/commission/list";
    }

    @GetMapping("view/commission/auditList")
    String commissionAuditList() {
        log.info("loading view/commission/auditList");
        return "view/commission/auditList";
    }

    @GetMapping("view/commission/she")
    String commissionShe() {
        log.info("loading view/commission/she");
        return "view/commission/she";
    }

    @GetMapping("view/commission/Ext")
    String ext() {
        log.info("loading view/commission/Ext");
        return "view/commission/Ext";
    }

    @GetMapping("view/custintent/custAuditList")
    String custAuditList() {
        log.info("loading view/custintent/custAuditList");
        return "view/custintent/custAuditList";
    }

    @GetMapping("view/custintent/custSupport")
    String custSupport() {
        log.info("loading view/custintent/custSupport");
        return "view/custintent/custSupport";
    }

    @GetMapping("view/custintent/view")
    String custintentView() {
        log.info("loading view/custintent/view");
        return "view/custintent/view";
    }

    @GetMapping("view/devpSubsidy/list")
    String devpSubsidyList() {
        log.info("loading view/devpSubsidy/list");
        return "view/devpSubsidy/list";
    }

    @GetMapping("view/devpSubsidy/list2")
    String devpSubsidyList2() {
        log.info("loading view/devpSubsidy/list2");
        return "view/devpSubsidy/list2";
    }

    @GetMapping("view/push/list")
    String pushList() {
        log.info("loading view/push/list");
        return "view/push/list";
    }

    @GetMapping("view/code/code")
    String codeCode() {
        log.info("loading view/code/code");
        return "view/code/code";
    }

    @GetMapping("view/code/telcode")
    String codeTelcode() {
        log.info("loading view/code/telcode");
        return "view/code/telcode";
    }

    @GetMapping("view/adv/list")
    String advList() {
        log.info("loading view/adv/list");
        return "view/adv/list";
    }

    @GetMapping("view/refund/list")
    String refundList() {
        log.info("loading view/refund/list");
        return "view/refund/list";
    }

    @GetMapping("view/refund/view")
    String refundView() {
        log.info("loading view/refund/view");
        return "view/refund/view";
    }

}
