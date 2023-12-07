package com.example.demo.controller;

import com.example.demo.models.Product;
import com.example.demo.models.Render;
import com.example.demo.models.UserInfo;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private TypeProductService typeProductService;

    @Autowired
    private RenderService renderService;

    @Autowired
    private Environment environment;

    @ModelAttribute("renders")
    public Iterable<Render> getListRender() {
        return renderService.findAll();
    }

    @GetMapping("/")
    public ModelAndView listProduct(@RequestParam("regex") Optional<String> regex,
                                    @SortDefault(value = {"description"}) @PageableDefault(value = 12) Pageable pageable) {
        Page<Product> products;
        ModelAndView modelAndView = new ModelAndView("home/index");
        if (regex.isPresent()) {
            products = productService.findAllByName(regex.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        if (products.getTotalPages() > 0) {
            modelAndView.addObject("products", products);
            modelAndView.addObject("regex", regex.orElse(""));
        } else {
            String mess = "Ko có sp nào";
            modelAndView.addObject("mess", mess);
            modelAndView.addObject("products", products);
            modelAndView.addObject("regex", regex.orElse(""));
        }
        return modelAndView;
    }

    @GetMapping("/type/{type}")
    public ModelAndView listProductByType(@PathVariable("type") String type,
                                          @SortDefault(value = {"description"}) @PageableDefault(value = 100) Pageable pageable) {

        Page<Product> products;
        ModelAndView modelAndView = new ModelAndView("home/index");
        if (type.equals("áo")) {
            products = productService.findAllByTypeProduct(typeProductService.getTypeProduct(1L), pageable);
        } else if (type.equals("quần")) {
            products = productService.findAllByTypeProduct(typeProductService.getTypeProduct(2L), pageable);
        } else {
            products = productService.findAllByTypeProduct(typeProductService.getTypeProduct(3L), pageable);
        }
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/accessDenied")
    public ModelAndView accessDenied() {
        return new ModelAndView("home/accessDenied");
    }

    @GetMapping("/register")
    public ModelAndView registrationUser() {
        return new ModelAndView("home/registration", "user", new UserInfo());
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("home/login");
    }

    @PostMapping("/register")
    public ModelAndView registrationAccount(@Valid @ModelAttribute("user") UserInfo userInfo, BindingResult bindingResult,
                                            @SortDefault(value = {"id"}) @PageableDefault(value = 20) Pageable pageable) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("home/registration");
            modelAndView.addObject(userInfo);
            return modelAndView;
        }
        MultipartFile file = userInfo.getImage();
        String image = file.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path");
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userInfo.setAvatar(image);
        userInfo.setDatecreated(new Date());
        userInfo.setRoles(rolesService.getRoleUser());
        userService.saveUser(userInfo);
        Page<Product> listProducts = productService.findAll(pageable);
        return new ModelAndView("home/index", "products", listProducts);
    }

}
