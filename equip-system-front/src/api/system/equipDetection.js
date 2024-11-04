import request from '@/utils/request'

//常量
const api_name = '/admin/system/sysEquipDetection/'


export default{

    // 1 分页排序列表查询
    getPageList(page,limit,searchObj,column,sortorder){
        return request({
            url: `${api_name}/name/${page}/${limit}/${column}/${sortorder}`,
            method: 'get',
            params:searchObj
        })
    },

    //2、删除
    removeId(id) {
        return request({
          url: `${api_name}/remove/${id}`,
          method: 'delete'
        })
    },
    //3、添加
    saveEquipDetection(equipDet) {
        return request({
          url: `${api_name}/save`,
          method: 'post',
          data: equipDet
        })
    },
    //4、根据id查询
    getEquipDetectionId(id) {
        return request({
            url: `${api_name}/findDetectionById/${id}`,
            method: 'get'
        })
    },
    //5、修改方法
    update(equipDet){
        return request({
            url: `${api_name}/update`,
            method: 'put',
            data: equipDet
        })
    },
    //6、批量删除
    batchRemove(idList) {
        return request({
          url: `${api_name}/batchRemove`,
          method: `delete`,
          data: idList
        })
    },

    //7、员工打卡次数和经历任务数查询
    UserDetectionCount(page,limit,searchObj) {
        return request({
          url: `${api_name}/UserDetectionCount/${page}/${limit}`,
            method: 'get',
            params:searchObj
        })
    },

    //8、员工打卡次数和经历任务数查询领导版
    UserDetectionCountForBoss(page,limit,searchObj) {
        return request({
          url: `${api_name}/UserDetectionCountForBoss/${page}/${limit}`,
            method: 'get',
            params:searchObj
        })
    }

    },

    //9、根据employeeCode查询最近一条检测记录
    getLastOneDetection(employeeCode) {
        return request({
            url: `${api_name}/findLastOne/${employeeCode}`,
            method: 'get'
        })
    },

    //10、自动补充日期批量插入检测记录
    saveBatchDateDetection(batchDateDetection) {
        return request({
          url: `${api_name}/batchSaveDate`,
          method: 'post',
          data: batchDateDetection
        })
    },

}
