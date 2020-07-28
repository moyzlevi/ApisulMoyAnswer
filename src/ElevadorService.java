import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ElevadorService implements IElevadorService {

	private static List<Survey> surveys = new ArrayList<>();
	private static JSONParser jsonparse = new JSONParser();

	private static void parserSurvey(JSONObject survey) {
		surveys.add(new Survey(Integer.parseInt((survey.get("andar").toString())),
				survey.get("turno").toString().charAt(0), survey.get("elevador").toString().charAt(0)));
	}

	public ElevadorService(String file) {

		try {
			Object obj = jsonparse.parse(new FileReader(file));
			JSONArray surveys = (JSONArray) obj;
			surveys.forEach(p -> parserSurvey((JSONObject) p));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public static List<Survey> getSurveys() {
		return surveys;
	}

	@Override
	public List<Integer> andarMenosUtilizado() {
		Map<Integer, Integer> andares = new HashMap<>();
		for (int i = 0; i <= 15; i++) {
			andares.put(i, 0);
		}

		for (Survey x : surveys) {
			for (int i = 0; i <= 15; i++) {
				if (x.getAndar() == i) {
					andares.put(i, andares.get(i) + 1);
				}
			}
		}

		Map<Integer, Integer> result = andares.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		List<Integer> menoresValores = new ArrayList<>();
		Integer[] vect = new Integer[15];
		int i = 0;
		for (Integer key : result.keySet()) {
			if (i < 10) {
				menoresValores.add(key);
			}
			i++;
		}

		return menoresValores;
	}

	@Override
	public List<Character> elevadorMaisFrequentado() {
		Map<Character, Integer> elevadores = new HashMap<>();
		elevadores.put('A', 0);
		elevadores.put('B', 0);
		elevadores.put('C', 0);
		elevadores.put('D', 0);
		elevadores.put('E', 0);

		for (Survey x : surveys) {
			if (x.getElevador() == 'A') {
				elevadores.put('A', elevadores.get('A') + 1);
			}
			if (x.getElevador() == 'B') {
				elevadores.put('B', elevadores.get('B') + 1);
			}
			if (x.getElevador() == 'C') {
				elevadores.put('C', elevadores.get('C') + 1);
			}
			if (x.getElevador() == 'D') {
				elevadores.put('D', elevadores.get('D') + 1);
			}
			if (x.getElevador() == 'E') {
				elevadores.put('E', elevadores.get('E') + 1);
			}
		}

		Map<Character, Integer> result = elevadores.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		List<Character> topElevadores = new ArrayList<>();
		Integer[] vect = new Integer[4];
		int i = 0;
		for (Character key : result.keySet()) {
			if (i > 2 && i <= 4) {
				topElevadores.add(key);
			}
			i++;
		}

		return topElevadores;
	}

	@Override
	public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
		Character[] topElevadores = new Character[2];
		List<Character> aux = elevadorMaisFrequentado();
		int i = 0;
		for (Character c : aux) {
			topElevadores[i] = c;
			i++;
		}
		Map<Character, Integer> periodoDoPrimeiro = new HashMap<>();
		periodoDoPrimeiro.put('M', 0);
		periodoDoPrimeiro.put('V', 0);
		periodoDoPrimeiro.put('N', 0);

		for (Survey x : surveys) {
			if(x.getElevador() == topElevadores[0]) {
				if (x.getTurno() == 'M' ) {
					periodoDoPrimeiro.put('M', periodoDoPrimeiro.get('M') + 1);
				}
				if (x.getTurno() == 'V') {
					periodoDoPrimeiro.put('V', periodoDoPrimeiro.get('V') + 1);
				}
				if (x.getTurno() == 'N') {
					periodoDoPrimeiro.put('N', periodoDoPrimeiro.get('N') + 1);
				}
			}
		}
		Map<Character, Integer> periodoDoSegundo = new HashMap<>();
		periodoDoSegundo.put('M', 0);
		periodoDoSegundo.put('V', 0);
		periodoDoSegundo.put('N', 0);
		for (Survey x : surveys) {
			if(x.getElevador() == topElevadores[1]) {
				if (x.getTurno() == 'M' ) {
					periodoDoSegundo.put('M', periodoDoSegundo.get('M') + 1);
				}
				if (x.getTurno() == 'V') {
					periodoDoSegundo.put('V', periodoDoSegundo.get('V') + 1);
				}
				if (x.getTurno() == 'N') {
					periodoDoSegundo.put('N', periodoDoSegundo.get('N') + 1);
				}
			}
		}

		Map<Character, Integer> result = periodoDoPrimeiro.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));

		Map<Character, Integer> result2 = periodoDoSegundo.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		
		List<Character> maioresPeriodosDosMaioresEle = new ArrayList<>();
		int j = 0;
		for (Character key : result.keySet()) {
			if (j >1) {
				maioresPeriodosDosMaioresEle.add(key);
			}
			j++;
		}
		int k = 0;
		for (Character key : result.keySet()) {
			if (k >1) {
				maioresPeriodosDosMaioresEle.add(key);
			}
			k++;
		}
		 
		 return maioresPeriodosDosMaioresEle;
	}

	@Override
	public List<Character> elevadorMenosFrequentado() {
		Map<Character, Integer> elevadores = new HashMap<>();
		elevadores.put('A', 0);
		elevadores.put('B', 0);
		elevadores.put('C', 0);
		elevadores.put('D', 0);
		elevadores.put('E', 0);

		for (Survey x : surveys) {
			if (x.getElevador() == 'A') {
				elevadores.put('A', elevadores.get('A') + 1);
			}
			if (x.getElevador() == 'B') {
				elevadores.put('B', elevadores.get('B') + 1);
			}
			if (x.getElevador() == 'C') {
				elevadores.put('C', elevadores.get('C') + 1);
			}
			if (x.getElevador() == 'D') {
				elevadores.put('D', elevadores.get('D') + 1);
			}
			if (x.getElevador() == 'E') {
				elevadores.put('E', elevadores.get('E') + 1);
			}
		}

		Map<Character, Integer> result = elevadores.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		List<Character> topElevadores = new ArrayList<>();
		Integer[] vect = new Integer[4];
		int i = 0;
		for (Character key : result.keySet()) {
			if (i < 2) {
				topElevadores.add(key);
			}
			i++;
		}

		return topElevadores;
	}

	@Override
	public List<Character> periodoMenorFluxoElevadorMenosFrequentado() {
		Character[] worstElevadores = new Character[2];
		List<Character> aux = elevadorMenosFrequentado();
		int i = 0;
		for (Character c : aux) {
			worstElevadores[i] = c;
			i++;
		}
		Map<Character, Integer> periodoDoPrimeiro = new HashMap<>();
		periodoDoPrimeiro.put('M', 0);
		periodoDoPrimeiro.put('V', 0);
		periodoDoPrimeiro.put('N', 0);

		for (Survey x : surveys) {
			if(x.getElevador() == worstElevadores[0]) {
				if (x.getTurno() == 'M' ) {
					periodoDoPrimeiro.put('M', periodoDoPrimeiro.get('M') + 1);
				}
				if (x.getTurno() == 'V') {
					periodoDoPrimeiro.put('V', periodoDoPrimeiro.get('V') + 1);
				}
				if (x.getTurno() == 'N') {
					periodoDoPrimeiro.put('N', periodoDoPrimeiro.get('N') + 1);
				}
			}
		}
		Map<Character, Integer> periodoDoSegundo = new HashMap<>();
		periodoDoSegundo.put('M', 0);
		periodoDoSegundo.put('V', 0);
		periodoDoSegundo.put('N', 0);
		for (Survey x : surveys) {
			if(x.getElevador() == worstElevadores[1]) {
				if (x.getTurno() == 'M' ) {
					periodoDoSegundo.put('M', periodoDoSegundo.get('M') + 1);
				}
				if (x.getTurno() == 'V') {
					periodoDoSegundo.put('V', periodoDoSegundo.get('V') + 1);
				}
				if (x.getTurno() == 'N') {
					periodoDoSegundo.put('N', periodoDoSegundo.get('N') + 1);
				}
			}
		}

		Map<Character, Integer> result = periodoDoPrimeiro.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));

		Map<Character, Integer> result2 = periodoDoSegundo.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		
		List<Character> menoresPeriodosDosMenoresEle = new ArrayList<>();
		int j = 0;
		for (Character key : result.keySet()) {
			if (j <1) {
				menoresPeriodosDosMenoresEle.add(key);
			}
			j++;
		}
		int k = 0;
		for (Character key : result.keySet()) {
			if (k <1) {
				menoresPeriodosDosMenoresEle.add(key);
			}
			k++;
		}
		 
		 return menoresPeriodosDosMenoresEle;
	}

	@Override
	public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
		Map<Character, Integer> periodos = new HashMap<>();
		periodos.put('M', 0);
		periodos.put('V', 0);
		periodos.put('N', 0);
		for (Survey x : surveys) {
			if (x.getTurno() == 'M') {
				periodos.put('M', periodos.get('M') + 1);
			}
			if (x.getTurno() == 'V') {
				periodos.put('V', periodos.get('V') + 1);
			}
			if (x.getTurno() == 'N') {
				periodos.put('N', periodos.get('N') + 1);
			}
			
		}

		Map<Character, Integer> result = periodos.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		List<Character> topPeriodo = new ArrayList<>();
		int i = 0;
		for (Character key : result.keySet()) {
			if (i >= 1) {
				topPeriodo.add(key);
			}
			i++;
		}

		return topPeriodo;
	}

	@Override
	public float percentualDeUsoElevadorA() {
		float total = surveys.size();
		
		Map<Character, Float> elevadores = new HashMap<>();
		elevadores.put('A', 0f);
		for (Survey x : surveys) {
			if (x.getElevador() == 'A') {
				elevadores.put('A', elevadores.get('A') + 1f);
			}
		}
		
		float percentual = elevadores.get('A')/total * 100;
		
		return percentual;
}

	@Override
	public float percentualDeUsoElevadorB() {
		float total = surveys.size();
		
		Map<Character, Float> elevadores = new HashMap<>();
		elevadores.put('B', 0f);
		for (Survey x : surveys) {
			if (x.getElevador() == 'B') {
				elevadores.put('B', elevadores.get('B') + 1f);
			}
		}
		
		float percentual = elevadores.get('B')/total * 100;
		
		return percentual;
}

	@Override
	public float percentualDeUsoElevadorC() {
		float total = surveys.size();
		
		Map<Character, Float> elevadores = new HashMap<>();
		elevadores.put('C', 0f);
		for (Survey x : surveys) {
			if (x.getElevador() == 'C') {
				elevadores.put('C', elevadores.get('C') + 1f);
			}
		}
		
		float percentual = elevadores.get('C')/total * 100;
		
		return percentual;
}

	@Override
	public float percentualDeUsoElevadorD() {
		float total = surveys.size();
		
		Map<Character, Float> elevadores = new HashMap<>();
		elevadores.put('D', 0f);
		for (Survey x : surveys) {
			if (x.getElevador() == 'D') {
				elevadores.put('D', elevadores.get('D') + 1f);
			}
		}
		
		float percentual = elevadores.get('D')/total * 100;
		
		return percentual;
}

	@Override
	public float percentualDeUsoElevadorE() {
		float total = surveys.size();
		
		Map<Character, Float> elevadores = new HashMap<>();
		elevadores.put('E', 0f);
		for (Survey x : surveys) {
			if (x.getElevador() == 'E') {
				elevadores.put('E', elevadores.get('E') + 1f);
			}
		}
		
		float percentual = elevadores.get('E')/total * 100;
		
		return percentual;
}

}
