package com.facishare.openapi;

import com.facishare.openapi.common.workFlows;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

/**
 * Created by sunsk on 2017/8/7.
 */
public class dataManage {

    @DataProvider
    public static Object[][] testDate( ) {
        Object[][] a=new String[workFlows.WorkFlowscope.values().length][1];
        System.out.println(a.length);
        int i=0;
        for (workFlows.WorkFlowscope e : workFlows.WorkFlowscope.values()) {

            a[i][0]=e.toString();
            i++;

        }

        return a;
    }

    @DataProvider
    public static Object[][] testNum( ) {

        Object[][] a=new String[workFlows.wordNum.values().length][2];
        System.out.println(a.length);
        int i=0;
        for (workFlows.wordNum e : workFlows.wordNum.values()) {

            a[i][0]=e.toString();
            a[i][1]=e.toName();
            i++;

        }

        return a;
    }
}
