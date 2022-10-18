package springbook.learningtest.template.before;

import springbook.learningtest.template.before.LineCallBack3_41;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator3_42 {

    public Integer lineReadTemplate(String filepath, LineCallBack3_41 callBack, int initVal) throws IOException{

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer res = initVal;
            String line = null;

            while((line = br.readLine()) != null){
                res = callBack.doSomethingWithLine(line, res); // 각 라인의 내용을 가지고 계산하는 작업을 call에게 맡긴다. callback이 계산한 값을 res에 저장해두었다가 다음 라인에서 다시 계산
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null){
                try{
                    br.close();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    public Integer calcSum(String filepath) throws IOException{

        LineCallBack3_41 sumCallBack =
                new LineCallBack3_41(){
                    @Override
                    public Integer doSomethingWithLine(String line, Integer value) {
                        return value + Integer.valueOf(line);
                    }
                };
        return lineReadTemplate(filepath, sumCallBack, 0);
    }


    public Integer calcMultiply(String filepath) throws IOException {

        LineCallBack3_41 multiplyCallback =
                new LineCallBack3_41() {
                    @Override
                    public Integer doSomethingWithLine(String line, Integer value) {
                        return value * Integer.valueOf(line);
                    }
                };

        return lineReadTemplate(filepath, multiplyCallback, 1);
    }

}
