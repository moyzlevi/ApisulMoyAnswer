public class Main {

	public static void main(String[] args) {

		ElevadorService es = new ElevadorService("src\\input.json");
		System.out.println("Andares menos utilizados:");
		for (Integer x : es.andarMenosUtilizado()) {
			System.out.println(x);
		}
		System.out.println("----------------------------------------------");
		System.out.println("Elevadores mais frequentados:");
		for (Character x : es. elevadorMaisFrequentado()) {
			System.out.println(x);
		}
		System.out.println("----------------------------------------------");
		System.out.println("Periodos mais frequentados dos mais frequentados elevadores:");
		for (Character x : es. periodoMaiorFluxoElevadorMaisFrequentado()) {
			System.out.println(x);
		}
		System.out.println("----------------------------------------------");
		System.out.println("Elevadores menos frequentados:");
		for (Character x : es.elevadorMenosFrequentado() ) {
			System.out.println(x);
		}
		System.out.println("----------------------------------------------");
		System.out.println("Menores periodos dos elevadores menos frequentados:");
		for (Character x : es.periodoMenorFluxoElevadorMenosFrequentado() ) {
			System.out.println(x);
		}
		System.out.println("----------------------------------------------");
		System.out.println("Periodos de maior utilização dos elevadores:");
		for (Character x : es.periodoMaiorUtilizacaoConjuntoElevadores() ) {
			System.out.println(x);
		}
		System.out.println("----------------------------------------------");
		System.out.println("Percentual de uso elevador A:");
		System.out.println(es.percentualDeUsoElevadorA());
		System.out.println("----------------------------------------------");
		System.out.println("Percentual de uso elevador B:");
		System.out.println(es.percentualDeUsoElevadorB());
		System.out.println("----------------------------------------------");
		System.out.println("Percentual de uso elevador C:");
		System.out.println(es.percentualDeUsoElevadorC());
		System.out.println("----------------------------------------------");
		System.out.println("Percentual de uso elevador D:");
		System.out.println(es.percentualDeUsoElevadorD());
		System.out.println("----------------------------------------------");
		System.out.println("Percentual de uso elevador E:");
		System.out.println(es.percentualDeUsoElevadorE());
	}

}
