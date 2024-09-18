package com.equipment.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.equipment.common.result.Result;
import com.equipment.model.system.SysEquipmentStock;
import com.equipment.model.vo.SysEquipmentDetectionQueryVo;
import com.equipment.model.system.SysDetection;
import com.equipment.system.service.SysDetectionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2024-08-21
 */
@Api(tags = "设备检测接口")
@RestController
@RequestMapping("/admin/system/sysEquipDetection")
public class SysDetectionController {

    @Autowired
    private SysDetectionService sysDetectionService;

    //1、查询所有记录
    @ApiOperation("查询所有记录接口")
    @GetMapping("findAll")
    public Result<List<SysDetection>> findAll(){
        List<SysDetection> list =  sysDetectionService.list();
        return Result.ok(list);
    }

    //2、物理删除接口
    @ApiOperation("物理删除接口")
    @DeleteMapping("remove/{id}")
    public Result<Void> removeEquip(@PathVariable Long id){
        //调用方法删除
        boolean isSuccess = sysDetectionService.removeById(id);
        if(isSuccess ){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //3、条件分页查询接口
    //page表示当前页 limit每页记录
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result<IPage<SysDetection>> findPageQueryEquip(@PathVariable Long page,
                                     @PathVariable Long limit,
                                     SysEquipmentDetectionQueryVo sysEquipmentDetectionQueryVo){
        //创建page对象
        Page<SysDetection> pageParam = new Page<>(page,limit);
        //构造查询条件
        LambdaQueryWrapper<SysDetection> queryWrapper = new LambdaQueryWrapper();
        if(sysEquipmentDetectionQueryVo.getKeyword() !=null){
            queryWrapper.like(SysDetection::getEmployeeCode,sysEquipmentDetectionQueryVo.getKeyword())
                    .or().like(SysDetection::getDetectionLocation,sysEquipmentDetectionQueryVo.getKeyword())
                    .or().like(SysDetection::getTaskCode,sysEquipmentDetectionQueryVo.getKeyword());
        }
        //调用service方法
        IPage<SysDetection> pageModel = sysDetectionService.page(pageParam,queryWrapper);
        //返回
        return  Result.ok(pageModel);
    }

    //4、添加设备
    @ApiOperation("添加设备检测")
    @PostMapping("save")
    public Result<Void> saveEquip(@RequestBody SysDetection sysDetection){
        boolean isSuccess = sysDetectionService.save(sysDetection);

        if(isSuccess){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //5、根据id查询
    @ApiOperation("根据id查询设备检测")
    @GetMapping("findDetectionById/{id}")
    public Result<SysDetection> findDetectionById(@PathVariable String id) {
        SysDetection sysDetection = sysDetectionService.getById(id);
        return Result.ok(sysDetection);
    }

    //6 修改设备出入库记录
    @ApiOperation("修改设备出入库记录")
    @PutMapping("update")
    public Result<Void> updateById(@RequestBody SysDetection sysDetection) {
        return sysDetectionService.updateById(sysDetection) ? Result.ok() : Result.fail();
    }

    //7、批量删除
    @ApiOperation("物理批量删除")
    @DeleteMapping("batchRemove")
    public Result<Void> batchRemove(@RequestBody List<Long> ids){
        return sysDetectionService.removeByIds(ids) ? Result.ok() : Result.fail();
    }
}

