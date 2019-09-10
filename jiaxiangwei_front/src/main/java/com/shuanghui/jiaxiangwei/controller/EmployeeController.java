package com.shuanghui.jiaxiangwei.controller;

import com.shuanghui.jiaxiangwei.aspect.ServerLog;
import com.shuanghui.jiaxiangwei.dto.EmployeeDto;
import com.shuanghui.jiaxiangwei.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class EmployeeController {
   @Autowired
   private IEmployeeService employeeService;

    /**
     * 查询全部
     * @return
     */
   @RequestMapping(value ="findAll",method=RequestMethod.PUT)
//   @ResponseBody
   public List<EmployeeDto> findAll(@RequestParam("name") String name){
       return employeeService.findAll();
   }
}
