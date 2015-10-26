package com.cola.autoresolution.util;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/*
 * 配置文件读写
 */
public class XmlUtil {

	private final static String UTF8 = "UTF-8";

	private final static String RESOURCES = "resources", DIMEN = "dimen", NAME = "name";

	public static class ResourcesInfo {
		public String strAttribName = "";
		public String strValue = "";
		public short sNodeType = Node.ATTRIBUTE_NODE;
	}

	/*
	 * 编码音乐信息
	 */
	public static String buildResourcesInfo(List<ResourcesInfo> resInfoList, int destResolution, int srcResolution) {
		XmlSerializer xmlSerializer = null;
		StringWriter stringWriter = null;
		String strRet = "";
		String unit = "dp";

		try {
			xmlSerializer = Xml.newSerializer();
			stringWriter = new StringWriter();

			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument(UTF8, true);

			xmlSerializer.text("\n");
			xmlSerializer.comment(" 分辨率 " + destResolution + " ");
			xmlSerializer.text("\n");

			xmlSerializer.startTag("", RESOURCES);

			for (ResourcesInfo info : resInfoList) {
				if (info.sNodeType == Node.COMMENT_NODE) {
					xmlSerializer.comment(info.strValue);
				} else if (info.sNodeType == Node.TEXT_NODE) {
					xmlSerializer.text(info.strValue);
				} else {
					xmlSerializer.startTag("", DIMEN);
					xmlSerializer.attribute("", NAME, info.strAttribName);
					int pixelValue = 0;
					if (info.strValue.contains("px")) {
						unit = "px";
						try {
							pixelValue = Integer.parseInt(info.strValue.replace(unit, ""));

							if (pixelValue > 1) {
								pixelValue = (int) (((float) pixelValue) * 2 * destResolution / srcResolution / 2);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (info.strValue.contains("dp")) {
						unit = "dp";
						try {
							pixelValue = Integer.parseInt(info.strValue.replace(unit, ""));

							pixelValue = (int) (((float) pixelValue) * 2 * destResolution / srcResolution);
						} catch (Exception e) {
							e.printStackTrace();
						}
						unit = "px";
					} else if (info.strValue.contains("dip")) {
						unit = "dip";
						try {
							pixelValue = Integer.parseInt(info.strValue.replace(unit, ""));

							pixelValue = (int) (((float) pixelValue) * 2 * destResolution / srcResolution);
						} catch (Exception e) {
							e.printStackTrace();
						}
						unit = "px";
					} else if (info.strValue.contains("sp")) {
						unit = "sp";
						try {
							pixelValue = Integer.parseInt(info.strValue.replace(unit, ""));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					xmlSerializer.text("" + pixelValue + unit);
					xmlSerializer.endTag("", DIMEN);
				}
			}

			xmlSerializer.endTag("", RESOURCES);
			xmlSerializer.endDocument();

			strRet = stringWriter.toString();
		} catch (Exception e) {
			strRet = "";
		}

		return strRet;
	}

	/*
	 * 解码音乐信息
	 */
	public static List<ResourcesInfo> getResourcesInfo(String strPath) {
		DocumentBuilderFactory documentBuilderFactory = null;
		DocumentBuilder documentBuilder = null;
		Document document = null;
		Element element = null;
		NodeList nodeList = null;
		NodeList nodeListChildren = null;
		NamedNodeMap namedNodeMap = null;

		Node node = null, nodeAttribute = null, nodeChild = null;
		File file = null;
		List<ResourcesInfo> resInfoList = null;

		try {
			file = new File(strPath);

			if (file.exists() && file.isFile()) {
				resInfoList = new ArrayList<ResourcesInfo>();

				documentBuilderFactory = DocumentBuilderFactory.newInstance();
				documentBuilder = documentBuilderFactory.newDocumentBuilder();
				document = documentBuilder.parse(file);
				element = document.getDocumentElement();
				nodeList = element.getChildNodes();

				for (int i = 0; i < nodeList.getLength(); ++i) {
					node = nodeList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.COMMENT_NODE || node.getNodeType() == Node.TEXT_NODE) {

						ResourcesInfo info = new ResourcesInfo();
						if (info != null) {
							if (node.getNodeType() == Node.COMMENT_NODE || node.getNodeType() == Node.TEXT_NODE) {
								info.sNodeType = node.getNodeType();
								info.strValue = node.getNodeValue();

								resInfoList.add(info);
							} else {
								info.strValue = node.getNodeValue();
								nodeListChildren = node.getChildNodes();
								if (nodeListChildren != null) {
									for (int j = 0; j < nodeListChildren.getLength(); ++j) {
										nodeChild = nodeListChildren.item(j);

										if (nodeChild != null) {
											info.strValue = StringUtil.newString(nodeChild.getNodeValue());
										}
									}
								}

								namedNodeMap = node.getAttributes();

								if (namedNodeMap != null) {
									for (int j = 0; j < namedNodeMap.getLength(); ++j) {
										nodeAttribute = namedNodeMap.item(j);

										if (nodeAttribute != null && nodeAttribute.getNodeName().equals(NAME)) {
											info.strAttribName = StringUtil.newString(nodeAttribute.getNodeValue());
										}
									}
								}

								resInfoList.add(info);
							}

						}
					}

				}
			}
		} catch (Exception e) {
			resInfoList = null;
		}

		return resInfoList;
	}
}