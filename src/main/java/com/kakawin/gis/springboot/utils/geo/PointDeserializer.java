package com.kakawin.gis.springboot.utils.geo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

public class PointDeserializer extends JsonDeserializer<Point> {

	@Override
	public Point deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		TreeNode treeNode = jp.getCodec().readTree(jp);
		ArrayNode coordinates = (ArrayNode) treeNode.get("coordinates");
		double x = ((DoubleNode) coordinates.get(0)).asDouble();
		double y = ((DoubleNode) coordinates.get(1)).asDouble();
		Point point = GeoUtil.getFactory().createPoint(new Coordinate(x, y));
		return point;
	}

}
