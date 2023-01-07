import $ from 'jquery'
function darkClass(Class){
    if(Class==="dark"){
        $("html").addClass("dark")
    }else{
        $("html").removeClass("dark")
    }
}
export default darkClass