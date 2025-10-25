import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Collections;


public class Main {

    static String Path1 = "inputData/data_a1.txt";
    static String Path4 = "inputData/data_b1.txt";

    public static void main(String[] args) throws IOException{
        //DATA1A
        BufferedReader br = new BufferedReader(new FileReader(Path1));
        String line1;
        ArrayList<Double> dane1 = new ArrayList<>();
        while ((line1 = br.readLine()) != null) {
            line1 = line1.trim();
            dane1.add(Double.valueOf(line1));
        }
        System.out.println(getOutputInfo(dane1,0,"Task I.I \nAnna Górecka, 300952",Path1.replace("inputData/","")));


        //DATA1B
        BufferedReader br2 = new BufferedReader(new FileReader(Path4));
        String line2;
        int noOfInvalidRecords2 =0;
        ArrayList<Double> dane2 = new ArrayList<>();
        while ((line2 = br2.readLine()) != null) {
            try{
                line2 = line2.trim();
                dane2.add(Double.valueOf(line2));
            } catch (NumberFormatException e){
                noOfInvalidRecords2+=1;
            }
        }
        System.out.println(getOutputInfo(dane2,noOfInvalidRecords2,"Task I.II",Path4.replace("inputData/","")));

    }

    //moje wcześniejsze printing + noOfInvalidRecords
    static String getOutputInfo(ArrayList<Double> data, int noOfInvalidRecords, String title, String filename){
        String str;
        String separator= "\n-~-~-~-~-~-~-~-~-~-~-~-~-~-~-\n";
        str = title + separator +
                "Data filename: " + filename +
                "\nLength of the series: " + data.size() +
                String.format("\nMax value: %.3f", getMax(data)) +
                String.format("\nMin value: %.3f", getMin(data)) +
                String.format("\nMean value: %.3f", getMean(data)) +
                String.format("\nMedian %.3f", getMedian(data)) +
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

    static double getMax(ArrayList<Double> data){
        Collections.sort(data);
        return data.getLast();
    }

    static double getMin(ArrayList<Double> data){
        Collections.sort(data);
        return data.getFirst();
    }
    static double getMean(ArrayList<Double> data){
        double sum= 0;
        for (Double datum : data) {
            sum += datum;
        }
        return sum/data.size();
    }

   static double getMedian(ArrayList<Double> data){
        if (data.size() % 2 == 0){
            return (data.get(data.size()/2-1)+data.get(data.size()/2))/2;
        }else{
            return data.get((data.size()/2));
        }
   }

   static int noOfCentralElements(ArrayList<Double> data){
        double mean = getMean(data);
        int numberOf = 0 ;
        double epsilon= (getMax(data)-getMin(data))/100;
       for (Double datum : data) {
           if (datum - mean < epsilon && datum - mean > -epsilon) {
               numberOf += 1;
           }}
        return numberOf;
   }



}