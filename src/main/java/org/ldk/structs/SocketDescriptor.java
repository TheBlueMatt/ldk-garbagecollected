package org.ldk.structs;

import org.ldk.impl.bindings;

import org.ldk.enums.*;

public class SocketDescriptor extends CommonBase {
	SocketDescriptor(Object _dummy, long ptr) { super(ptr); }
	public SocketDescriptor(bindings.LDKSocketDescriptor arg) {
		super(bindings.LDKSocketDescriptor_new(arg));
		this.ptrs_to.add(arg);
	}
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		bindings.SocketDescriptor_free(ptr); super.finalize();
	}

	// Skipped SocketDescriptor_call_send_data
	public void call_disconnect_socket() {
		bindings.SocketDescriptor_call_disconnect_socket(this.ptr);
	}

	public long call_hash() {
		long ret = bindings.SocketDescriptor_call_hash(this.ptr);
		return ret;
	}

}