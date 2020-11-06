package com.tesgng.testautomation;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.internal.junit.ArrayAsserts;
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
         JSONObject objJson1 = new JSONObject();
         List<keyValueTypePojo> pojoList = new ArrayList<>();
         while (itr.hasNext())
         {
        	 keyValueTypePojo pojo = new keyValueTypePojo();
        	 	String cluster = itr.next();
        	 	pojo.setService(cluster);
                 JSONObject clusterObject = (JSONObject)jsonObject.get(cluster);
                 if(clusterObject.get("ecs") != null) {
                	 JSONObject envObject = (JSONObject)clusterObject.get("ecs");
                     Iterator<String> itr1 = envObject.keySet().iterator();  
                	 HashMap<String, Integer> dclusters = new HashMap<String, Integer>();
                     List<String> ecsCluster = new ArrayList<String>();
                     while (itr1.hasNext())
                     {
                    	 String c = itr1.next();
                    	 JSONObject envObjectd = (JSONObject)envObject.get(c);
                    	 Integer d = 0;
                    	 if(envObjectd.get("desiredCount") != null) {
                    		 d = Integer.parseInt(envObjectd.get("desiredCount").toString());
                    	 }                    	 
                    	 //HashMap<String, Integer> clus = new HashMap<String, Integer>();
                    	 dclusters.put(c, d);
                    	 ecsCluster.add(c);
                     }
                     pojo.setDesiredCount(dclusters);
                     pojo.setEscCluster(ecsCluster);
                 }
                 pojoList.add(pojo);
                 
                                
         }
         //System.out.println(pojoList);
         JSONObject objJsonFinalCluster = new JSONObject();
         for(keyValueTypePojo k : pojoList) {
        	 
        	 for(String clustername : k.getEscCluster()) {
        		 int activeServiccount = 0;
        		 int runningTasksCount = 0;
        		 String ecsService = "svc-txtnlt-"+k.getService()+"-"+clustername;
        		 String ecsClusterFinal = "TXTNLT-ECS-CLUSTER-"+clustername.toUpperCase();
        		 //System.out.println(ecsClusterFinal);
        		 //System.out.println(ecsService);
        		 for(keyValueTypePojo kCount : pojoList) {
        			 if(kCount.getEscCluster().contains(clustername)) {
        				 activeServiccount+=1; 
        			 }
        			 runningTasksCount+=(kCount.getDesiredCount().get(clustername)!=null)?kCount.getDesiredCount().get(clustername):0;
        		 }
        		// System.out.println("activeServiccount="+activeServiccount);
        		 //System.out.println("runningTasksCount="+runningTasksCount);
        		 
        		 JSONArray array = new JSONArray();
                 JSONObject tag1 = new JSONObject();
                 tag1.put("key", "Status");
                 tag1.put("value", "active");
                 array.add(tag1);
                 JSONObject tag2 = new JSONObject();
                 tag2.put("key", "RunningTasksCount");
                 tag2.put("value", runningTasksCount);
                 array.add(tag2);
                 JSONObject tag3 = new JSONObject();
                 tag3.put("key", "ActiveServicesCount");
                 tag3.put("value", activeServiccount);
                 array.add(tag3);
                 JSONObject tag4 = new JSONObject();
                 tag4.put("key", "ClusterArn");
                 tag4.put("value", "arn:aws:ecs:us-east-1:142248000760:cluster"+"/"+ecsClusterFinal);
                 array.add(tag4);
                 objJson.put(ecsClusterFinal,array);
                 
                 
                 JSONArray array1 = new JSONArray();
                 JSONObject tag5 = new JSONObject();
                 tag5.put("key", "Status");
                 tag5.put("value", "active");
                 array1.add(tag5);
                 JSONObject tag6 = new JSONObject();
                 tag6.put("key", "LaunchType");
                 tag6.put("value", "EC2");
                 array1.add(tag6);
                 JSONObject tag7 = new JSONObject();
                 tag7.put("key", "ServiceRole");
                 tag7.put("value", "SVC_ECS_TXTNLT_SR");
                 array1.add(tag7);
                 JSONObject tag8 = new JSONObject();
                 tag8.put("key", "TargetGroupName");
                 tag8.put("value", ecsService);
                 array1.add(tag8);
                 JSONObject tag9 = new JSONObject();
                 tag9.put("key", "ContainerName");
                 tag9.put("value", k.getService()+"-ig");
                 array1.add(tag9);
                 JSONObject tag10 = new JSONObject();
                 tag10.put("key", "ContainerPort");
                 tag10.put("value", "8443");
                 array1.add(tag10);
                 objJson1.put(ecsService,array1);
        		 
        	 }
         }
         objJsonFinalCluster.put("ecscluster",objJson);
         objJsonFinalCluster.put("ecsservices",objJson1);
         System.out.println(objJsonFinalCluster);
         
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}
