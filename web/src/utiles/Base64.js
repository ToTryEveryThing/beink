
function base(i){
   let s = "data:image/png;base64,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"
    let b = "data:image/png;base64,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"
    let j = "data:image/png;base64,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"
    if(i==="b")return b
    if(i==="j")return j
    if(i==="s")return s
    return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEQAAAAqCAIAAACFj2rPAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAAEXRFWHRTb2Z0d2FyZQBTbmlwYXN0ZV0Xzt0AAABPSURBVGiB7c/BCcAgAMBAdX9wX18uUTCUuwmSufcZf7FeB3zJTJWZKjNVZqrMVJmpMlNlpspMlZkqM1VmqsxUmakyU2WmykyVmSozVWaqLhE/AmwMqW5LAAAAAElFTkSuQmCC"
}
export default base