// 跨域配置
module.exports = {
  devServer: {                //记住，别写错了devServer//设置本地默认端口  选填
    // port: 9876,
    // host: 'localhost',  // todo 发布记得换 设置npm run server指定打开localhost
    host: '43.139.122.43',  // 设置npm run server指定打开120.24.6.28
    proxy: {                 //设置代理，必须填
      '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
        // target: 'localhost:9091',     // todo 发布记得换 代理的目标地址
        target: '43.139.122.43:9091',     //代理的目标地址
        changeOrigin: true,              //是否设置同源，输入是的
        pathRewrite: {                   //路径重写
          '/api': ''                     //选择忽略拦截器里面的单词
        }
      }
    }
  }
}
