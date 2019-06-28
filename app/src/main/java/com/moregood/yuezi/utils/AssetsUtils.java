
package com.moregood.yuezi.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.moregood.yuezi.entity.Dish;
import com.moregood.yuezi.entity.Material;
import com.moregood.yuezi.entity.SkuMaterial;

import android.content.Context;

public class AssetsUtils {

	private static String parseString(Element main, String key) {
		NodeList nodes = main.getElementsByTagName(key);
		if (nodes.getLength() <= 0 || nodes.item(0).getFirstChild()==null)
			return "";
		return nodes.item(0).getFirstChild().getNodeValue();
	}

	private static long parseLong(Element main, String key) {
		NodeList nodes = main.getElementsByTagName(key);
		if (nodes.getLength() <= 0)
			return 0;
		return Long.valueOf(nodes.item(0).getFirstChild().getNodeValue());
	}
	private static boolean parseBoolean(Element main, String key) {
		NodeList nodes = main.getElementsByTagName(key);
		if (nodes.getLength() <= 0)
			return false;
		return Boolean.valueOf(nodes.item(0).getFirstChild().getNodeValue());
	}

	public static <T> NodeList getNodeList(Context context,Class<T> clasz,String folderName) throws ParserConfigurationException, SAXException, IOException{
		String classSimpleName = clasz.getSimpleName();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(context.getResources().getAssets().open(String.format("%s/list.xml", classSimpleName)));
		Element eleConfig = document.getDocumentElement();
		NodeList nodes = eleConfig.getElementsByTagName(classSimpleName);
		return nodes;
	}

	public static <T> Map<Integer,T> parseFromAssets(Context context,Class<T> clasz,String folderName) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException{
		Map<Integer,T> map = new HashMap<Integer, T>();
		NodeList nodes = getNodeList(context,clasz,folderName);
		int length = nodes.getLength();
		Element eleGiftShop;
		for (int i = 0; i < length; ++i) {
			T data = clasz.newInstance();
			eleGiftShop = (Element) nodes.item(i);
			Field fields[] = clasz.getFields();
			int id =0;
			for(Field field:fields){
				try {
					if(field.getName().equals("id")){
						String value = eleGiftShop.getAttribute(field.getName());
						if(null!=value && !"".equals(value)){
							id = Integer.valueOf(value);
							field.set(data, id);
						}
					}else if(field.getType().toString().equals("class java.lang.String")){
						String v = parseString(eleGiftShop,field.getName());
						if("unit".equals(field.getName()) && "".equals(v)){
							v = "克";
						}
						field.set(data, v);
					}else if(field.getType().toString().equals("boolean")){
						field.set(data, parseBoolean(eleGiftShop,field.getName()));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			map.put(id, data);
		}
		return map;
	}

	public static Map<Integer,Dish> parseDishFromAssets(Context context,Map<Integer, Material> materialList) throws Exception{
		Class clasz = Dish.class;
		NodeList nodes = getNodeList(context,clasz,clasz.getSimpleName());
		int length = nodes.getLength();
		Element eleGiftShop;
		Map<Integer, Dish> dishes = new HashMap<Integer, Dish>();
		for (int i = 0; i < length; ++i) {
			Dish dish = new Dish();
			eleGiftShop = (Element) nodes.item(i);
			Field fields[] = clasz.getFields();
			for(Field field:fields){
				if("id".equals(field.getName())){
					String value = eleGiftShop.getAttribute(field.getName());
					if(null!=value && !"".equals(value)){
						int id = Integer.valueOf(value);
						field.set(dish, id);
					}
				}else if("materials".equals(field.getName())){
					NodeList MaterialList = eleGiftShop.getElementsByTagName("Material-list");
					if(MaterialList!=null && MaterialList.getLength()>0){
						NodeList materials = ((Element)MaterialList.item(0)).getElementsByTagName("material");
						int materialsLen = materials.getLength();
						List<SkuMaterial> skus = new ArrayList<SkuMaterial>();
						for(int j=0;j<materialsLen;j++){
							Element ele = (Element)materials.item(j);
							String value = ele.getAttribute("id");
							if(null!=value && !"".equals(value)){
								int id = Integer.valueOf(value);
								float amount = 0.0f;
								try {
									amount = Float.valueOf(ele.getFirstChild().getNodeValue());
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
								skus.add(new SkuMaterial(materialList.get(id), amount));
							}
						}
						field.set(dish,skus);
					}
				}else{
					try{
						field.set(dish, parseString(eleGiftShop,field.getName()));
					}catch(Exception je){}
				}
			}
			dishes.put(dish.id, dish);
		}
		return dishes;
	}

}
