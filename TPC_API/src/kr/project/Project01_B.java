package kr.project;

import org.json.JSONArray;
import org.json.JSONObject;

public class Project01_B {

	public static void main(String[] args) {
		// JSON->Java(org.Json)
		JSONArray students = new JSONArray(); // [ {},{},{} ]

		JSONObject student = new JSONObject();
		student.put("name", "홍길동");
		student.put("phone", "010-1234-1234");
		student.put("address", "서울 종로구 관철동 13-13");
		System.out.println("---------------------student------------------------------");
		System.out.println(student);
		students.put(student);

		// ----------------------------------------------------
		student.put("name", "김길동");
		student.put("phone", "010-3333-4444");
		student.put("address", "서울 종로구12길 15 ");
		System.out.println(student);
		students.put(student); // Json배열방에 저장

		System.out.println(
				"------------------------------------------------student---------------------------------------------");
		System.out.println(students);
		System.out.println(students.toString(2));
		
		System.out.println(
				"------------------------------------------------object---------------------------------------------");
		
		JSONObject object=new JSONObject();
		object.put("stdInfo",students);
		System.out.println(object);
		System.out.println(object.toString(2));
	}

}
