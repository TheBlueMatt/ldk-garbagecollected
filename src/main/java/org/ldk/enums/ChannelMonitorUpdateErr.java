package org.ldk.enums;

/**
 * An error enum representing a failure to persist a channel monitor update.
 */
public enum ChannelMonitorUpdateErr {
	LDKChannelMonitorUpdateErr_TemporaryFailure,
	LDKChannelMonitorUpdateErr_PermanentFailure,
	; static native void init();
	static { init(); }
}