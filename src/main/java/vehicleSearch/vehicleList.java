package vehicleSearch; 
import java.util.*;

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

   public String printSortedPrices(){
      String out = "";
      ArrayList<Integer> listPrices = new ArrayList<Integer>();
      listPrices.addAll(prices.keySet());
      Collections.sort(listPrices);
      int count=1;
      for(Integer price:listPrices){
         for(Integer vIndex: prices.get(price)){
            Vehicle v = getVehicle(vIndex);
            out = out + count+". {"+v.getName()+"}-{"+v.getPrice()+"}\n";
            count++;
         }
      }  
      return out;  
   }

   public String printVehicleSippInfo(){
      String out="";
      int count=1;
      for(Vehicle v:list){
         out = out + count+". {"+v.getName()+"}-{"+v.getSippString()+"}-{"+v.getType()+"}-{"+v.getDoor()+"}-{"+v.getFuel()+"}-{"+v.getAC()+"}\n";
         count++;
      }
      return out; 
   }

   public String printBestSupplierEachType(){
      String out =""; 
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
               out = out +count+". {"+v.getName()+"}-{"+v.getType()+"}-{"+v.getSupplier()+"}-{"+v.getRating()+"}\n";
            }
         }
         index++;
      }
      return out;
   }

   public String printOrderedScores(){
      String out=""; 
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
            out = out + count+". {"+v.getName()+"}-{"+v.getScore()+"}-{"+v.getRating()+"}-{"+i+"}\n";
            count++;
         }
      }
      return out;
   }

}