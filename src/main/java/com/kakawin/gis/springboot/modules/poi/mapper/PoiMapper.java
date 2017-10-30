package com.kakawin.gis.springboot.modules.poi.mapper;

import java.util.List;
import java.util.Map;

import com.kakawin.gis.springboot.modules.poi.entity.Poi;
import com.kakawin.gis.springboot.utils.MyMapper;
import com.vividsolutions.jts.geom.Polygon;

public interface PoiMapper extends MyMapper<Poi> {

	List<Poi> listByParamMap(Map<String, Object> paramMap);

	List<Poi> listFeatureByExtent(Polygon extent);

}