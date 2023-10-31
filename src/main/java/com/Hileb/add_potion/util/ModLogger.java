package com.Hileb.add_potion.util;

import org.apache.logging.log4j.Logger;

public class ModLogger {
	public static final boolean SHOW_WARN = true;

	public static Logger logger;

	public static void LogWarning(String str, Object... args) {
		if (SHOW_WARN) {
			logger.warn(String.format(str, args));
		}
	}
}
