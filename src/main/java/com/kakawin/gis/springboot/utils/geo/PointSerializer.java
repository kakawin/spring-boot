package com.kakawin.gis.springboot.utils.geo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Point;

public class PointSerializer extends JsonSerializer<Point> {
	@Override
	public void serialize(Point value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeStringField("type", value.getGeometryType());

		jgen.writeArrayFieldStart("coordinates");
		jgen.writeNumber(value.getX());
		jgen.writeNumber(value.getY());
		jgen.writeEndArray();

		jgen.writeEndObject();
	}
}
