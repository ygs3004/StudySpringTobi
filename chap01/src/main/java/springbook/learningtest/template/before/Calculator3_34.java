package springbook.learningtest.template.before;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator3_34 {

    public Integer calcSum(String filepath) throws IOException{

        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filepath));
            Integer sum = 0;
            String line = null;

            while((line = br.readLine()) != null){
                sum += Integer.valueOf(line);
            }
            return sum;

        }catch (IOException e){
            System.out.println(e.getMessage());
            throw e;

        }finally {
            if(br != null){ // BufferedReader 오브젝트가 생성되기 전에 예외 발생을 할 수 있으므로 null 체크
                try{ br.close();} 
                catch(IOException e) { System.out.println(e.getMessage()); }
            }
        }
    }

}
