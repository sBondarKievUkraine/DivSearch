package org.ae;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class FileReader {


    public static final String CHARSET_NAME = "UTF-8";
    private final File xmlFile;

    public FileReader(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        this.xmlFile = new File(classLoader.getResource(fileName).getFile());
    }

    public Document readJsoupDocFromFile() throws IOException {
        Document document = Jsoup.parse(xmlFile, CHARSET_NAME).ownerDocument();
        return document;
    }


    public File getXmlFile() {
        return xmlFile;
    }
}
