package com.shuanghui.jiaxiangwei.controller;

import com.shuanghui.jiaxiangwei.dto.JxwGoodsTypeDto;
import com.shuanghui.jiaxiangwei.service.IGoodsTypeService;
import com.shuanghui.jiaxiangwei.service.IMemberService;
import com.shuanghui.jiaxiangwei.shiro.ShiroRealm;
import com.shuanghui.jiaxiangwei.util.CommonConstant;
import com.shuanghui.jiaxiangwei.util.DirectoryFolder;
import com.shuanghui.jiaxiangwei.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品类别管理
 */
@Controller
@RequestMapping(value = "/goodsType")
public class GoodsTypeController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String PAGE_SIZE = "20";
    @Autowired
    private IGoodsTypeService goodsTypeService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private DirectoryFolder directoryFolder;
    /**
     * 类别类别
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "page",defaultValue = "1") int pageNumber, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,ServletRequest request) {
        model.addAttribute("titlename","商品类型管理");
        return "goodstype/list";
    }

    /**
     * 查询全部
     * @return
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<JxwGoodsTypeDto> getAllTree(){
        return goodsTypeService.findAll();
    }

    /**
     * 类型处理
     * @param request
     * @return
     */
    @RequestMapping(value ="/dealGoodsType")
    public String dealGoodsType(JxwGoodsTypeDto dto, @RequestParam("file")  MultipartFile file, HttpServletRequest request){
          String operation =  dto.getOperation();
        try{
            ShiroRealm.ShiroUser user = memberService.getCurrentUser();
            dto.setLoginParam(user);
            if(!StringUtils.isEmpty(operation)){
                if(operation.equalsIgnoreCase(CommonConstant.OPERATION_ADD)){//新增
                    //获取父类
                    String goods_type_file = null;
                    String originalFillName = file.getOriginalFilename();
                    if(file!=null && !StringUtils.isEmpty(originalFillName)){
                        int index = originalFillName.lastIndexOf(".");
                        String after = originalFillName.substring(index,originalFillName.length());
                        String goods_type_path = directoryFolder.getGoods_type_path();
                        goods_type_file = Util.saveFile(goods_type_path,"/",after,request,file,goods_type_file);
                    }
                   dto.setGoodsTypeFile(goods_type_file);
                   goodsTypeService.insert(dto);
                }else if(operation.equalsIgnoreCase(CommonConstant.OPERATION_DEL)){//删除
                    goodsTypeService.deleteGoodsTypeByParentId(dto);
                }else if(operation.equalsIgnoreCase(CommonConstant.OPERATION_UPDATE)){//修改
                    String goods_type_file = null;
                    String originalFillName = file.getOriginalFilename();
                    if(file!=null && !StringUtils.isEmpty(originalFillName)){
                        int index = originalFillName.lastIndexOf(".");
                        String after = originalFillName.substring(index,originalFillName.length());
                        String goods_type_path = directoryFolder.getGoods_type_path();
                        goods_type_file = Util.saveFile(goods_type_path,"/",after,request,file,goods_type_file);
                        dto.setGoodsTypeFile(goods_type_file);
                    }
                    goodsTypeService.updateById(dto);
                }
            }
        } catch (Exception e){
            logger.error("执行 {} 操作商品失败 ：",operation,e.getMessage());
        }
        return "goodstype/list";
    }
}
