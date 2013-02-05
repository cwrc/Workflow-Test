package wfms.engine;

public interface ISerializable {
	String serialize();
	void deserialize(String serializedData);

}
