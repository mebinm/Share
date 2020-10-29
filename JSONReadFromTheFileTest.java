package com.tesgng.testautomation;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class JSONReadFromTheFileTest {
   public static void main(String[] args) {
      JSONParser parser = new JSONParser();
      try {
         Object obj = parser.parse(new FileReader("C:\\Users\\Mebin Mathews\\eclipse-workspace\\testautomation\\src\\main\\java\\com\\tesgng\\testautomation\\test.json"));
         JSONObject jsonObject = (JSONObject)obj;
         Iterator<String> itr = jsonObject.keySet().iterator();
         String env = "QA";
         JSONObject objJsonFinal = new JSONObject();
         JSONObject objJson = new JSONObject();
         while (itr.hasNext())
         {
        	 String cluster = itr.next();
                 JSONObject clusterObject = (JSONObject)jsonObject.get(cluster);
                 String instanceType = null;
                 if(clusterObject.get("instanceType") != null) {
                	 instanceType = clusterObject.get("instanceType").toString();
                 }
                 JSONObject envObject = (JSONObject)clusterObject.get("QA");
                 JSONArray grpSize = (JSONArray)envObject.get("groupSize");            
                 
                 JSONArray array = new JSONArray();
                 JSONObject tag1 = new JSONObject();
                 tag1.put("type", "tag");
                 tag1.put("key", "ags");
                 tag1.put("value", "TXTNLT");
                 array.add(tag1);
                 JSONObject tag2 = new JSONObject();
                 tag2.put("type", "tag");
                 tag2.put("key", "cost center");
                 tag2.put("value", "TXT900");
                 array.add(tag2);
                 JSONObject tag3 = new JSONObject();
                 tag3.put("type", "tag");
                 tag3.put("key", "sdlc");
                 tag3.put("value", env);
                 array.add(tag3);
                 if(grpSize != null) {
                	 JSONObject param1 = new JSONObject();
                     param1.put("type", "parameter");
                     param1.put("key", "DesiredCapacity");
                     param1.put("value", grpSize.get(2));
                     array.add(param1);
                     JSONObject param2 = new JSONObject();
                     param2.put("type", "parameter");
                     param2.put("key", "asGroupMaxSize");
                     param2.put("value", grpSize.get(1));
                     array.add(param2);
                 }
                 JSONObject param3 = new JSONObject();
                 param3.put("type", "parameter");
                 param3.put("key", "asHealthCheckType");
                 param3.put("value", "EC2");
                 array.add(param3);
                 
                 if(instanceType != null) {
                	 JSONObject param4 = new JSONObject();
                     param4.put("type", "parameter");
                     param4.put("key", "instance");
                     param4.put("value",instanceType);
                     array.add(param4);
                 }
                 
                
                 objJson.put("TXTNLT-provision-ecs-"+env+"-"+cluster,array);
                
         }
         objJsonFinal.put("cloudformation", objJson);
         System.out.println(objJsonFinal);
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}
