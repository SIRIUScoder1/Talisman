package com.talisman.controllers;

import com.talisman.dao.OrderDAO;
import com.talisman.dao.ProductDAO;
import com.talisman.model.CustomerInfo;
import com.talisman.validators.CustomerInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@Transactional
@EnableWebMvc
public class MainController {

    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private CustomerInfoValidator customerInfoValidator;

    @Autowired
    public MainController(final OrderDAO orderDAO, final ProductDAO productDAO,
                          final CustomerInfoValidator customerInfoValidator) {

        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.customerInfoValidator = customerInfoValidator;
    }

    @InitBinder
    public void webInitBinder(final WebDataBinder webDataBinder) {

        Object object = webDataBinder.getTarget();
        if(object == null) {
            return;
        }

        System.out.println("debug - Databinder : " + object);

        if(object.getClass() == CustomerInfo.class) {
            webDataBinder.setValidator(customerInfoValidator);
        }
    }

    @RequestMapping("/")
    public ModelAndView homePage() {

        ModelAndView homePageView = new ModelAndView();
        homePageView.setViewName("home.jsp");
        return homePageView;
    }


}
