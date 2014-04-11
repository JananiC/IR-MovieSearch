package com.irproject.search.dao;



import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * The Class DataSource.
 *
 * @author JANANI
 */
public class DataSource {

    /** The Constant DS_XML_PATH. */
    public static final String DS_XML_PATH = "springcfg/";

    /** The Constant DS_XML_NAME. */
    public static final String DS_XML_NAME = "imdb-dataSource-context.xml";

    /** The bean factory. */
    private XmlBeanFactory beanFactory = new XmlBeanFactory(
            new ClassPathResource(DS_XML_PATH + DS_XML_NAME));

    /**
     * Gets the bean factory.
     *
     * @return the bean factory
     */
    public XmlBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
