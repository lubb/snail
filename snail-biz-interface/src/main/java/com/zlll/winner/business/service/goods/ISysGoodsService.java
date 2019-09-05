package com.zlll.winner.business.service.goods;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlll.winner.business.model.goods.SysGoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ricky
 * @since 2019-08-23
 */
public interface ISysGoodsService extends IService<SysGoods> {

    IPage<SysGoods> findGoodsPage(SysGoods sysGoods, Integer pageNo, Integer pageSize);

}
