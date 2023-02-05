
                                    __  __    _            
      ___ _   _____  _______  __   / /_/ /_  (_)___  ____ _
     / _ \ | / / _ \/ ___/ / / /  / __/ __ \/ / __ \/ __  /
    /  __/ |/ /  __/ /  / /_/ /  / /_/ / / / / / / / /_/ / 
    \___/|___/\___/_/   \___ /   \__/_/ /_/_/_/ /_/\___ /  
                       /____/                     /____/  
                       
               _ ____       __                ____  __ __
     _      __(_) / /      / /_  ___         / __ \/ //_/
    | | /| / / / / /      / __ / _  \       / / / / / <   
    | |/ |/ / / / /      / /_/ /  __/      / /_/ / /| |  
    |__/|__/_/_/_/      /_ ___/\___/       \____/_/ |_| 


## 导出excel
> npm install xlsx --save
```js
exportData() {
  /* 定义要导出的数据 */
  const data = [
    { name: 'John Doe', age: 25, city: 'New York' },
    { name: 'Jane Doe', age: 30, city: 'London' },
    // ...
  ];

  /* 将数据转换为二进制 */
  const ws = XLSX.utils.json_to_sheet(data);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
  const binaryData = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });

  /* 创建 Blob 对象 */
  const blob = new Blob([binaryData], { type: 'application/octet-stream' });

  /* 下载文件 */
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = 'data.xlsx';
  link.click();
}
```

