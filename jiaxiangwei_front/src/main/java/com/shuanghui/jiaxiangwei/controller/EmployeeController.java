package com.shuanghui.jiaxiangwei.controller;

import com.shuanghui.jiaxiangwei.aspect.ServerLog;
import com.shuanghui.jiaxiangwei.dto.EmployeeDto;
import com.shuanghui.jiaxiangwei.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employeeController")
public class EmployeeController {
   @Autowired
   private IEmployeeService employeeService;

    /**
     * 查询全部
     * @return
     */
   @GetMapping("findAll")
   @ResponseBody
   public List<EmployeeDto> findAll(){
       return employeeService.findAll();
   }
}
