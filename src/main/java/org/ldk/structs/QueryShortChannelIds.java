package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;

@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class QueryShortChannelIds extends CommonBase {
	QueryShortChannelIds(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		bindings.QueryShortChannelIds_free(ptr);
	}

	public static QueryShortChannelIds constructor_clone(QueryShortChannelIds orig) {
		long ret = bindings.QueryShortChannelIds_clone(orig == null ? 0 : orig.ptr & ~1);
		QueryShortChannelIds ret_hu_conv = new QueryShortChannelIds(null, ret);
		ret_hu_conv.ptrs_to.add(orig);
		return ret_hu_conv;
	}

	public byte[] get_chain_hash() {
		byte[] ret = bindings.QueryShortChannelIds_get_chain_hash(this.ptr);
		return ret;
	}

	public void set_chain_hash(byte[] val) {
		bindings.QueryShortChannelIds_set_chain_hash(this.ptr, val);
	}

	public void set_short_channel_ids(long[] val) {
		bindings.QueryShortChannelIds_set_short_channel_ids(this.ptr, val);
	}

	public static QueryShortChannelIds constructor_new(byte[] chain_hash_arg, long[] short_channel_ids_arg) {
		long ret = bindings.QueryShortChannelIds_new(chain_hash_arg, short_channel_ids_arg);
		QueryShortChannelIds ret_hu_conv = new QueryShortChannelIds(null, ret);
		return ret_hu_conv;
	}

	public static QueryShortChannelIds constructor_read(byte[] ser) {
		long ret = bindings.QueryShortChannelIds_read(ser);
		QueryShortChannelIds ret_hu_conv = new QueryShortChannelIds(null, ret);
		return ret_hu_conv;
	}

	public byte[] write(QueryShortChannelIds obj) {
		byte[] ret = bindings.QueryShortChannelIds_write(obj == null ? 0 : obj.ptr & ~1);
		this.ptrs_to.add(obj);
		return ret;
	}

}
