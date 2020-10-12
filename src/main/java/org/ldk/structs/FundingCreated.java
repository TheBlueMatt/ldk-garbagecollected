package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;

public class FundingCreated extends CommonBase {
	FundingCreated(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		bindings.FundingCreated_free(ptr); super.finalize();
	}

	public FundingCreated(FundingCreated orig) {
		super(bindings.FundingCreated_clone(orig.ptr & ~1));
		this.ptrs_to.add(orig);
	}

	public byte[] get_temporary_channel_id(FundingCreated this_ptr) {
		byte[] ret = bindings.FundingCreated_get_temporary_channel_id(this_ptr.ptr & ~1);
		this.ptrs_to.add(this_ptr);
		return ret;
	}

	public void set_temporary_channel_id(FundingCreated this_ptr, byte[] val) {
		bindings.FundingCreated_set_temporary_channel_id(this_ptr.ptr & ~1, val);
		this.ptrs_to.add(this_ptr);
	}

	public byte[] get_funding_txid(FundingCreated this_ptr) {
		byte[] ret = bindings.FundingCreated_get_funding_txid(this_ptr.ptr & ~1);
		this.ptrs_to.add(this_ptr);
		return ret;
	}

	public void set_funding_txid(FundingCreated this_ptr, byte[] val) {
		bindings.FundingCreated_set_funding_txid(this_ptr.ptr & ~1, val);
		this.ptrs_to.add(this_ptr);
	}

	public short get_funding_output_index(FundingCreated this_ptr) {
		short ret = bindings.FundingCreated_get_funding_output_index(this_ptr.ptr & ~1);
		this.ptrs_to.add(this_ptr);
		return ret;
	}

	public void set_funding_output_index(FundingCreated this_ptr, short val) {
		bindings.FundingCreated_set_funding_output_index(this_ptr.ptr & ~1, val);
		this.ptrs_to.add(this_ptr);
	}

	// Skipped FundingCreated_get_signature
	// Skipped FundingCreated_set_signature
	// Skipped FundingCreated_new
	// Skipped FundingCreated_write
	// Skipped FundingCreated_read
}