import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphA3<T>{


    public class Edge<T>{

        final private int weight;
        final private T destination;

        public Edge(T destination, int weight) {
            this.weight = weight;
            this.destination = destination;
        }

        public T getDest() {
            return destination;
        }

        public int getWeight(){
            return weight;
        }

    }

    private Map<T, List<Edge<T>>> graph;

    public GraphA3() {
        graph = new HashMap<>();
    }

    public void addVertex(T vertex){
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    public Set<T> getVertecies(){
        return graph.keySet();
    }

    public List<T> getAdjVertecies(T currentLocation) {
        List<T> adjVertecies = new ArrayList<>();
        for(Edge<T> v: graph.get(currentLocation)){
            adjVertecies.add(v.getDest());
        }
        return adjVertecies;
    }

    public void addEdge(T source, T dest, int weight){

        graph.get(source).add(new Edge<T>(dest, weight));
        graph.get(dest).add(new Edge<>(source, weight));

    }

    public void removeEdge(T source, T dest){

        graph.get(source).removeIf(edge -> edge.getDest().equals(dest));
        graph.get(dest).removeIf(edge -> edge.getDest().equals(source));

    }


    public void dijkstraShortestPath(T source, T dest){

        Map<T, T> parents = new HashMap<>();
        Map<T, Integer> dist = new HashMap<>();
        Queue<T> pq = new PriorityQueue<>(
                Comparator.comparing(dist::get)
        );

        dist.put(source, 0);
        for(T key: graph.keySet()){
            if(!key.equals(source)){
                dist.put(key, Integer.MAX_VALUE);
            }
            parents.put(key, null);
            pq.add(key);
        }


        while(!pq.isEmpty()){

            T minVertex = pq.poll();

            for(Edge<T> edge: graph.get(minVertex)){

                T adj = edge.getDest();
                int alt = dist.get(minVertex) + edge.getWeight();

                if(pq.contains(adj) && alt < dist.get(adj)){
                    pq.remove(adj);
                    parents.put(adj, minVertex);
                    dist.put(adj, alt);
                    pq.add(adj);
                }

            }
        }

        printSP(source, dest, parents, dist);

    }

    private void printSP(T s, T d, Map<T,T> parents, Map<T, Integer> keys) {

        List<T> path = new ArrayList<>();

        T curr = d;
        while(!path.contains(s)){
            path.add(0, curr);
            curr = parents.get(curr);
        }
        System.out.printf("The shortest path is: %s\nTotal travel distance: %d\n", path, keys.get(d));
    }

}
