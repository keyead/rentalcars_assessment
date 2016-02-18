import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.*;


public class JSONToJava {

   private VehicleList fullList;

   public static void main(String[] args){
      JSONToJava jt= new JSONToJava();
   }


   public JSONToJava(){
      fullList=new VehicleList();
      readFile();
      fullList.printSortedPrices();
      System.out.println("\n\n");
      fullList.printVehicleSippInfo();
      System.out.println("\n\n");
      fullList.printBestSupplierEachType();
      System.out.println("\n\n");
      fullList.printOrderedScores();
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
}

class VehicleList{
   ArrayList<Vehicle> list;
   HashMap<String, ArrayList<Integer>> types;
   HashMap<Integer, ArrayList<Integer>> prices, ratings;

   public VehicleList(){
      list = new ArrayList<Vehicle>();
      
      types = new HashMap<String,ArrayList<Integer>>();
      prices = new HashMap<Integer, ArrayList<Integer>>();
      ratings = new HashMap<Integer, ArrayList<Integer>>();

   }

   public void addVehicle(Vehicle v){
      list.add(v);
      ArrayList<Integer> priceIndexList, ratingsIndexList, typeIndexList;

      priceIndexList = prices.get((int)(v.getPrice()*100));
      ratingsIndexList = ratings.get((int)(v.getRating()*10));
      typeIndexList = types.get(v.getType());
      
      if(priceIndexList==null){
         priceIndexList = new ArrayList<Integer>();  
      }
      if(ratingsIndexList==null){
         ratingsIndexList = new ArrayList<Integer>();  
      }
      if(typeIndexList==null){
         typeIndexList = new ArrayList<Integer>();
      }

      Integer index = list.size()-1;

      priceIndexList.add(index);
      prices.put((int)(v.getPrice()*100), priceIndexList);

      ratingsIndexList.add(index);
      ratings.put((int)(v.getRating()*10), ratingsIndexList);

      typeIndexList.add(index);
      types.put(v.getType(), typeIndexList);
   }

   public Vehicle getVehicle(int i){
      return list.get(i);
   }

   public void printSortedPrices(){
      ArrayList<Integer> listPrices = new ArrayList<Integer>();
      listPrices.addAll(prices.keySet());
      Collections.sort(listPrices);
      int count=1;
      for(Integer price:listPrices){
         for(Integer vIndex: prices.get(price)){
            Vehicle v = getVehicle(vIndex);
            System.out.println(count+". {"+v.getName()+"}-{"+v.getPrice()+"}");
            count++;
         }
      }    
   }

   public void printVehicleSippInfo(){
      int count=1;
      for(Vehicle v:list){
         System.out.println(count+". {"+v.getName()+"}-{"+v.getSippString()+"}-{"+v.getType()+"}-{"+v.getDoor()+"}-{"+v.getFuel()+"}-{"+v.getAC()+"}");
         count++;
      }
   }

   public void printBestSupplierEachType(){
      ArrayList<Integer> ratingsList =  new ArrayList<Integer>();
      ArrayList<String> typeList = new ArrayList<String>();
      HashMap<String,Boolean> typeCheck = new HashMap<String,Boolean>();

      typeList.addAll(types.keySet());
      ratingsList.addAll(ratings.keySet());

      Collections.sort(ratingsList);
      Collections.reverse(ratingsList);
      Vehicle v;

      int count = 0;
      int index=0;

      while(count<typeList.size()){
         Integer rating = ratingsList.get(index);
         String type = "";
         for(int i: ratings.get(rating)){
            v = list.get(i); 
            type = v.getType();
            if(typeCheck.get(type)!=null){
               continue;
            }
            else{
               typeCheck.put(type, true);
               count++;
               System.out.println(count+". {"+v.getName()+"}-{"+v.getType()+"}-{"+v.getSupplier()+"}-{"+v.getRating()+"}");
            }
         }
         index++;
      }
   }

