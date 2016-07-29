package com.lib.service.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lib.dao.UserInfoDao;
import com.lib.entity.UserInfo;
import com.lib.exception.user.UserException;
import com.lib.exception.user.UserNullAccountException;
import com.lib.exception.user.UserPasswordWrongException;
import com.lib.service.user.UserService;
import com.lib.utils.StringValueUtil;

@Service
public class UserServiceImpl implements UserService {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public void checkUserByEmail(UserInfo user) throws UserException {
		UserInfo record = userInfoDao.queryByEmail(user.getUserEmail());
		if (record == null) {
			throw new UserNullAccountException("用户不存在");
		} else if (!record.getUserPassword().equals(StringValueUtil.getMD5(user.getUserPassword()))) {
			LOG.warn(record.getUserId() + "登录密码错误");
			throw new UserPasswordWrongException("用户密码错误");
		}
	}

	@Override
	public UserInfo getUserById(long l) {

		return userInfoDao.queryById(l);
	}

	@Override
	public UserInfo getBasicUserInfo(Long userId) {
		UserInfo user = new UserInfo();
		UserInfo record = userInfoDao.queryById(userId);
		user.setUserName(record.getUserName());
		user.setUserEmail(record.getUserEmail());
		user.setUserPhoto(record.getUserPhoto());
		user.setUserType(record.getUserType());
		return user;
	}

	@Override
	public UserInfo getBasicUserInfoByEmail(String userEmail) {
		UserInfo user = new UserInfo();
		UserInfo record = userInfoDao.queryByEmail(userEmail);
		user.setUserName(record.getUserName());
		user.setUserEmail(record.getUserEmail());
		user.setUserPhoto(record.getUserPhoto());
		user.setUserType(record.getUserType());
		return user;
	}

}