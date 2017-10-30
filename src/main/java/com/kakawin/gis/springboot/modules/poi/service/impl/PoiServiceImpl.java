package com.kakawin.gis.springboot.modules.poi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.kakawin.gis.springboot.modules.poi.entity.Poi;
import com.kakawin.gis.springboot.modules.poi.mapper.PoiMapper;
import com.kakawin.gis.springboot.modules.poi.service.PoiService;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;
import com.vividsolutions.jts.geom.Polygon;

@Service
public class PoiServiceImpl implements PoiService {

	@Autowired
	private PoiMapper poiMapper;

	@Override
	public ResponsePage<Poi> getPage(RequestPage requestPage, Map<String, Object> paramMap) {
		PageHelper.startPage(requestPage.getPage(), requestPage.getSize());
		List<Poi> list = poiMapper.listByParamMap(paramMap);
		return new ResponsePage<Poi>(list);
	}

	@Override
	public List<Poi> listFeatureByExtent(Polygon extent) {
		return poiMapper.listFeatureByExtent(extent);
	}
}
