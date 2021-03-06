
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location

public class Result_OpenChannelDecodeErrorZ extends CommonBase {
	private Result_OpenChannelDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_OpenChannelDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_OpenChannelDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_OpenChannelDecodeErrorZ_result_ok(ptr)) {
			return new Result_OpenChannelDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_OpenChannelDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_OpenChannelDecodeErrorZ_OK extends Result_OpenChannelDecodeErrorZ {
		public final OpenChannel res;
		private Result_OpenChannelDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number res = bindings.LDKCResult_OpenChannelDecodeErrorZ_get_ok(ptr);
			const res_hu_conv: OpenChannel = new OpenChannel(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_OpenChannelDecodeErrorZ_Err extends Result_OpenChannelDecodeErrorZ {
		public final DecodeError err;
		private Result_OpenChannelDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number err = bindings.LDKCResult_OpenChannelDecodeErrorZ_get_err(ptr);
			const err_hu_conv: DecodeError = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	public static Result_OpenChannelDecodeErrorZ constructor__ok(OpenChannel o) {
		number ret = bindings.CResult_OpenChannelDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		Result_OpenChannelDecodeErrorZ ret_hu_conv = Result_OpenChannelDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	public static Result_OpenChannelDecodeErrorZ constructor__err(DecodeError e) {
		number ret = bindings.CResult_OpenChannelDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		Result_OpenChannelDecodeErrorZ ret_hu_conv = Result_OpenChannelDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	public Result_OpenChannelDecodeErrorZ _clone() {
		number ret = bindings.CResult_OpenChannelDecodeErrorZ_clone(this.ptr);
		Result_OpenChannelDecodeErrorZ ret_hu_conv = Result_OpenChannelDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
