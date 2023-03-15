package com.spring.pace.Algorithm;

import java.util.HashMap;
import java.util.Map;

public class Random {

public Map map(int c) {
		
		Map map = new HashMap();
		
//		int b = (int)(Math.random()*c);
//		int a =(int)(Math.random()*c);//48-57: 0-9사이의 랜덤값 
		int a = 0;
		int b = 0;
		
		do { // 실행문 
		 a =(int) (Math.random()*c)+1;
		 b = (int)(Math.random()*c)+1;
		}while(a == b);  // 조건문 
		

		System.out.println(a);
		System.out.println(b);
		
		map.put("a", a);
		map.put("b", b);
		return map;
	}
}
