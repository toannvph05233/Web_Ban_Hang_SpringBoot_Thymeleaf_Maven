package com.example.demo.controller;

import com.example.demo.models.Orders;
import com.example.demo.models.OrdersDetail;
import com.example.demo.service.OrdersDetailService;
import com.example.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersDetailService ordersDetailService;


    @GetMapping
    public ModelAndView homeStaff(){
        return new ModelAndView("staff/home");
    }

    @GetMapping("/bill")
    public ModelAndView viewBill(@SortDefault(sort = {"id"}) @PageableDefault(value = 15) Pageable pageable){
        Page<Orders> orders = ordersService.findAll(pageable);
        return new ModelAndView("staff/allbillstaff", "orders", orders);
    }

    @GetMapping("/billdetail/{id}")
    public ModelAndView viewBillDetail(@PathVariable("id") Long id){
        Iterable<OrdersDetail> ordersDetails = ordersDetailService.findOrdersDetailById_Order(id);
        return new ModelAndView("staff/billstaff", "ordersdetail", ordersDetails);
    }
}
