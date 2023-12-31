package us.lsi.alg.pack;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Data {
	
	public static List<Integer> volumenesObjetos;
	public static Integer n;
	public static Integer volumenContenedor;
	public static Integer m; 
	
	public static void data(String file, Integer volumenContenedor) {
		Data.volumenesObjetos = Files2.streamFromFile(file).map(e->Integer.parseInt(e)).collect(Collectors.toList());
		Collections.sort(Data.volumenesObjetos,Comparator.reverseOrder());
		Data.n = Data.volumenesObjetos.size();
		Data.volumenContenedor = volumenContenedor;
	}

}
