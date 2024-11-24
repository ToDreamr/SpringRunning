package com.pray.mybatis;

import org.apache.ibatis.io.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
/**
 * XmlResolve
 *
 * @author Cotton Eye Joe
 * @since 2024/11/23 23:34
 */
public class XmlResolve {
    public static void main(String[] args) {
        // 创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream urlAsStream = Resources.getResourceAsStream("xml/boy.xml");
            Document d = builder.parse(urlAsStream);
            // 每一个标签都作为一个节点
            NodeList nodeList = d.getElementsByTagName("outer");  // 可能有很多个名字为test的标签
            Node rootNode = nodeList.item(0); // 获取首个

            NodeList childNodes = rootNode.getChildNodes(); // 一个节点下可能会有很多个节点，比如根节点下就囊括了所有的节点
            //节点可以是一个带有内容的标签（它内部就还有子节点），也可以是一段文本内容

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node child = childNodes.item(i);
                if(child.getNodeType() == Node.ELEMENT_NODE)  //过滤换行符之类的内容，因为它们都被认为是一个文本节点
                    System.out.println(child.getNodeName() + "：" +child.getFirstChild().getNodeValue());
                // 输出节点名称，也就是标签名称，以及标签内部的文本（内部的内容都是子节点，所以要获取内部的节点）
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
