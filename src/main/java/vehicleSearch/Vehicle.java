package vehicleSearch; 
import java.util.*;

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