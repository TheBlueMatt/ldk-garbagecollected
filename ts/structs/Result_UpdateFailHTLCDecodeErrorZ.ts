
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location

public class Result_UpdateFailHTLCDecodeErrorZ extends CommonBase {
	private Result_UpdateFailHTLCDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_UpdateFailHTLCDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_UpdateFailHTLCDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_UpdateFailHTLCDecodeErrorZ_result_ok(ptr)) {
			return new Result_UpdateFailHTLCDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_UpdateFailHTLCDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_UpdateFailHTLCDecodeErrorZ_OK extends Result_UpdateFailHTLCDecodeErrorZ {
		public final UpdateFailHTLC res;
		private Result_UpdateFailHTLCDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number res = bindings.LDKCResult_UpdateFailHTLCDecodeErrorZ_get_ok(ptr);
			const res_hu_conv: UpdateFailHTLC = new UpdateFailHTLC(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_UpdateFailHTLCDecodeErrorZ_Err extends Result_UpdateFailHTLCDecodeErrorZ {
		public final DecodeError err;
		private Result_UpdateFailHTLCDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number err = bindings.LDKCResult_UpdateFailHTLCDecodeErrorZ_get_err(ptr);
			const err_hu_conv: DecodeError = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	public static Result_UpdateFailHTLCDecodeErrorZ constructor__ok(UpdateFailHTLC o) {
		number ret = bindings.CResult_UpdateFailHTLCDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		Result_UpdateFailHTLCDecodeErrorZ ret_hu_conv = Result_UpdateFailHTLCDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	public static Result_UpdateFailHTLCDecodeErrorZ constructor__err(DecodeError e) {
		number ret = bindings.CResult_UpdateFailHTLCDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		Result_UpdateFailHTLCDecodeErrorZ ret_hu_conv = Result_UpdateFailHTLCDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	public Result_UpdateFailHTLCDecodeErrorZ _clone() {
		number ret = bindings.CResult_UpdateFailHTLCDecodeErrorZ_clone(this.ptr);
		Result_UpdateFailHTLCDecodeErrorZ ret_hu_conv = Result_UpdateFailHTLCDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
