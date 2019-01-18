package tk.mybatis.springboot.service;

import tk.mybatis.simple.model.SysUser;

import java.util.List;

public interface UserService {

    SysUser findById(Long id);

    List<SysUser> findAll();
}
