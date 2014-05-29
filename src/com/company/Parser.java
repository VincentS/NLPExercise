package com.company;



import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


public class Parser {
    XMLEventReader eventReader;


    public void openStream(String filename) throws XMLStreamException, IOException {
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
                    d.addSentence(readNextSentence());
                }
            }
        }
        return d;
    }

    public String getXMLAttribute(StartElement startElement) {
        Iterator<Attribute> XMLAttribute = startElement.getAttributes();
        while(XMLAttribute.hasNext()) {
            Attribute attribute = XMLAttribute.next();
            if(attribute.getName().toString().equals("tok")) {
                return attribute.getValue().toString();
            }
        }

        return null;

    }







    public Document.Sentence readNextSentence() throws XMLStreamException {
        Document.Sentence Sentence = new Document.Sentence();
        XMLEvent event;
        while(eventReader.hasNext()) {
            event = eventReader.nextEvent();
            if (event.isStartElement()) {
                XMLEvent contentEvent = eventReader.nextEvent();
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("tok")) {
                    Attribute attributes = startElement.getAttributeByName(new QName("cat"));
                    String XMLAttributeContent = attributes.getValue().toString();
                    Document.Token token = new Document.Token(contentEvent.asCharacters().getData(),
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


}
