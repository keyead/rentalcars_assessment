package vehicleSearch;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.*;


public class JSONToJava {

   private VehicleList fullList;
   private String sortedPrice, sippInfo, supplierTopType, orderedScores;

   public JSONToJava(){
      fullList=new VehicleList();
      readFile();
      sortedPrice = fullList.printSortedPrices();
      //System.out.println("\n\n");
      sippInfo = fullList.printVehicleSippInfo();
      //System.out.println("\n\n");
      supplierTopType = fullList.printBestSupplierEachType();
      //System.out.println("\n\n");
      orderedScores = fullList.printOrderedScores();
   }

   private void readFile() {
      try{
         FileReader fReader = new FileReader("vehicles.json");
         BufferedReader bReader = new BufferedReader(fReader);
         String file = "";

         String line;
         while( (line = bReader.readLine()) != null) {
            line = line.trim();
            file = file+line;
         }
        
         String list = file.replace("[","§");
         list = list.split("§")[1];
         list = list.split("]")[0].trim();
         list = list.replace("{","§");

         String[] vehiclesInfo = list.split("§");
         int index1=0;
         for(String st:vehiclesInfo){
            if(st.equals("")){continue;}
            st=st.split("}")[0];
            vehiclesInfo[index1]=st.trim();
            String[] indVehicle = vehiclesInfo[index1].split(",");
            int index2=0;

            for(String info: indVehicle){
               indVehicle[index2] = info.split(":")[1].replace("\"","").trim();
               index2++;
            }
            fullList.addVehicle(new Vehicle(indVehicle[0], indVehicle[1], Double.parseDouble(indVehicle[2]), indVehicle[3], Double.parseDouble(indVehicle[4])));
            index1++;
         }

      }
      catch (IOException o){
         System.out.println(o.getMessage());
      }
   }

   public String getSortedPrice(){
      return sortedPrice;
   }
   
   public String getSippInfo(){
      return sippInfo;
   }

   public String getSupplierTopType(){
      return supplierTopType;
   }

   public String getOrderedScores(){
      return orderedScores;
   }
}