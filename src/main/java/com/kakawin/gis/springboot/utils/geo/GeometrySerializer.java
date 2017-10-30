package com.kakawin.gis.springboot.utils.geo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;

public class GeometrySerializer extends JsonSerializer<Geometry> {

	@Override
	public void serialize(Geometry value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeString(value.toText());
	}

}
