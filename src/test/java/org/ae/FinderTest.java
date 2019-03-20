package org.ae;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FinderTest {
    private static Logger LOGGER = Logger.getLogger(Finder.class);
    private static String fileName = "sample-0-origin.html";
    @Test
    public void findElementsByQueryTest() {

//        String fileName = "sample-1-evil-gemini.html";

//        String cssQuery = "div.make-everything-ok-button";
//        String cssQuery = "div[id=\"make-everything-ok-button\"]";
        String cssQuery = "button[class*=\"btn btn-success\"]";

        Finder finder = new Finder();

        Optional<Elements> elementsOpt = finder.findElementsByQuery(new FileReader(fileName).getXmlFile(), cssQuery);

        Optional<List<String>> elementsAttrsOpts = elementsOpt.map(buttons ->
                {
                    List<String> stringifiedAttrs = new ArrayList<>();

                    buttons.iterator().forEachRemaining(button ->
                            stringifiedAttrs.add(
                                    button.attributes().asList().stream()
                                            .map(attr -> attr.getKey() + " = " + attr.getValue())
                                            .collect(Collectors.joining(", "))));

                    return stringifiedAttrs;
                }
        );

        elementsAttrsOpts.ifPresent(attrsList ->
                attrsList.forEach(attrs ->
                        LOGGER.info("Target element attrs: " + attrs)
                )
        );
    }

    @Test
    public void findElementByIdTest() {

        String targetElementId = "make-everything-ok-button";

        Finder finder = new Finder();

        Optional<Element> buttonOpt = finder.findElementById(new FileReader(fileName).getXmlFile(), targetElementId);

        Optional<String> stringifiedAttributesOpt = buttonOpt.map(button ->
                button.attributes().asList().stream()
                        .map(attr -> attr.getKey() + " = " + attr.getValue())
                        .collect(Collectors.joining(", "))
        );

        stringifiedAttributesOpt.ifPresent(attrs -> LOGGER.info("Target element attrs: " + attrs));
    }
}