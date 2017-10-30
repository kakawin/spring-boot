package com.kakawin.gis.springboot.utils.geo;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureCollection {

	@JsonProperty("features")
	private Collection<?> features;

	public FeatureCollection() {

	}

	public FeatureCollection(Collection<?> features) {
		this.features = features;
	}

	public Collection<?> getFeatures() {
		return features;
	}

	public void setFeatures(Collection<?> features) {
		this.features = features;
	}

	@JsonProperty("type")
	public String getType() {
		return "FeatureCollection";
	}

}
