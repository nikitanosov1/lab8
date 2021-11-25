import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.meta.Composition;
import threads.*;

import java.io.*;
import java.util.Random;


public class Main {
    public static void main(String[] args){
       TabulatedFunction a = new ArrayTabulatedFunction(-121,1023,8);
        for (FunctionPoint p : a){
            System.out.println(p);
        }
    }
}