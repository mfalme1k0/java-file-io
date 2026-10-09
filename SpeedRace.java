import java.io.*;

public class SpeedRace {


  static void copyPlain(String source, String target) throws IOException {
      try(
              FileInputStream in = new FileInputStream(source);
              FileOutputStream out  = new FileOutputStream(target);
              ){
          int value;
          while((value = in.read())!= -1){
              out.write(value);
          }
      }catch (IOException e){
          System.out.println("Error" + e.getMessage());
      }

  }
  //copying using a buffer
  static void copyBuffered(String source, String target) throws IOException{
     try(
             BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream(source));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target));
             ){
         int value;
         while((value = inputStream.read()) != -1){
             outputStream.write(value);
         }

     } catch (IOException e){
         System.out.println("Error" + e.getMessage());
     }

  }



}