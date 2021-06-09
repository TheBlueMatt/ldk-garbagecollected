package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;

public class Result_ChannelFeaturesDecodeErrorZ extends CommonBase {
	private Result_ChannelFeaturesDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_ChannelFeaturesDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_ChannelFeaturesDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_ChannelFeaturesDecodeErrorZ_result_ok(ptr)) {
			return new Result_ChannelFeaturesDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_ChannelFeaturesDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_ChannelFeaturesDecodeErrorZ_OK extends Result_ChannelFeaturesDecodeErrorZ {
		public final ChannelFeatures res;
		private Result_ChannelFeaturesDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_ChannelFeaturesDecodeErrorZ_get_ok(ptr);
			ChannelFeatures res_hu_conv = new ChannelFeatures(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_ChannelFeaturesDecodeErrorZ_Err extends Result_ChannelFeaturesDecodeErrorZ {
		public final DecodeError err;
		private Result_ChannelFeaturesDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_ChannelFeaturesDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	/**
	 * Creates a new CResult_ChannelFeaturesDecodeErrorZ in the success state.
	 */
	public static Result_ChannelFeaturesDecodeErrorZ ok(ChannelFeatures o) {
		long ret = bindings.CResult_ChannelFeaturesDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		Result_ChannelFeaturesDecodeErrorZ ret_hu_conv = Result_ChannelFeaturesDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_ChannelFeaturesDecodeErrorZ in the error state.
	 */
	public static Result_ChannelFeaturesDecodeErrorZ err(DecodeError e) {
		long ret = bindings.CResult_ChannelFeaturesDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		Result_ChannelFeaturesDecodeErrorZ ret_hu_conv = Result_ChannelFeaturesDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

}
