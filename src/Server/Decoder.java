package Server;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Decoder
{

	private final String SERIALIZED_FILE_NAME;

	public Decoder()
	{
		SERIALIZED_FILE_NAME = "BusinessEntity.xml";
	}

	public Decoder(String fileName)
	{
		SERIALIZED_FILE_NAME = fileName;
	}

	public BusinessEntity deserialize()
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File " + SERIALIZED_FILE_NAME + " not found");
		}

		BusinessEntity entity = (BusinessEntity) decoder.readObject();
		decoder.close();
		return entity;
	}

}