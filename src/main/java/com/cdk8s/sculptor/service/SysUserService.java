package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.RegisterOriginEnum;
import com.cdk8s.sculptor.enums.RegisterTypeEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.mapper.SysUserMapper;
import com.cdk8s.sculptor.mapstruct.SysUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserMobilePhoneMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserUserEmailMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserUsernameMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.code.Md5Util;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysUserService")
@Service
public class SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserMapStruct sysUserMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUser findOneById(Long id) {
		return sysUserMapper.selectById(new IdMapperBO(id));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String findUsernameById(Long id) {
		if (null == id) {
			return null;
		}
		SysUser result = findOneById(id);
		if (null != result) {
			return result.getUsername();
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUser findOneByUsername(String username) {
		return sysUserMapper.selectOneByUsername(new SysUserUsernameMapperBO(username));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public SysUser findOneByUsernameIncludeDelete(String username) {
		SysUserUsernameMapperBO mapperBO = new SysUserUsernameMapperBO(username);
		mapperBO.setDeleteEnum(null);
		return sysUserMapper.selectOneByUsername(mapperBO);
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUser> findListByMobilePhone(String mobilePhone) {
		return sysUserMapper.selectByMobilePhone(new SysUserMobilePhoneMapperBO(mobilePhone));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUser> findListByUserEmail(String userEmail) {
		return sysUserMapper.selectByUserEmail(new SysUserUserEmailMapperBO(userEmail));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUser> findListByIdList(List<Long> idList) {
		return sysUserMapper.selectByIdList(new IdListMapperBO(idList));
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public List<SysUser> findListByServiceBO(SysUserPageQueryServiceBO serviceBO) {
		return sysUserMapper.selectByPageQueryMapperBo(sysUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public PageInfo findPageByServiceBO(SysUserPageQueryServiceBO serviceBO) {
		return PageHelper.startPage(serviceBO.getPageNum(), serviceBO.getPageSize()).doSelectPageInfo(() -> {
			sysUserMapper.selectByPageQueryMapperBo(sysUserMapStruct.pageQueryServiceBOToMapperBO(serviceBO));
		});
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	/**
	 * 该方法名不允许修改，被用在反射上
	 */
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public void cacheEvict() {
		// 用来主动清除所有缓存数据
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public void resetPassword(List<Long> userIdList) {
		List<SysUser> result = findListByIdList(userIdList);
		if (CollectionUtil.isEmpty(result)) {
			throw new RuntimeException("用户不存在");
		}

		for (SysUser entity : result) {
			SysUserUpdateServiceBO serviceBO = new SysUserUpdateServiceBO();
			serviceBO.setId(entity.getId());

			String passwordSalt = RandomUtil.randomAlphanumeric(10);
			serviceBO.setPasswordSalt(passwordSalt);

			String passwordByMD5 = Md5Util.md5ByGuava("123456" + passwordSalt);
			serviceBO.setUserPassword(passwordByMD5);
			update(serviceBO);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer create(SysUserCreateServiceBO serviceBO) {
		Long userId = GenerateIdUtil.getId();
		serviceBO.setId(userId);
		int result = sysUserMapper.insert(initCreateBasicParam(serviceBO));

		if (result == 0) {
			throw new RuntimeException("创建用户失败");
		}

		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysUserUpdateServiceBO serviceBO) {
		return sysUserMapper.updateByPrimaryKeySelective(initUpdateBasicParam(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchUpdateState(BatchUpdateStateServiceBO serviceBO) {
		return sysUserMapper.updateStateEnumByIdList(sysUserMapStruct.batchUpdateStateServiceBOToMapperBO(serviceBO));
	}

	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		List<Long> idList = serviceBO.getIdList();


		int result = sysUserMapper.updateDeleteEnumByIdList(sysUserMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
		if (result == 0) {
			throw new RuntimeException("删除用户失败");
		}

		return result;
	}

	public String md5UserPassword(String inputPassword, String passwordSalt) {
		if (StringUtil.isBlank(passwordSalt)) {
			passwordSalt = RandomUtil.randomAlphanumeric(10);
		}
		// 数据库密码 = MD5(输入密码 + 随机盐)
		return Md5Util.md5ByGuava(inputPassword + passwordSalt);
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysUser initCreateBasicParam(SysUserCreateServiceBO serviceBO) {
		// 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		String passwordSalt = RandomUtil.randomAlphanumeric(10);
		serviceBO.setPasswordSalt(passwordSalt);

		serviceBO.setUserPassword(md5UserPassword(serviceBO.getUserPassword(), passwordSalt));

		// 实际情况要根据业务情况分析注册来源
		serviceBO.setRegisterTypeEnum(RegisterTypeEnum.MANAGEMENT_ADD.getCode());
		serviceBO.setRegisterOriginEnum(RegisterOriginEnum.WEB.getCode());

		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}

		return sysUserMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysUser initUpdateBasicParam(SysUserUpdateServiceBO serviceBO) {
		return sysUserMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

