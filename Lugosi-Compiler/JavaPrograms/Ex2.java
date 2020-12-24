class Ex2{
	public static void main(String args[]){
		boolean positivo;
		int num1;
		int num2;
		int num3;


		positivo = false;
		num1 = 67;
		num2 = ( 0 - 44 );
		num3 = 89;
		if( ( ( ( num1 > 0 ) && ( num2 > 0 ) ) || ( num3 > 0 ) )){
			System.out.println( 2 );
		}

		if( ( num1 > 0 )){
			positivo = true;
			System.out.println( positivo );
		}

		positivo = false;
		System.out.println( positivo );
		System.out.println( num1 );
		while( ( num1 < 0 )){
			num1 = ( num1 + 1 );
		}

		num1 = ( num1 + 1 );
		positivo = true;
		System.out.println( positivo );
		System.out.println( num1 );
		if( ( num2 > 0 )){
			positivo = true;
			System.out.println( positivo );
		}

		positivo = false;
		System.out.println( positivo );
		System.out.println( num2 );
		while( ( num2 < 0 )){
			num2 = ( num2 + 1 );
		}

		num2 = ( num2 + 1 );
		positivo = true;
		System.out.println( positivo );
		System.out.println( num2 );
		if( ( num3 > 0 )){
			positivo = true;
			System.out.println( positivo );
		}

		positivo = false;
		System.out.println( positivo );
		System.out.println( num3 );
		while( ( num3 < 0 )){
			num3 = ( num3 + 1 );
		}

		num3 = ( num3 + 1 );
		positivo = true;
		System.out.println( positivo );
		System.out.println( num3 );
	}


}