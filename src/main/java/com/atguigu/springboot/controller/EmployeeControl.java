package com.atguigu.springboot.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.atguigu.springboot.dao.DepartmentDao;
import com.atguigu.springboot.dao.EmployeeDao;
import com.atguigu.springboot.entities.Department;
import com.atguigu.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Collection;

@Controller
public class EmployeeControl {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    /**
     * 查询所有员工返回列表
     *
     * @return
     */
    @GetMapping("/emps")
    public String list(Model model) {

        // thymeleaf 默认从类路径下拼接 classpath:/templates/emp/list.html
        final Collection<Employee> employeeList = employeeDao.getAll();
        model.addAttribute("emps", employeeList);
        return "emp/list";
    }

    /**
     * 来到员工添加页面
     * @return
     */
    @GetMapping("/emp")
    public String toAddPage(Model model){

        //进入添加页面后，查出所有部门信息，在页面显示

        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    // SpringMVC自动将请求参数和入参对象的属性进行一一绑定：要求请求参数的名字，和javaBean入参对象里面的属性名保持一致
    @PostMapping("/emp")
    public String addEmp(Employee employee){

        // 添加完成后，应来到员工列表页面
        // forward: 转发到一个地址
        // redirect: 重定向到一个地址 / 表示当前项目路径
        employeeDao.save(employee);
        System.out.println("新增的员工信息：" + employee);
        return "redirect:/emps";// 重定向
    }

    /**
     * 来到修改页面，查出当前员工，在页面回显
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp", employee);
        // 页面显示所有部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        // 回到修改页面
        return "emp/add";// 重用添加页面
    }

    @PutMapping("/emp")
    public String updateEmployee(Employee employee){
        System.out.println("修改的员工数据：" + employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }
}
