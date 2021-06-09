package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;


/**
 * Recoverable signature
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class InvoiceSignature extends CommonBase {
	InvoiceSignature(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.InvoiceSignature_free(ptr); }
	}

	/**
	 * Checks if two InvoiceSignatures contain equal inner contents.
	 * This ignores pointers and is_owned flags and looks at the values in fields.
	 * Two objects with NULL inner values will be considered "equal" here.
	 */
	public boolean eq(InvoiceSignature b) {
		boolean ret = bindings.InvoiceSignature_eq(this.ptr, b == null ? 0 : b.ptr & ~1);
		this.ptrs_to.add(b);
		return ret;
	}

	/**
	 * Creates a copy of the InvoiceSignature
	 */
	public InvoiceSignature clone() {
		long ret = bindings.InvoiceSignature_clone(this.ptr);
		InvoiceSignature ret_hu_conv = new InvoiceSignature(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

}
