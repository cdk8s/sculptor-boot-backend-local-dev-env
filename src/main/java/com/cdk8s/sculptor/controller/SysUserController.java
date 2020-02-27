package com.cdk8s.sculptor.controller;


import com.cdk8s.sculptor.mapstruct.SysUserMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.SysUserUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysuser.SysUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "SysUser API")
@Slf4j
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserMapStruct sysUserMapStruct;

	// =====================================查询业务 start=====================================

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<?> detail(@RequestParam Long id) {
		return R.success(sysUserMapStruct.toResponseDTO(sysUserService.findOneById(id)));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})

	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysUserMapStruct.toResponseDTOList(sysUserService.findListByIdList(param.getIdList())));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@RequestBody SysUserPageQueryParam param) {
		List<SysUser> result = sysUserService.findListByServiceBO(sysUserMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysUserMapStruct.toResponseDTOList(result));
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "Core Object Model", response = SysUserResponseDTO.class)
	})

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysUserPageQueryParam param) {
		PageInfo result = sysUserService.findPageByServiceBO(sysUserMapStruct.pageQueryParamToServiceBO(param));
		result.setList(sysUserMapStruct.toResponseDTOList(result.getList()));
		return R.success(result);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@RequestMapping(value = "/cacheEvict", method = RequestMethod.GET)
	public ResponseEntity<?> cacheEvict() {
		sysUserService.cacheEvict();
		return R.success();
	}


	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@Valid @RequestBody IdListRequestParam param) {
		sysUserService.resetPassword(param.getIdList());
		return R.success();
	}


	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysUserCreateRequestParam param) {
		sysUserService.create(sysUserMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysUserUpdateRequestParam param) {
		sysUserService.update(sysUserMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysUserService.batchUpdateState(sysUserMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}


	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysUserService.batchDelete(sysUserMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
