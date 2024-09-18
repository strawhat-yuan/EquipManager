package com.equipment.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.equipment.common.result.Result;
import com.equipment.common.utils.NamingUtils;
import com.equipment.model.system.SysRole;
import com.equipment.model.vo.AssignRoleVo;
import com.equipment.model.vo.SysRoleQueryVo;
import com.equipment.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")

public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    //http://localhost:8800/admin/system/sysRole/findAll
    // 1 查询所有记录
    @ApiOperation("查询所有接口")
    @GetMapping("findAll")
    public Result<List<SysRole>> findAll(){
        // 模拟异常效果 ArithmeticException
//        try{
//            int i= 10/0;
//        }catch (Exception e){
//            throw new EquipException(20001,"出现自定义异常");
//        }
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    // 2 逻辑删除
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("remove/{id}")
    public Result<Void> removeRole(@PathVariable String id){
        return sysRoleService.removeById(id)?Result.ok():Result.fail();
    }

    // 3 条件分页查询
    // 参数：page当前页，limit每页记录
//    @PreAuthorize("hasAnyAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}/{column}/{order}")
    public Result<IPage<SysRole>> findPageQueryRole(

            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable int page,

            @ApiParam(name = "limit", value = "每页记录数量", required = true)
            @PathVariable int limit,

            @ApiParam(name = "sysRoleQueryVo", value = "查询对象", required = false)
            SysRoleQueryVo sysRoleQueryVo,

            @ApiParam(name = "column", value = "字段{ascending,descending}", required = false)
            @PathVariable String column,

            @ApiParam(name = "order", value = "排序方式", required = false)
            @PathVariable String order
    ){
        // 创建page对象
        Page<SysRole> pageParam = new Page<>(page,limit);
        // 构造查询条件
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if(sysRoleQueryVo.getKeyword() !=null){
            queryWrapper.like("role_code",sysRoleQueryVo.getKeyword())
                    .or().like("role_name",sysRoleQueryVo.getKeyword())
                    .or().like("description",sysRoleQueryVo.getKeyword());
        }
        if (column != null && order != null) {
            String field = NamingUtils.camelToUnderline(column);
            if (order.equals("ascending")) {
                queryWrapper.orderByAsc(field);
            } else {
                queryWrapper.orderByDesc(field);
            }
        }
        // 调用service方法
        IPage<SysRole> pageModel = sysRoleService.page(pageParam, queryWrapper);
        // 返回
        return  Result.ok(pageModel);
    }

    // 4 添加角色
//    @PreAuthorize("hasAnyAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result<Void> saveSysRole(@RequestBody SysRole sysRole){
        return sysRoleService.save(sysRole) ? Result.ok():Result.fail();
    }

    // 5 修改角色bnt.sysRole.update
//    @PreAuthorize("hasAnyAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result<Void> updateById(@RequestBody SysRole sysRole){
        System.out.println(sysRole);
        return sysRoleService.updateById(sysRole) ? Result.ok():Result.fail();
    }

    // 6 根据id查询
//    @PreAuthorize("hasAnyAuthority('bnt.sysRole.list')")
    @ApiOperation("查询角色")
    @GetMapping("get/{id}")
    public Result<SysRole> get(@PathVariable String id){
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }
    // 7 根据id批量删除
//    @PreAuthorize("hasAnyAuthority('bnt.sysRole.remove')")
    @ApiOperation("根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<Void> batchRemove(@RequestBody List<String> ids){
        return sysRoleService.removeByIds(ids)?Result.ok():Result.fail();
    }

    // 8 根据用户获取角色数据
    @ApiOperation("根据用户获取角色数据")
    @GetMapping("toAssign/{userId}")
    public Result<Map<String,Object>> toAssign(@PathVariable String userId){
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }

    // 9 根据用户分配角色
    @ApiOperation("根据用户分配角色")
    @PostMapping("toAssign")
    public Result<Void> toAssign(@RequestBody AssignRoleVo assignRoleVo){
        sysRoleService.doAssign(assignRoleVo);
        return Result.ok();
    }
}
