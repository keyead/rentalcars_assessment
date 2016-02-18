package vehicleSearch;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;


@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/search")
    public String query(@RequestParam(value="section", defaultValue="0") String section) {
    	JSONToJava jt= new JSONToJava();
    	String out = "";

    	if(section.equals("1")){
    		out = out+jt.getSortedPrice();
    	}
    	else if(section.equals("2")){
    		out = out+jt.getSippInfo();
    	}
    	else if(section.equals("3")){
    		out = out+jt.getSupplierTopType();
    	}
    	else if(section.equals("4")){
    		out = out+jt.getOrderedScores();
    	}
    	else{
    		out = out+jt.getSortedPrice()+"\r\r"+jt.getSippInfo()+"\r\r"+jt.getSupplierTopType()+"\r\r"+jt.getOrderedScores()+"\r\r";	
    	}
        return out;
    }
}