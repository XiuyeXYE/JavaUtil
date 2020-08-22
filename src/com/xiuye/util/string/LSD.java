package com.xiuye.util.string;

import com.xiuye.sharp.X;
import com.xiuye.util.log.XLog;

import java.util.Arrays;

public class LSD {

    public static void sort(String []a,int W){
        int N = a.length;
        int R = 256;
        String []aux = new String[N];
        for(int d=W-1;d>=0;d--){
            int []count = new int[R+1];
            for(int i=0;i<N;i++){//计算频率
                count[a[i].charAt(d)+1]++;
            }

//            for(int i=0;i< count.length;i++){
//                XLog.print(count[i]," ");
//            }
//            XLog.println();

            for(int r=0;r<R;r++){//frequency to index
                count[r+1] += count[r];
            }
//            for(int i=0;i< count.length;i++){
//                XLog.print(count[i]," ");
//            }
//            XLog.println();
            for (int i=0;i<N;i++){//classify
                aux[count[a[i].charAt(d)]++] = a[i];
            }
//            for(int i=0;i< N;i++){
//                XLog.print(aux[i]," ");
//            }
//            XLog.println();
            for(int i=0;i<N;i++){//rewrite back
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args) {

        String []str = {
//                "A",
//                "B",
//                "C"
          "4PGC938",
          "2IYE230",
          "3CI0720",
          "1ICK750",
          "10HV845",
          "4JZY524",
          "1ICK750",
          "3CI0720",
          "10HV845",
          "10HV845",
          "2RLA629",
          "2RLA629",
          "3ATW723"

        };
        sort(str,1);

        for(int i=0;i<str.length;i++){
            X.lnS(str[i]);
        }
        X.lnS(str);

    }

}
