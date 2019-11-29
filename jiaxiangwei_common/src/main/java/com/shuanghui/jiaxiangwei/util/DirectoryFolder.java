package com.shuanghui.jiaxiangwei.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储路劲
 */
@Configuration
@Data
public class DirectoryFolder {
    //商品类别图片路径
    @Value("${directoryfolder.goodsTypePath}")
    private String goods_type_path;
}
