package us.lsi.alg.bufete;

import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.AStarType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosBufete.iniDatos("ficheros/bufete" + id_fichero + ".txt");

			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			BufeteVertex start = BufeteVertex.initialVertex();
			Predicate<BufeteVertex> goal = BufeteVertex.goal();

			/**
			 * IMPORTANTE. En este tipo se usa el tipo "Last".
			 */
			EGraph<BufeteVertex, BufeteEdge> graph = 
					SimpleVirtualGraph.last(start,goal, v -> (double) v.maxCarga());
			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*

			AStar<BufeteVertex, BufeteEdge> aStar = AStar.of(graph, Heuristica::heuristic,AStarType.Min);
			GraphPath<BufeteVertex, BufeteEdge> gp_as = aStar.search().get(); // getEdgeList();
			
			SolucionBufete s_as = SolucionBufete.of(gp_as);
			System.out.println("maxCarga = "+s_as.maxCarga());
			System.out.println(s_as);
		}
	}
}
