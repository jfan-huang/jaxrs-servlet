/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.jfan.web.ws.rs.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 4.3
 */
public final class TextUtils {

	/**
	 * c在str中出现的所有位置（下标）
	 */
	public static final List<Integer> index4symbols(String str, char c) {
		List<Integer> list = new ArrayList<Integer>();

		int length;
		if (null != str && 0 < (length = str.length()))
			for (int i = 0; i < length; i++)
				if (str.charAt(i) == c)
					list.add(i);

		// return list.toArray(new Integer[list.size()]);
		return list;
	}

	/**
	 * c在str中出现的次数
	 */
	public static final Integer number4symbols(String str, char c) {
		Integer num = 0;
		int length;
		if (null != str && 0 < (length = str.length()))
			for (int i = 0; i < length; i++)
				if (str.charAt(i) == c)
					num += 1;
		return num;
	}

	public static boolean isEmpty(final CharSequence s) {
		if (s == null) {
			return true;
		}
		return s.length() == 0;
	}

	public static boolean isBlank(final CharSequence s) {
		if (s == null) {
			return true;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
