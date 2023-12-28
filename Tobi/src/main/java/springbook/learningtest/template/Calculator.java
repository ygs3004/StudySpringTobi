package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public <T> T lineReadTemplate(String filepath, LineCallBack<T> callBack, T initVal) throws IOException{

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
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

        LineCallBack<Integer> sumCallBack =
                new LineCallBack<Integer>(){
                    @Override
                    public Integer doSomethingWithLine(String line, Integer value) {
                        return value + Integer.valueOf(line);
                    }
                };
        return lineReadTemplate(filepath, sumCallBack, 0);
    }


    public Integer calcMultiply(String filepath) throws IOException {

        LineCallBack<Integer> multiplyCallback =
                new LineCallBack<Integer>() {
                    @Override
                    public Integer doSomethingWithLine(String line, Integer value) {
                        return value * Integer.valueOf(line);
                    }
                };

        return lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public String concatenate(String filepath) throws IOException {

        LineCallBack<String> concatenateCallback =
                new LineCallBack<String>() {
                    @Override
                    public String doSomethingWithLine(String line, String value) {
                        return value+line;
                    }
                };

        return lineReadTemplate(filepath, concatenateCallback, "");
    }

}
