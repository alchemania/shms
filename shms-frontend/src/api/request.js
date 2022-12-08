import axios from 'axios';
import { ElMessage } from 'element-plus'
import { url } from './url';
import router from '../vue-router/index.js';

var request = axios.create({
    timeout: 60000,
});

function ajax(opt, method) {
    if (opt.params == null) {
        var config = {
            url: url + opt.url + '/',
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
            },
        }
        return axios(config)
    }
    if (method == "GET") {
        var config = {
            url: url + opt.url + opt.params.id + '/',
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
            },
            data: para,
        }
    } else {
        var para = JSON.stringify(opt.params)
        var config = {
            url: url + opt.url + '/',
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
            },
            data: para,
        }
    }
    console.log(config)
    return axios(config);
}



export function ajaxGet(opt) {
    return ajax(opt, "GET")
}
export function ajaxPut(opt) {
    return ajax(opt, "PUT")
}
export function ajaxDelete(opt) {
    return ajax(opt, "DELETE")
}
export function ajaxPost(opt) {
    return ajax(opt, "POST")
}
export function ajaxPatch(opt) {
    return ajax(opt, "PATCH")
}
// 单例详情获取get /api/test/12xxxxxxxx/
export function ajaxGetDetailByID(opt) {
    return ajax(opt, "GET2")
}

//websocket获取jwt请求token
// export function getJWTAuthorization() {
//     // var token = store.getters.getLogintoken
//     var jwt = 'JWTlybbn' + token
//     return jwt
// }

// export function reqExpost(method, url, params) {
//     const timestamp = new Date().getTime().toString();
//     // let token = store.getters.getLogintoken
//     for (let key in params) {
//         if (params[key] == null || params[key] == 'undefined' || params[key] == '') {
//             delete params[key]
//         }
//     }
//     const keys = Object.keys(params).sort(); let i;
//     const length = keys.length;
//     let key;
//     let sortedString = '';
//     for (i = 0; i < length; i++) {
//         key = keys[i];
//         sortedString += (key + '=' + params[key]);
//     }
//     return axios({
//         method: method,
//         url: url,
//         data: params,
//         responseType: 'blob' // 表明返回服务器返回的数据类型
//     }).then(res => res.data);
// }
// 上传图片
// export function uploadImg(param) {
//     let formData = new FormData()
//     formData.append('file', param.params.file)
//     // let token = store.getters.getLogintoken
//     return axios({
//         method: 'post',
//         url: url + param.url,
//         data: formData,
//         onUploadProgress: progressEvent => {
//             // progressEvent.loaded:已上传文件大小
//             // progressEvent.total:被上传文件的总大小
//             let loadProgress = (progressEvent.loaded / progressEvent.total * 100)
//             param.params.onProgress({ percent: loadProgress })
//             // console.info(progressEvent.loaded)
//             // console.info(progressEvent.total)
//         }
//     }).then(res => res.data);
// }