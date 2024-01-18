package com.snottis.exercise.backend.encoding;

import org.springframework.util.Assert;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class JaxbContextContainer {

    private final ConcurrentMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<>(64);

    public Marshaller createMarshaller(Class<?> clazz) throws JAXBException {
        JAXBContext jaxbContext = getJaxbContext(clazz);
        return jaxbContext.createMarshaller();
    }

    public Unmarshaller createUnmarshaller(Class<?> clazz) throws JAXBException {
        JAXBContext jaxbContext = getJaxbContext(clazz);
        return jaxbContext.createUnmarshaller();
    }

    private JAXBContext getJaxbContext(Class<?> clazz) throws JAXBException {
        Assert.notNull(clazz, "Class must not be null");
        JAXBContext jaxbContext = this.jaxbContexts.get(clazz);
        if (jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance(clazz);
            this.jaxbContexts.putIfAbsent(clazz, jaxbContext);
        }
        return jaxbContext;
    }

}