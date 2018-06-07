package sample;


public class HelperClass {

    public static int numberCeiling(double number){
        int NumberToCeiling = (int)number;

        if(NumberToCeiling!=number)
            return (NumberToCeiling+1) ;
        else
            return  NumberToCeiling;
    }
}

