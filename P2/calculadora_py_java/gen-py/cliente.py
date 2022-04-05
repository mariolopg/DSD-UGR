from calculadora import Calculadora

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

transport = TSocket.TSocket("localhost", 9090)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)

client = Calculadora.Client(protocol)

transport.open()

result = 0

print("***********************************")
print("       CALCULADORA EN PYTHON       ")
print("***********************************")
print(" ")

while True:
    print("¿Que operacion quiere realizar?");
    print(" + : Para realizar una suma");
    print(" - : Para realizar una resta");
    print(" * : Para realizar una multiplicacion");
    print(" / : Para realizar una division");
    print(" ^ : Para realizar una potencia");
    print(" v : Para realizar una raiz");
    print(" ! : Para realizar un factorial");
    print(" % : Para realizar un modulo")
    print(" a : Para realizar un valor absoluto");
    print(" s : Para salir")

    while True:
        operador = input()
        if(len(operador) == 1 and (operador == "+" or operador == "-" or operador == "*" or operador == "/" or operador == "^" or operador == "v" or operador == "!" or operador == "%" or operador == "s" or operador == "a")):
            break;
        print("Por favor introduzca un caracter valido")

    if(operador == "s"):
        break

    if(operador == "+" or operador == "-" or operador == "*" or operador == "/" or operador == "%" ):
        print("Introduzca el primer numero");
        n1 = float(input())
        
        print("Introduzca el segundo numero");
        n2 = float(input())

        if(operador == "+"):
            result = client.suma(n1, n2)
            print("La operacion realizada es una suma --> " + str(n1) + " + " + str(n2) + " = " + str(result))

        if(operador == "-"):
            result = client.resta(n1, n2)
            print("La operacion realizada es una resta --> " + str(n1) + " - " + str(n2) + " = " + str(result))

        if(operador == "*"):
            result = client.multiplicacion(n1, n2)
            print("La operacion realizada es una multiplicacion --> " + str(n1) + " * " + str(n2) + " = " + str(result))

        if(operador == "/"):
            while n2 == 0:
                print("Error. Introduzca un divisor distinto de 0");
                n2 = float(input())
            result = client.division(n1, n2)
            print("La operacion realizada es una division --> " + str(n1) + " / " + str(n2) + " = " + str(result))
        
        if(operador == "%"):
            result = client.modulo(n1, n2)
            print("La operacion realizada es una modulo --> " + str(n1) + " % " + str(n2) + " = " + str(result))
        
    if(operador == "^"):
        print("Introduzca la base");
        n1 = float(input())
        
        print("Introduzca el exponente");
        n2 = float(input())

        result = client.potencia(n1, n2)
        print("La operacion realizada es una potencia --> " + str(n1) + " ^ " + str(n2) + " = " + str(result))

    if(operador == "v"):
        print("Introduzca el indice");
        n1 = float(input())

        while(n1 < 2):
            print("Error. Introduzca un indice mayor o igual a dos")
            n1 = float(input())

        print("Introduzca el radicando");
        n2 = float(input())

        result = client.raiz(n1, n2)
        print("La operacion realizada es una raiz --> " + str(n1) + " √ " + str(n2) + " = " + str(result))

    if(operador == "!" or operador == "a"):
        print("Introduzca el numero");
        n1 = float(input())

        if(operador == "!"):
            while (n1 < 0):
                print("Error. Por favor introduzca un valor mayor o igual a uno")
                n1 = float(input())
                
            result = client.factorial(n1)
            print("La operacion realizada es una factorial --> " + str(n1) + "! = " + str(result))

        if(operador == "a"):
            result = client.vabsoluto(n1)
            print("La operacion realizada es un valor absoluto --> abs(" + str(n1) + ") = " + str(result))

    print(" ")
    print("**********************************************")
    print(" ")





transport.close()
