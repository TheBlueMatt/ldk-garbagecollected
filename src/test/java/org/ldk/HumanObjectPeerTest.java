package org.ldk;

import org.bitcoinj.core.*;
import org.bitcoinj.script.Script;
import org.junit.jupiter.api.Test;
import org.ldk.batteries.ChannelManagerConstructor;
import org.ldk.batteries.NioPeerHandler;
import org.ldk.enums.Currency;
import org.ldk.enums.Network;
import org.ldk.impl.bindings;
import org.ldk.structs.*;
import org.ldk.util.TwoTuple;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.util.*;

class HumanObjectPeerTestInstance {
    private final boolean nice_close;
    private final boolean use_km_wrapper;
    private final boolean use_manual_watch;
    private final boolean reload_peers;
    private final boolean break_cross_peer_refs;
    private final boolean use_nio_peer_handler;
    private final boolean use_filter;
    private final boolean use_chan_manager_constructor;

    HumanObjectPeerTestInstance(boolean nice_close, boolean use_km_wrapper, boolean use_manual_watch, boolean reload_peers, boolean break_cross_peer_refs, boolean use_nio_peer_handler, boolean use_filter, boolean use_chan_manager_constructor) {
        this.nice_close = nice_close;
        this.use_km_wrapper = use_km_wrapper;
        this.use_manual_watch = use_manual_watch;
        this.reload_peers = reload_peers;
        this.break_cross_peer_refs = break_cross_peer_refs;
        this.use_nio_peer_handler = use_nio_peer_handler;
        this.use_filter = use_filter;
        this.use_chan_manager_constructor = use_chan_manager_constructor;
    }

    class Peer {
        KeysInterface manual_keysif(KeysInterface underlying_if) {
            return KeysInterface.new_impl(new KeysInterface.KeysInterfaceInterface() {
                @Override public byte[] get_node_secret() { return underlying_if.get_node_secret(); }
                @Override public byte[] get_destination_script() { return underlying_if.get_destination_script(); }
                @Override public byte[] get_shutdown_pubkey() { return underlying_if.get_shutdown_pubkey(); }

                @Override
                public Sign get_channel_signer(boolean inbound, long channel_value_satoshis) {
                    Sign underlying_ck = underlying_if.get_channel_signer(inbound, channel_value_satoshis);
                    // TODO: Expose the underlying signer from a Sign
                    /*BaseSign.BaseSignInterface si = new BaseSign.BaseSignInterface() {
                        @Override
                        public byte[] get_per_commitment_point(long idx) {
                            return underlying_ck.get_per_commitment_point(idx);
                        }

                        @Override
                        public byte[] release_commitment_secret(long idx) {
                            return underlying_ck.release_commitment_secret(idx);
                        }

                        @Override
                        public byte[] channel_keys_id() {
                            return new byte[32];
                        }

                        @Override
                        public Result_C2Tuple_SignatureCVec_SignatureZZNoneZ sign_counterparty_commitment(CommitmentTransaction commitment_tx) {
                            return underlying_ck.sign_counterparty_commitment(commitment_tx);
                        }

                        @Override
                        public Result_C2Tuple_SignatureCVec_SignatureZZNoneZ sign_holder_commitment_and_htlcs(HolderCommitmentTransaction holder_commitment_tx) {
                            return underlying_ck.sign_holder_commitment_and_htlcs(holder_commitment_tx);
                        }

                        @Override
                        public Result_SignatureNoneZ sign_justice_transaction(byte[] justice_tx, long input, long amount, byte[] per_commitment_key, HTLCOutputInCommitment htlc) {
                            return underlying_ck.sign_justice_transaction(justice_tx, input, amount, per_commitment_key, htlc);
                        }

                        @Override
                        public Result_SignatureNoneZ sign_counterparty_htlc_transaction(byte[] htlc_tx, long input, long amount, byte[] per_commitment_point, HTLCOutputInCommitment htlc) {
                            return underlying_ck.sign_counterparty_htlc_transaction(htlc_tx, input, amount, per_commitment_point, htlc);
                        }

                        @Override
                        public Result_SignatureNoneZ sign_closing_transaction(byte[] closing_tx) {
                            return underlying_ck.sign_closing_transaction(closing_tx);
                        }

                        @Override
                        public Result_SignatureNoneZ sign_channel_announcement(UnsignedChannelAnnouncement msg) {
                            return underlying_ck.sign_channel_announcement(msg);
                        }

                        @Override
                        public void ready_channel(ChannelTransactionParameters params) {
                            underlying_ck.ready_channel(params);
                        }

                        @Override
                        public byte[] write() {
                            return underlying_ck.write();
                        }
                    };*/
                    //Sign resp = Sign.new_impl(si, underlying_ck.get_pubkeys());
                    //must_free_objs.add(new WeakReference<>(si));
                    //must_free_objs.add(new WeakReference<>(resp));
                    must_free_objs.add(new WeakReference<>(underlying_ck));
                    //return resp;
                    return underlying_ck;
                }

                @Override
                public byte[] get_secure_random_bytes() {
                    return underlying_if.get_secure_random_bytes();
                }

                @Override
                public Result_SignDecodeErrorZ read_chan_signer(byte[] reader) {
                    return underlying_if.read_chan_signer(reader);
                }

                @Override
                public Result_RecoverableSignatureNoneZ sign_invoice(byte[] invoice_preimage) {
                    return underlying_if.sign_invoice(invoice_preimage);
                }
            });
        }

