import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Collections;

public class Main {

    static String path="inputData/";
    static String fName1 = "data_a1.txt";
    static String fName2 = "data_b1.txt";
    static String fName3 = "data_c1.txt";

    public static void main(String[] args) throws IOException{
        //DATA1A
        Logger logger=new LoggerFile("data_a.log");
        BufferedReader br = new BufferedReader(new FileReader(path+fName1));
        String line1;
        ArrayList<Readout> dane1 = new ArrayList<>();
        while ((line1 = br.readLine()) != null) {
            line1 = line1.trim();
            dane1.add(new Readout(Double.parseDouble( line1)));
        }
        logger.flush();
        br.close();
        System.out.println(getOutputInfo(dane1,0,"Task I.I ",fName1));


        //DATA1B
        Logger logger2=new LoggerFile("data_b.log");
        BufferedReader br2 = new BufferedReader(new FileReader(path+fName2));
        String line2;
        int noOfInvalidRecords2 =0;
        ArrayList<Readout> dane2 = new ArrayList<>();
        while ((line2 = br2.readLine()) != null) {
            try{
                line2 = line2.trim();
                dane2.add(new Readout(Double.parseDouble( line2)));
            } catch (NumberFormatException e){
                noOfInvalidRecords2+=1;
                logger2.log(Logger.Level.ERROR, "Faulty record in " + fName2 + ": " + line2  );

            }
        }
        logger2.flush();
        br2.close();
        System.out.println(getOutputInfo(dane2,noOfInvalidRecords2,"Task I.II",fName2));


        //DATA1C
        Logger logger3=new LoggerFile("data_c.log");
        BufferedReader br3 = new BufferedReader(new FileReader(path+fName3));
        String line3;
        int noOfInvalidRecords3 =0;
        ArrayList<Readout> dane3 = new ArrayList<>();
        while ((line3 = br3.readLine()) != null) {
            try{
                line3 = line3.trim();
                String[] parts = line3.split("id");


                double value3 = Double.parseDouble(parts[0].trim());
                String uuid3 = parts[1].replace(":", "").trim();

                dane3.add(new ReadoutWithUuid(value3, uuid3));
            } catch (NumberFormatException e){
                noOfInvalidRecords3+=1;
                logger3.log(Logger.Level.ERROR, "Faulty record in " + fName3 + ": " + line3  );
            }
        }
        logger3.flush();
        br3.close();
        System.out.println(getOutputInfo(dane3,noOfInvalidRecords3,"Task II.II",fName3));

    }


    static String getOutputInfo(ArrayList<Readout> data, int noOfInvalidRecords, String title, String filename){
        String str;
        String separator= "\n-~-~-~-~-~-~-~-~-~-~-~-~-~-~-\n";
        str = title +
                "\nAnna Górecka, 300952" + separator +
                "Data filename: " + filename +
                "\nLength of the series: " + data.size() +
                 "\nMax value: " + getMax(data).toString() +
                "\nMin value: "+ getMin(data).toString() +
                String.format("\nMean value: %.3f", getMean(data)) +
                "\nMedian " + getMedian(data).toString()  +
                "\nNumber of central elements: " + noOfCentralElements(data);
        if(noOfInvalidRecords==0) {
            str += separator;
            return str;
        }
        else{
            str=str+"\nNumber of invalid records: " + noOfInvalidRecords + separator;
            return str;
        }
    }



    static Readout getMax(ArrayList<Readout> data){
        Collections.sort(data);
        return data.getLast();
    }

    static Readout getMin(ArrayList<Readout> data){
        Collections.sort(data);
        return data.getFirst();
    }
    static double getMean(ArrayList<Readout> data){
        double sum= 0;
        for (Readout datum : data) {
            sum += datum.getValue();
        }
        return sum/data.size();
    }

    static MedianWrapper getMedian(ArrayList<Readout> data){
       Collections.sort(data);
       int n = data.size();
       if (n % 2 == 0) {
           return new MedianWrapper(data.get(n/2-1), data.get(n/2));
       } else {
           return new MedianWrapper(data.get(n / 2));
       }
        }


   static int noOfCentralElements(ArrayList<Readout> data){
        double mean = getMean(data);
        int numberOf = 0 ;
        double epsilon= (getMax(data).getValue()-getMin(data).getValue())/100;
       for (Readout datum : data) {
           if (datum.getValue() - mean < epsilon && datum.getValue() - mean > -epsilon) {
               numberOf += 1;
           }}
        return numberOf;
   }

}
