package com.Hileb.add_potion.common.util;

import org.apache.logging.log4j.Logger;

public class ModLogger {

	@SuppressWarnings("NotNullFieldNotInitialized")
	public static Logger logger;

	public static void LogWarning(String str, Object... args) {
		logger.warn(String.format(str, args));
	}

	public static void LogInfo(String str, Object... args) {
		logger.info(String.format(str, args));
	}
}
