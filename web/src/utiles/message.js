import { ElMessage } from 'element-plus'
import { ElNotification } from 'element-plus'
const message = (i) => {
    ElMessage({
      message: i,
    })
}

const success = (i) => {
    ElMessage({
      message: i,
      type: 'success',
    })
}

const warning = (i) => {
    ElMessage({
      message: i,
      type: 'warning',
    })
}

const error = (i) => {
    ElMessage({
      message: i,
      type: 'error',
    })
}

const open2 = (i) => {
  ElNotification({
    title: '通知',
    message: i,
    duration: 5000,
  })
}



export {message,success,warning,error,open2}

