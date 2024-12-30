package com.nutcracker.example.demo.entity.sys;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * sys user
 *
 * @author 胡桃夹子
 * @date 2022/12/22 09:02
 */
@Data
@Builder
public class SysUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1407653731495690080L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 现居地
     */
    private String liveAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public SysUser() {

    }

    public SysUser(String id, String name, String password, String nickName, String sex, String mobile,
                   String email, String birthday, String hobby, String liveAddress, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.sex = sex;
        this.mobile = mobile;
        this.email = email;
        this.birthday = birthday;
        this.hobby = hobby;
        this.liveAddress = liveAddress;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

}
