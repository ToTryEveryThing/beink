const  express=  require('express')
const   app = express()
//node hh.js
// 启动我 相当于小型服务器
// 让前端自己去配置跨域
app.use((request,response,next)=>{
	console.log('有人请求了');
	console.log('请求的资源是',request.url);
	next()
})

app.get('/students',(request,response)=>{
	const students = [
	{id:'01',name:'tom'},
	{id:'02',name:'jerry'}
	]
	response.send(students)
	
})

app.listen(5000,(err)=>{
	if(!err)
	console.log('地址为:5000')
})