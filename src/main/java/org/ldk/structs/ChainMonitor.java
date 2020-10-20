package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;

@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class ChainMonitor extends CommonBase {
	ChainMonitor(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		bindings.ChainMonitor_free(ptr);
	}

	public void block_connected(byte[] header, TwoTuple<Long, Transaction>[] txdata, int height) {
		bindings.ChainMonitor_block_connected(this.ptr, header, Arrays.stream(txdata).mapToLong(arr_conv_29 -> /*TODO b*/0).toArray(), height);
		/* TODO 2 TwoTuple<Long, Transaction>  */;
	}

	public void block_disconnected(byte[] header, int disconnected_height) {
		bindings.ChainMonitor_block_disconnected(this.ptr, header, disconnected_height);
	}

	public static ChainMonitor constructor_new(Filter chain_source, BroadcasterInterface broadcaster, Logger logger, FeeEstimator feeest) {
		long ret = bindings.ChainMonitor_new(chain_source == null ? 0 : chain_source.ptr, broadcaster == null ? 0 : broadcaster.ptr, logger == null ? 0 : logger.ptr, feeest == null ? 0 : feeest.ptr);
		ChainMonitor ret_hu_conv = new ChainMonitor(null, ret);
		ret_hu_conv.ptrs_to.add(chain_source);
		ret_hu_conv.ptrs_to.add(broadcaster);
		ret_hu_conv.ptrs_to.add(logger);
		ret_hu_conv.ptrs_to.add(feeest);
		return ret_hu_conv;
	}

	public Watch as_Watch() {
		long ret = bindings.ChainMonitor_as_Watch(this.ptr);
		Watch ret_hu_conv = new Watch(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	public EventsProvider as_EventsProvider() {
		long ret = bindings.ChainMonitor_as_EventsProvider(this.ptr);
		EventsProvider ret_hu_conv = new EventsProvider(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

}
