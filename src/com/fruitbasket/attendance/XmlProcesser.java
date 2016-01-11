package com.fruitbasket.attendance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public final class XmlProcesser{
    private Document mDocument;
    private static final XmlProcesser mXmlProcesser=new XmlProcesser();
    
    private XmlProcesser() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.mDocument = builder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static XmlProcesser getInstance(){
    	return mXmlProcesser;
    }
 
    /**
     * create the configuration file with the default values
     * @param xmlFileName
     */
    public void createXmlConfig() {
        Element root = this.mDocument.createElement("configuration"); 
        this.mDocument.appendChild(root); 
        
        Element excelfile=mDocument.createElement("excelfile");
        excelfile.appendChild(mDocument.createTextNode("default.xls"));
        root.appendChild(excelfile);
        
        Element absoluterootpath=mDocument.createElement("absoluterootpath");
        absoluterootpath.appendChild(mDocument.createTextNode("d:"));
        root.appendChild(absoluterootpath);
        
        Element year=mDocument.createElement("year");
        year.appendChild(mDocument.createTextNode("2015"));
        root.appendChild(year);
        
        Element month=mDocument.createElement("month");
        month.appendChild(mDocument.createTextNode("8"));
        root.appendChild(month);
        
        Element selectedidlist = this.mDocument.createElement("selectedidlist"); 
        selectedidlist.appendChild(this.mDocument.createTextNode("1")); 
        root.appendChild(selectedidlist); 
        
        Element selectednamelist = this.mDocument.createElement("selectednamelist"); 
        selectednamelist.appendChild(this.mDocument.createTextNode("3")); 
        root.appendChild(selectednamelist); 
        
        Element selecteddatelist = this.mDocument.createElement("selecteddatelist"); 
        selecteddatelist.appendChild(this.mDocument.createTextNode("5")); 
        root.appendChild(selecteddatelist); 
        
        Element selectedinlist = this.mDocument.createElement("selectedinlist"); 
        selectedinlist.appendChild(this.mDocument.createTextNode("9")); 
        root.appendChild(selectedinlist); 
        
        Element selectedoutlist = this.mDocument.createElement("selectedoutlist"); 
        selectedoutlist.appendChild(this.mDocument.createTextNode("10")); 
        root.appendChild(selectedoutlist); 
        
        Element selectedtimelist = this.mDocument.createElement("selectedtimelist"); 
        selectedtimelist.appendChild(this.mDocument.createTextNode("25")); 
        root.appendChild(selectedtimelist); 
        
        Element idlist = this.mDocument.createElement("idlist"); 
        idlist.appendChild(this.mDocument.createTextNode("0")); 
        root.appendChild(idlist); 
        
        Element namelist = this.mDocument.createElement("namelist"); 
        namelist.appendChild(this.mDocument.createTextNode("1")); 
        root.appendChild(namelist); 
        
        Element datelist = this.mDocument.createElement("datelist"); 
        datelist.appendChild(this.mDocument.createTextNode("2")); 
        root.appendChild(datelist); 
        
        Element inlist = this.mDocument.createElement("inlist"); 
        inlist.appendChild(this.mDocument.createTextNode("3")); 
        root.appendChild(inlist); 
        
        Element outlist = this.mDocument.createElement("outlist"); 
        outlist.appendChild(this.mDocument.createTextNode("4")); 
        root.appendChild(outlist); 
        
        Element timelist = this.mDocument.createElement("timelist"); 
        timelist.appendChild(this.mDocument.createTextNode("5")); 
        root.appendChild(timelist); 

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(mDocument);
            transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(Environment.CONFIG_FILE));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
           e.printStackTrace();
        } catch (IllegalArgumentException e) {
        	e.printStackTrace();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (TransformerException e) {
        	e.printStackTrace();
        }
    }
 
    /**
     * set the configuration from a xml file
     * @param xmlFileName
     * @param environment
     * @throws Exception
     */
    public void setXmlConfig(Environment environment) 
    		throws Exception {
    	Node configuration;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(Environment.CONFIG_FILE);
            configuration=document.getChildNodes().item(0);
            
            for(Node node=configuration.getFirstChild();node!=null;node=node.getNextSibling()){
    			if(node.getNodeType()==Node.ELEMENT_NODE){
    				switch(node.getNodeName()){
    				case "excelfile":
    					environment.excelFile=node.getFirstChild().getNodeValue();
    					break;
    				case "absoluterootpath":
    					environment.mAbsoluteRootPath=node.getFirstChild().getNodeValue();
    					break;
    				case "year":
    					environment.year=node.getFirstChild().getNodeValue();
    					break;
    				case "month":
    					environment.month=node.getFirstChild().getNodeValue();
    					break;
    				case "selectedidlist":
    					environment.selectIdList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "selectednamelist":
    					environment.selectNameList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "selecteddatelist":
    					environment.selectDateList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "selectedinlist":
    					environment.selectInList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "selectedoutlist":
    					environment.selectOutList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "selectedtimelist":
    					environment.selectTimeList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "idlist":
    					environment.idList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "namelist":
    					environment.nameList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "datelist":
    					environment.dateList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "inlist":
    					environment.inList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "outlist":
    					environment.outList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				case "timelist":
    					environment.timeList=Integer.parseInt(node.getFirstChild().getNodeValue());
    					break;
    				default:
    					throw new Exception("config.xml error!");
    				}
    			}
    		}
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveXmlConfig(Environment environment){
    	Environment en=Environment.getInstance();
    	 Element root = this.mDocument.createElement("configuration"); 
         this.mDocument.appendChild(root); 
         
         Element excelfile=mDocument.createElement("excelfile");
         excelfile.appendChild(mDocument.createTextNode(en.excelFile));
         root.appendChild(excelfile);
         
         Element absoluterootpath=mDocument.createElement("absoluterootpath");
         absoluterootpath.appendChild(mDocument.createTextNode(en.mAbsoluteRootPath));
         root.appendChild(absoluterootpath);
         
         Element year=mDocument.createElement("year");
         year.appendChild(mDocument.createTextNode(en.year));
         root.appendChild(year);
         
         Element month=mDocument.createElement("month");
         month.appendChild(mDocument.createTextNode(en.month));
         root.appendChild(month);
         
         Element selectedidlist = this.mDocument.createElement("selectedidlist"); 
         selectedidlist.appendChild(this.mDocument.createTextNode(String.valueOf(en.selectIdList))); 
         root.appendChild(selectedidlist); 
         
         Element selectednamelist = this.mDocument.createElement("selectednamelist"); 
         selectednamelist.appendChild(this.mDocument.createTextNode(String.valueOf(en.selectNameList))); 
         root.appendChild(selectednamelist); 
         
         Element selecteddatelist = this.mDocument.createElement("selecteddatelist"); 
         selecteddatelist.appendChild(this.mDocument.createTextNode(String.valueOf(en.selectDateList))); 
         root.appendChild(selecteddatelist); 
         
         Element selectedinlist = this.mDocument.createElement("selectedinlist"); 
         selectedinlist.appendChild(this.mDocument.createTextNode(String.valueOf(en.selectInList))); 
         root.appendChild(selectedinlist); 
         
         Element selectedoutlist = this.mDocument.createElement("selectedoutlist"); 
         selectedoutlist.appendChild(this.mDocument.createTextNode(String.valueOf(en.selectOutList))); 
         root.appendChild(selectedoutlist); 
         
         Element selectedtimelist = this.mDocument.createElement("selectedtimelist"); 
         selectedtimelist.appendChild(this.mDocument.createTextNode(String.valueOf(en.selectTimeList))); 
         root.appendChild(selectedtimelist); 
         
         Element idlist = this.mDocument.createElement("idlist"); 
         idlist.appendChild(this.mDocument.createTextNode(String.valueOf(en.idList))); 
         root.appendChild(idlist); 
         
         Element namelist = this.mDocument.createElement("namelist"); 
         namelist.appendChild(this.mDocument.createTextNode(String.valueOf(en.nameList))); 
         root.appendChild(namelist); 
         
         Element datelist = this.mDocument.createElement("datelist"); 
         datelist.appendChild(this.mDocument.createTextNode(String.valueOf(en.dateList))); 
         root.appendChild(datelist); 
         
         Element inlist = this.mDocument.createElement("inlist"); 
         inlist.appendChild(this.mDocument.createTextNode(String.valueOf(en.inList))); 
         root.appendChild(inlist); 
         
         Element outlist = this.mDocument.createElement("outlist"); 
         outlist.appendChild(this.mDocument.createTextNode(String.valueOf(en.outList))); 
         root.appendChild(outlist); 
         
         Element timelist = this.mDocument.createElement("timelist"); 
         timelist.appendChild(this.mDocument.createTextNode(String.valueOf(en.timeList))); 
         root.appendChild(timelist); 

         TransformerFactory tf = TransformerFactory.newInstance();
         try {
             Transformer transformer = tf.newTransformer();
             DOMSource source = new DOMSource(mDocument);
             transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
             transformer.setOutputProperty(OutputKeys.INDENT, "yes");
             PrintWriter pw = new PrintWriter(new FileOutputStream(Environment.CONFIG_FILE));
             StreamResult result = new StreamResult(pw);
             transformer.transform(source, result);
         } catch (TransformerConfigurationException e) {
            e.printStackTrace();
         } catch (IllegalArgumentException e) {
         	e.printStackTrace();
         } catch (FileNotFoundException e) {
         	e.printStackTrace();
         } catch (TransformerException e) {
         	e.printStackTrace();
         }
    }
}