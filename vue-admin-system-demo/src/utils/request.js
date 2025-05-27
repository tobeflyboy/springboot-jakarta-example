import axios from "axios";
import {ElMessage} from "element-plus";
import router from '@/router';

const request = axios.create({
    baseURL: "http://localhost:8080/demo",
    timeout: 30000
});

// request 拦截器
// 可以自请求发送前对请求做一些处理
request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        // 每次发送请求之前判断是否存在token，如果存在，则统一在http请求的header都加上token，不用每次请求都手动添加了
        // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
        const token = localStorage.getItem('token');
        token && (config.headers['Token'] = token);
        return config;
    },
    error => {
        return Promise.error(error);
    }
);

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }

        if (res.code === 401) {
            ElMessage.error('登录过期，请重新登录');
            console.log("401, redirect login page");
            router.push('/login').catch(err => {
                console.error('Failed to redirect to login:', err);
            });
        }
        return res;

    },
    error => {
        console.log('请求异常:', error)
        if (error.response) {
            if (error.response.status === 401) {
                ElMessage.error('登录过期，请重新登录');
                console.log("401, redirect login page");
                router.push('/login').catch(err => {
                    console.error('Failed to redirect to login:', err);
                });
            }
            if (error.response.status === 404) {
                ElMessage.error('未找到请求接口');
            }
            if (error.response.status === 403) {
                ElMessage.error('未找到请求接口');
            }
            if (error.response.status === 500) {
                ElMessage.error('系统异常，请查看系统日志');
            } else {
                ElMessage.error(error.message);
            }
            // 返回拒绝，让调用方可以 catch
            return Promise.reject(error)
        }

        // 请求根本没有到达服务器（超时、断网等）
        if (!navigator.onLine) {
            ElMessage.error('网络已断开，请检查网络连接');
        } else {
            ElMessage.error('请求超时或服务器无响应');
        }
        return Promise.reject(error);
    }
);

export default request;