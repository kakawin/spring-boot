package com.kakawin.gis.springboot.utils.geo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;

public class GeometryDeserializer extends JsonDeserializer<Geometry> {

	@Override
	public Geometry deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		try {
			return GeoUtil.getWktReader().read(jp.getText());
		} catch (ParseException e) {

		}
		return null;
	}
}
