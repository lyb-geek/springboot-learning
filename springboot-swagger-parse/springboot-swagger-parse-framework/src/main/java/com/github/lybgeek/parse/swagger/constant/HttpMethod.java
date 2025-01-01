package com.github.lybgeek.parse.swagger.constant;

import cn.hutool.core.lang.Assert;
import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;

@Data
public final class HttpMethod implements Serializable {

	private static final long serialVersionUID = -70133475680645360L;

	/**
	 * The HTTP method {@code GET}.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.3">HTTP 1.1, section 9.3</a>
	 */
	public static final HttpMethod GET = new HttpMethod("GET");

	/**
	 * The HTTP method {@code HEAD}.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.4">HTTP 1.1, section 9.4</a>
	 */
	public static final HttpMethod HEAD = new HttpMethod("HEAD");

	/**
	 * The HTTP method {@code POST}.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.5">HTTP 1.1, section 9.5</a>
	 */
	public static final HttpMethod POST = new HttpMethod("POST");

	/**
	 * The HTTP method {@code PUT}.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.6">HTTP 1.1, section 9.6</a>
	 */
	public static final HttpMethod PUT = new HttpMethod("PUT");

	/**
	 * The HTTP method {@code PATCH}.
	 * @see <a href="https://datatracker.ietf.org/doc/html/rfc5789#section-2">RFC 5789</a>
	 */
	public static final HttpMethod PATCH = new HttpMethod("PATCH");

	/**
	 * The HTTP method {@code DELETE}.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.7">HTTP 1.1, section 9.7</a>
	 */
	public static final HttpMethod DELETE = new HttpMethod("DELETE");

	/**
	 * The HTTP method {@code OPTIONS}.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.2">HTTP 1.1, section 9.2</a>
	 */
	public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");

	/**
	 * The HTTP method {@code TRACE}.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.8">HTTP 1.1, section 9.8</a>
	 */
	public static final HttpMethod TRACE = new HttpMethod("TRACE");

	private static final HttpMethod[] values = new HttpMethod[] { GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE };


	private final String name;


	private HttpMethod(String name) {
		this.name = name;
	}

	/**
	 * Returns an array containing the standard HTTP methods. Specifically,
	 * this method returns an array containing {@link #GET}, {@link #HEAD},
	 * {@link #POST}, {@link #PUT}, {@link #PATCH}, {@link #DELETE},
	 * {@link #OPTIONS}, and {@link #TRACE}.
	 *
	 * <p>Note that the returned value does not include any HTTP methods defined
	 * in WebDav.
	 */
	public static HttpMethod[] values() {
		HttpMethod[] copy = new HttpMethod[values.length];
		System.arraycopy(values, 0, copy, 0, values.length);
		return copy;
	}



	/**
	 * Return the name of this method, e.g. "GET", "POST".
	 */
	public String name() {
		return this.name;
	}

	/**
	 * Determine whether this {@code HttpMethod} matches the given method value.
	 * @param method the HTTP method as a String
	 * @return {@code true} if it matches, {@code false} otherwise
	 * @since 4.2.4
	 */
	public boolean matches(String method) {
		return name().equals(method);
	}




}
