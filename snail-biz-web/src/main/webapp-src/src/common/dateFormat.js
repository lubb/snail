export function Format(fmt){
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}


export function parseDate(dateStr,fmt){
  if(!dateStr || !fmt ) {
    throw new Error("转换时间时发生错误,时间字符串与格式字符串不匹配!");
  }
  //排除特殊字符,避免正则发生错误
  fmt = fmt.replace(/([\^\$\.\*\+\T\?\=\!\:\|\\\/\(\)\[\]\{\}])/ig,"\\$1");
  dateStr = dateStr.replace(/([\^\$\.\*\+\T\?\=\!\|\\\/\(\)\[\]\{\}])/ig," ");
  function getReg(str){
    var cfmt = fmt;
    cfmt = cfmt.replace(new RegExp(str+"+","g"),function(full){ return "("+full+")";}) || "";
    return cfmt.replace(/[yMdhmsS]/g,"\\d");
  }
  var year   = parseInt((new RegExp(getReg("y")).exec(dateStr)[1])) || 0;
  var month  = parseInt((new RegExp(getReg("M")).exec(dateStr)[1])) - 1 || 0;
  var day    = parseInt((new RegExp(getReg("d")).exec(dateStr)[1])) || 0;
  var hour   = parseInt((new RegExp(getReg("h")).exec(dateStr)[1])) || 0;
  var minute = parseInt((new RegExp(getReg("m")).exec(dateStr)[1])) || 0;
  var second = parseInt((new RegExp(getReg("s")).exec(dateStr)[1])) || 0;

  return new Date(year,month,day,hour,minute,second);
}
