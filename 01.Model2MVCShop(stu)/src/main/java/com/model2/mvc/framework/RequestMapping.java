package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties();
			properties.load(in);
			
			System.out.println("resources :"+resources);
			System.out.println("map : "+map.toString());
			System.out.println("in : "+in);
			
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){ //�ν��Ͻ��� �Ѱ��� ����!!!!
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		
		return requestMapping;
	}
	
	public Action getAction(String path){
		Action action = map.get(path);  //���� �� ������ Ʈ���̹� �Ʒ��� map.put���� ����.    1��° ������Ʈ�� �����Ȱ� ��� ������!
		System.out.println("Request Mapping GetAction�޼ҵ�");
		if(action == null){
			System.out.println("if������");
			
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className+"\n");
			System.out.println(className);
			className = className.trim(); // .trim ��Ʈ�� Ÿ���� ������ ������ ����
			System.out.println("������Ʈ ���� Ʈ���̹� ����");
			System.out.println(className);
			try{
				System.out.println("try�� ����.");
				Class c = Class.forName(className); // ���� properties ���� ��� ������ Ŭ���������� ����. 
													// ex) com.model2.mvc.view.user.GetUserAction  ���� Ŭ��������
				System.out.println("Ŭ���������� ���Ľ���");
				Object obj = c.newInstance();	// == new Class(); ��������? 
				System.out.println("obj = "+obj);
				if(obj instanceof Action){ 		// �ν��Ͻ����긦 ���� Ation Ŭ������ ��ȯ���� üũ, �����ϴٸ� if�� ���� �Ұ��ɽ� �ͼ��ǿ����߻�
					map.put(path, (Action)obj); // ������ �ڿ� @~~~~~ �����°� ����?
					System.out.println("map.put : "+  map.put(path, (Action)obj));
					action = (Action)obj;
					System.out.println("action : "+action);
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}