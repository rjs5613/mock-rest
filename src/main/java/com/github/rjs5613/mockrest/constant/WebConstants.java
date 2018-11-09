package com.github.rjs5613.mockrest.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebConstants {
	
	private WebConstants() {}
	
	public static final List<String> ADMIN_PATHS = Arrays.asList("/mappings");
	
	public static final String LOCALHOST_URL = "http://localhost";
	
	public static final Map<String, String> UNSUPPORTED_TO_SUPPORTED_MEDIA_TYPE_MAP;
	
	static {
		UNSUPPORTED_TO_SUPPORTED_MEDIA_TYPE_MAP = new HashMap<>();
		UNSUPPORTED_TO_SUPPORTED_MEDIA_TYPE_MAP.put("text/html", "application/xml");
	}

}
