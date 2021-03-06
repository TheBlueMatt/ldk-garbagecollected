
import CommonBase from './CommonBase';
import * as bindings from '../bindings' // TODO: figure out location

public class Result_CommitmentTransactionDecodeErrorZ extends CommonBase {
	private Result_CommitmentTransactionDecodeErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_CommitmentTransactionDecodeErrorZ_free(ptr); } super.finalize();
	}

	static Result_CommitmentTransactionDecodeErrorZ constr_from_ptr(long ptr) {
		if (bindings.LDKCResult_CommitmentTransactionDecodeErrorZ_result_ok(ptr)) {
			return new Result_CommitmentTransactionDecodeErrorZ_OK(null, ptr);
		} else {
			return new Result_CommitmentTransactionDecodeErrorZ_Err(null, ptr);
		}
	}
	public static final class Result_CommitmentTransactionDecodeErrorZ_OK extends Result_CommitmentTransactionDecodeErrorZ {
		public final CommitmentTransaction res;
		private Result_CommitmentTransactionDecodeErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number res = bindings.LDKCResult_CommitmentTransactionDecodeErrorZ_get_ok(ptr);
			const res_hu_conv: CommitmentTransaction = new CommitmentTransaction(null, res);
			res_hu_conv.ptrs_to.add(this);
			this.res = res_hu_conv;
		}
	}

	public static final class Result_CommitmentTransactionDecodeErrorZ_Err extends Result_CommitmentTransactionDecodeErrorZ {
		public final DecodeError err;
		private Result_CommitmentTransactionDecodeErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			number err = bindings.LDKCResult_CommitmentTransactionDecodeErrorZ_get_err(ptr);
			const err_hu_conv: DecodeError = new DecodeError(null, err);
			err_hu_conv.ptrs_to.add(this);
			this.err = err_hu_conv;
		}
	}

	public static Result_CommitmentTransactionDecodeErrorZ constructor__ok(CommitmentTransaction o) {
		number ret = bindings.CResult_CommitmentTransactionDecodeErrorZ_ok(o == null ? 0 : o.ptr & ~1);
		Result_CommitmentTransactionDecodeErrorZ ret_hu_conv = Result_CommitmentTransactionDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(o);
		return ret_hu_conv;
	}

	public static Result_CommitmentTransactionDecodeErrorZ constructor__err(DecodeError e) {
		number ret = bindings.CResult_CommitmentTransactionDecodeErrorZ_err(e == null ? 0 : e.ptr & ~1);
		Result_CommitmentTransactionDecodeErrorZ ret_hu_conv = Result_CommitmentTransactionDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(e);
		return ret_hu_conv;
	}

	public Result_CommitmentTransactionDecodeErrorZ _clone() {
		number ret = bindings.CResult_CommitmentTransactionDecodeErrorZ_clone(this.ptr);
		Result_CommitmentTransactionDecodeErrorZ ret_hu_conv = Result_CommitmentTransactionDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
