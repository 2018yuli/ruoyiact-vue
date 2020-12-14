import request from '@/utils/request'

export const routerMap = {
  'task': 'leave',
  's-leave': 'leave',
  'purchase': 'purchase',
  'procMain': 'PmsTask'
}

export const modalMap = {
  'task': 'leavemodal',
  's-leave': 'leavemodal',
  'purchase': 'purchasemodal',
  'procMain': 'tskmodal',
  'procAssign': 'tskmodal',
  'procChecking': 'tskmodal',
  'procCloser': 'tskmodal'
}

export const statusMap = {
  '1': '处理中',
  '2': '结束',
  '3': '撤销',
  '4': '中止'
}
export const resultMap = {
  '1': '处理中',
  '2': '通过',
  '3': '驳回',
  '4': '撤销',
  '5': '删除',
  '6': '中止'
}

export const deployByFileURL = '/act/process/deployByFile'

export function getModelList (parameter) {
  return request({
    url: '/act/models/list',
    method: 'get',
    params: parameter
  })
}
export function delModel (parameter) {
  return request({
    url: '/act/models/remove',
    method: 'post',
    params: parameter
  })
}
export function deploy (id) {
  return request({
    url: '/act/models/deploy/' + `${id}`,
    method: 'post'
  })
}

export function getProfList (parameter) {
  return request({
    url: '/act/prof/list',
    method: 'get',
    params: parameter
  })
}
export function getProfAllLatest (parameter) {
  return request({
    url: '/act/prof/allLatest',
    method: 'get',
    params: parameter
  })
}

export function delProf (parameter) {
  return request({
    url: '/act/prof/remove',
    method: 'post',
    params: parameter
  })
}

export function diagram (did) {
  return request({
    url: '/act/process/show?ext=.png&did=' + `${did}`,
    method: 'get',
    responseType: 'blob'
  })
}

export function modelXml (did) {
  return request({
    url: '/act/process/show?ext=.bpmn&did=' + `${did}`,
    method: 'get',
    responseType: 'blob'
  })
}

export function processState (processId, state) {
  return request({
    url: '/act/process/update/' + `${processId}` + '/' + `${state}`,
    method: 'post'
  })
}

export function getProfInfRuns (parameter) {
  return request({
    url: '/act/process/ins/runs',
    method: 'get',
    params: parameter
  })
}
export function getProfInfFinished (parameter) {
  return request({
    url: '/act/process/ins/finished',
    method: 'get',
    params: parameter
  })
}

export function processInsState (procInstId, state) {
  return request({
    url: '/act/process/ins/update/' + `${procInstId}` + '/' + `${state}`,
    method: 'post'
  })
}
/**
 * 删除流程实例 撤销申请
 * @param {*} parameter
 */
export function delProcessIns (parameter) {
  return request({
    url: '/act/process/ins/remove',
    method: 'post',
    params: parameter
  })
}
export function delHiProcessIns (parameter) {
  return request({
    url: '/act/process/ins/remove/his',
    method: 'post',
    params: parameter
  })
}
// 我的申请（未删除）
export function getBusinessListMy (parameter) {
  return request({
    url: '/act/business/list/my',
    method: 'get',
    params: parameter
  })
}

export function saveBusiness (parameter) {
  return request({
    url: '/act/business' + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function auditTask (parameter) {
  return request({
    url: '/act/task/audit',
    method: 'post',
    data: parameter
  })
}
export function auditTaskBatch (parameter) {
  return request({
    url: '/act/task/audit/batch',
    method: 'post',
    data: parameter
  })
}

export function delBusiness (parameter) {
  return request({
    url: '/act/business/remove',
    method: 'post',
    params: parameter
  })
}

export function getLeaveByBusiness (key) {
  return request({
    url: '/act/leave/biz/' + `${key}`,
    method: 'get'
  })
}
export function getPurchaseByBusiness (key) {
  return request({
    url: '/act/purchase/biz/' + `${key}`,
    method: 'get'
  })
}

export function saveLeave (parameter) {
  return request({
    url: '/act/leave' + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
/**
 * 保存报销申请
 * @param {*} parameter
 */
export function savePurchase (parameter) {
  return request({
    url: '/act/purchase' + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delLeave (parameter) {
  return request({
    url: '/act/leave/remove',
    method: 'post',
    params: parameter
  })
}

export function getTasking (parameter) {
  return request({
    url: '/act/task/ing',
    method: 'get',
    params: parameter
  })
}

export function getTaskdone (parameter) {
  return request({
    url: '/act/task/done',
    method: 'get',
    params: parameter
  })
}

export function delTaskLogic (parameter) {
  return request({
    url: '/act/task/remove',
    method: 'post',
    params: parameter
  })
}

/**
 * 任务流转历史
 * @param {} parameter
 */
export function getTaskFlow (parameter) {
  return request({
    url: '/act/task/flow',
    method: 'get',
    params: parameter
  })
}

/**
 * 获取高亮流程图
 * @param  procInstId 流程实例编号
 */
export function hightlightFlow (procInstId) {
  return request({
    url: '/act/process/highlightImg/' + `${procInstId}`,
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 获取流程节点列表
 * @param {String} proDefId 流程定义编号
 */
export function getNodeList (proDefId) {
  return request({
    url: '/act/node/list/' + `${proDefId}`,
    method: 'get'
  })
}
export function getNodeInfo (nodeId) {
  return request({
    url: '/act/node/get/' + `${nodeId}`,
    method: 'get'
  })
}
export function updateNodeInfo (parameter) {
  return request({
    url: '/act/node/update',
    method: 'post',
    data: parameter
  })
}
