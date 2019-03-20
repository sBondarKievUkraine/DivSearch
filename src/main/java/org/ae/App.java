package org.ae;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Qualification task is running.");
        String targetElementId = "make-everything-ok-button";
        String originFile =  "sample-0-origin.html";
        String sampleFile;
        if (args.length!=2){
//            throw new Exception("Wrong arguments. Must be: input_origin_file_path input_other_sample_file_path");

        } else {
            originFile = args[0];
            sampleFile = args[1];
        }

        Finder finder = new Finder();
        finder.findElementById(new FileReader(originFile).getXmlFile(), targetElementId);
        //TODO get attributes from founded element and try to get query by it



    }
}
