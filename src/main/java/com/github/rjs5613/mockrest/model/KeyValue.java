package com.github.rjs5613.mockrest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class KeyValue {

	private String key;
	
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> values;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public void setValues(List<String> value) {
		this.values = value;
	}

	@Override
	public String toString() {
		return String.format("{key=%s, values=%s}", key, values);
	}

}
