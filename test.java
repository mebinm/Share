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
                 tag1.put("key", "AGS");
                 tag1.put("value", "TXTNLT");
                 array.add(tag1);
                 JSONObject tag2 = new JSONObject();
                 tag2.put("key", "Architecture");
                 tag2.put("value", "x86_64");
                 array.add(tag2);
                 JSONObject tag3 = new JSONObject();
                 tag3.put("key", "State");
                 tag3.put("value", "running");
                 array.add(tag3);
                 JSONObject tag4 = new JSONObject();
                 tag4.put("type", "tag");
                 tag4.put("key", "aws:cloudformation:stack-name");
                 tag4.put("value", "TXTNLT-provision-ecs-"+env+"-"+cluster);
                 array.add(tag4);
                 
                 if(instanceType != null) {
                	 JSONObject param4 = new JSONObject();
                     param4.put("key", "InstanceType");
                     param4.put("value",instanceType);
                     array.add(param4);
                 }
                 
                
                 objJson.put("AWSLXTXTNLT-ECS"+cluster+"EQ00",array);
                
         }
         objJsonFinal.put("ec2", objJson);
         System.out.println(objJsonFinal);
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}
