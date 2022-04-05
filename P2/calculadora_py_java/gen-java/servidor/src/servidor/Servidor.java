/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package servidor;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import static servidor.CalculadoraHandler.processor;

/**
 *
 * @author mariolopezgonzalez
 */

class CalculadoraHandler implements Calculadora.Iface{
    
    public static Calculadora.Processor processor;
   
    @Override
    public double suma(double num1, double num2) throws TException {
        double result = num1 + num2;
        System.out.println("La operacion realizada es una suma --> " + num1 + " + " + num2 + " = " + result);
        return result;
    }

    @Override
    public double resta(double num1, double num2) throws TException {
        double result = num1 - num2;
        System.out.println("La operacion realizada es una resta --> " + num1 + " - " + num2 + " = " + result);
        return result;
    }

    @Override
    public double multiplicacion(double num1, double num2) throws TException {
        double result = num1 * num2;
        System.out.println("La operacion realizada es una multiplicacion --> " + num1 + " * " + num2 + " = " + result);
        return result;
    }

    @Override
    public double division(double num1, double num2) throws TException {
        double result = num1 / num2;
        System.out.println("La operacion realizada es una division --> " + num1 + " / " + num2 + " = " + result);
        return result;
    }

    @Override
    public double potencia(double num1, double num2) throws TException {
        double result = Math.pow(num1, num2);
        System.out.println("La operacion realizada es una potencia --> " + num1 + " ^ " + num2 + " = " + result);
        return result;
    }

    @Override
    public double raiz(double num1, double num2) throws TException {
        double result = Math.pow(num2, 1.0 / num1);
        System.out.println("La operacion realizada es una potencia --> " + num1 + " √ " + num2 + " = " + result);
        return result;
    }

    @Override
    public double factorial(double num1) throws TException {
        double result = 1;
        for(int i = 2; i <= num1; i++)
            result *= i;
        System.out.println("La operacion realizada es un factorial --> " + num1 + "! = " + result);
        return result;
    }

    @Override
    public double modulo(double num1, double num2) throws TException {
        double result = num1 % num2;
        System.out.println("La operacion realizada es un modulo --> " + num1 + " % " + num2 + " = " + result);
        return result;
    }

    @Override
    public double vabsoluto(double num1) throws TException {
        double result = Math.abs(num1);
        System.out.println("La operacion realizada es un valor absoluto --> abs(" + num1 + ") = " + result);
        return result;
    }

}

public class Servidor {

  public static CalculadoraHandler handler;

  public static Calculadora.Processor processor;

  public static void main(String[] args) {
        try{
            handler = new CalculadoraHandler();
            processor = new Calculadora.Processor(handler);
            
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            System.out.println("Iniciando servidor...");
            server.serve();
        } catch (Exception e) { e.printStackTrace(); }
    }
  
}
