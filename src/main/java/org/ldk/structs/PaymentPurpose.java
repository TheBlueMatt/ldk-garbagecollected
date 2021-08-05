package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;


/**
 * Some information provided on receipt of payment depends on whether the payment received is a
 * spontaneous payment or a \"conventional\" lightning payment that's paying an invoice.
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class PaymentPurpose extends CommonBase {
	private PaymentPurpose(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.PaymentPurpose_free(ptr); }
	}
	static PaymentPurpose constr_from_ptr(long ptr) {
		bindings.LDKPaymentPurpose raw_val = bindings.LDKPaymentPurpose_ref_from_ptr(ptr);
		if (raw_val.getClass() == bindings.LDKPaymentPurpose.InvoicePayment.class) {
			return new InvoicePayment(ptr, (bindings.LDKPaymentPurpose.InvoicePayment)raw_val);
		}
		if (raw_val.getClass() == bindings.LDKPaymentPurpose.SpontaneousPayment.class) {
			return new SpontaneousPayment(ptr, (bindings.LDKPaymentPurpose.SpontaneousPayment)raw_val);
		}
		assert false; return null; // Unreachable without extending the (internal) bindings interface
	}

	public final static class InvoicePayment extends PaymentPurpose {
		public final byte[] payment_preimage;
		public final byte[] payment_secret;
		public final long user_payment_id;
		private InvoicePayment(long ptr, bindings.LDKPaymentPurpose.InvoicePayment obj) {
			super(null, ptr);
			this.payment_preimage = obj.payment_preimage;
			this.payment_secret = obj.payment_secret;
			this.user_payment_id = obj.user_payment_id;
		}
	}
	public final static class SpontaneousPayment extends PaymentPurpose {
		public final byte[] spontaneous_payment;
		private SpontaneousPayment(long ptr, bindings.LDKPaymentPurpose.SpontaneousPayment obj) {
			super(null, ptr);
			this.spontaneous_payment = obj.spontaneous_payment;
		}
	}
	/**
	 * Creates a copy of the PaymentPurpose
	 */
	public PaymentPurpose clone() {
		long ret = bindings.PaymentPurpose_clone(this.ptr);
		if (ret < 1024) { return null; }
		PaymentPurpose ret_hu_conv = PaymentPurpose.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

}
