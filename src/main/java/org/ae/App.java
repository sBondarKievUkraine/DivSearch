package org.ae;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Qualification task is running.");
        String targetElementId = "make-everything-ok-button";
        String originFile = "sample-0-origin.html";
//        String sampleFile = "sample-1-evil-gemini.html";
//        String sampleFile = "sample-2-container-and-clone.html";
        String sampleFile = "sample-3-the-escape.html";
        if (args.length != 2) {
//            throw new Exception("Wrong arguments. Must be: input_origin_file_path input_other_sample_file_path");

        } else {
            originFile = args[0];
            sampleFile = args[1];
        }

        Finder finder = new Finder();
        Optional<Element> buttonOpt = finder.findElementById(new FileReader(originFile).getXmlFile(), targetElementId);

        /*Optional<String> stringifiedAttributesOpt = buttonOpt.map(button ->
                button.attributes().asList().stream()
                        .map(attr -> attr.getKey() + " = " + attr.getValue())
                        .collect(Collectors.joining(", "))
        );*/

        Optional<Attributes> stringifiedAttributesOpt = buttonOpt.map(button -> button.attributes());

//        stringifiedAttributesOpt.ifPresent(attrs -> System.out.println("Target element attrs: " + attrs));
        Attributes initialAttributes = stringifiedAttributesOpt.get();
        Element element = null;
        int bestMatcher = 0;
        for (Attribute attribute : initialAttributes) {
            String cssQuery = String.format("a[%s=\"%s\"]", attribute.getKey(), attribute.getValue());
            Optional<Elements> elementsByQuery = finder.findElementsByQuery(new FileReader(sampleFile).getXmlFile(), cssQuery);
            for (Element e : elementsByQuery.get()) {
                int matcherCount = 0;

                for (Attribute foundedAttr : e.attributes()) {
                    for (Attribute initAttr : initialAttributes) {

                        if (foundedAttr.equals(initAttr)) {
                            matcherCount++;
                        }
                    }
                }
                if (matcherCount > bestMatcher) {
                    bestMatcher = matcherCount;
                    element = e;
                }
            }

        }
//        element.ifPresent(attrs -> System.out.println(cssQuery + ";\t " + attrs));
        System.out.println("element = " + element);

    }
}
