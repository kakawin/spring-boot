package com.kakawin.gis.springboot.modules.poi.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakawin.gis.springboot.modules.poi.entity.Poi;
import com.kakawin.gis.springboot.modules.poi.service.PoiService;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;
import com.kakawin.gis.springboot.utils.SysResponse;
import com.kakawin.gis.springboot.utils.WebUtil;
import com.kakawin.gis.springboot.utils.geo.FeatureCollection;
import com.kakawin.gis.springboot.utils.geo.GeoUtil;
import com.vividsolutions.jts.geom.Polygon;

@RestController
@RequestMapping("/poi")
public class PoiController {
	@Autowired
	private PoiService poiService;

	@GetMapping("/page")
	public SysResponse getPageList(RequestPage requestPage, HttpServletRequest request) {
		ResponsePage<Poi> responsePage = poiService.getPage(requestPage, WebUtil.getParamMap(request));
		return SysResponse.ok(responsePage);
	}

	@GetMapping("/featuresByExtent")
	public FeatureCollection listFeatureByExtent(String coords) {
		Polygon extent = GeoUtil.getExtent(coords);
		List<Poi> list = poiService.listFeatureByExtent(extent);
		return new FeatureCollection(list);
	}

}