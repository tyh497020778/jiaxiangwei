package com.shuanghui.jiaxiangwei.dto;

import com.shuanghui.jiaxiangwei.shiro.ShiroRealm;
import lombok.Data;

import java.util.Date;

/**
 * 基本实体对象
 */
@Data
public class BaseDto  {

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date creationDate;
    /**
     * 修改人
     */
    private String lastUpdateBy;
    /**
     * 修改时间
     */
    private Date lastDate;
    /**
     * 版本
     */
    private Integer version;


    public void setLoginParam(ShiroRealm.ShiroUser user){
        this.setCreateBy(user.userName);
        this.setLastUpdateBy(user.userName);
        Date date = new Date();
        this.setCreationDate(date);
        this.setLastDate(date);
    }
}
