package com.github.lybgeek.http.common;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {

	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;


	private static final Map<String, HttpMethod> mappings = new HashMap<>(16);

	static {
		for (HttpMethod httpMethod : values()) {
			mappings.put(httpMethod.name(), httpMethod);
		}
	}


	/**
	 * Resolve the given method value to an {@code HttpMethod}.
	 * @param method the method value as a String
	 * @return the corresponding {@code HttpMethod}, or {@code null} if not found
	 * @since 4.2.4
	 */
	@Nullable
	public static HttpMethod resolve(@Nullable String method) {
		return (method != null ? mappings.get(method) : null);
	}


	/**
	 * Determine whether this {@code HttpMethod} matches the given
	 * method value.
	 * @param method the method value as a String
	 * @return {@code true} if it matches, {@code false} otherwise
	 * @since 4.2.4
	 */
	public boolean matches(String method) {
		return (this == resolve(method));
	}

}
