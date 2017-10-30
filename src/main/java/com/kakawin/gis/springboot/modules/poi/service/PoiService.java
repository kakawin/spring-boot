package com.kakawin.gis.springboot.modules.poi.service;

import java.util.List;
import java.util.Map;

import com.kakawin.gis.springboot.modules.poi.entity.Poi;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;
import com.vividsolutions.jts.geom.Polygon;

public interface PoiService {

	ResponsePage<Poi> getPage(RequestPage requestPage, Map<String, Object> paramMap);

	List<Poi> listFeatureByExtent(Polygon extent);

}
