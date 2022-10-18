package springbook.learningtest.template.before;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator3_32 {

    public Integer calcSum(String filepath) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        Integer sum = 0;
        String line = null;

        while((line = br.readLine()) != null){
            sum += Integer.valueOf(line);
        }

        br.close();
        return sum;
    }

}
