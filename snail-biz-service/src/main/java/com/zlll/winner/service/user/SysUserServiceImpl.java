package com.zlll.winner.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlll.winner.business.model.user.SysUser;
import com.zlll.winner.mapper.user.SysUserMapper;
import com.zlll.winner.business.service.user.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlll.winner.util.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ricky
 * @since 2019-08-23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Transactional(readOnly = true)
    public SysUser login(String userName, String passWord)  {
        SysUser result = null;
        QueryWrapper<SysUser> query = new QueryWrapper<SysUser>();
        query.eq("user_name", userName);
        List<SysUser> userList = sysUserMapper.selectList(query);
        if (userList != null && userList.size() > 0) {
            SysUser user = userList.get(0);
            String encodePassWord = PasswordEncoder.encode(passWord,user.getSalt());
            Assert.isTrue(user.getPassword().equals(encodePassWord),"密码错误，请重试！");
            result = user;
        }
        return result;
    }

    @Override
    public IPage<SysUser> findUserPage(SysUser user, Integer pageNo, Integer pageSize) {
        IPage<SysUser> page = new Page<SysUser>(pageNo,pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true,false,"create_time");
        return sysUserMapper.selectPage(page, queryWrapper);
    }
}
