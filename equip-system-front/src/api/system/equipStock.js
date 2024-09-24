import request from '@/utils/request'

//常量
const api_name = '/admin/equipment/equipmentStock/'


export default{

    // 1 入库列表排序查询
    getPageListIn(page,limit,searchObj,column,sortorder){
        return request({
            url: `${api_name}/in/${page}/${limit}/${column}/${sortorder}`,
            method: 'get',
            params:searchObj
        })
    },

    // 2 出库列表排序查询
    getPageListOut(page,limit,searchObj,column,sortorder){
        return request({
            url: `${api_name}/out/${page}/${limit}/${column}/${sortorder}`,
            method: 'get',
            params:searchObj
        })
    },

    // 3 删除
    removeId(id) {
        return request({
          url: `${api_name}/remove/${id}`,
          method: 'delete'
        })
    },

    // 4 添加
    saveEquipStock(equipStock) {
        return request({
          url: `${api_name}/save`,
          method: 'post',
          data: equipStock
        })
    },
    
    // 5 根据id查询
    getEquipStockId(id) {
        return request({
            url: `${api_name}/findEquipStockById/${id}`,
            method: 'get'
        })
    },

    // 6 修改方法
    update(equipStock){
        return request({
            url: `${api_name}/update`,
            method: 'put',
            data: equipStock
        })
    },

    // 7 批量删除
    batchRemove(idList) {
        return request({
          url: `${api_name}/batchRemove`,
          method: `delete`,
          data: idList
        })
    },
    //7、空闲设备查询
    equipmentFinder(page,limit,searchObj){
        return request({
            url: `${api_name}/idleEquipmentFinder/${page}/${limit}`,
            method: 'get',
            params:searchObj
        })
    }
    
    
  
    
}