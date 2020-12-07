class Ex3{
	public static void main(String args[]){
		float B;
		float b;
		float h;
		float i;
		float ret;
		float Area_Trapezio;


		B = 2.98E-4f;
		b = 2147483647;
		h = 90000000;
		Area_Trapezio = ( ( ( B + b ) / 2 ) * h );
		System.out.println( Area_Trapezio );
		calcAreaQuadrado( B );
		calcAreaQuadrado( b );
		calcAreaQuadrado( h );
		ret = calcAreaRetangulo( b, h );
		i = 1.5f;
		do
		{
			System.out.println( calcAreaQuadrado( i ) );
			System.out.println( calcAreaRetangulo( i, i ) );
			i = ( i + 1 );
		}while(( i < 10 ));


	}
	public static float calcAreaQuadrado(float lado){
		float area;


		area = ( lado * lado );
		return area;
	}
	public static float calcAreaRetangulo(float base, float altura){
		float area3;


		area3 = ( base * altura );
		return area3;
	}
}