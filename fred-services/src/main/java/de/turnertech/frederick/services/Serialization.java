package de.turnertech.frederick.services;

import java.io.File;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Serialization {

    private Serialization() {
        
    }

	// deserialize to Object from given file
	public static <T> Optional<T> deserialize(Class<T> clazz, String fileName) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			Object deserialisedObject = jaxbUnmarshaller.unmarshal(new File(fileName));
			if(deserialisedObject.getClass().equals(clazz)) {
				return Optional.of(clazz.cast(deserialisedObject));
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	// serialize the given object and save it to file
	public static <T> void serialize(final T obj, final String fileName) {
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			mar.marshal(obj, new File(fileName));
		} catch(JAXBException e) {
			Logging.LOGGER.severe(() -> "Could not serialize to file: " +  e.getMessage());
		}
	}
}