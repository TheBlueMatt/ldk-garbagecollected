	public static Result_StringErrorZ constructor_ign(Uint8Array msg, Uint8Array sk) {
		number ret = bindings.sign(msg, sk);
		Result_StringErrorZ ret_hu_conv = Result_StringErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	public static Result_PublicKeyErrorZ constructor_ecover_pk(Uint8Array msg, String sig) {
		number ret = bindings.recover_pk(msg, sig);
		Result_PublicKeyErrorZ ret_hu_conv = Result_PublicKeyErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	public static boolean constructor_erify(Uint8Array msg, String sig, Uint8Array pk) {
		boolean ret = bindings.verify(msg, sig, pk);
		return ret;
	}

	public static Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ constructor_BlockHashChannelMonitorZ_read(Uint8Array ser, KeysInterface arg) {
		number ret = bindings.C2Tuple_BlockHashChannelMonitorZ_read(ser, arg == null ? 0 : arg.ptr);
		Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ ret_hu_conv = Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(arg);
		return ret_hu_conv;
	}

	public static Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ constructor_BlockHashChannelManagerZ_read(Uint8Array ser, KeysInterface arg_keys_manager, FeeEstimator arg_fee_estimator, Watch arg_chain_monitor, BroadcasterInterface arg_tx_broadcaster, Logger arg_logger, UserConfig arg_default_config, ChannelMonitor[] arg_channel_monitors) {
		number ret = bindings.C2Tuple_BlockHashChannelManagerZ_read(ser, bindings.ChannelManagerReadArgs_new(arg_keys_manager == null ? 0 : arg_keys_manager.ptr, arg_fee_estimator == null ? 0 : arg_fee_estimator.ptr, arg_chain_monitor == null ? 0 : arg_chain_monitor.ptr, arg_tx_broadcaster == null ? 0 : arg_tx_broadcaster.ptr, arg_logger == null ? 0 : arg_logger.ptr, arg_default_config == null ? 0 : arg_default_config.ptr & ~1, Arrays.stream(arg_channel_monitors).map(arg_channel_monitors_conv_16 -> arg_channel_monitors_conv_16 == null ? 0 : arg_channel_monitors_conv_16.ptr & ~1).toArray(number[]::new)));
		Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ ret_hu_conv = Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(arg_keys_manager);
		ret_hu_conv.ptrs_to.add(arg_fee_estimator);
		ret_hu_conv.ptrs_to.add(arg_chain_monitor);
		ret_hu_conv.ptrs_to.add(arg_tx_broadcaster);
		ret_hu_conv.ptrs_to.add(arg_logger);
		ret_hu_conv.ptrs_to.add(arg_default_config);
		/* TODO 2 ChannelMonitor  */;
		return ret_hu_conv;
	}

	public static Uint8Array constructor_uild_commitment_secret(Uint8Array commitment_seed, number idx) {
		Uint8Array ret = bindings.build_commitment_secret(commitment_seed, idx);
		return ret;
	}

	public static Result_SecretKeyErrorZ constructor_erive_private_key(Uint8Array per_commitment_point, Uint8Array base_secret) {
		number ret = bindings.derive_private_key(per_commitment_point, base_secret);
		Result_SecretKeyErrorZ ret_hu_conv = Result_SecretKeyErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	public static Result_PublicKeyErrorZ constructor_erive_public_key(Uint8Array per_commitment_point, Uint8Array base_point) {
		number ret = bindings.derive_public_key(per_commitment_point, base_point);
		Result_PublicKeyErrorZ ret_hu_conv = Result_PublicKeyErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	public static Result_SecretKeyErrorZ constructor_erive_private_revocation_key(Uint8Array per_commitment_secret, Uint8Array countersignatory_revocation_base_secret) {
		number ret = bindings.derive_private_revocation_key(per_commitment_secret, countersignatory_revocation_base_secret);
		Result_SecretKeyErrorZ ret_hu_conv = Result_SecretKeyErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	public static Result_PublicKeyErrorZ constructor_erive_public_revocation_key(Uint8Array per_commitment_point, Uint8Array countersignatory_revocation_base_point) {
		number ret = bindings.derive_public_revocation_key(per_commitment_point, countersignatory_revocation_base_point);
		Result_PublicKeyErrorZ ret_hu_conv = Result_PublicKeyErrorZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	public static Uint8Array constructor_et_revokeable_redeemscript(Uint8Array revocation_key, number contest_delay, Uint8Array broadcaster_delayed_payment_key) {
		Uint8Array ret = bindings.get_revokeable_redeemscript(revocation_key, contest_delay, broadcaster_delayed_payment_key);
		return ret;
	}

	public static Uint8Array constructor_et_htlc_redeemscript(HTLCOutputInCommitment htlc, TxCreationKeys keys) {
		Uint8Array ret = bindings.get_htlc_redeemscript(htlc == null ? 0 : htlc.ptr & ~1, keys == null ? 0 : keys.ptr & ~1);
		// this.ptrs_to.add(htlc);
		// this.ptrs_to.add(keys);
		return ret;
	}

	public static Uint8Array constructor_ake_funding_redeemscript(Uint8Array broadcaster, Uint8Array countersignatory) {
		Uint8Array ret = bindings.make_funding_redeemscript(broadcaster, countersignatory);
		return ret;
	}

	public static Uint8Array constructor_uild_htlc_transaction(Uint8Array commitment_txid, number feerate_per_kw, number contest_delay, HTLCOutputInCommitment htlc, Uint8Array broadcaster_delayed_payment_key, Uint8Array revocation_key) {
		Uint8Array ret = bindings.build_htlc_transaction(commitment_txid, feerate_per_kw, contest_delay, htlc == null ? 0 : htlc.ptr & ~1, broadcaster_delayed_payment_key, revocation_key);
		// this.ptrs_to.add(htlc);
		return ret;
	}

	public static number constructor_et_commitment_transaction_number_obscure_factor(Uint8Array broadcaster_payment_basepoint, Uint8Array countersignatory_payment_basepoint, boolean outbound_from_broadcaster) {
		number ret = bindings.get_commitment_transaction_number_obscure_factor(broadcaster_payment_basepoint, countersignatory_payment_basepoint, outbound_from_broadcaster);
		return ret;
	}

	public static Result_RouteLightningErrorZ constructor_et_route(Uint8Array our_node_id, NetworkGraph network, Uint8Array payee, InvoiceFeatures payee_features, ChannelDetails[] first_hops, RouteHintHop[] last_hops, number final_value_msat, number final_cltv, Logger logger) {
		number ret = bindings.get_route(our_node_id, network == null ? 0 : network.ptr & ~1, payee, payee_features == null ? 0 : payee_features.ptr & ~1, Arrays.stream(first_hops).map(first_hops_conv_16 -> first_hops_conv_16 == null ? 0 : first_hops_conv_16.ptr & ~1).toArray(number[]::new), Arrays.stream(last_hops).map(last_hops_conv_14 -> last_hops_conv_14 == null ? 0 : last_hops_conv_14.ptr & ~1).toArray(number[]::new), final_value_msat, final_cltv, logger == null ? 0 : logger.ptr);
		Result_RouteLightningErrorZ ret_hu_conv = Result_RouteLightningErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(network);
		ret_hu_conv.ptrs_to.add(payee_features);
		/* TODO 2 ChannelDetails  */;
		/* TODO 2 RouteHintHop  */;
		ret_hu_conv.ptrs_to.add(logger);
		return ret_hu_conv;
	}

	public static Result_NoneErrorZ constructor_ilesystemPersister_persist_manager(String data_dir, ChannelManager manager) {
		number ret = bindings.FilesystemPersister_persist_manager(data_dir, manager == null ? 0 : manager.ptr & ~1);
		Result_NoneErrorZ ret_hu_conv = Result_NoneErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(manager);
		return ret_hu_conv;
	}

	public static Result_InvoiceSignOrCreationErrorZ constructor_reate_invoice_from_channelmanager(ChannelManager channelmanager, KeysInterface keys_manager, Currency network, Option_u64Z amt_msat, String description) {
		number ret = bindings.create_invoice_from_channelmanager(channelmanager == null ? 0 : channelmanager.ptr & ~1, keys_manager == null ? 0 : keys_manager.ptr, network, amt_msat.ptr, description);
		Result_InvoiceSignOrCreationErrorZ ret_hu_conv = Result_InvoiceSignOrCreationErrorZ.constr_from_ptr(ret);
		ret_hu_conv.ptrs_to.add(channelmanager);
		ret_hu_conv.ptrs_to.add(keys_manager);
		return ret_hu_conv;
	}