        Watch get_manual_watch() {
            Watch.WatchInterface watch_impl = new Watch.WatchInterface() {
                public Result_NoneChannelMonitorUpdateErrZ watch_channel(OutPoint funding_txo, ChannelMonitor monitor) {
                    synchronized (monitors) {
                        assert monitors.put(Arrays.toString(funding_txo.get_txid()), monitor) == null;
                    }
                    return Result_NoneChannelMonitorUpdateErrZ.ok();
                }

                public Result_NoneChannelMonitorUpdateErrZ update_channel(OutPoint funding_txo, ChannelMonitorUpdate update) {
                    synchronized (monitors) {
                        String txid = Arrays.toString(funding_txo.get_txid());
                        assert monitors.containsKey(txid);
                        Result_NoneMonitorUpdateErrorZ update_res = monitors.get(txid).update_monitor(update, tx_broadcaster, fee_estimator, logger);
                        assert update_res instanceof Result_NoneMonitorUpdateErrorZ.Result_NoneMonitorUpdateErrorZ_OK;
                    }
                    return Result_NoneChannelMonitorUpdateErrZ.ok();
                }

                @Override
                public MonitorEvent[] release_pending_monitor_events() {
                    synchronized (monitors) {
                        assert monitors.size() <= 1;
                        for (ChannelMonitor mon : monitors.values()) {
                            return mon.get_and_clear_pending_monitor_events();
                        }
                    }
                    return new MonitorEvent[0];
                }
            };
            Watch watch = Watch.new_impl(watch_impl);
            must_free_objs.add(new WeakReference<>(watch_impl));
            must_free_objs.add(new WeakReference<>(watch));
            return watch;
        }

        NioPeerHandler nio_peer_handler;
        short nio_port;
        final byte seed;
        final Logger logger;
        final FeeEstimator fee_estimator;
        final BroadcasterInterface tx_broadcaster;
        final KeysManager explicit_keys_manager;
        final KeysInterface keys_interface;
        final ChainMonitor chain_monitor;
        final NetGraphMsgHandler router;
        final Watch chain_watch;
        final HashSet<String> filter_additions;
        final Filter filter;
        ChannelManager chan_manager;
        PeerManager peer_manager;
        final HashMap<String, ChannelMonitor> monitors; // Wow I forgot just how terrible Java is - we can't put a byte array here.
        byte[] node_id;
        final LinkedList<byte[]> broadcast_set = new LinkedList<>();
        final LinkedList<Event> pending_manager_events = new LinkedList<>();
        ChannelManagerConstructor constructor = null;
        GcCheck obj = new GcCheck();

        private TwoTuple<OutPoint, byte[]> test_mon_roundtrip(ChannelMonitor mon) {
            // Because get_funding_txo() returns an OutPoint in a tuple that is a reference to an OutPoint inside the
            // ChannelMonitor, its a good test to ensure that the OutPoint isn't freed (or is cloned) before the
            // ChannelMonitor is. This used to be broken.
            Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ roundtrip_monitor = UtilMethods.BlockHashChannelMonitorZ_read(mon.write(), keys_interface);
            assert roundtrip_monitor instanceof Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ.Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ_OK;
            TwoTuple<OutPoint, byte[]> funding_txo = ((Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ.Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ_OK) roundtrip_monitor).res.b.get_funding_txo();
            System.gc(); System.runFinalization(); // Give the GC a chance to run.
            return funding_txo;
        }

