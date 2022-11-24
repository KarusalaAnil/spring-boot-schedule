package com.springbootschedule.Util;

//import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class CommonUtil {

//    private T t;

    public static String generatorCode(int count,String prefixCode){

        String code="";
        if(count ==0)
            code = prefixCode+"00001";
        else{
//            int count = (list.size()+1);
            code = prefixCode;
            if(count <=9){
                code += "0000"+count;
            }else if(count >9)
                code += "000"+count;
            else if(count >99)
                code += "00"+count;
        }

        return code;

    }
}
