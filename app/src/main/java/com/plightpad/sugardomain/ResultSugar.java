package com.plightpad.sugardomain;

import com.orm.SugarRecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mabak on 17.08.2017.
 */
@AllArgsConstructor
@Getter
@Setter
public class ResultSugar extends SugarRecord<ResultSugar> {
    String name;
    int wholeResult=0;
    List<Integer> eachLineResult;

    public ResultSugar(String name) {
        this.name = name;

    }
    public  ResultSugar(String name, int wholeResult){
        this.name = name;
        this.wholeResult = wholeResult;
    }
    public ResultSugar() {

    }
    public void addResult(int points){
        eachLineResult.add(points);
    }
    public void removeResult(){eachLineResult.remove(eachLineResult.size()-1);}
    public int lastValue(){
        return eachLineResult.get(eachLineResult.size()-1);
    }
}
