package com.github.rjs5613.mockrest.model;

import java.util.Comparator;
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
	
	public static class KeyBasedComparator implements Comparator<KeyValue>{

		@Override
		public int compare(KeyValue o1, KeyValue o2) {
			return o1.getKey().compareTo(o2.getKey());
		}
		
	}

}
