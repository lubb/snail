import Vue from 'vue'
import {Format,parseDate} from '@/common/js/dateFormat'

Date.prototype.Format = Format;

let minxin = {
  methods: {
    //格式化日期
    renderTime(time) {
      if(time){
        time = time.toString();
        if(time.length === 10){
          time = (time-0) * 1000;
        }
        time = (time - 0);
        if (time) {
          return (new Date(time)).Format("yyyy-MM-dd hh:mm:ss");
        } else {
          return "--";
        }
      }
    },
    renderTimeWithOutYear(time) {
      if(time){
        time = time.toString();
        if(time.length === 10){
          time = (time-0) * 1000;
        }
        time = (time - 0);
        if (time) {
          return (new Date(time)).Format("MM-dd hh:mm");
        } else {
          return "--";
        }
      }
    },
    //格式化日期
    renderTimeNoTime(time) {
      time = (time - 0);
      if (time) {
        return (new Date(time)).Format("yyyy-MM-dd");
      } else {
        return "--";
      }
    },
    //格式化日期
    renderTimeMonthDay(time) {
      time = (time - 0);
      if (time) {
        return (new Date(time)).Format("MM月dd日");
      } else {
        return "--";
      }
    },
    renderTime_str2date(datestr,fmt){
      if(datestr){
        if(!fmt) fmt = 'yyyy-MM-dd hh:mm:ss';
        return this.renderTime(parseDate(datestr,fmt))
      }else{
        return "--"
      }
    }

  }
};

Vue.mixin(minxin)
