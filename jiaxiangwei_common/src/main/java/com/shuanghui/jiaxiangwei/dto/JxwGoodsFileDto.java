package com.shuanghui.jiaxiangwei.dto;

import lombok.Data;

/**
 * 商品附件对象
 */
@Data
public class JxwGoodsFileDto  extends  BaseDto{

    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsFileName1;
    /**
     * 商品路径
     */
    private String goodsFilePath1;
    /**
     * 商品名称
     */
    private String goodsFileName2;
    /**
     * 商品路径
     */
    private String goodsFilePath2;
    /**
     * 商品名称
     */
    private String goodsFileName3;
    /**
     * 商品路径
     */
    private String goodsFilePath3;
    /**
     * 商品名称
     */
    private String goodsFileName4;
    /**
     * 商品路径
     */
    private String goodsFilePath4;
    /**
     * 商品排序 越大越靠前
     */
    private Integer  sort;
}
