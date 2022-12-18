module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset',
    ["@babel/preset-env",{"modules":false}],
  ],
  plugins:[
    [
      "component",
      {
        "libraryName":"element-ui",
        "styleLibraryName":"theme-chalk"
      }
    ]
  ],
  //语法高亮
  plugins: [
    [
      'prismjs',
      {
        languages: ['html','java','javascript','css','xml'],
      },
    ],
  ],
}
