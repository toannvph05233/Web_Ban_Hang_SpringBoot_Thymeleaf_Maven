package com.example.demo.controller;

import com.example.demo.models.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ProductService productService;

    @Autowired
    private TypeProductService typeProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersDetailService ordersDetailService;

    @Autowired
    private Environment environment;

    @Autowired
    private RenderService renderService;

    @ModelAttribute("renders")
    public Iterable<Render> getListRender() {
        return renderService.findAll();
    }

    @ModelAttribute("user")
    private UserInfo getPrincipal() {
        UserInfo userInfo = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userInfo = userService.findOne(((UserDetails) principal).getUsername());
        }
        return userInfo;
    }

    @Autowired
    private CommentService commentService;

    public Iterable<Comment> getAllCommentByProduct(Product product, Pageable pageable) {
        return commentService.getAllCommentByProduct(product, pageable);
    }

    @ModelAttribute("typeproducts")
    public Iterable<TypeProduct> getListType() {
        return typeProductService.findAll();
    }

    @GetMapping
    public ModelAndView homeViewUser(@RequestParam("regex") Optional<String> regex,
                                     @SortDefault(sort = {"description"}) @PageableDefault(value = 12) Pageable pageable) {
        Page<Product> products;
        ModelAndView modelAndView = new ModelAndView("user/home");
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

    @GetMapping("/view")
    public ModelAndView viewInformation() {
        return new ModelAndView("user/information");
    }


    @GetMapping("/password")
    public ModelAndView passwordUser() {
        return new ModelAndView("user/password");
    }

    @PostMapping("/password")
    public ModelAndView updatePasswordUser(@ModelAttribute("user") UserInfo userInfo,
                                           @RequestParam("newpass") String newpass, @RequestParam("pass") String pass) {
        if (!pass.equals(userInfo.getPassword())) {
            String message = "Sai mật khẩu";
            return new ModelAndView("user/password", "message", message);
        } else
            userInfo.setPassword(newpass);
        userService.saveUser(userInfo);
        return new ModelAndView("user/information");
    }

    @GetMapping("/viewproduct/{id}")
    public ModelAndView viewProduct(@PathVariable("id") Long id, @PageableDefault(value = 5, page = 0)
    @SortDefault(sort = "timeComment", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Product product = productService.findOne(id);
        ModelAndView modelAndView = new ModelAndView("user/viewproduct", "product", product);
        modelAndView.addObject("commentPage", getAllCommentByProduct(product,pageable));
        modelAndView.addObject("currentTime", System.currentTimeMillis());
        modelAndView.addObject("newComment", new Comment());
        return modelAndView;
    }

    @PostMapping("/viewproduct/{id}")
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment,
                                               @PathVariable("id") Long id) {
        comment.setTimeComment(getCurrentDate());
        commentService.save(comment);
        Product product = productService.findOne(id);
        product.setCommentCount(commentService.countAllByProduct(product));
        productService.saveProduct(product);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    private Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    @GetMapping("/bill")
    public ModelAndView viewBill(@SortDefault(sort = {"id"}) @PageableDefault(value = 5) Pageable pageable) {
        Page<Orders> orders = ordersService.findByAccountUser(getPrincipal().getAccount(), pageable);
        return new ModelAndView("user/allbilluser", "orders", orders);
    }

    @GetMapping("/billdetail/{id}")
    public ModelAndView viewBillDetail(@PathVariable("id") Long id) {
        Iterable<OrdersDetail> ordersDetails = ordersDetailService.findOrdersDetailById_Order(id);
        return new ModelAndView("user/billuser", "ordersdetail", ordersDetails);
    }
}
