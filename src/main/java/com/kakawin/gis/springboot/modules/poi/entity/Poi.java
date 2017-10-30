package com.kakawin.gis.springboot.modules.poi.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kakawin.gis.springboot.utils.geo.PointDeserializer;
import com.kakawin.gis.springboot.utils.geo.PointSerializer;
import com.vividsolutions.jts.geom.Point;

@TableName(value = "tb_map_poi")
public class Poi {
	@TableId
	private String id;

	@JsonSerialize(using = PointSerializer.class)
	@JsonDeserialize(using = PointDeserializer.class)
	private Point geometry;

	@TableField(value = "poi_type")
	private String poiType;

	@TableField(value = "poi_code")
	private String poiCode;

	@TableField(value = "poi_name")
	private String poiName;

	@JsonProperty("type")
	public String getType() {
		return "Feature";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Point getGeometry() {
		return geometry;
	}

	public void setGeometry(Point geometry) {
		this.geometry = geometry;
	}

	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}

	public String getPoiCode() {
		return poiCode;
	}

	public void setPoiCode(String poiCode) {
		this.poiCode = poiCode;
	}

	public String getPoiName() {
		return poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

}
