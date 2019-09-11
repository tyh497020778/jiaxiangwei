package com.shuanghui.jiaxiangwei.controller;

import com.shuanghui.jiaxiangwei.aspect.ServerLog;
import com.shuanghui.jiaxiangwei.dto.EmployeeDto;
import com.shuanghui.jiaxiangwei.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/service")
public class EmployeeController {
   @Autowired
   private IEmployeeService employeeService;

    /**
     * 查询全部
     * @return
     */
   @RequestMapping(value ="findAll")
   public List<EmployeeDto> findAll(){
       return employeeService.findAll();
   }

    /**
     * 测试
     * @return
     */
   @RequestMapping(value = "toIndex")
   public String toIndex(){
       return "index";
   }
}
