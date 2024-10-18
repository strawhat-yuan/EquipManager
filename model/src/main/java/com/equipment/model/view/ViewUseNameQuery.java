package com.equipment.model.view;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName view_use_name_query
 */
@TableName(value ="view_use_name_query")
@Data
public class ViewUseNameQuery implements Serializable {
    /**
     * 设备使用id
     */
    private Long id;

    /**
     * 设备id
     */
    private String equipmentCode;

    /**
     * 任务id
     */
    private String taskCode;

    /**
     * 设备使用日期
     */
    private Date equipmentUseDate;

    /**
     * 设备使用员工编码
     */
    private String employeeUseCode;

    /**
     * 设备使用地点
     */
    private String location;

    /**
     * 设备使用前状态
     */
    private String preUseEquipmentStatus;

    /**
     * 维护保养情况
     */
    private String maintenanceStatus;

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;

    /**
     * 删除标记（0:可用 1:已删除）
     */
    private Integer isDeleted;

    /**
     * 是否补充记录（0:否，真实 1:是，后续补充）
     */
    private Integer isAdditional;

    /**
     * 员工姓名
     */
    private String employeeUseName;

    /**
     * 设备名称
     */
    private String equipmentUseName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}