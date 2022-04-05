/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

/**
 *
 * @author mariolopezgonzalez
 */
public class Cliente {
    
    private static void perform(Calculadora.Client client) throws TException{


    }
    
    public static void main(String [] args) {

    if (args.length != 1) {
      System.out.println("Please enter 'simple' or 'secure'");
      System.exit(0);
    }

    try {
      TTransport transport;
      if (args[0].contains("simple")) {
        transport = new TSocket("localhost", 9090);
        transport.open();
      }
      else {
        /*
         * Similar to the server, you can use the parameters to setup client parameters or
         * use the default settings. On the client side, you will need a TrustStore which
         * contains the trusted certificate along with the public key. 
         * For this example it's a self-signed cert. 
         */
        TSSLTransportParameters params = new TSSLTransportParameters();
        params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
        /*
         * Get a client transport instead of a server transport. The connection is opened on
         * invocation of the factory method, no need to specifically call open()
         */
        transport = TSSLTransportFactory.getClientSocket("localhost", 9091, 0, params);
      }

      TProtocol protocol = new  TBinaryProtocol(transport);
      Calculadora.Client client = new Calculadora.Client(protocol);

      perform(client);

      transport.close();
    } catch (TException x) {
      x.printStackTrace();
    } 
  }
}
