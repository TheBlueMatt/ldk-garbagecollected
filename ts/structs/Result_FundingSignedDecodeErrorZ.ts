
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location

public class Result_FundingSignedDecodeErrorZ extends CommonBase {
	private Result_FundingSignedDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_FundingSignedDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_FundingSignedDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_FundingSignedDecodeErrorZ_result_ok(ptr)) {
			return new Result_FundingSignedDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_FundingSignedDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_FundingSignedDecodeErrorZ_OK extends Result_FundingSignedDecodeErrorZ {
		public final FundingSigned res;
		private Result_FundingSignedDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number res = bindings.LDKCResult_FundingSignedDecodeErrorZ_get_ok(ptr);
			const res_hu_conv: FundingSigned = new FundingSigned(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_FundingSignedDecodeErrorZ_Err extends Result_FundingSignedDecodeErrorZ {
		public final DecodeError err;
		private Result_FundingSignedDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number err = bindings.LDKCResult_FundingSignedDecodeErrorZ_get_err(ptr);
			const err_hu_conv: DecodeError = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	public static Result_FundingSignedDecodeErrorZ constructor__ok(FundingSigned o) {
		number ret = bindings.CResult_FundingSignedDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		Result_FundingSignedDecodeErrorZ ret_hu_conv = Result_FundingSignedDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	public static Result_FundingSignedDecodeErrorZ constructor__err(DecodeError e) {
		number ret = bindings.CResult_FundingSignedDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		Result_FundingSignedDecodeErrorZ ret_hu_conv = Result_FundingSignedDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	public Result_FundingSignedDecodeErrorZ _clone() {
		number ret = bindings.CResult_FundingSignedDecodeErrorZ_clone(this.ptr);
		Result_FundingSignedDecodeErrorZ ret_hu_conv = Result_FundingSignedDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
