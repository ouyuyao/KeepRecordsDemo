package org.otto.demo.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class FileManager {
    private Logger log= LoggerFactory.getLogger(FileManager.class);

    public Map<String,String> readFile(){
        Map<String,String> infoMap = new HashMap<String,String>();
        String path = new FileManager().getClass().getClassLoader().getResource("").getPath();
        File file = new File(path+Constants.FILENAME);
        if (file.exists()&& file.isFile()){
            BufferedReader br = null;
            String str = null;
            try {
                br = new BufferedReader(new FileReader(file));
                while ((str = br.readLine())!= null) {
                    String[] split = str.split(Constants.SPACE);
                    if (StringUtils.isNotBlank(split[0])&&StringUtils.isNotBlank(split[1])){
                        double insertAMT = 0;
                        if (StringUtils.isNotBlank(infoMap.get(split[0]))){
                            insertAMT = Double.parseDouble(split[1]) + Double.parseDouble(infoMap.get(split[0]));
                        }else{
                            insertAMT = Double.parseDouble(split[1]);
                        }
                        infoMap.put(split[0],String.valueOf(insertAMT));
                    }
                }
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return infoMap;
    }

    public StringBuilder readFileWithCurrency(String currencyCode){
        StringBuilder readResult = new StringBuilder();
        String path = new FileManager().getClass().getClassLoader().getResource("").getPath();
        File file = new File(path+Constants.FILENAME);
        if (file.exists()&& file.isFile()){
            BufferedReader br = null;
            String str = null;
            try {
                br = new BufferedReader(new FileReader(file));
                while ((str = br.readLine())!= null) {
                    if (str.indexOf(currencyCode)==0){
                        String[] split = str.split(Constants.SPACE);
                        if (StringUtils.isNotBlank(split[0])&&StringUtils.isNotBlank(split[1])){
                            readResult.append( split[0] + "  " + split[1] );
                            readResult.append(Constants.LINE_FEED);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            }catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return readResult;
    }

    public StringBuilder readFileGetSummary(String currencyCode){
        StringBuilder readResult = new StringBuilder();
        String path = new FileManager().getClass().getClassLoader().getResource("").getPath();
        File file = new File(path+Constants.FILENAME);
        if (file.exists()&& file.isFile()){
            BufferedReader br = null;
            String str = null;
            try {
                br = new BufferedReader(new FileReader(file));
                double insertAMT = 0;
                boolean existCurrencyCode = false;
                while ((str = br.readLine())!= null) {
                    if (str.indexOf(currencyCode)==0) {
                        String[] split = str.split(Constants.SPACE);
                        if (StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[1])) {
                            insertAMT = insertAMT + Double.parseDouble(split[1]);
                            existCurrencyCode = true;
                        }
                    }
                }
                if (existCurrencyCode){
                    readResult.append( currencyCode + "  " + String.valueOf(insertAMT) );
                    readResult.append(Constants.LINE_FEED);
                }

            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            }catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return readResult;
    }

    public void writeFile(Map<String,String> infoMap) {
        String path = new FileManager().getClass().getClassLoader().getResource("").getPath();
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter ( new FileWriter( path+Constants.FILENAME , true ));
            for (Map.Entry<String, String> entry : infoMap.entrySet()) {
                bw.write( entry.getKey() + Constants.SPACE + entry.getValue());
                bw.newLine();
            }
            bw.flush();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally{
            if(bw != null){
                try{
                    bw.close();
                }catch (IOException ioe){
                    log.error(ioe.getMessage());
                }
            }
        }
    }
}
