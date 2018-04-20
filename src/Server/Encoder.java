package Server;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Encoder
{
	private final String SERIALIZED_FILE_NAME;

	public Encoder(String fileName)
	{
		SERIALIZED_FILE_NAME = fileName;
	}

	public Encoder()
	{
		SERIALIZED_FILE_NAME = "BusinessEntity.xml";
	}

	public void serialize(BusinessEntity entity) throws FileNotFoundException
	{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));

		encoder.setExceptionListener(new ExceptionListener()
		{
			public void exceptionThrown(Exception e)
			{
				System.out.println("Exception! :" + e.toString());
			}
		});
		encoder.writeObject(entity);
		encoder.close();
	}

}
