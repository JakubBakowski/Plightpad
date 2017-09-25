package com.plightpad.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Szczypiorek on 17.07.2017.
 */

public class MiloszekDecryptorUtils {

    private static final Integer MILOSZEKS_CONSTANT = 378223;
    private static final Integer MILOSZEKS_CONSTANT2 = 421149;

    public static String encode(List<String> parameters) {
        StringBuilder wholeCode = new StringBuilder();
        Boolean situation = false;
        parameters.forEach(s -> {
                    s.toCharArray();
                    List<char[]> charArray = Arrays.asList(s.toCharArray());
                    List<Character> listC = new ArrayList<Character>();
                    for (char c : s.toCharArray()) {
                        listC.add(c);
                    }
                    listC.forEach(k -> {
                        if((int)k % 2 == 0){
                            wholeCode.append(String.valueOf((int)k*MILOSZEKS_CONSTANT));
                        }else{
                            wholeCode.append(String.valueOf((int)k*MILOSZEKS_CONSTANT2));
                        }
                    });
                }
        );
        return wholeCode.toString();
    }


}