   public void printOrderedScores(){
      ArrayList<Double> scoresList =  new ArrayList<Double>();
      HashMap<Double, ArrayList<Integer>> scoreMap = new HashMap<Double, ArrayList<Integer>>();

      int index = 0;
      for(Vehicle v:list){
         double sum = v.getRating()+v.getScore();

         ArrayList<Integer> indexList;
         indexList = scoreMap.get(sum);
         if(indexList==null){
            indexList = new ArrayList<Integer>();
            scoresList.add(sum);
         } 
         indexList.add(index);
         scoreMap.put(sum, indexList);
         index++;
      }


      Collections.sort(scoresList);
      Collections.reverse(scoresList);
      int count=1;
      for(double i:scoresList){
         for(int j:scoreMap.get(i)){
            Vehicle v = list.get(j);
            System.out.println(count+". {"+v.getName()+"}-{"+v.getScore()+"}-{"+v.getRating()+"}-{"+i+"}");
            count++;
         }
      }

   }

}

class Vehicle{
   char[] sipp;
   String name, supplier, type, door, transmission, fuel, airCon;
   double price, rating;
   int score;

   HashMap<Character, String> typeList, doorList , transmissionList;
   HashMap<Character, String[]> fuelList;


   public Vehicle(String sipp, String name, double price,  String supplier, double rating){
      buildLists();
      this.sipp = sipp.toCharArray();
      this.name = name;
      this.price = price;
      this.supplier = supplier;
      this.rating = rating;
      useSipp();
      buildScore();
   }

   private void buildLists(){
      typeList = new HashMap<Character,String>();
      doorList = new HashMap<Character,String>();
      transmissionList = new HashMap<Character,String>();
      fuelList = new HashMap<Character,String[]>();

      typeList.put('M',"Mini"); typeList.put('E',"Economy"); typeList.put('C',"Compact");
      typeList.put('I',"Intermediate"); typeList.put('S',"Standard"); typeList.put('F',"Full Size");
      typeList.put('P',"Premium"); typeList.put('L',"Luxury"); typeList.put('X',"Special");

      doorList.put('B',"2 Door"); doorList.put('C',"4 Door"); doorList.put('D',"5 Door");
      doorList.put('W',"Estate"); doorList.put('T',"Convertible"); doorList.put('F',"SUV");
      doorList.put('P',"Pick Up"); doorList.put('V',"Passenger Van");

      transmissionList.put('M',"Manual"); transmissionList.put('A',"Automatic");
      
      String[] n = {"Petrol","No AC"};
      String[] r = {"Petrol","AC"};
      fuelList.put('N',n); fuelList.put('R',r);
   }

   private void useSipp(){
      for(int i=0; i<sipp.length; i++){
         Character c = sipp[i];
         if(i==0){
            type = typeList.get(c);
         }
         if(i==1){
            door = doorList.get(c);
         }
         if(i==2){
            transmission = transmissionList.get(c);
         }
         if(i==3){
            String[] both = fuelList.get(c);
            fuel = both[0];
            airCon = both[1];
         }
      }
   }

   private void buildScore(){
      score = 0;
      if(transmission == transmissionList.get('M')){
         score= score + 1;
      }
      if(transmission == transmissionList.get('A')){
         score= score + 5;
      }
      if(airCon == fuelList.get('R')[1]){
         score = score + 2;
      }
   }

   public char[] getSipp(){
      return sipp;
   }
   public String getSippString(){
      return new String(sipp);
   }

   public String getName(){
      return name;
   }

   public double getPrice(){
      return price;
   }

   public String getSupplier(){
      return supplier;
   }

   public double getRating(){
      return rating;
   }

   public String getType(){
      return type;
   }

   public String getDoor(){
      return door;
   }

   public String getTransmission(){
      return transmission;
   }

   public String getFuel(){
      return fuel;
   }

   public String getAC(){
      return airCon;
   }

   public int getScore(){
      return score;
   }
}