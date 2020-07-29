package com.lin.controller;


import com.lin.common.lang.Result;
import com.lin.entity.User;
import com.lin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linwar
 * @since 2020-07-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //    @RequiresAuthentication
    @GetMapping("/xxx")
    public Result index(){
        User user=userService.getById(1L);
//        if (user!=null){
        return Result.succ(user);
//        }
//        else{
//            return Result.fail("未找到此id的相关数据！");
//        }
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){

        return Result.succ(user);
    }

}
