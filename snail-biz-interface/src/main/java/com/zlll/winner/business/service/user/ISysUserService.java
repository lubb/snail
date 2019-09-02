package com.zlll.winner.business.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlll.winner.business.model.user.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ricky
 * @since 2019-08-23
 */
public interface ISysUserService extends IService<SysUser> {

    SysUser login(String userName, String passWord);

    IPage<SysUser> findUserPage(SysUser user, Integer pageNo, Integer pageSize);
}
