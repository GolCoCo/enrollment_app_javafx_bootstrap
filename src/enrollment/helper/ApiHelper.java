package enrollment.helper;

import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class ApiHelper {
	
	public JSONObject callAPI(JSONObject request) {
		
		String url = (String) request.get("url");
		JSONObject result = new JSONObject();
		JSONParser parser = new JSONParser();
		result.put("status", 500);
		result.put("response", null);
		
		String path = "";
		
		switch(url) {
		case "login":
			path = "src/enrollment/helper/json/login.json";
			break;
		case "get-person-data":
			path = "src/enrollment/helper/json/person-data.json";
			break;
		default:
			path = "";
			break;
		}
		
		if (path == "") return result;
		
		try {     
            Object obj = parser.parse(new FileReader(path));
            JSONObject response =  (JSONObject) obj;
            result.put("status", 200);
            result.put("response", response);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		return result;
	}
	
	public boolean getCameraState() {
		JSONParser parser = new JSONParser();
		try {     
            Object obj = parser.parse(new FileReader("src/enrollment/helper/json/device.json"));

            JSONObject res =  (JSONObject) obj;
            boolean state = (boolean) res.get("camera");
            return state;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public boolean getFingerPrintState() {
		JSONParser parser = new JSONParser();
		try {     
            Object obj = parser.parse(new FileReader("src/enrollment/helper/json/device.json"));

            JSONObject res =  (JSONObject) obj;
            boolean state = (boolean) res.get("fingerprint");
            return state;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public boolean getFingerScan(int finger) {
		boolean[] states = {true, false, true, true, true, false, true, true, false, true};
		return states[finger];
	}
}
