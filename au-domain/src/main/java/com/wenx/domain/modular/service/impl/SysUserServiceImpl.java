package com.wenx.domain.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenx.domain.modular.domain.SysUser;
import com.wenx.domain.modular.mapper.SysUserMapper;
import com.wenx.domain.modular.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author wenx
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
