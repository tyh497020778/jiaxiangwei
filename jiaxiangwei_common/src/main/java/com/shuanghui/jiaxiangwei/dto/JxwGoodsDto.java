package com.shuanghui.jiaxiangwei.dto;

import lombok.Data;

/**
 * 商品基本信息
 */
@Data
public class JxwGoodsDto  extends  BaseDto{

    /**
     * 商品状态  0:售罄,1：出售，2：预售，3：爆款，4：促销
     */
   private Integer isSale;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品原价
     */
   private Double goodsPrice;
    /**
     * 商品促销价
     */
    private Double promotePrice;
    /**
     * 商品关键词搜索
     */
    private String keywords;
    /**
     * 商品分类id
     */
    private Integer goodsCategory;
    /**
     * 商品分类名称
     */
    private String goodsCategoryName;
    /**
     * 商品详情
     */
    private String goodsDesc;
    /**
     * 	商品累计销量
     */
    private Integer goodsSaleNumber;
    /**
     * 商品库存
     */
    private Integer goodsRemained;
    /**
     *商品累计评价
     */
    private Integer goodsAppraise;
    /**
     * 商品排序 越大越靠前
     */
    private Integer  sort;
}
