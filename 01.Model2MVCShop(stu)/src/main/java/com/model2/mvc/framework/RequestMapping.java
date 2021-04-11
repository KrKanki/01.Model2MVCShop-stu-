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
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){ //인스턴스를 한개만 생성!!!!
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		
		return requestMapping;
	}
	
	public Action getAction(String path){
		Action action = map.get(path);  //맵의 값 지정은 트라이문 아래에 map.put에서 지정.    1번째 리퀘스트후 생성된것 계속 재사용함!
		System.out.println("GetAction메소드");
		if(action == null){
			System.out.println("if문실행");
			
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className+"\n");
			System.out.println(className);
			className = className.trim(); // .trim 스트링 타입인 문자의 공백을 제거
			System.out.println("리퀘스트 맵핑 트라이문 시작");
			System.out.println(className);
			try{
				System.out.println("try문 시작.");
				Class c = Class.forName(className); // 위의 properties 에서 밸류 값으로 클래스네임을 얻어옴. 
													// ex) com.model2.mvc.view.user.GetUserAction  그후 클래스지정
				System.out.println("클래스포네임 이후시작");
				Object obj = c.newInstance();	// == new Class(); 맞으려나? 
				System.out.println("obj = "+obj);
				if(obj instanceof Action){ 		// 인스턴스오브를 통한 Ation 클래스로 변환여부 체크, 가능하다면 if문 실행 불가능시 익셉션오류발생
					map.put(path, (Action)obj); // 디버깅시 뒤에 @~~~~~ 나오는게 뭘까?
					System.out.println("map.put : "+  map.put(path, (Action)obj));
					action = (Action)obj;
					System.out.println("action : "+action);
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}