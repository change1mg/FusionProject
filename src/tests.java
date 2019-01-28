import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import domain.NeedCheck;

public class tests {

   public static void main(String[] args) {
      File f1;
      LinkedList<NeedCheck> needCheckList = new LinkedList<NeedCheck>();

      String a = " ";
      FileReader fr;
      try {
         f1 = new File("C:\\Users\\abcab\\OneDrive\\바탕 화면\\식자재소요량산출.csv");
         fr = new FileReader(f1);

         BufferedReader br = new BufferedReader(fr);

         try {
        	 
        	 
            while ((a = br.readLine()) != null) {

               boolean flag = true;
               String[] array = a.split(",");

               NeedCheck nc = new NeedCheck();

               for (int i = 0; i < needCheckList.size(); i++) {
                  if (array[0].trim().equals(needCheckList.get(i).getDate())
                        && array[1].trim().equals(needCheckList.get(i).getPlace())
                        && array[2].trim().equals(needCheckList.get(i).getIngredientName())) {
                     needCheckList.get(i).setNeed(needCheckList.get(i).getNeed() + Integer.parseInt(array[3]));
                     flag = false;
                  }
               }
               if (flag == true) {
                  nc.setDate(array[0].trim());
                  nc.setPlace(array[1].trim());
                  nc.setIngredientName(array[2].trim());
                  nc.setNeed(Integer.parseInt(array[3].trim()));
                  nc.setUnit(array[4].trim());
                  needCheckList.add(nc);
               }

               
//                for (int i = 0; i < needCheckList.size(); i++) {
//                        System.out.println(needCheckList.get(i).getDate() + " " + needCheckList.get(i).getPlace() + " "
//                              + needCheckList.get(i).getIngredientName() + " " + needCheckList.get(i).getNeed() + " "
//                              + needCheckList.get(i).getUnit());
//                     }
            }
         } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
}