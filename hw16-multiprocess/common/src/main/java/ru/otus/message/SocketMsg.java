package ru.otus.message;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SocketMsg implements Serializable {
	private final UUID id = UUID.randomUUID();
	private final String clientFrom;
	private final String clientTo;
	private final UUID sourceID;
	private final String type;
	private final byte[] payload;

	public SocketMsg(String clientFrom, String clientTo, UUID sourceID, String type, byte[] payload) {
		this.clientFrom = clientFrom;
		this.clientTo = clientTo;
		this.sourceID = sourceID;
		this.type = type;
		this.payload = payload;
	}

	public UUID getId() {
		return id;
	}

	public String getClientFrom() {
		return clientFrom;
	}

	public String getClientTo() {
		return clientTo;
	}

	public String getType() {
		return type;
	}

	public byte[] getPayload() {
		return payload;
	}

	public UUID getSourceID() {
		return sourceID;
	}

	public Optional<UUID> getSourceSocketMsgId() {
		return Optional.ofNullable(sourceID);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SocketMsg)) return false;
		SocketMsg socketMsg = (SocketMsg) o;
		return Objects.equals(getId(), socketMsg.getId()) &&
				Objects.equals(getClientFrom(), socketMsg.getClientFrom()) &&
				Objects.equals(getClientTo(), socketMsg.getClientTo()) &&
				Objects.equals(getSourceID(), socketMsg.getSourceID()) &&
				Objects.equals(getType(), socketMsg.getType()) &&
				Arrays.equals(getPayload(), socketMsg.getPayload());
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(getId(), getClientFrom(), getClientTo(), getSourceID(), getType());
		result = 31 * result + Arrays.hashCode(getPayload());
		return result;
	}

	@Override
	public String toString() {
		return "SocketMsg{" +
				"id=" + id +
				", clientFrom='" + clientFrom + '\'' +
				", clientTo='" + clientTo + '\'' +
				", sourceID=" + sourceID +
				", type='" + type + '\'' +
				", payload=" + Arrays.toString(payload) +
				'}';
	}
}