        private Peer(Object _dummy, byte seed) {
            logger = Logger.new_impl((String arg) -> System.out.println(seed + ": " + arg));
            fee_estimator = FeeEstimator.new_impl((confirmation_target -> 253));
            tx_broadcaster = BroadcasterInterface.new_impl(tx -> {
                broadcast_set.add(tx);
            });
            monitors = new HashMap<>();
            this.seed = seed;
            Persist persister = Persist.new_impl(new Persist.PersistInterface() {
                @Override
                public Result_NoneChannelMonitorUpdateErrZ persist_new_channel(OutPoint id, ChannelMonitor data) {
                    synchronized (monitors) {
                        String key = Arrays.toString(id.to_channel_id());
                        assert monitors.put(key, data) == null;
                        TwoTuple<OutPoint, byte[]> res = test_mon_roundtrip(data);
                        assert Arrays.equals(res.a.get_txid(), id.get_txid());
                        assert res.a.get_index() == id.get_index();
                    }
                    return Result_NoneChannelMonitorUpdateErrZ.ok();
                }

                @Override
                public Result_NoneChannelMonitorUpdateErrZ update_persisted_channel(OutPoint id, ChannelMonitorUpdate update, ChannelMonitor data) {
                    synchronized (monitors) {
                        String key = Arrays.toString(id.to_channel_id());
                        assert monitors.put(key, data) != null;
                        TwoTuple<OutPoint, byte[]> res = test_mon_roundtrip(data);
                        assert Arrays.equals(res.a.get_txid(), id.get_txid());
                        assert res.a.get_index() == id.get_index();
                    }
                    return Result_NoneChannelMonitorUpdateErrZ.ok();
                }
            });

            filter_additions = new HashSet<>();
            if (use_filter) {
                this.filter = Filter.new_impl(new Filter.FilterInterface() {
                    @Override public void register_tx(byte[] txid, byte[] script_pubkey) {
                        filter_additions.add(Arrays.toString(txid));
                    }
                    @Override public Option_C2Tuple_usizeTransactionZZ register_output(WatchedOutput output) {
                        filter_additions.add(Arrays.toString(output.get_outpoint().get_txid()) + ":" + output.get_outpoint().get_index());
                        return Option_C2Tuple_usizeTransactionZZ.none();
                    }
                });
            } else {
                this.filter = null;
            }

            if (use_manual_watch) {
                chain_watch = get_manual_watch();
                chain_monitor = null;
            } else {
                chain_monitor = ChainMonitor.of(filter, tx_broadcaster, logger, fee_estimator, persister);
                chain_watch = chain_monitor.as_Watch();
            }

            byte[] key_seed = new byte[32];
            for (byte i = 0; i < 32; i++) {
                key_seed[i] = (byte) (i ^ seed);
            }
            KeysManager keys = KeysManager.of(key_seed, System.currentTimeMillis() / 1000, (int) (System.currentTimeMillis() * 1000));
            if (use_km_wrapper) {
                this.keys_interface = manual_keysif(keys.as_KeysInterface());
                this.explicit_keys_manager = null;
            } else {
                this.keys_interface = keys.as_KeysInterface();
                this.explicit_keys_manager = keys;
            }
            this.router = NetGraphMsgHandler.of(new byte[32], null, logger);
        }
        private void bind_nio() {
            if (!use_nio_peer_handler) return;
            if (use_chan_manager_constructor) {
                this.nio_peer_handler = this.constructor.nio_peer_handler;
            } else {
                try { this.nio_peer_handler = new NioPeerHandler(peer_manager); } catch (IOException e) { assert false; }
            }
            for (short i = 10_000; true; i++) {
                try {
                    nio_peer_handler.bind_listener(new InetSocketAddress("127.0.0.1", i));
                    nio_port = i;
                    break;
                } catch (IOException e) { assert i < 10_500; }
            }
        }
        Peer(byte seed) {
            this(null, seed);
            if (use_chan_manager_constructor) {
                this.constructor = new ChannelManagerConstructor(Network.LDKNetwork_Bitcoin, UserConfig.with_default(), new byte[32], 0,
                        this.keys_interface, this.fee_estimator, this.chain_monitor, this.router, this.tx_broadcaster, this.logger);
                constructor.chain_sync_completed(new ChannelManagerConstructor.ChannelManagerPersister() {
                    @Override public void handle_event(Event event) {
                        synchronized (pending_manager_events) {
                            pending_manager_events.add(event);
                            pending_manager_events.notifyAll();
                        }
                    }
                    @Override public void persist_manager(byte[] channel_manager_bytes) { }
                });
                this.chan_manager = constructor.channel_manager;
                this.peer_manager = constructor.peer_manager;
                must_free_objs.add(new WeakReference<>(this.chan_manager));
            } else {
                ChainParameters params = ChainParameters.of(Network.LDKNetwork_Bitcoin, BestBlock.of(new byte[32], 0));
                this.chan_manager = ChannelManager.of(this.fee_estimator, chain_watch, tx_broadcaster, logger, this.keys_interface, UserConfig.with_default(), params);
                byte[] random_data = keys_interface.get_secure_random_bytes();
                this.peer_manager = PeerManager.of(chan_manager.as_ChannelMessageHandler(), router.as_RoutingMessageHandler(), keys_interface.get_node_secret(), random_data, logger);
            }

            this.node_id = chan_manager.get_our_node_id();
            bind_nio();
            System.gc();
        }

