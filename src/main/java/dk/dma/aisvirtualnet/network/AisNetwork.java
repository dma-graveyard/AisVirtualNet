package dk.dma.aisvirtualnet.network;

import java.util.ArrayList;
import java.util.List;

import dk.frv.ais.binary.SixbitException;
import dk.frv.ais.handler.IAisHandler;
import dk.frv.ais.message.AisMessage;
import dk.frv.ais.message.AisMessageException;
import dk.frv.ais.sentence.SentenceException;
import dk.frv.ais.sentence.Vdm;

/**
 * Class that represents the virtual AIS network
 */
public class AisNetwork {

	private List<IAisHandler> listeners = new ArrayList<IAisHandler>();

	public AisNetwork() {

	}

	/**
	 * Method to use for broadcasting VDM messages on the virtual network
	 * 
	 * @param vdms
	 */
	synchronized public void broadcast(AisMessage aisMessage) {
		// Send to all listeners
		for (IAisHandler listener : listeners) {
			listener.receive(aisMessage);
		}
	}

	public void broadcast(Vdm vdm) throws SentenceException, SixbitException, AisMessageException {
		// Make AisMessage from Vdm sentences
		Vdm[] vdms = vdm.createSentences();
		vdm = new Vdm();
		for (Vdm partVdm : vdms) {
			vdm.parse(partVdm.getEncoded());
		}
		broadcast(AisMessage.getInstance(vdm));
	}

	/**
	 * Add listener to network
	 * 
	 * @param listener
	 */
	synchronized public void addListener(IAisHandler listener) {
		listeners.add(listener);
	}
	
	/**
	 * Remove listener
	 * 
	 * @param listener
	 */
	synchronized public void removeListener(IAisHandler listener) {
		listeners.remove(listener);
	}

}
