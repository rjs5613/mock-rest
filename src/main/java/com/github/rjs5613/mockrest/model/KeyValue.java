package com.github.rjs5613.mockrest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class KeyValue {

	private String key;
	
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> value;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public List<String> getValue() {
		return value;
	}
	
	public void setValue(List<String> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("{key=%s, value=%s}", key, value);
	}

}
