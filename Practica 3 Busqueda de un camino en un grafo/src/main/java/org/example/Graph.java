package org.example;
import java.util.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Graph<V>{
    // Lista de adyacencia
    private Map<V, Set<V>> adjacencyList = new HashMap<>();
    // private HashSet<V> verticesList = new HashSet<V>();
    // En este primer método estamos controlando si los vértices ya están o no en el conjunto de vértices. En caso de que no estén
    // creamos un conjunto vacío donde guardarlos mediante la interfaz HashSet para crear conjuntos sin repeticiones.
    public boolean addVertex(V v){
        if(adjacencyList.containsKey(v)){
            return false;
        }
        adjacencyList.put(v, new HashSet<>());
        return true;
    }
    // En este método tenemos que añadir los arcos entre los vértices del grafo v1 y v2. En caso de que los vertices no estén los
    // añadimos y retornamos true. Y si los vertices ya están, entonces generamos el arco entre v1 y v2.
    public boolean addEdge(V v1, V v2){
        boolean vertex1Exists = adjacencyList.containsKey(v1);
        boolean vertex2Exists = adjacencyList.containsKey(v2);

        if(!vertex1Exists){
            addVertex(v1);
        }
        if(!vertex2Exists){
            addVertex(v2);
        }
        Set<V> adyacentes = adjacencyList.get(v1);
        // Si existe una 'key-value pair' entre V1 y V2 entonces existe un arco, sino lo añadimos. Es decir, si hay un mapeo de la llave de V1 a la llave de V2 que es el valor de la dupla
        // llave - valor entonces existe el arco.
        if(adyacentes.contains(v2)){
            return false;
        } else{
            adyacentes.add(v2);
            return true;
        }
    }
    // En este método tenemos que obtener el conjunto conteniendo todos los vértices adyacentes del grafo. Si el vértice V no está
    // entonces se lanza un excepción avisándonos de que el vértice no está en el conjunto de vértices. En cambio, si se contiene a V
    // entonces retornamos el propio conjunto de aydacentes.
    public Set<V> obtainAdjacents(V v) throws Exception{
        if(!adjacencyList.containsKey(v)){
            throw new Exception("El vértice que se ha introducido no está en el grafo");
        } else{
            return adjacencyList.get(v);
        }
    }
    public boolean containsVertex(V v){
        if(adjacencyList.containsKey(v)){
            return true;
        } else{
            return false;
        }
    }
    public String toString(){
        Set<V> adyacentes = adjacencyList.get(v1);
        StringBuilder stb1 = new StringBuilder();
        for(V vertex: adjacencyList.keySet()){
            stb1.append(vertex.toString()).append(": ");
            for(V adyacente: adyacentes){
                stb1.append(adyacente.toString()).append(" ");
            }
            stb1.append("\n");
        }
        return stb1.toString();
    }
    // Este método se ocupa de obtener un camino entre v1 y v2 en caso de que exista, y en caso de que no exista devuelve 'null'. Aquí aplicamos directamente el pseudocódigo de la práctica
    public List<V> onePath(V v1, V v2){
        // Vamos a definir la tabla llamada "traza", una pila llamada abierta.
        HashMap<V,V> traza = new HashMap<>();
        Stack<V> abierta = new Stack<>();
        List<V> path = new ArrayList<>();
        abierta.push(v1);
        traza.put(v1, null);
        boolean encontrado = false;

        while(!abierta.isEmpty() && (!encontrado)){
            V actual = abierta.pop();
            encontrado = actual.equals(v2);
            if(!encontrado){
                Set<V> adyacentes = adjacencyList.get(actual);
                if(adyacentes != null){
                    for(V adyacente: adyacentes){
                        if(!traza.containsKey(adyacente)){
                            abierta.push(adyacente);
                            traza.put(adyacente, actual);
                        }
                    }
                }
            }
        }
        if(encontrado){
            V actual = v2;
            while(actual != null){
                path.add(0, actual);
                actual = traza.get(actual);
            }
        }
        // Usamos el operador ternario CONDICION ? VALOR_SI_VERDADERO : VALOR_SI_FALSO.
        return encontrado ? path : null;
    }
    @Test
    public void onePathFindsAPath(){
        System.out.println("\nTest onePathFindsAPath");
        System.out.println("----------------------");
        // Se construye el grafo.
        Graph<Integer> g = new Graph<>();
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addEdge(1, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 4);
        // Se construye el camino esperado.
        List<Integer> expectedPath = new ArrayList<Integer>();
        expectedPath.add(1);
        expectedPath.add(5);
        expectedPath.add(6);
        expectedPath.add(4);
        //Se comprueba si el camino devuelto es igual al esperado.
        assertEquals(expectedPath, g.onePath(1, 4));
    }
}



