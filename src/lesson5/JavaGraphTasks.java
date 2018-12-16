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
     *      G -- H
     *      |    |
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
        List<Graph.Edge> eilerPath = new ArrayList<>();
        Set<Graph.Edge> visited = new HashSet<>();
        ArrayDeque<Graph.Vertex> verticesDeque = new ArrayDeque<>();

        for (Graph.Vertex vertex : graph.getVertices()) {
            int neighbors = graph.getNeighbors(vertex).size();
            if (neighbors % 2 == 1 || neighbors == 0) {
                return eilerPath;
            }
        }

        verticesDeque.addFirst(graph.getVertices().iterator().next());
        while (graph.getEdges().size() != eilerPath.size()) {
            Graph.Edge curEdge = null;
            Graph.Vertex vertex = verticesDeque.getFirst();
            for (Graph.Edge edge : graph.getConnections(vertex).values()) {
                if (!visited.contains(edge)) {
                    curEdge = edge;
                }
            }
            if (curEdge != null) {
                if (vertex == curEdge.getBegin()) {
                    verticesDeque.addFirst(curEdge.getEnd());
                } else {
                    verticesDeque.addFirst(curEdge.getBegin());
                }
                visited.add(curEdge);
                eilerPath.add(curEdge);
            }
        }
        return eilerPath;
    }
    //Трудоемкость - O(n)
    //Ресурсоёмкость - O(n)
    //где n - кол-во рёбер графа

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
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     *      G    H
     *      |    |
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
        throw new NotImplementedError();
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

        for (List<Graph.Vertex> path : maxPathSet) {
            if (path.size() > maxPath.size()) {
                maxPath = path;
            }
        }
        return new Path(maxPath, maxPath.size() - 1);
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
            if (pathEnd) {
                maxPathSet.add(new ArrayList<>(visited));
            }
        } else {
            maxPathSet.add(Collections.singletonList(current));
        }
        visited.remove();
    }
    //Трудоемкость - O(n*m)
    //где n - кол-во вершин графа графа, а m - кол-во соседей у каждой из вершин
    //Ресурсоёмкость - O(p)
    //где p - кол-во возможных путей
}
