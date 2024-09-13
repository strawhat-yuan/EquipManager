package com.equipment.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.equipment.model.system.SysRole;
import com.equipment.model.vo.AssignRoleVo;
import com.equipment.model.vo.SysRoleQueryVo;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {
    // 获取用户的角色数据
    Map<String, Object> getRolesByUserId(String userId);

    void doAssign(AssignRoleVo assignRoleVo);
}
