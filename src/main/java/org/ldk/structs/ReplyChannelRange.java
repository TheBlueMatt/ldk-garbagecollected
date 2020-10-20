package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;

@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class ReplyChannelRange extends CommonBase {
	ReplyChannelRange(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		bindings.ReplyChannelRange_free(ptr);
	}

	public static ReplyChannelRange constructor_clone(ReplyChannelRange orig) {
		long ret = bindings.ReplyChannelRange_clone(orig == null ? 0 : orig.ptr & ~1);
		ReplyChannelRange ret_hu_conv = new ReplyChannelRange(null, ret);
		ret_hu_conv.ptrs_to.add(orig);
		return ret_hu_conv;
	}

	public byte[] get_chain_hash() {
		byte[] ret = bindings.ReplyChannelRange_get_chain_hash(this.ptr);
		return ret;
	}

	public void set_chain_hash(byte[] val) {
		bindings.ReplyChannelRange_set_chain_hash(this.ptr, val);
	}

	public int get_first_blocknum() {
		int ret = bindings.ReplyChannelRange_get_first_blocknum(this.ptr);
		return ret;
	}

	public void set_first_blocknum(int val) {
		bindings.ReplyChannelRange_set_first_blocknum(this.ptr, val);
	}

	public int get_number_of_blocks() {
		int ret = bindings.ReplyChannelRange_get_number_of_blocks(this.ptr);
		return ret;
	}

	public void set_number_of_blocks(int val) {
		bindings.ReplyChannelRange_set_number_of_blocks(this.ptr, val);
	}

	public boolean get_full_information() {
		boolean ret = bindings.ReplyChannelRange_get_full_information(this.ptr);
		return ret;
	}

	public void set_full_information(boolean val) {
		bindings.ReplyChannelRange_set_full_information(this.ptr, val);
	}

	public void set_short_channel_ids(long[] val) {
		bindings.ReplyChannelRange_set_short_channel_ids(this.ptr, val);
	}

	public static ReplyChannelRange constructor_new(byte[] chain_hash_arg, int first_blocknum_arg, int number_of_blocks_arg, boolean full_information_arg, long[] short_channel_ids_arg) {
		long ret = bindings.ReplyChannelRange_new(chain_hash_arg, first_blocknum_arg, number_of_blocks_arg, full_information_arg, short_channel_ids_arg);
		ReplyChannelRange ret_hu_conv = new ReplyChannelRange(null, ret);
		return ret_hu_conv;
	}

	public static ReplyChannelRange constructor_read(byte[] ser) {
		long ret = bindings.ReplyChannelRange_read(ser);
		ReplyChannelRange ret_hu_conv = new ReplyChannelRange(null, ret);
		return ret_hu_conv;
	}

	public byte[] write(ReplyChannelRange obj) {
		byte[] ret = bindings.ReplyChannelRange_write(obj == null ? 0 : obj.ptr & ~1);
		this.ptrs_to.add(obj);
		return ret;
	}

}
