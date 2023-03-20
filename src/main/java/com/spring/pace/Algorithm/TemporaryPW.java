package com.spring.pace.Algorithm;

public class TemporaryPW {//임시비밀번호 알고리즘
	
	public String temPW() {
		String result="";
	//48-57: 0-9  65-90: A-Z  97-122: a-z 
		
		
		char [] temPW = new char[12];

		
		for(int i = 0; i<12; i++) {
			
			int a =(int)(Math.random()*10)+48;//48-57: 0-9사이의 랜덤값 
			int b = (int)(Math.random()*26)+65;//65-90: A-Z사이의 랜덤값
			int c = (int)(Math.random()*26)+97;//97-122: a-z사이의 랜덤값
			
			int [] random = new int [] {a,b,c};//숫자랜덤, 소문자랜덤, 대문자랜덤 생성 
			int abc = (int)(Math.random()*3);
			
			int no = random[abc];//랜덤값
//			System.out.println(no);
			
			char s1 = (char)no;//아스키코드 문자로 변환
//			System.out.println(s1);
			
			temPW[i] = s1;//임시비밀번호 char배열
		
		}
		
		result = new String(temPW);
//		System.out.println(str);
		
		
		
		
		return result;
	}
	
}
