package soa.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q, @RequestParam("lang") String lang, @RequestParam("count") String count) {
    	
    	Map<String, Object> headers = new HashMap<String, Object>();
    	
    	String numPages = "";
    	String headerValue = "";
    	
    	Scanner s = new Scanner(q);
    	
    	while (s.hasNext()) {
    		String word = s.next();
    		if (word.startsWith("max:")) {
    			count = word.split(":")[1];
    		}
    		else if (word.startsWith("lang:")) {
    			lang = word.split(":")[1];
    		}
    		else if (word.startsWith("numPages:")) {
    			numPages = word.split(":")[1];
    		}
    		else {
    			headerValue += " " + word;
    		}
    	}
    	
    	headers.put("CamelTwitterKeywords", headerValue);
    	if (!count.equals("")) {		// Sets the number of results
        	headers.put("CamelTwitterCount", count);
    	}
    	if (!lang.equals("all")) {			// Sets the language of results
        	headers.put("CamelTwitterSearchLanguage", lang);
    	}
    	if (!numPages.equals("")) {		// Sets the number of pages
        	headers.put("CamelTwitterNumberOfPages", numPages);
    	}
        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
    }
}