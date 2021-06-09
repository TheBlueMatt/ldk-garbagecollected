package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;


/**
 * A transaction output watched by a [`ChannelMonitor`] for spends on-chain.
 * 
 * Used to convey to a [`Filter`] such an output with a given spending condition. Any transaction
 * spending the output must be given to [`ChannelMonitor::block_connected`] either directly or via
 * the return value of [`Filter::register_output`].
 * 
 * If `block_hash` is `Some`, this indicates the output was created in the corresponding block and
 * may have been spent there. See [`Filter::register_output`] for details.
 * 
 * [`ChannelMonitor`]: channelmonitor::ChannelMonitor
 * [`ChannelMonitor::block_connected`]: channelmonitor::ChannelMonitor::block_connected
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class WatchedOutput extends CommonBase {
	WatchedOutput(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.WatchedOutput_free(ptr); }
	}

	/**
	 * First block where the transaction output may have been spent.
	 */
	public byte[] get_block_hash() {
		byte[] ret = bindings.WatchedOutput_get_block_hash(this.ptr);
		return ret;
	}

	/**
	 * First block where the transaction output may have been spent.
	 */
	public void set_block_hash(byte[] val) {
		bindings.WatchedOutput_set_block_hash(this.ptr, val);
	}

	/**
	 * Outpoint identifying the transaction output.
	 */
	public OutPoint get_outpoint() {
		long ret = bindings.WatchedOutput_get_outpoint(this.ptr);
		OutPoint ret_hu_conv = new OutPoint(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Outpoint identifying the transaction output.
	 */
	public void set_outpoint(OutPoint val) {
		bindings.WatchedOutput_set_outpoint(this.ptr, val == null ? 0 : val.ptr & ~1);
		this.ptrs_to.add(val);
	}

	/**
	 * Spending condition of the transaction output.
	 */
	public byte[] get_script_pubkey() {
		byte[] ret = bindings.WatchedOutput_get_script_pubkey(this.ptr);
		return ret;
	}

	/**
	 * Spending condition of the transaction output.
	 */
	public void set_script_pubkey(byte[] val) {
		bindings.WatchedOutput_set_script_pubkey(this.ptr, val);
	}

	/**
	 * Constructs a new WatchedOutput given each field
	 */
	public static WatchedOutput of(byte[] block_hash_arg, OutPoint outpoint_arg, byte[] script_pubkey_arg) {
		long ret = bindings.WatchedOutput_new(block_hash_arg, outpoint_arg == null ? 0 : outpoint_arg.ptr & ~1, script_pubkey_arg);
		WatchedOutput ret_hu_conv = new WatchedOutput(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		ret_hu_conv.ptrs_to.add(outpoint_arg);
		return ret_hu_conv;
	}

	/**
	 * Creates a copy of the WatchedOutput
	 */
	public WatchedOutput clone() {
		long ret = bindings.WatchedOutput_clone(this.ptr);
		WatchedOutput ret_hu_conv = new WatchedOutput(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Checks if two WatchedOutputs contain equal inner contents.
	 */
	public long hash() {
		long ret = bindings.WatchedOutput_hash(this.ptr);
		return ret;
	}

}
