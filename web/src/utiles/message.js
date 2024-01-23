import { ElMessage } from 'element-plus'
import { ElNotification } from 'element-plus'
const message = (i) => {
    ElMessage({
      grouping: true,
      message: i,
    })
}
const articlMessage = (x,y,z) => {
  ElNotification({
    title: `${x}更新了`,
    dangerouslyUseHTMLString: true,
    message: `<a href="/article/${z}">
                《${y}》
            </a>点击前去查看`,
    // message: `<strong>This is <i>标题为：${y}地址为：${z}</i> string</strong>`,
    duration: 0
  })
}

const success = (i) => {
    ElMessage({
      message: i,
      grouping: true,
      type: 'success',
    })
}

const warning = (i) => {
    ElMessage({
      message: i,
      grouping: true,
      type: 'warning',
    })
}

const error = (i) => {
    ElMessage({
      message: i,
      grouping: true,
      type: 'error',
    })
}

const open2 = (i) => {
  ElNotification({
    title: '通知',
    message: i,
    duration: 0,
  })
}



export {message,success,warning,error,open2,articlMessage}

