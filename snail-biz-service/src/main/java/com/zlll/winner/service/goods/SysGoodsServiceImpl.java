package com.zlll.winner.service.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlll.winner.business.model.goods.SysGoods;
import com.zlll.winner.mapper.goods.SysGoodsMapper;
import com.zlll.winner.business.service.goods.ISysGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private SysGoodsMapper sysGoodsMapper;

    @Override
    public IPage<SysGoods> findGoodsPage(SysGoods sysGoods, Integer pageNo, Integer pageSize) {
        IPage<SysGoods> page = new Page<SysGoods>(pageNo,pageSize);
        QueryWrapper<SysGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true,false,"create_time");
        return sysGoodsMapper.selectPage(page, queryWrapper);
    }
}
