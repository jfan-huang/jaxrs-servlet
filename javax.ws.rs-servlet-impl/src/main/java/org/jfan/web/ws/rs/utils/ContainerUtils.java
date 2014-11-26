package org.jfan.web.ws.rs.utils;

/**
 * Utility methods used by container implementations. <br>
 * <br>
 * 
 * @author JFan - 2014年11月17日 上午10:41:10
 */
public final class ContainerUtils {

	/**
	 * Encodes (predefined subset of) unsafe/unwise URI characters with the
	 * percent-encoding.
	 * <p/>
	 * <p>
	 * Replaces the predefined set of unsafe URI characters in the query string
	 * with its percent-encoded counterparts. The reserved characters (as
	 * defined by the RFC) are automatically encoded by browsers, but some
	 * characters are in the "gray zone" - are not explicitly forbidden, but not
	 * recommended and known to cause issues.
	 * </p>
	 * <p/>
	 * <p>
	 * Currently, the method only encodes the curly brackets ({@code \ and
	 * {@code \} ), if any, to allow URI request parameters to contain JSON.
	 * </p>
	 *
	 * @param originalQueryString URI query string (the part behind the question
	 *            mark character).
	 * @return the same string with unsafe characters (currently only curly
	 *         brackets) eventually percent encoded.
	 */
	public static String encodeUnsafeCharacters(String originalQueryString) {
		if (originalQueryString == null) {
			return null;
		}
		if (originalQueryString.contains("{") || originalQueryString.contains("}")) {
			return originalQueryString.replace("{", "%7B").replace("}", "%7D");
		}
		return originalQueryString;
	}

}
