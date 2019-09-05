package com.zlll.winner.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlll.winner.business.model.goods.SysGoods;
import com.zlll.winner.business.model.user.SysUser;
import com.zlll.winner.business.service.goods.ISysGoodsService;
import com.zlll.winner.business.service.user.ISysUserService;
import com.zlll.winner.common.BaseController;
import com.zlll.winner.common.BaseResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 活动管理控制器
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {

    @Reference
    private ISysGoodsService goodsService;

    /**
     * 查询商品信息 pageNo 页码 pageSize 页数
     * @param data
     * @return
     */
    @PostMapping(value = "list")
    public BaseResult page(@RequestBody JSONObject data){
        BaseResult baseResult = new BaseResult();
        Integer pageNo = data.getInteger("pageNo");
        Integer pageSize = data.getInteger("pageSize");
        SysGoods sysGoods = data.toJavaObject(SysGoods.class);
        IPage<SysGoods> pages = goodsService.findGoodsPage(sysGoods, pageNo, pageSize);
        baseResult.setData(pages);
        return baseResult;
    }

}
