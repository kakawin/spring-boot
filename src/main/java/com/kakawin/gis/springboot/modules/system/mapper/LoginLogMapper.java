package com.kakawin.gis.springboot.modules.system.mapper;

import java.util.List;
import java.util.Map;

import com.kakawin.gis.springboot.modules.system.entity.LoginLog;
import com.kakawin.gis.springboot.utils.MyMapper;

public interface LoginLogMapper extends MyMapper<LoginLog> {

	List<LoginLog> listByParamMap(Map<String, Object> paramMap);

}
