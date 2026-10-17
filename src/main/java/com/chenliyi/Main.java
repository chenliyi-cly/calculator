package com.chenliyi;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator cal = new Calculator();
        System.out.println("Input first number:");
        double n1 = sc.nextDouble();
        System.out.println("Input operator (+ - * /):");
        String op = sc.next();
        System.out.println("Input second number:");
        double n2 = sc.nextDouble();
        double res;
        try{
            switch(op){
                case "+": res = cal.add(n1,n2); break;
                case "-": res = cal.sub(n1,n2); break;
                case "*": res = cal.mul(n1,n2); break;
                case "/": res = cal.div(n1,n2); break;
                default: System.out.println("Invalid operator"); return;
            }
            System.out.println("Result = " + res);
        }catch (ArithmeticException e){
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}
