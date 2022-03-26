package com.wenx.authuser.modular.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wenx.domain.modular.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wenx
 */
@RestController
@Slf4j
public class SysUserController {

    @Resource
    SysUserService service;

    @GetMapping("/api/user/list")
    public R list() {
        return R.ok(service.list());
    }

    @GetMapping("/api/hello")
    public R hello() {
        return R.ok("H e l l o !");
    }
}
