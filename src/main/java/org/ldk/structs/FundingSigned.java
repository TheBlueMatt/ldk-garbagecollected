package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;

@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class FundingSigned extends CommonBase {
	FundingSigned(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.FundingSigned_free(ptr); }
	}

	public static FundingSigned constructor_clone(FundingSigned orig) {
		long ret = bindings.FundingSigned_clone(orig == null ? 0 : orig.ptr & ~1);
		FundingSigned ret_hu_conv = new FundingSigned(null, ret);
		ret_hu_conv.ptrs_to.add(orig);
		return ret_hu_conv;
	}

	public byte[] get_channel_id() {
		byte[] ret = bindings.FundingSigned_get_channel_id(this.ptr);
		return ret;
	}

	public void set_channel_id(byte[] val) {
		bindings.FundingSigned_set_channel_id(this.ptr, val);
	}

	public byte[] get_signature() {
		byte[] ret = bindings.FundingSigned_get_signature(this.ptr);
		return ret;
	}

	public void set_signature(byte[] val) {
		bindings.FundingSigned_set_signature(this.ptr, val);
	}

	public static FundingSigned constructor_new(byte[] channel_id_arg, byte[] signature_arg) {
		long ret = bindings.FundingSigned_new(channel_id_arg, signature_arg);
		FundingSigned ret_hu_conv = new FundingSigned(null, ret);
		return ret_hu_conv;
	}

	public byte[] write(FundingSigned obj) {
		byte[] ret = bindings.FundingSigned_write(obj == null ? 0 : obj.ptr & ~1);
		this.ptrs_to.add(obj);
		return ret;
	}

	public static FundingSigned constructor_read(byte[] ser) {
		long ret = bindings.FundingSigned_read(ser);
		FundingSigned ret_hu_conv = new FundingSigned(null, ret);
		return ret_hu_conv;
	}

}
