package com.uddhavzende.controller;
import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class SentimentController {

	public static final Map<String,Integer> feelingsMap = new HashMap<>();
	
	static {
	feelingsMap.put("love",2);
	feelingsMap.put("happy",2);
	feelingsMap.put("fun",2);
	feelingsMap.put("like",1);
	feelingsMap.put("hate",-2);
	feelingsMap.put("sad",-2);
	feelingsMap.put("bore",-2);
	feelingsMap.put("dislike",-1);
	}

	@PostMapping("/analyze")
	public Map<String,String> analyze(@RequestBody Map<String,String> body){
		String text = body.get("text").toLowerCase();
		int score=0;
		for(String word : text.split("\\s+")) {
			score += feelingsMap.getOrDefault(word, 0);
		}
		
		String sentiment = "Hmm...Neutral";
		if (score>0) sentiment = "Yayy! Positive";
		else if(score<0) sentiment ="Oops! Negative";
		
		return Map.of("sentiment",sentiment);
		
	}
}
