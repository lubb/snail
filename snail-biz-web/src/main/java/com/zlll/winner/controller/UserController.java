package com.zlll.winner.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlll.winner.business.model.user.SysUser;
import com.zlll.winner.business.service.user.ISysUserService;
import com.zlll.winner.common.BaseController;
import com.zlll.winner.common.BaseResult;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.apache.dubbo.config.annotation.Reference;

/**
 * 活动管理控制器
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Reference
    private ISysUserService userService;

    /**
     * 登陆方法
     * @param params
     * @return
     */
    @PostMapping(value = "login")
    public Object login(@RequestBody JSONObject params){
        String userName = params.getString("userName");
        String password = params.getString("password");
        Assert.notNull(userName,"用户名不能为空！");
        Assert.notNull(password,"密码不能为空！");
        SysUser user = userService.login(userName,password);
        if(user != null){
            session.setAttribute("user",user);
        }
        return user;
    }

    /**
     * 查询用户信息 pageNo 页码 pageSize 页数
     * @param data
     * @return
     */
    @PostMapping(value = "list")
    public BaseResult page(@RequestBody JSONObject data){
        BaseResult baseResult = new BaseResult();
        Integer pageNo = data.getInteger("pageNo");
        Integer pageSize = data.getInteger("pageSize");
        SysUser user = data.toJavaObject(SysUser.class);
        IPage<SysUser> pages = userService.findUserPage(user, pageNo, pageSize);
        baseResult.setData(pages);
        return baseResult;
    }

}
