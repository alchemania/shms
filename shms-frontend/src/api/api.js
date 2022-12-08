import axios from 'axios';
import {ajaxGet, ajaxPost, ajaxDelete, ajaxPut, ajaxPatch} from './request';
import {url} from './url';

//登录
export const login = params => ajaxPost({url: `user/login/`, params})

//注册
export const register = params => ajaxPost({url: `user/`, params})

//登出
export const logout = (params) => ajaxPut({url: `user/`, params})

//获取健康信息
export const fetchHealthData = params => ajaxGet({url: `healthinfo/`, params})

//修改健康信息
export const alterHealthData = params => ajaxPut({url: `healthinfo/`, params})

//省市区三级联查
export const threeLevelQuery = params => ajaxPost({url: `riskarea/menu/`, params})

//查询风险区
export const riskAreaQuery = params => ajaxPost({url: `riskarea/`, params})

//查询疫情信息
export const epDetail = params => ajaxGet({url: `riskarea/`, params})

//获取邮件验证码
export const getValidCode = params => ajaxPost({url: `user/code/`, params})

//验证邮件验证码
export const validValidCode = params => ajaxPut({url: `user/code/`, params})

//新建核酸预约
export const createNATBook = params => ajaxPut({url: `natbook/`, params})

//查询核酸预约
export const queryNATBook = params => ajaxGet({url: `natbook/`, params})

//重定向
export const redirectAdmin = params => ajaxPost({url: `user/redirect/`, params})

//获取全部用户信息 admin
export const getAllUser = params => ajaxGet({url: `admin/all/`, params})

//获取一个用户信息 admin
export const getOneUser = params => ajaxGet({url: `admin/`, params})

//通过用户名模糊查询用户信息
export const getUserByfuzzyUsername = params => ajaxPost({url: `admin/fuzzyquery/`, params})

//修改用户信息 admin
export const editOneUser = params => ajaxPut({url: `admin/`, params})

//删除用户信息 admin
export const deleteOneUser = params => ajaxDelete({url: `admin/`, params})

//获取全部健康信息 admin
export const getAllJiankang = params => ajaxGet({url: `jiankang/all/`, params})

//获取全部学生信息 admin
export const getAllStudents = params => ajaxGet({url: `student/all/`, params})

//新增一个学生
export const addOneStudent = params => ajaxPut({url: `student/`, params})

//更新一个学生信息 admin
export const editOneStudent = params => ajaxPut({url: `student/`, params})

//删除一个学生信息 admin
export const deleteOneStudent = params => ajaxDelete({url: `student/`, params})

//获取全部公告 admin
export const getAllNotice = params => ajaxGet({url: `notice/all/`, params})

//新增公告 admin
export const addOneNotice = params => ajaxPost({url: `notice/`, params})

//编辑公告 admin
export const editOneNotice = params => ajaxPut({url: `notice/`, params})

//删除公告 admin
export const deleteOneNotice = params => ajaxDelete({url: `notice/`, params})

//获取有效公告
export const getValidNotice = params => ajaxGet({url: `notice/valid/`, params})