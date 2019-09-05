module.exports = {
    publicPath: './',
    outputDir:'../static-resources/static',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer:{
       proxy:'http://localhost:8088/'
    }
}