        Object ptr_to;
        Peer(Peer orig) {
            this(null, orig.seed);
            if (use_chan_manager_constructor) {
                byte[][] monitors = {orig.monitors.values().stream().iterator().next().write()};
                byte[] serialized = orig.chan_manager.write();
                try {
                    this.constructor = new ChannelManagerConstructor(serialized, monitors, this.keys_interface,
                            this.fee_estimator, this.chain_monitor, this.filter, this.router, this.tx_broadcaster, this.logger);
                    constructor.chain_sync_completed(new ChannelManagerConstructor.ChannelManagerPersister() {
                        @Override public void handle_event(Event event) {
                            synchronized (pending_manager_events) {
                                pending_manager_events.add(event);
                                pending_manager_events.notifyAll();
                            }
                        }
                        @Override public void persist_manager(byte[] channel_manager_bytes) { }
                    });
                    this.chan_manager = constructor.channel_manager;
                    this.peer_manager = constructor.peer_manager;
                    must_free_objs.add(new WeakReference<>(this.chan_manager));
                    // If we are using a ChannelManagerConstructor, we may have pending events waiting on the old peer
                    // which have been removed from the ChannelManager but which we still need to handle.
                    this.pending_manager_events.addAll(orig.pending_manager_events);
                    if (!this.pending_manager_events.isEmpty()) {
                        // However, this implies cross_reload_ref_pollution
                        cross_reload_ref_pollution = true;
                    }
                } catch (ChannelManagerConstructor.InvalidSerializedDataException e) {
                    assert false;
                }
            } else {
                ChannelMonitor[] monitors = new ChannelMonitor[1];
                assert orig.monitors.size() == 1;
                if (!break_cross_peer_refs) {
                    monitors[0] = orig.monitors.values().stream().iterator().next();
                } else {
                    byte[] serialized = orig.monitors.values().stream().iterator().next().write();
                    Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ res =
                            UtilMethods.BlockHashChannelMonitorZ_read(serialized, this.keys_interface);
                    assert res instanceof Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ.Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ_OK;
                    monitors[0] = ((Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ.Result_C2Tuple_BlockHashChannelMonitorZDecodeErrorZ_OK) res).res.b;
                }
                byte[] serialized = orig.chan_manager.write();
                Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ read_res =
                        UtilMethods.BlockHashChannelManagerZ_read(serialized, this.keys_interface, this.fee_estimator, this.chain_watch, this.tx_broadcaster, this.logger, UserConfig.with_default(), monitors);
                assert read_res instanceof Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ.Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ_OK;
                this.chan_manager = ((Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ.Result_C2Tuple_BlockHashChannelManagerZDecodeErrorZ_OK) read_res).res.b;
                this.chain_watch.watch_channel(monitors[0].get_funding_txo().a, monitors[0]);
                byte[] random_data = keys_interface.get_secure_random_bytes();
                this.peer_manager = PeerManager.of(chan_manager.as_ChannelMessageHandler(), router.as_RoutingMessageHandler(), keys_interface.get_node_secret(), random_data, logger);
                if (!break_cross_peer_refs && (use_manual_watch || use_km_wrapper)) {
                    // When we pass monitors[0] into chain_watch.watch_channel we create a reference from the new Peer to a
                    // field in the old peer, preventing freeing of the original Peer until the new Peer is freed. Thus, we
                    // shouldn't bother waiting for the original to be freed later on.
                    cross_reload_ref_pollution = true;
                }
            }
            this.node_id = chan_manager.get_our_node_id();
            bind_nio();

            if (cross_reload_ref_pollution) {
                // This really, really needs to be handled at the bindings layer, but its rather complicated -
                // ChannelSigners can be cloned and passed around without java being involved, resulting in them being
                // owned by both one or more ChannelMonitors and a ChannelManager, with only one having proper pointers
                // to the ChannelSigner. Ideally, the ChannelSigner would have a global reference to the Java
                // implementation class, but that results in circular references. Instead, we need some ability to,
                // while cloning ChannelSigners, add new references in the calling Java struct (ie ChannelMonitor) to
                // the ChannelSigner.
                this.ptr_to = orig.chan_manager;
            }
        }

        TwoTuple<byte[], TwoTuple<Integer, TxOut>[]>[] connect_block(Block b, int height, long expected_monitor_update_len) {
            byte[] header = Arrays.copyOfRange(b.bitcoinSerialize(), 0, 80);
            TwoTuple<Long, byte[]>[] txn;
            if (b.hasTransactions()) {
                assert b.getTransactions().size() == 1;
                TwoTuple<Long, byte[]> txp = new TwoTuple<>((long) 0, b.getTransactions().get(0).bitcoinSerialize());
                txn = new TwoTuple[]{txp};
            } else
                txn = new TwoTuple[0];
            if (chain_monitor != null) {
                chan_manager.as_Listen().block_connected(b.bitcoinSerialize(), height);
                chain_monitor.as_Listen().block_connected(b.bitcoinSerialize(), height);
            } else {
                chan_manager.as_Confirm().transactions_confirmed(header, txn, height);
                chan_manager.as_Confirm().best_block_updated(header, height);
                // Connect manually if we aren't using a ChainMonitor and are implementing Watch ourselves
                synchronized (monitors) {
                    assert monitors.size() == 1;
                    for (ChannelMonitor mon : monitors.values()) {
                        TwoTuple<byte[], TwoTuple<Integer, TxOut>[]>[] ret = mon.block_connected(header, txn, height, tx_broadcaster, fee_estimator, logger);
                        assert ret.length == expected_monitor_update_len;
                        return ret;
                    }
                }
            }
            return null;
        }

        Event[] get_monitor_events(int expected_len) {
            if (use_chan_manager_constructor) {
                while (true) {
                    synchronized (this.pending_manager_events) {
                        if (expected_len != 0 && this.pending_manager_events.size() != expected_len) {
                            break;
                        }
                    }
                    try { Thread.sleep(500); } catch (InterruptedException e) { assert false; }
                    break;
                }
                synchronized (this.pending_manager_events) {
                    Event[] res = this.pending_manager_events.toArray(new Event[0]);
                    this.pending_manager_events.clear();
                    assert res.length == expected_len;
                    return res;
                }
            } else if (chain_monitor != null) {
                ArrayList<Event> l = new ArrayList<Event>();
                chain_monitor.as_EventsProvider().process_pending_events(EventHandler.new_impl(l::add));
                assert l.size() == expected_len;
                return l.toArray(new Event[0]);
            } else {
                synchronized (monitors) {
                    assert monitors.size() == 1;
                    for (ChannelMonitor mon : monitors.values()) {
                        Event[] res = mon.get_and_clear_pending_events();
                        assert res.length == expected_len;
                        return res;
                    }
                    return null;
                }
            }
        }

