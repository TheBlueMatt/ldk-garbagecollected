package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;

@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class Result_RouteLightningErrorZ extends CommonBase {
	private Result_RouteLightningErrorZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		bindings.CResult_RouteLightningErrorZ_free(ptr); super.finalize();
	}

	public static final class Result_RouteLightningErrorZ_OK extends Result_RouteLightningErrorZ {
		public Route res;
		private Result_RouteLightningErrorZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.LDKCResult_RouteLightningErrorZ_get_ok(ptr);
			Route res_hu_conv = new Route(null, res);
			this.res = res_hu_conv;
		}

	}
	public static final class Result_RouteLightningErrorZ_Err extends Result_RouteLightningErrorZ {
		public LightningError err;
		private Result_RouteLightningErrorZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long err = bindings.LDKCResult_RouteLightningErrorZ_get_err(ptr);
			LightningError err_hu_conv = new LightningError(null, err);
			this.err = err_hu_conv;
		}
	}
}
