class Ex4{
	public static void main(String args[]){
		int num;


		num = 9;
		System.out.println( fibonacci( num ) );
	}


	public static int fibonacci(int num){


		if( ( num == 0 )){
			return 0;
		}

		if( ( num == 1 )){
			return 1;
		}

		return ( fibonacci( ( num - 1 ) ) + fibonacci( ( num - 2 ) ) );
	}

}