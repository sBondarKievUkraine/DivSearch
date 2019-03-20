package org.ae;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FileReaderTest {

    @Test
    public void readJsoupDocFromFileTest() throws IOException {
        String fileName = "sample-0-origin.html";
        FileReader fileReader = new FileReader(fileName);
        Document document = fileReader.readJsoupDocFromFile();
        Assert.assertNotEquals(null, document);
    }
}