package com.shuanghui.jiaxiangwei.dto;

import lombok.Data;

/**
 *  商品类别
 */
@Data
public class JxwGoodsTypeDto extends  BaseDto{
    private Integer parentId;//父节点id
    private String name;//节点名称
    private Integer whileOnIndex;//栏目顺序
    private String  goodsTypeFile;//商品类型的路径+图片名称
    private String operation;//操作  add;del;update
}
