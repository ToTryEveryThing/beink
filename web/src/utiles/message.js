import { ElMessage } from 'element-plus'
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


export {message,success,warning,error}

