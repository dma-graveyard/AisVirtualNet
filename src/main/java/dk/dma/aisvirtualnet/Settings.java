/* Copyright (c) 2011 Danish Maritime Safety Administration
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package dk.dma.aisvirtualnet;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import dk.dma.aisvirtualnet.transponder.Transponder;
import dk.frv.ais.reader.AisReader;
import dk.frv.ais.reader.AisSerialReader;
import dk.frv.ais.reader.AisTcpReader;

/**
 * Settings class to load settings and setup project entities
 */
public class Settings {

	private static final Logger LOG = Logger.getLogger(Settings.class);

	private Properties props;
	private String filename;

	public Settings() {
	}
	
	public void save() {
		int count;
		Properties saveProps = new Properties();
		Map<String, AisSerialReader> serialSources = new HashMap<String, AisSerialReader>(); 
		Map<String, AisTcpReader> tcpSources = new HashMap<String, AisTcpReader>();
		Map<String, Transponder> transponders = new HashMap<String, Transponder>();
				
		count = 0;
		for (Transponder transponder : AisVirtualNet.getTransponders()) {
			transponders.put("TRANS" + (count++), transponder);
		}
		
		count = 0;
		for (AisReader aisReader : AisVirtualNet.getSourceReader().getReaders()) {
			String name = "SOURCE" + (count++);
			if (aisReader instanceof AisTcpReader) {
				tcpSources.put(name, (AisTcpReader)aisReader);
			} else {
				serialSources.put(name, (AisSerialReader)aisReader);
			}
		}
		
		saveProps.put("serial_sources", StringUtils.join(serialSources.keySet(), ","));
		for (String name : serialSources.keySet()) {
			AisSerialReader aisSerialReader = serialSources.get(name);
			saveProps.put("serial_port." + name, aisSerialReader.getPortName());			
		}
		
		saveProps.put("tcp_sources", StringUtils.join(tcpSources.keySet(), ","));
		for (String name : tcpSources.keySet()) {
			AisTcpReader aisTcpReader = tcpSources.get(name);
			saveProps.put("tcp_source_host." + name, aisTcpReader.getHostname());
			saveProps.put("tcp_source_port." + name, Integer.toString(aisTcpReader.getPort()));
		}
		
		saveProps.put("transponders", StringUtils.join(transponders.keySet(), ","));
		for (String name : transponders.keySet()) {
			Transponder transponder = transponders.get(name);
			saveProps.put("transponder_mmsi." + name, Long.toString(transponder.getMmsi()));
			saveProps.put("transponder_tcp_port." + name, Integer.toString(transponder.getTcpPort()));
			saveProps.put("transponder_own_message_force." + name, Integer.toString(transponder.getForceOwnInterval()));			
		}
				
		try {
			FileWriter outFile = new FileWriter(filename);
			saveProps.store(outFile, "AisVirtualNet settings");
			outFile.close();
		} catch (IOException e) {
			LOG.error("Failed to save settings: " + e.getMessage());
		}		
	}


	public void load(String filename) throws IOException {
		this.filename = filename;
		props = new Properties();
		URL url = ClassLoader.getSystemResource(filename);
		if (url == null) {
			throw new IOException("Could not find properties file: " + filename);
		}
		props.load(url.openStream());

		// Iterate throgh serial sources
		String serialSourcesStr = props.getProperty("serial_sources", "");
		for (String sourceDescription : StringUtils.split(serialSourcesStr, ",")) {
			AisSerialReader aisSerialReader = new AisSerialReader();
			aisSerialReader.setPortName(props.getProperty("serial_port." + sourceDescription, ""));
			aisSerialReader.setPortSpeed(getInt("serial_speed." + sourceDescription, "38400"));
			aisSerialReader.setDataBits(getInt("serial_data_bits." + sourceDescription, "8"));
			aisSerialReader.setStopBits(getInt("serial_stop_bits." + sourceDescription, "1"));
			aisSerialReader.setParity(getInt("serial_parity." + sourceDescription, "0"));
			// Find port
			try {
				aisSerialReader.findPort();
			} catch (IOException e) {
				LOG.error("Failed to find serial port " + aisSerialReader.getPortName());
				continue;
			}
			LOG.info("Adding serial source " + aisSerialReader.getPortName() + " (" + sourceDescription + ")");
			AisVirtualNet.getSourceReader().addReader(aisSerialReader);
		}

		// Iterate through TCP sources
		String tcpSourcesStr = props.getProperty("tcp_sources", "");
		for (String tcpSourceName : StringUtils.split(tcpSourcesStr, ",")) {
			AisTcpReader aisTcpReader = new AisTcpReader();
			aisTcpReader.setHostname(props.getProperty("tcp_source_host." + tcpSourceName, "localhost"));
			aisTcpReader.setPort(getInt("tcp_source_port." + tcpSourceName, "4001"));
			LOG.info("Adding TCP source " + tcpSourceName + " " + aisTcpReader.getHostname() + ":" + aisTcpReader.getPort());
			AisVirtualNet.getSourceReader().addReader(aisTcpReader);
		}

		// Iterate through transponders
		String transpondersStr = props.getProperty("transponders", "");
		for (String transponderName : StringUtils.split(transpondersStr, ",")) {
			Transponder transponder = new Transponder(AisVirtualNet.getAisNetwork());
			transponder.setMmsi(getInt("transponder_mmsi." + transponderName, "0"));
			transponder.setTcpPort(getInt("transponder_tcp_port." + transponderName, "0"));
			transponder.setForceOwnInterval(getInt("transponder_own_message_force." + transponderName, "0"));
			LOG.info("Adding transponder " + transponderName + " mmsi " + transponder.getMmsi() + " port "
					+ transponder.getTcpPort());
			AisVirtualNet.getTransponders().add(transponder);
		}

	}

	private int getInt(String key, String defaultValue) {
		String val = props.getProperty(key, defaultValue);
		return Integer.parseInt(val);
	}
		
}
