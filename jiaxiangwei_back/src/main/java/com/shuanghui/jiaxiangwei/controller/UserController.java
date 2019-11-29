package com.shuanghui.jiaxiangwei.controller;

import com.shuanghui.jiaxiangwei.dto.JxwMemberEntityDto;
import com.shuanghui.jiaxiangwei.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户中心
 */
@Controller
@RequestMapping(value = "userController")
public class UserController {
    @Autowired
    private IMemberService memberService;
    /**
     * 修改页面
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "update/{userId}",method= RequestMethod.GET)
    public String update(@PathVariable("userId") Integer userId, Model model) {
        //查询当前用户
        JxwMemberEntityDto memberEntityDto =  memberService.findUserByUserId(userId);
        model.addAttribute("entity",memberEntityDto);
        model.addAttribute("titlename","后台用户修改");
        return "user/update";
    }

}
