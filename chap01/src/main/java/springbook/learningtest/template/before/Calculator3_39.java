package springbook.learningtest.template.before;

import springbook.learningtest.template.BufferedReaderCallback;
import springbook.learningtest.template.LineCallBack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator3_39 {

    public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException{

        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filepath));
            int ret = callback.doSomethingWithReader(br);
            return ret;
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

    public Integer lineReadTemplate(String filepath, LineCallBack callBack, int initVal) throws IOException{

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer res = initVal;
            String line = null;

            while((line = br.readLine()) != null){
               // res = callBack.doSomethingWithLine(line, res); // 각 라인의 내용을 가지고 계산하는 작업을 call에게 맡긴다. callback이 계산한 값을 res에 저장해두었다가 다음 라인에서 다시 계산
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
        BufferedReaderCallback sumCallback =
                new BufferedReaderCallback() {
                    @Override
                    public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                        Integer sum = 0;
                        String line = null;
                        while((line = br.readLine()) != null){
                            sum += Integer.valueOf(line);
                        }
                        return sum;
                    }
                };

        return fileReadTemplate(filepath, sumCallback);
    }


    public Integer calcMultiply(String filepath) throws IOException {

        BufferedReaderCallback multiplyCallback =
                new BufferedReaderCallback() {
                    @Override
                    public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                        Integer multiply = 1;
                        String line = null;
                        while ((line = br.readLine()) != null){
                            multiply *= Integer.valueOf(line);
                        }
                        return multiply;
                    }
                };

        return fileReadTemplate(filepath, multiplyCallback);
    }

}
