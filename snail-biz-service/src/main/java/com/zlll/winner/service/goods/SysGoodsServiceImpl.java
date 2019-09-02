package com.zlll.winner.service.goods;

import com.zlll.winner.business.model.goods.SysGoods;
import com.zlll.winner.mapper.goods.SysGoodsMapper;
import com.zlll.winner.business.service.goods.ISysGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ricky
 * @since 2019-08-23
 */
@Service
public class SysGoodsServiceImpl extends ServiceImpl<SysGoodsMapper, SysGoods> implements ISysGoodsService {

}
