package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;


/**
 * A pong message to be sent or received from a peer
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class Pong extends CommonBase {
	Pong(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.Pong_free(ptr); }
	}

	/**
	 * The pong packet size.
	 * This field is not sent on the wire. byteslen zeros are sent.
	 */
	public short get_byteslen() {
		short ret = bindings.Pong_get_byteslen(this.ptr);
		return ret;
	}

	/**
	 * The pong packet size.
	 * This field is not sent on the wire. byteslen zeros are sent.
	 */
	public void set_byteslen(short val) {
		bindings.Pong_set_byteslen(this.ptr, val);
	}

	/**
	 * Constructs a new Pong given each field
	 */
	public static Pong of(short byteslen_arg) {
		long ret = bindings.Pong_new(byteslen_arg);
		Pong ret_hu_conv = new Pong(null, ret);
		ret_hu_conv.ptrs_to.add(ret_hu_conv);
		return ret_hu_conv;
	}

	/**
	 * Creates a copy of the Pong
	 */
	public Pong clone() {
		long ret = bindings.Pong_clone(this.ptr);
		Pong ret_hu_conv = new Pong(null, ret);
		ret_hu_conv.ptrs_to.add(this);
		return ret_hu_conv;
	}

	/**
	 * Serialize the Pong object into a byte array which can be read by Pong_read
	 */
	public byte[] write() {
		byte[] ret = bindings.Pong_write(this.ptr);
		return ret;
	}

	/**
	 * Read a Pong from a byte array, created by Pong_write
	 */
	public static Result_PongDecodeErrorZ read(byte[] ser) {
		long ret = bindings.Pong_read(ser);
		Result_PongDecodeErrorZ ret_hu_conv = Result_PongDecodeErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
