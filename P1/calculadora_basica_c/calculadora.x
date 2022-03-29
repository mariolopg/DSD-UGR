union operacion_result switch(int errno){
	case 0:
		double result;
	case 1:
		void;
};

program CALCULADORA {
	version CALCULADORA_1 {
		operacion_result sumar (double, double) = 1;
		operacion_result restar (double, double) = 2;
		operacion_result multiplicar (double, double) = 3;
		operacion_result dividir (double, double) = 4;
		operacion_result potencia (double, double) = 5;
		operacion_result raiz (double, double) = 6;
		operacion_result factorial (double) = 7;
		operacion_result modulo (double, double) = 8;
		operacion_result vAbsoluto (double) = 9;
	} = 1;
} = 0x20000155;
