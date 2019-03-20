package org.ae;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Finder {
    private static String CHARSET_NAME = "utf8";
    private static Logger LOGGER = Logger.getLogger(Finder.class);

    public Finder() {
    }

    public Optional<Elements> findElementsByQuery(File htmlFile, String cssQuery) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.select(cssQuery));

        } catch (IOException e) {
            LOGGER.error(String.format("Error reading %s file" ,  htmlFile.getAbsolutePath()), e);
            return Optional.empty();
        }
    }

    public Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error(String.format("Error reading %s file" ,  htmlFile.getAbsolutePath()), e);
            return Optional.empty();
        }
    }

}
