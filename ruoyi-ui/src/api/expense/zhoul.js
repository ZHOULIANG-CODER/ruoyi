import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 开销列表
export function listZhoulExpense(query) {
  return request({
    url: '/expense/listZl',
    method: 'get',
    params: query
  })
}

// 新增
export function addExpense(data) {
  return request({
    url: '/expense/add',
    method: 'post',
    data: data
  })
}
// 查询开支详情
export function getExpenseDetail(id) {
  return request({
    url: '/expense/' + id,
    method: 'get'
  })
}
export function deleteExpense(id) {
  return request({
    url: '/expense/' + id,
    method: 'delete'
  })
}
export function addplan(data) {
  return request({
    url: '/expense/addPlan',
    method: 'post',
    data: data
  })
}




