package org.ae;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.Optional;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Qualification task is running.");
        String targetElementId = "make-everything-ok-button";
        String originFile = "sample-0-origin.html";
//        String sampleFile = "sample-1-evil-gemini.html";
//        String sampleFile = "sample-2-container-and-clone.html";
        String sampleFile = "sample-3-the-escape.html";
//        String sampleFile = "sample-4-the-mash.html";
        if (args.length != 2) {
//            throw new Exception("Wrong arguments. Must be: input_origin_file_path input_other_sample_file_path");

        } else {
            originFile = args[0];
            sampleFile = args[1];
        }

        Finder finder = new Finder();
        Optional<Element> buttonOpt = finder.findElementById(new FileReader(originFile).getXmlFile(), targetElementId);

        Optional<Attributes> finedAttributesOpt = buttonOpt.map(Element::attributes);

        Attributes initialAttributes;
        if (finedAttributesOpt.isPresent()) {
            initialAttributes = finedAttributesOpt.get();
        } else {
            return;
        }
        Element bestMachElement = null;
        int bestMatcher = 0;
        for (Attribute attribute : initialAttributes) {
            String cssQuery = String.format("a[%s=\"%s\"]", attribute.getKey(), attribute.getValue());
            Optional<Elements> elementsByQuery = finder.findElementsByQuery(new FileReader(sampleFile).getXmlFile(), cssQuery);
            for (Element element : elementsByQuery.get()) {
                int matcherCount = 0;

                for (Attribute foundedAttr : element.attributes()) {
                    for (Attribute initAttr : initialAttributes) {

                        if (foundedAttr.equals(initAttr)) {
                            matcherCount++;
                        }
                    }
                }
                if (matcherCount > bestMatcher) {
                    bestMatcher = matcherCount;
                    bestMachElement = element;
                }
            }

        }
//        bestMachElement.ifPresent(attrs -> System.out.println(cssQuery + ";\t " + attrs));
        System.out.println("bestMachElement = " + getXmlPath(bestMachElement));

    }

    private static String getXmlPath(Element element) {
        StringBuffer path = new StringBuffer();
        Elements parents = element.parents();
        for (int i = parents.size()-1; i >= 0; i--) {
            path.append(parents.get(i).tagName());
            path.append(">");
        }
        path.append(element.tagName());
        return String.valueOf(path);
    }
}
