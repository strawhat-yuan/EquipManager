package com.equipment.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.equipment.common.result.Result;
import com.equipment.common.utils.NamingUtils;
import com.equipment.model.system.SysUser;
import com.equipment.model.vo.SysDetectionEmployeeQueryVo;
import com.equipment.model.vo.SysUserQueryVo;
import com.equipment.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //1 查询所有接口
    @ApiOperation("查询所有接口")
    @GetMapping("findAll")
    public Result<List<SysUser>> findAll() {
        List<SysUser> list = sysUserService.list();
        return Result.ok(list);
    }

    //2 条件分页排序查询
    @ApiOperation("条件排序分页查询")
    @GetMapping("{page}/{limit}/{column}/{order}")
    public Result<IPage<SysUser>> findPageQuerySysUser(

            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable int page,

            @ApiParam(name = "limit", value = "每页记录数量", required = true)
            @PathVariable int limit,

            @ApiParam(name = "sysRoleQueryVo", value = "查询对象", required = false)
            SysUserQueryVo sysUserQueryVo,

            @ApiParam(name = "column", value = "字段", required = false)
            @PathVariable String column,

            @ApiParam(name = "order", value = "排序方式{ascending,descending}", required = false)
            @PathVariable String order
    ) {
        //创建page对象
        Page<SysUser> pageParam = new Page<>(page, limit);
        //构造查询条件
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (sysUserQueryVo.getKeyword() != null) {
            queryWrapper.like("user_code", sysUserQueryVo.getKeyword())
                    .or().like("user_name", sysUserQueryVo.getKeyword())
                    .or().like("description", sysUserQueryVo.getKeyword())
                    .or().like("phone", sysUserQueryVo.getKeyword());
        }
        //构造排序条件
        if (column != null && order != null) {
            String field = NamingUtils.camelToUnderline(column);
            if (order.equals("ascending")) {
                queryWrapper.orderByAsc(field);
            } else {
                queryWrapper.orderByDesc(field);
            }
        }
        IPage<SysUser> pageModel = sysUserService.page(pageParam, queryWrapper);
        return Result.ok(pageModel);
    }

    // 3 添加用户
    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result<Void> saveSysUser(@RequestBody SysUser sysUser) {
        // 把输入密码进行MD5加密
        String encrypt = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(encrypt);
        return sysUserService.save(sysUser) ? Result.ok() : Result.fail();
    }

    // 4 修改用户
    @ApiOperation("修改用户")
    @PutMapping("update")
    public Result<Void> updateById(@RequestBody SysUser sysUser) {
        return sysUserService.updateById(sysUser) ? Result.ok() : Result.fail();
    }

    // 5 删除用户
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("remove/{id}")
    public Result<Void> removeSysUser(@PathVariable String id) {
        return sysUserService.removeById(id) ? Result.ok() : Result.fail();
    }

    // 6 根据id查询
    @ApiOperation("查询用户")
    @GetMapping("get/{id}")
    public Result<SysUser> get(@PathVariable String id) {
       SysUser sysUser = sysUserService.getById(id);
       return Result.ok(sysUser);
    }

    // 7 根据角色名称查询用户列表
    @ApiOperation("根据角色名称查询用户列表")
    @GetMapping("query/{roleName}")
    public Result<List<SysUser>> querySysUser(
            @ApiParam(name = "roleName", value = "角色名称", required = false)
            @PathVariable String roleName
    ) {
        List<SysUser> sysUserList = null;
        sysUserList = sysUserService.getUserListByRoleName(roleName);
        return sysUserList != null ? Result.ok(sysUserList):Result.fail();
    }

    // 8 根据id批量删除
    @ApiOperation("根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<Void> batchRemove(@RequestBody List<String> ids){
        return sysUserService.removeByIds(ids)?Result.ok():Result.fail();
    }

    @ApiOperation("空闲目测人员名单")
    @GetMapping("/detectionEmployees/{page}/{limit}")
    public Result<IPage<SysUser>> detectionEmployees(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable int page,

            @ApiParam(name = "limit", value = "每页记录数量", required = true)
            @PathVariable int limit,

            @ApiParam(name = "sysDetectionEmployeeQueryVo", value = "查询对象", required = false)
            SysDetectionEmployeeQueryVo sysDetectionEmployeeQueryVo) {

        //创建page对象
        Page<SysUser> pageParam = new Page<>(page, limit);
        //调用service方法
        IPage<SysUser> pageModel = sysUserService.sysDetectionEmployeesMap(pageParam,sysDetectionEmployeeQueryVo);
        return Result.ok(pageModel);
    }

}
