package org.smart4j.framework.controller;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.model.Customer;
import org.smart4j.framework.service.CustomerService;

import java.util.List;

/**
 * @author haohj
 * @date 2020-05-19 16:16
 */
@Controller
public class CustomerController {
    private CustomerService customerService;

    /**
     * 进入 客户列表 界面
     */
    @Action("get:/customer")
    public View index(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }
}
