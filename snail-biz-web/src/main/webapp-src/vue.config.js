const Timestamp = new Date().getTime();
module.exports = {
    publicPath: './',
    outputDir:'../static-resources/static',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer:{
       proxy:'http://localhost:8888/'
    },
    configureWebpack: { // webpack 配置
        output: { // 输出重构  打包编译后的 文件名称  【模块名称.版本号.时间戳】
            filename: `static/js/[name].${process.env.NODE_ENV}.${Timestamp}.js`,
            chunkFilename: `static/js/[name].${process.env.NODE_ENV}.${Timestamp}.js`
        },
    },
}
