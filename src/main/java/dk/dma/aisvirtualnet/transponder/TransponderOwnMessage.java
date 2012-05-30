package dk.dma.aisvirtualnet.transponder;

import org.apache.log4j.Logger;

import dk.dma.aisvirtualnet.AisVirtualNet;

/**
 * Utility class to hold latest OWN message and resend with certain interval
 */
public class TransponderOwnMessage extends Thread {

	private static final Logger LOG = Logger.getLogger(TransponderOwnMessage.class);

	private static final long MESSAGE_MAX_AGE = 20 * 60 * 1000; // 20 minutes

	private Long lastReceived = 0L;
	private byte[] ownMessage = null;
	private int forceInterval = 0;
	private Transponder transponder = null;

	public TransponderOwnMessage(Transponder transponder) {
		this.transponder = transponder;
	}

	@Override
	public void run() {
		// Should own message re-sending be forced
		if (forceInterval == 0) {
			return;
		}

		// Wait for everything to start up
		if (!AisVirtualNet.sleep(5000)) {
			return;
		}

		// Enter re-send loop
		while (true) {
			AisVirtualNet.sleep(forceInterval * 1000);
			reSend();
		}
	}

	private void reSend() {
		long elapsed = 0L;

		synchronized (lastReceived) {
			if (ownMessage == null) {
				return;
			}
			// Determine last send elapsed
			elapsed = System.currentTimeMillis() - lastReceived;
		}

		// Do not send if already sent with interval
		if (elapsed < forceInterval * 1000) {
			return;
		}
		// Send if not too old
		if (elapsed < MESSAGE_MAX_AGE) {
			LOG.info("Re-sending own message for " + transponder.getMmsi());
			transponder.sendData(ownMessage);
		}

	}

	public void setOwnMessage(byte[] ownMessage) {
		synchronized (lastReceived) {			
			LOG.debug("Setting own message for " + transponder.getMmsi());
			this.ownMessage = ownMessage;
			this.lastReceived = System.currentTimeMillis();
		}
	}

	public void setForceInterval(int forceInterval) {
		this.forceInterval = forceInterval;
	}
	
	public int getForceInterval() {
		return forceInterval;
	}

}
