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
        String sampleFile = "sample-1-evil-gemini.html";
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
        Attributes attributes = stringifiedAttributesOpt.get();
        for (Attribute attribute : attributes.asList()) {
            String cssQuery = String.format("a[%s=\"%s\"]", attribute.getKey(), attribute.getValue());
//            System.out.println("cssQuery = " + cssQuery);
            Optional<Elements> elementsByQuery = finder.findElementsByQuery(new FileReader(sampleFile).getXmlFile(), cssQuery);
            elementsByQuery.ifPresent(attrs -> System.out.println("Target element attrs: " + attrs));
        }


        //TODO get attributes from founded element and try to get query by it


    }
}
