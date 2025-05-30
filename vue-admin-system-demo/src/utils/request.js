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
request.interceptors.response.use(
    response => {
        console.log('response:', response);
        // ✅ 如果是 blob 类型，直接返回原始数据，不再做任何处理
        if (response.config.responseType === 'blob') {
            console.log('blob besponse:', response);
            return response; // 返回整个 response，包含 headers 等完整信息
        }

        // 否则正常处理 JSON 数据
        let res = response.data;

        if (typeof res === 'string') {
            try {
                res = JSON.parse(res);
            } catch (e) {
                // 忽略无法解析的字符串
                console.error('Failed to parse JSON:', e);
            }
        }

        if (res.code === 401) {
            ElMessage.error('登录过期，请重新登录');
            router.push('/login').catch(err => {
                console.error('Failed to redirect to login:', err);
            });
        }

        return res;
    },
    error => {
        // 错误处理不变
        if (error.response) {
            if (error.response.status === 401) {
                ElMessage.error('登录过期，请重新登录');
                router.push('/login').catch(err => {
                    console.error('Failed to redirect to login:', err);
                });
            }
            if (error.response.status === 404) {
                ElMessage.error('未找到请求接口');
            }
            if (error.response.status === 403) {
                ElMessage.error('无权限访问');
            }
            if (error.response.status === 500) {
                ElMessage.error('系统异常，请查看系统日志');
            } else {
                console.error(`请求失败, url:${error.response.config.url}, error msg:${error.message}`);
            }
            return Promise.reject(error);
        }

        // if (!navigator.onLine) {
        //     ElMessage.error('网络已断开，请检查网络连接');
        // } else {
        //     ElMessage.error('请求超时或服务器无响应');
        // }
        return Promise.reject(error);
    }
);

export default request;