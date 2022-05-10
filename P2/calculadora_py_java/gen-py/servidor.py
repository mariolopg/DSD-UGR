import glob
import sys

from calculadora import Calculadora

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import logging

logging.basicConfig(level=logging.DEBUG)


class CalculadoraHandler:
    def __init__(self):
        self.log = {}

    def suma(self, n1, n2):
        result = n1 + n2
        print("La operacion realizada es una suma --> " + str(n1) + " + " + str(n2) + " = " + str(result))
        return result

    def resta(self, n1, n2):
        result = n1 - n2
        print("La operacion realizada es una resta --> " + str(n1) + " + " + str(n2) + " = " + str(result))
        return result

    def multiplicacion(self, n1, n2):
        result = n1 * n2
        print("La operacion realizada es una multiplicacion --> " + str(n1) + " * " + str(n2) + " = " + str(result))
        return result

    def division(self, n1, n2):
        result = n1 / n2
        print("La operacion realizada es una division --> " + str(n1) + " / " + str(n2) + " = " + str(result))
        return result

    def potencia(self, n1, n2):
        result = n1 ** n2
        print("La operacion realizada es una potencia --> " + str(n1) + " ^ " + str(n2) + " = " + str(result))
        return result

    def raiz(self, n1, n2):
        result = n2 ** (1.0 / n1)
        print("La operacion realizada es una raiz --> " + str(n1) + " √ " + str(n2) + " = " + str(result))
        return result

    def factorial(self, n1):
        result = 1
    # Modificado por error en cálculo
        for i in range(2, int(n1) + 1):
            result = result * i

        print("La operacion realizada es una factorial --> " + str(n1) + "! = " + str(result))
        return result

    def modulo(self, n1, n2):
        result = n1 % n2
        print("La operacion realizada es una modulo --> " + str(n1) + " % " + str(n2) + " = " + str(result))
        return result

    def vabsoluto(self, n1):
        result = n1
        if(n1 < 0):
            result = -n1

        print("La operacion realizada es un valor absoluto --> abs(" + str(n1) + ") = " + str(result))
        return result

if __name__ == "__main__":
    handler = CalculadoraHandler()
    processor = Calculadora.Processor(handler)
    transport = TSocket.TServerSocket(host="127.0.0.1", port=9090)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    print("iniciando servidor...")
    server.serve()
    print("fin")
