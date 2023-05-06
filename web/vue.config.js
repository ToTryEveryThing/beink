const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack:{
    optimization:{
      splitChunks:false
    }
  }
})

module.exports = {
  publicPath: './',
}

module.exports = {//解决跨域问题
  devServer:{ //方法一
     proxy:'https://suggestion.baidu.com'
   }
 }

//element plus 按需引入
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')
module.exports = {
  outputDir: './build',
  // 和webpapck属性完全一致，最后会进行合并
  configureWebpack: {
    resolve: {
      alias: {
        components: '@/components'
      }
    },
    //配置webpack自动按需引入element-plus，
      plugins: [
        AutoImport({
          resolvers: [ElementPlusResolver()]
        }),
        Components({
          resolvers: [ElementPlusResolver()]
        })
      ]
  }
}

const TerserPlugin = require('terser-webpack-plugin')

// module.exports 中配置，可加参数，详见github文档或百度
configureWebpack: config => {
  if (process.env.NODE_ENV === 'production') {
    config.plugins.push(new TerserPlugin())
  }
}

