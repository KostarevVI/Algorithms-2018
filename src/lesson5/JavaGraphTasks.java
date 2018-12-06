package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     * <p>
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        throw new NotImplementedError();
    }
//        for (Graph.Vertex vertex : graph.getVertices()) {
//
//        }
//    }
//
//    void cycle_search(u) {
//        for (берем любое непройденное ребро (u,v)) {
//            (u,v) – отметить и удалить из списка;
//            cycle_search(v);
//        }
//        вывести_вершину (u);
//    }


    /**
     * Минимальное остовное дерево.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     * G    H
     * |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     * <p>
     * Дан граф без циклов (получатель), например
     * <p>
     * G -- H -- J
     * |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     * <p>
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     * <p>
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     * <p>
     * В данном случае ответ (A, E, F, D, G, J)
     * <p>
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        Map<Set<Graph.Vertex>, Boolean> setsWithIndependence = new HashMap<>();
        for (Graph.Vertex vertex : graph.getVertices()) {
            setsWithIndependence.put(new HashSet<>(Collections.singleton(vertex)), true);
        }
        List<Set<Graph.Vertex>> previous = new ArrayList<>();
        for (Graph.Vertex vertex : graph.getVertices()) {
            previous.add(new HashSet<>(Collections.singleton(vertex)));
        }
        for (int i = 0; i < graph.getVertices().size() - 1; i++) {
            List<Set<Graph.Vertex>> newPrevious = new ArrayList<>();
            for (Set<Graph.Vertex> set : previous) {
                if (setsWithIndependence.get(set)) {
                    for (Graph.Vertex addingVertex : graph.getVertices()) {
                        Set<Graph.Vertex> newSet = new HashSet<>(set);
                        newSet.add(addingVertex);
                        if (!previous.contains(newSet) && !newPrevious.contains(newSet)) {
                            newPrevious.add(newSet);
                            setsWithIndependence.put(newSet, setsWithIndependence.get(set) && isIndependent(graph, set, addingVertex));
                        }
                    }
                }
            }
            previous = newPrevious;
        }
        int max = 0;
        Set<Graph.Vertex> maxSet = new HashSet<>();
        for (Map.Entry<Set<Graph.Vertex>, Boolean> entry : setsWithIndependence.entrySet()) {
            if (entry.getValue() && entry.getKey().size() > max) {
                max = entry.getKey().size();
                maxSet = entry.getKey();
            }
        }
        return maxSet;
    }

    //Трудоемкость - O(2^V*E), так как наиболее трудоемкой частью является перебор всех 2^V подмножеств множества вершин,
    //внутри которого вызывается функция проверки независимости элемента относительно множества трудоемкостью O(E),
    //Ресурсоемкость - O(2^V), потому что создатеся ассоциативный массив, размером в количество всех подмножеств

    private static boolean isIndependent(Graph graph, Set<Graph.Vertex> set, Graph.Vertex vertex) {
        for (Graph.Edge edge : graph.getEdges()) {
            if (edge.getBegin() == vertex && set.contains(edge.getEnd()) || edge.getEnd() == vertex && set.contains(edge.getBegin())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     * <p>
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     * <p>
     * Пример:
     * <p>
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        Set<List<Graph.Vertex>> maxPathSet = new HashSet<>();
        ArrayDeque<Graph.Vertex> visited = new ArrayDeque<>();
        for (Graph.Vertex vertex : graph.getVertices()) {
            DFS(vertex, visited, maxPathSet, graph);
        }

        List<Graph.Vertex> maxPath = new ArrayList<>(0);

        for(List<Graph.Vertex> path: maxPathSet){
            if(path.size()>maxPath.size()){
                maxPath = path;
            }
        }
        return new Path(maxPath, maxPath.size()-1);
    }

    private static void DFS(Graph.Vertex current, ArrayDeque<Graph.Vertex> visited,
                            Set<List<Graph.Vertex>> maxPathSet, Graph graph) {
        visited.push(current);
        if (!graph.getNeighbors(current).isEmpty()) {                               //если детей нет вообще
            boolean pathEnd = true;
            for (Graph.Vertex child : graph.getNeighbors(current)) {
                if (!visited.contains(child)) {
                    DFS(child, visited, maxPathSet, graph);
                    pathEnd = false;
                }
            }
            if(pathEnd){
                maxPathSet.add(new ArrayList<>(visited));
            }
        } else {
            maxPathSet.add(Collections.singletonList(current));
        }
        visited.remove();
    }
}
