package com.vs;



import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Parser {
    XMLEventReader eventReader;


    public void openStream(String filename)
            throws XMLStreamException, IOException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(filename);
        eventReader = inputFactory.createXMLEventReader(in);
    }

    public Document parseDocument(String filename)
            throws XMLStreamException, IOException, InterruptedException {
        openStream(filename);
        Document d = new Document();
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if (tagName.equals("sentence")) {
                    d.add(readNextSentence());
                }
                if (tagName.equals("PMID")) {
                    d.setId(getString(event));
                }
            }
        }
        return d;
    }


    public Document.Sentence readNextSentence() throws XMLStreamException {
        Document.Sentence Sentence = new Document.Sentence();
        XMLEvent event;
        while(eventReader.hasNext()) {
            event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if (tagName.equals("tok")) {
                    Attribute attributes = startElement.getAttributeByName(new QName("cat"));
                    String XMLAttributeContent = attributes.getValue();
                    Document.Token token = new Document.Token(getString(event),
                            XMLAttributeContent);
                    Sentence.add(token);
                }
            } else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String tagName = endElement.getName().getLocalPart();
                if(tagName.equals("sentence")) {
                    break;
                }
            }


        }
        return Sentence;

    }
    //TODO Warum muss ich das event mit übergeben und kann dann es in einen anderen Eventtypen casten?!?!?
    private String getString(XMLEvent event) throws XMLStreamException {
         event = eventReader.nextEvent();
        String content = event.asCharacters().getData();
        return content;
    }


}
