import axios from 'axios';
import { ajaxGet, ajaxPost, ajaxDelete, ajaxPut, ajaxPatch } from './request';
import { url } from './url';

//登录
export const login = params => ajaxPost({ url: `user/login/`, params })

//注册
export const register = params => ajaxPost({ url: `user/`, params })

//登出
export const logout = (params) => ajaxPut({ url: `user/`, params })

//获取健康信息
export const fetchHealthData = params => ajaxGet({ url: `healthinfo/`, params })

//修改健康信息
export const alterHealthData = params => ajaxPut({ url: `healthinfo/`, params })

//省市区三级联查
export const threeLevelQuery = params => ajaxPost({ url: `riskarea/menu/`, params })

//查询风险区
export const riskAreaQuery = params => ajaxPost({ url: `riskarea/`, params })

//查询疫情信息
export const epDetail = params => ajaxGet({ url: `riskarea/`, params })

//获取邮件验证码
export const getValidCode = params => ajaxPost({ url: `user/code/`, params })

//验证邮件验证码
export const validValidCode = params => ajaxPut({ url: `user/code/`, params })

//新建核酸预约
export const createNATBook = params => ajaxPut({ url: `natbook/`, params })

//查询核酸预约
export const queryNATBook  = params => ajaxGet({ url: `natbook/`, params })

//重定向
export const redirectAdmin = params => ajaxPost({ url: `user/redirect/`, params })