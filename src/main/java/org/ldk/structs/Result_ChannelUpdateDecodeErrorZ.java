package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;

public class Result_ChannelUpdateDecodeErrorZ extends CommonBase {
	private Result_ChannelUpdateDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_ChannelUpdateDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_ChannelUpdateDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_ChannelUpdateDecodeErrorZ_result_ok(ptr)) {
			return new Result_ChannelUpdateDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_ChannelUpdateDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_ChannelUpdateDecodeErrorZ_OK extends Result_ChannelUpdateDecodeErrorZ {
		public final ChannelUpdate res;
		private Result_ChannelUpdateDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_ChannelUpdateDecodeErrorZ_get_ok(ptr);
			ChannelUpdate res_hu_conv = new ChannelUpdate(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
		public Result_ChannelUpdateDecodeErrorZ_OK(ChannelUpdate res) {
			this(null, bindings.CResult_ChannelUpdateDecodeErrorZ_ok(res == null ? 0 : res.ptr & ~1));
			this.ptrs_to.add(res);
		}
	}

	public static final class Result_ChannelUpdateDecodeErrorZ_Err extends Result_ChannelUpdateDecodeErrorZ {
		public final DecodeError err;
		private Result_ChannelUpdateDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_ChannelUpdateDecodeErrorZ_get_err(ptr);
			DecodeError err_hu_conv = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
		public Result_ChannelUpdateDecodeErrorZ_Err(DecodeError err) {
			this(null, bindings.CResult_ChannelUpdateDecodeErrorZ_err(err == null ? 0 : err.ptr & ~1));
			this.ptrs_to.add(err);
		}
	}
}
