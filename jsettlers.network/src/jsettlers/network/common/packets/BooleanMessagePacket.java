package jsettlers.network.common.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import jsettlers.network.infrastructure.channel.packet.Packet;

/**
 * 
 * @author Andreas Eberle
 * 
 */
public class BooleanMessagePacket extends Packet {

	private boolean value;

	public BooleanMessagePacket() {
	}

	public BooleanMessagePacket(boolean value) {
		this.value = value;
	}

	@Override
	public void serialize(DataOutputStream dos) throws IOException {
		dos.writeBoolean(value);
	}

	@Override
	public void deserialize(DataInputStream dis) throws IOException {
		value = dis.readBoolean();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BooleanMessagePacket other = (BooleanMessagePacket) obj;
		if (value != other.value)
			return false;
		return true;
	}

	public boolean getValue() {
		return value;
	}

}
