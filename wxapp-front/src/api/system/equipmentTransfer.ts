import { http } from '@/utils/request'

//常量
const api_name = '/admin/equipment/equipmentTransfer'

// 0 获取所有记录
export const findAllRecords = () =>{
	const url = `${api_name}/findAll`
	return http.get(url, {} ,{
		auth: true,
	})
}
// 分页排序查询
export const getPageList = (
	page: number,
	limit: number,
	searchObj: object,
	column: string,
	sortorder: string) =>{
	const url = `${api_name}/name/${page}/${limit}/${column}/${sortorder}`
	return http.get(url,searchObj,{
		auth: true,
	})
}
// 添加
export const saveEquipTransfer = (equipTrans: object) => {
	const url = `${api_name}/save`
	return http.post(url,equipTrans,{
		auth: true,
	})
}
// 删除
export const removeId = (id: number) => {
	const url = `${api_name}/remove/${id}`
	return http.delete(url,{},{
			auth: true,
	})
}
// 根据id查询
export const getEquipTransferById = (id: number) =>{
	const url = `${api_name}/findEquipTransferById/${id}`
	return http.get(url,{},{
		auth: true,
	})
}

// 修改
export const update = (equipTrans: object) => {
	const url = `${api_name}/update`
	return http.put(url,equipTrans,{
		auth: true,
	})
}