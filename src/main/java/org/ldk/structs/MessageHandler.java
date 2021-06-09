package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;


/**
 * Provides references to trait impls which handle different types of messages.
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class MessageHandler extends CommonBase {
	MessageHandler(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.MessageHandler_free(ptr); }
	}

	/**
	 * A message handler which handles messages specific to channels. Usually this is just a
	 * ChannelManager object or a ErroringMessageHandler.
	 */
	public ChannelMessageHandler get_chan_handler() {
		long ret = bindings.MessageHandler_get_chan_handler(this.ptr);
		ChannelMessageHandler ret_hu_conv = new ChannelMessageHandler(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * A message handler which handles messages specific to channels. Usually this is just a
	 * ChannelManager object or a ErroringMessageHandler.
	 */
	public void set_chan_handler(ChannelMessageHandler val) {
		bindings.MessageHandler_set_chan_handler(this.ptr, val == null ? 0 : val.ptr);
		this.ptrs_to.add(val);
	}

	/**
	 * A message handler which handles messages updating our knowledge of the network channel
	 * graph. Usually this is just a NetGraphMsgHandlerMonitor object or an IgnoringMessageHandler.
	 */
	public RoutingMessageHandler get_route_handler() {
		long ret = bindings.MessageHandler_get_route_handler(this.ptr);
		RoutingMessageHandler ret_hu_conv = new RoutingMessageHandler(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * A message handler which handles messages updating our knowledge of the network channel
	 * graph. Usually this is just a NetGraphMsgHandlerMonitor object or an IgnoringMessageHandler.
	 */
	public void set_route_handler(RoutingMessageHandler val) {
		bindings.MessageHandler_set_route_handler(this.ptr, val == null ? 0 : val.ptr);
		this.ptrs_to.add(val);
	}

	/**
	 * Constructs a new MessageHandler given each field
	 */
	public static MessageHandler of(ChannelMessageHandler chan_handler_arg, RoutingMessageHandler route_handler_arg) {
		long ret = bindings.MessageHandler_new(chan_handler_arg == null ? 0 : chan_handler_arg.ptr, route_handler_arg == null ? 0 : route_handler_arg.ptr);
		MessageHandler ret_hu_conv = new MessageHandler(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		ret_hu_conv.ptrs_to.add(chan_handler_arg);
		ret_hu_conv.ptrs_to.add(route_handler_arg);
		return ret_hu_conv;
	}

}
