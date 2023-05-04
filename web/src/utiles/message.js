import { ElMessage } from 'element-plus'
import { ElNotification } from 'element-plus'
const message = (i) => {
    ElMessage({
      grouping: true,
      message: i,
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



export {message,success,warning,error,open2}

