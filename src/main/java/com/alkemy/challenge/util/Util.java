package com.alkemy.challenge.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Util {

    public static String format(String chain, String parameter){
        chain = String.format(chain,parameter);
        return chain;
    }

    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{

    }
}
