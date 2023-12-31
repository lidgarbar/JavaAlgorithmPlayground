package us.lsi.alg.productos;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosProductos.iniDatos("ficheros/productos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			ProductosVertex start = ProductosVertex.initial();
			Predicate<ProductosVertex> goal = ProductosVertex.goal();

			EGraph<ProductosVertex, ProductosEdge> graph = 
					SimpleVirtualGraph.sum(start, goal,x -> x.weight());

			GraphPath<ProductosVertex, ProductosEdge> path = 
					ProductosHeuristic.graphPathVoraz(start,goal);
			List<Integer> la = path.getEdgeList().stream().map(e->e.action()).toList();
			
			System.out.println("\n\n#### Algoritmo BT ####");

			// Algoritmo BT
			BackTracking<ProductosVertex, ProductosEdge,SolucionProductos> bta = BackTracking.of(graph, 
					ProductosHeuristic::heuristic,
					ProductosVertex::getSolucion, 
					BTType.Min);
			
			bta.bestValue = path.getWeight();
			bta.solutions.add(SolucionProductos.of(la));
			
			GraphPath<ProductosVertex, ProductosEdge> gp = bta.optimalPath!=null?bta.optimalPath:path;
			bta.withGraph = true;
			bta.search();
			
			System.out.println(bta.getSolution());
					
			GraphColors.toDot(bta.graph(),"ficheros/productosBTGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,goal.test(v)),
					e->GraphColors.colorIf(Color.red,gp.getEdgeList().contains(e))
					);

		}
	}

}

