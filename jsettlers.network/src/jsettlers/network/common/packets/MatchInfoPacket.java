package jsettlers.network.common.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import jsettlers.network.infrastructure.channel.packet.Packet;
import jsettlers.network.server.match.Match;

/**
 * 
 * @author Andreas Eberle
 * 
 */
public class MatchInfoPacket extends Packet {
	private String id;
	private String matchName;
	private int maxPlayers;
	private MapInfoPacket mapInfo;
	private PlayerInfoPacket[] players;

	public MatchInfoPacket() {
	}

	public MatchInfoPacket(String id, String matchName, int maxPlayers, MapInfoPacket mapInfo, PlayerInfoPacket[] players) {
		this.id = id;
		this.matchName = matchName;
		this.maxPlayers = maxPlayers;
		this.mapInfo = mapInfo;
		this.players = players;
	}

	public MatchInfoPacket(Match match) {
		this();
		id = match.getId();
		matchName = match.getName();
		maxPlayers = match.getMaxPlayers();
		mapInfo = match.getMap();
		players = match.getPlayerInfos();
	}

	@Override
	public void serialize(DataOutputStream dos) throws IOException {
		dos.writeUTF(id);
		dos.writeUTF(matchName);
		dos.writeInt(maxPlayers);
		mapInfo.serialize(dos);

		PlayerInfoPacket[] players = this.players;
		dos.writeInt(players.length);
		for (PlayerInfoPacket curr : players) {
			curr.serialize(dos);
		}
	}

	@Override
	public void deserialize(DataInputStream dis) throws IOException {
		id = dis.readUTF();
		matchName = dis.readUTF();
		maxPlayers = dis.readInt();
		mapInfo = new MapInfoPacket();
		mapInfo.deserialize(dis);

		int length = dis.readInt();
		PlayerInfoPacket[] players = new PlayerInfoPacket[length];
		for (int i = 0; i < length; i++) {
			PlayerInfoPacket curr = new PlayerInfoPacket();
			curr.deserialize(dis);
			players[i] = curr;
		}
		this.players = players;
	}

	public String getId() {
		return id;
	}

	public String getMatchName() {
		return matchName;
	}

	public MapInfoPacket getMapInfo() {
		return mapInfo;
	}

	public PlayerInfoPacket[] getPlayers() {
		return players;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mapInfo == null) ? 0 : mapInfo.hashCode());
		result = prime * result + ((matchName == null) ? 0 : matchName.hashCode());
		result = prime * result + maxPlayers;
		result = prime * result + Arrays.hashCode(players);
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
		MatchInfoPacket other = (MatchInfoPacket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mapInfo == null) {
			if (other.mapInfo != null)
				return false;
		} else if (!mapInfo.equals(other.mapInfo))
			return false;
		if (matchName == null) {
			if (other.matchName != null)
				return false;
		} else if (!matchName.equals(other.matchName))
			return false;
		if (maxPlayers != other.maxPlayers)
			return false;
		if (!Arrays.equals(players, other.players))
			return false;
		return true;
	}
}
