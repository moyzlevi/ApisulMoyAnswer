public class Main {

	public static void main(String[] args) {
		
	ElevadorService es = new ElevadorService("src\\input.json");
		System.out.println(es.percentualDeUsoElevadorE());
	}

}