        Event[] get_manager_events(int expected_len) {
            Event[] res = new Event[0];
            if (use_chan_manager_constructor) {
                while (res.length < expected_len) {
                    synchronized (this.pending_manager_events) {
                        res = this.pending_manager_events.toArray(res);
                        assert res.length == expected_len || res.length == 0; // We don't handle partial results
                        this.pending_manager_events.clear();
                        if (res.length < expected_len) {
                            try { this.pending_manager_events.wait(); } catch (InterruptedException e) { assert false; }
                        }
                    }
                }
            } else {
                ArrayList<Event> l = new ArrayList<Event>();
                chan_manager.as_EventsProvider().process_pending_events(EventHandler.new_impl(l::add));
                return l.toArray(new Event[0]);
            }
            assert res.length == expected_len;
            return res;
        }
    }

    static class DescriptorHolder { SocketDescriptor val; }

    boolean running = false;
    final LinkedList<Runnable> runqueue = new LinkedList();
    boolean ran = false;
    Thread t = new Thread(() -> {
            while (true) {
                try {
                    Runnable r;
                    synchronized (runqueue) {
                        while (runqueue.isEmpty()) {
                            runqueue.wait();
                        }
                        running = true;
                        r = runqueue.pollFirst();
                    }
                    r.run();
                    synchronized (runqueue) {
                        running = false;
                        runqueue.notifyAll();
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
    });
    void wait_events_processed(Peer peer1, Peer peer2) {
        if (use_nio_peer_handler) {
            peer1.nio_peer_handler.check_events();
            peer2.nio_peer_handler.check_events();
            try { Thread.sleep(500); } catch (InterruptedException e) { assert false; }
        } else {
            synchronized (runqueue) {
                ran = false;
            }
            while (true) {
                peer1.peer_manager.process_events();
                peer2.peer_manager.process_events();
                synchronized (runqueue) {
                    if (runqueue.isEmpty() && !running) {
                        if (ran) {
                            ran = false;
                            continue;
                        } else { break; }
                    }
                    try { runqueue.wait(); } catch (InterruptedException e) { assert false; }
                }
            }
        }
    }
    void do_read_event(PeerManager pm, SocketDescriptor descriptor, byte[] data) {
        if (!t.isAlive()) t.start();
        synchronized (runqueue) {
            ran = true;
            runqueue.add(() -> {
                Result_boolPeerHandleErrorZ res = pm.read_event(descriptor, data);
                assert res instanceof Result_boolPeerHandleErrorZ.Result_boolPeerHandleErrorZ_OK;
            });
            runqueue.notifyAll();
        }
        must_free_objs.add(new WeakReference<>(data));
    }

    void connect_peers(final Peer peer1, final Peer peer2) {
        if (use_nio_peer_handler) {
            try {
                peer1.nio_peer_handler.connect(peer2.chan_manager.get_our_node_id(), new InetSocketAddress("127.0.0.1", peer2.nio_port), 100);
            } catch (IOException e) { assert false; }
        } else {
            DescriptorHolder descriptor1 = new DescriptorHolder();
            DescriptorHolder descriptor1ref = descriptor1;
            SocketDescriptor descriptor2 = SocketDescriptor.new_impl(new SocketDescriptor.SocketDescriptorInterface() {
                @Override
                public long send_data(byte[] data, boolean resume_read) {
                    do_read_event(peer1.peer_manager, descriptor1ref.val, data);
                    return data.length;
                }

                @Override public void disconnect_socket() { assert false; }
                @Override public boolean eq(SocketDescriptor other_arg) { return other_arg.hash() == 2; }
                @Override public long hash() { return 2; }
            });

            descriptor1.val = SocketDescriptor.new_impl(new SocketDescriptor.SocketDescriptorInterface() {
                @Override
                public long send_data(byte[] data, boolean resume_read) {
                    do_read_event(peer2.peer_manager, descriptor2, data);
                    return data.length;
                }

                @Override public void disconnect_socket() { assert false; }
                @Override public boolean eq(SocketDescriptor other_arg) { return other_arg.hash() == 1; }
                @Override public long hash() { return 1; }
            });

            Result_CVec_u8ZPeerHandleErrorZ conn_res = peer1.peer_manager.new_outbound_connection(peer2.node_id, descriptor1.val);
            assert conn_res instanceof Result_CVec_u8ZPeerHandleErrorZ.Result_CVec_u8ZPeerHandleErrorZ_OK;

            Result_NonePeerHandleErrorZ inbound_conn_res = peer2.peer_manager.new_inbound_connection(descriptor2);
            assert inbound_conn_res instanceof Result_NonePeerHandleErrorZ.Result_NonePeerHandleErrorZ_OK;
            do_read_event(peer2.peer_manager, descriptor2, ((Result_CVec_u8ZPeerHandleErrorZ.Result_CVec_u8ZPeerHandleErrorZ_OK) conn_res).res);
        }
    }

    TestState do_test_message_handler() throws InterruptedException {
        Peer peer1 = new Peer((byte) 1);
        Peer peer2 = new Peer((byte) 2);

        connect_peers(peer1, peer2);
        wait_events_processed(peer1, peer2);

        Result_NoneAPIErrorZ cc_res = peer1.chan_manager.create_channel(peer2.node_id, 10000, 1000, 42, null);
        assert cc_res instanceof Result_NoneAPIErrorZ.Result_NoneAPIErrorZ_OK;
        wait_events_processed(peer1, peer2);

        Event[] events = peer1.get_manager_events(1);
        assert events[0] instanceof Event.FundingGenerationReady;
        assert ((Event.FundingGenerationReady) events[0]).channel_value_satoshis == 10000;
        assert ((Event.FundingGenerationReady) events[0]).user_channel_id == 42;
        byte[] funding_spk = ((Event.FundingGenerationReady) events[0]).output_script;
        assert funding_spk.length == 34 && funding_spk[0] == 0 && funding_spk[1] == 32; // P2WSH
        byte[] chan_id = ((Event.FundingGenerationReady) events[0]).temporary_channel_id;

        NetworkParameters bitcoinj_net = NetworkParameters.fromID(NetworkParameters.ID_MAINNET);

        Transaction funding = new Transaction(bitcoinj_net);
        funding.addInput(new TransactionInput(bitcoinj_net, funding, new byte[0]));
        funding.getInputs().get(0).setWitness(new TransactionWitness(2)); // Make sure we don't complain about lack of witness
        funding.getInput(0).getWitness().setPush(0, new byte[]{0x1});
        funding.addOutput(Coin.SATOSHI.multiply(10000), new Script(funding_spk));
        Result_NoneAPIErrorZ funding_res = peer1.chan_manager.funding_transaction_generated(chan_id, funding.bitcoinSerialize());
        assert funding_res instanceof Result_NoneAPIErrorZ.Result_NoneAPIErrorZ_OK;
        wait_events_processed(peer1, peer2);

        assert peer1.broadcast_set.size() == 1;
        assert Arrays.equals(peer1.broadcast_set.get(0), funding.bitcoinSerialize());
        peer1.broadcast_set.clear();

        Block b = new Block(bitcoinj_net, 2, Sha256Hash.ZERO_HASH, Sha256Hash.ZERO_HASH, 42, 0, 0, Arrays.asList(new Transaction[]{funding}));
        peer1.connect_block(b, 1, 0);
        peer2.connect_block(b, 1, 0);

        for (int height = 2; height < 10; height++) {
            b = new Block(bitcoinj_net, 2, b.getHash(), Sha256Hash.ZERO_HASH, 42, 0, 0, Arrays.asList(new Transaction[0]));
            peer1.connect_block(b, height, 0);
            peer2.connect_block(b, height, 0);
        }
        wait_events_processed(peer1, peer2);

        peer1.chan_manager.list_channels();
        ChannelDetails[] peer1_chans = peer1.chan_manager.list_usable_channels();
        ChannelDetails[] peer2_chans = peer2.chan_manager.list_usable_channels();
        assert peer1_chans.length == 1;
        assert peer2_chans.length == 1;
        assert peer1_chans[0].get_channel_value_satoshis() == 10000;
        assert peer1_chans[0].get_is_usable();
        Option_u64Z short_chan_id = peer1_chans[0].get_short_channel_id();
        assert short_chan_id instanceof Option_u64Z.Some;
        assert ((Option_u64Z.Some)short_chan_id).some == (1L << 40); // 0th output in the 0th transaction in the 1st block
        assert Arrays.equals(peer1_chans[0].get_channel_id(), funding.getTxId().getReversedBytes());
        assert Arrays.equals(peer2_chans[0].get_channel_id(), funding.getTxId().getReversedBytes());

        Result_InvoiceSignOrCreationErrorZ invoice = UtilMethods.create_invoice_from_channelmanager(peer2.chan_manager, peer2.keys_interface, Currency.LDKCurrency_Bitcoin, Option_u64Z.none(), "Invoice Description");
        assert invoice instanceof Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK;
        System.out.println("Got invoice: " + ((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.to_str());

        Result_InvoiceNoneZ parsed_invoice = Invoice.from_str(((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.to_str());
        assert parsed_invoice instanceof Result_InvoiceNoneZ.Result_InvoiceNoneZ_OK;
        assert Arrays.equals(((Result_InvoiceNoneZ.Result_InvoiceNoneZ_OK) parsed_invoice).res.payment_hash(), ((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.payment_hash());
        SignedRawInvoice signed_raw = ((Result_InvoiceNoneZ.Result_InvoiceNoneZ_OK) parsed_invoice).res.into_signed_raw();
        RawInvoice raw_invoice = signed_raw.raw_invoice();
        byte[] desc_hash = raw_invoice.hash();
        Description raw_invoice_description = raw_invoice.description();
        String description_string = raw_invoice_description.into_inner();
        assert description_string.equals("Invoice Description");
        byte[] payment_hash = ((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.payment_hash();
        byte[] payment_secret = ((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.payment_secret();
        byte[] dest_node_id = ((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.recover_payee_pub_key();
        assert Arrays.equals(dest_node_id, peer2.node_id);
        InvoiceFeatures invoice_features = ((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.features();
        ArrayList<RouteHintHop> invoice_hops = new ArrayList<RouteHintHop>();
        for (RouteHint hint: ((Result_InvoiceSignOrCreationErrorZ.Result_InvoiceSignOrCreationErrorZ_OK) invoice).res.routes()) {
            RouteHintHop[] hops = hint.into_inner();
            if (hops.length == 1) {
                invoice_hops.add(hops[0]);
            }
        }

        Route route;
        try (LockedNetworkGraph netgraph = peer1.router.read_locked_graph()) {
            NetworkGraph graph = netgraph.graph();
            Result_RouteLightningErrorZ route_res = UtilMethods.get_route(peer1.chan_manager.get_our_node_id(), graph, peer2.node_id, invoice_features, peer1_chans, invoice_hops.toArray(new RouteHintHop[0]), 1000000, 42, peer1.logger);
            assert route_res instanceof Result_RouteLightningErrorZ.Result_RouteLightningErrorZ_OK;
            route = ((Result_RouteLightningErrorZ.Result_RouteLightningErrorZ_OK) route_res).res;
        }

        Result_NonePaymentSendFailureZ payment_res = peer1.chan_manager.send_payment(route, payment_hash, payment_secret);
        assert payment_res instanceof Result_NonePaymentSendFailureZ.Result_NonePaymentSendFailureZ_OK;
        wait_events_processed(peer1, peer2);

        RouteHop[][] hops = new RouteHop[1][1];
        byte[] hop_pubkey = new byte[33];
        hop_pubkey[0] = 3;
        hop_pubkey[1] = 42;
        hops[0][0] = RouteHop.of(hop_pubkey, NodeFeatures.known(), 42, ChannelFeatures.known(), 100, 0);
        Route r2 = Route.of(hops);
        payment_res = peer1.chan_manager.send_payment(r2, payment_hash, payment_secret);
        assert payment_res instanceof Result_NonePaymentSendFailureZ.Result_NonePaymentSendFailureZ_Err;

        if (!use_chan_manager_constructor) {
            peer1.get_monitor_events(0);
            peer2.get_monitor_events(0);
        } else {
            // The events are combined across manager + monitors but peer1 still has no events
        }

        if (reload_peers) {
            if (use_nio_peer_handler) {
                peer1.nio_peer_handler.interrupt();
                peer2.nio_peer_handler.interrupt();
            }
            if (use_chan_manager_constructor) {
                peer1.constructor.interrupt();
                peer2.constructor.interrupt();
            }
            WeakReference<Peer> op1 = new WeakReference<Peer>(peer1);
            peer1 = new Peer(peer1);
            peer2 = new Peer(peer2);
            return new TestState(op1, peer1, peer2, b.getHash());
        }
        return new TestState(null, peer1, peer2, b.getHash());
    }

    boolean cross_reload_ref_pollution = false;
    class TestState {
        private final WeakReference<Peer> ref_block;
        private final Peer peer1;
        private final Peer peer2;
        public Sha256Hash best_blockhash;

        public TestState(WeakReference<Peer> ref_block, Peer peer1, Peer peer2, Sha256Hash best_blockhash) {
            this.ref_block = ref_block;
            this.peer1 = peer1;
            this.peer2 = peer2;
            this.best_blockhash = best_blockhash;
        }
    }
    void do_test_message_handler_b(TestState state) {
        GcCheck obj = new GcCheck();
        if (state.ref_block != null) {
            // Ensure the original peers get freed before we move on. Note that we have to be in a different function
            // scope to do so as the (at least current OpenJDK) JRE won't release anything created in the same scope.
            while (!cross_reload_ref_pollution && state.ref_block.get() != null) {
                System.gc();
                System.runFinalization();
            }
            connect_peers(state.peer1, state.peer2);
        }
        wait_events_processed(state.peer1, state.peer2);

        Event[] events = state.peer2.get_manager_events(1);
        assert events[0] instanceof Event.PendingHTLCsForwardable;
        state.peer2.chan_manager.process_pending_htlc_forwards();

        events = state.peer2.get_manager_events(1);
        assert events[0] instanceof Event.PaymentReceived;
        byte[] payment_preimage = ((Event.PaymentReceived)events[0]).payment_preimage;
        assert !Arrays.equals(payment_preimage, new byte[32]);
        state.peer2.chan_manager.claim_funds(payment_preimage);
        wait_events_processed(state.peer1, state.peer2);

        events = state.peer1.get_manager_events(1);
        assert events[0] instanceof Event.PaymentSent;
        assert Arrays.equals(((Event.PaymentSent) events[0]).payment_preimage, payment_preimage);
        wait_events_processed(state.peer1, state.peer2);

        ChannelDetails[] peer1_chans = state.peer1.chan_manager.list_channels();

        if (nice_close) {
            Result_NoneAPIErrorZ close_res = state.peer1.chan_manager.close_channel(peer1_chans[0].get_channel_id());
            assert close_res instanceof Result_NoneAPIErrorZ.Result_NoneAPIErrorZ_OK;
            wait_events_processed(state.peer1, state.peer2);

            assert state.peer1.broadcast_set.size() == 1;
            assert state.peer2.broadcast_set.size() == 1;
        } else {
            state.peer1.chan_manager.force_close_all_channels();
            wait_events_processed(state.peer1, state.peer2);

            assert state.peer1.broadcast_set.size() == 1;
            assert state.peer2.broadcast_set.size() == 1;

            NetworkParameters bitcoinj_net = NetworkParameters.fromID(NetworkParameters.ID_MAINNET);
            Transaction tx = new Transaction(bitcoinj_net, state.peer1.broadcast_set.getFirst());
            Block b = new Block(bitcoinj_net, 2, state.best_blockhash, Sha256Hash.ZERO_HASH, 42, 0, 0,
                    Arrays.asList(new Transaction[]{tx}));
            TwoTuple<byte[], TwoTuple<Integer, TxOut>[]>[] watch_outputs = state.peer2.connect_block(b, 10, 1);
            if (watch_outputs != null) { // We only process watch_outputs manually when we use a manually-build Watch impl
                assert watch_outputs.length == 1;
                assert Arrays.equals(watch_outputs[0].a, tx.getTxId().getReversedBytes());
                assert watch_outputs[0].b.length == 2;
                assert watch_outputs[0].b[0].a == 0;
                assert watch_outputs[0].b[1].a == 1;
            }

            for (int i = 11; i < 21; i++) {
                b = new Block(bitcoinj_net, 2, b.getHash(), Sha256Hash.ZERO_HASH, 42, 0, 0, new ArrayList<>());
                state.peer2.connect_block(b, i, 0);
            }

            Event[] broadcastable_event = state.peer2.get_monitor_events(1);
            for (ChannelMonitor mon : state.peer2.monitors.values()) {
                // This used to be buggy and double-free, so go ahead and fetch them!
                byte[][] txn = mon.get_latest_holder_commitment_txn(state.peer2.logger);
            }
            assert broadcastable_event.length == 1;
            assert broadcastable_event[0] instanceof Event.SpendableOutputs;
            if (state.peer2.explicit_keys_manager != null) {
                Result_TransactionNoneZ tx_res = state.peer2.explicit_keys_manager.spend_spendable_outputs(((Event.SpendableOutputs) broadcastable_event[0]).outputs, new TxOut[0], new byte[] {0x00}, 253);
                assert tx_res instanceof Result_TransactionNoneZ.Result_TransactionNoneZ_OK;
                Transaction built_tx = new Transaction(bitcoinj_net, ((Result_TransactionNoneZ.Result_TransactionNoneZ_OK) tx_res).res);
                assert built_tx.getOutputs().size() == 1;
                assert Arrays.equals(built_tx.getOutput(0).getScriptBytes(), new byte[]{0x00});
            }
        }

        if (use_nio_peer_handler) {
            state.peer1.peer_manager.disconnect_by_node_id(state.peer2.chan_manager.get_our_node_id(), false);
            wait_events_processed(state.peer1, state.peer2);
            assert state.peer1.peer_manager.get_peer_node_ids().length == 0;
            assert state.peer2.peer_manager.get_peer_node_ids().length == 0;
            state.peer1.nio_peer_handler.interrupt();
            state.peer2.nio_peer_handler.interrupt();
        }

        state.peer1.get_monitor_events(0);
        state.peer2.get_monitor_events(0);

        if (use_chan_manager_constructor) {
            state.peer1.constructor.interrupt();
            state.peer2.constructor.interrupt();
        }

        t.interrupt();
    }

    java.util.LinkedList<WeakReference<Object>> must_free_objs = new java.util.LinkedList();
    int gc_count = 0;
    int gc_exp_count = 0;
    class GcCheck {
        GcCheck() { gc_exp_count += 1; }
        @Override
        protected void finalize() throws Throwable {
            gc_count += 1;
            super.finalize();
        }
    }
}
public class HumanObjectPeerTest {
    HumanObjectPeerTestInstance do_test_run(boolean nice_close, boolean use_km_wrapper, boolean use_manual_watch, boolean reload_peers, boolean break_cross_peer_refs, boolean nio_peer_handler, boolean use_chan_manager_constructor) throws InterruptedException {
        HumanObjectPeerTestInstance instance = new HumanObjectPeerTestInstance(nice_close, use_km_wrapper, use_manual_watch, reload_peers, break_cross_peer_refs, nio_peer_handler, !nio_peer_handler, use_chan_manager_constructor);
        HumanObjectPeerTestInstance.TestState state = instance.do_test_message_handler();
        instance.do_test_message_handler_b(state);
        return instance;
    }
    void do_test(boolean nice_close, boolean use_km_wrapper, boolean use_manual_watch, boolean reload_peers, boolean break_cross_peer_refs, boolean nio_peer_handler, boolean use_chan_manager_constructor) throws InterruptedException {
        HumanObjectPeerTestInstance instance = do_test_run(nice_close, use_km_wrapper, use_manual_watch, reload_peers, break_cross_peer_refs, nio_peer_handler, use_chan_manager_constructor);
        while (instance.gc_count != instance.gc_exp_count) {
            System.gc();
            System.runFinalization();
        }
        for (WeakReference<Object> o : instance.must_free_objs)
            assert o.get() == null;
    }
    @Test
    public void test_message_handler() throws InterruptedException {
        for (int i = 0; i < (1 << 7) - 1; i++) {
            boolean nice_close =                   (i & (1 << 0)) != 0;
            boolean use_km_wrapper =               (i & (1 << 1)) != 0;
            boolean use_manual_watch =             (i & (1 << 2)) != 0;
            boolean reload_peers =                 (i & (1 << 3)) != 0;
            boolean break_cross_refs =             (i & (1 << 4)) != 0;
            boolean nio_peer_handler =             (i & (1 << 5)) != 0;
            boolean use_chan_manager_constructor = (i & (1 << 6)) != 0;
            if (break_cross_refs && !reload_peers) {
                // There are no cross refs to break without reloading peers.
                continue;
            }
            if (use_chan_manager_constructor && (use_manual_watch || !nio_peer_handler)) {
                // ChannelManagerConstructor requires a ChainMonitor as the Watch and creates a NioPeerHandler for us.
                continue;
            }
            System.err.println("Running test with flags " + i);
            do_test(nice_close, use_km_wrapper, use_manual_watch, reload_peers, break_cross_refs, nio_peer_handler, use_chan_manager_constructor);
        }
    }
}
