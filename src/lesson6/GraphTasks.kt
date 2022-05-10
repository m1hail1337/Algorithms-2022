@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson6


/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {               //DFS
    var current: Graph.Edge
    val result = mutableListOf<Graph.Edge>()        //R(N) = O(N), где N - кол-во ребер
    val vertices = mutableListOf(vertices.firstOrNull() ?: return result)   //R(N) = O(N)
    var connections = getConnections(vertices.first())      //R(N) = O(k), зависит от ребер у конкретной вершины
    result += connections.values.lastOrNull() ?: return result
    var maybe = vertices.first()
    while (connections.values.isNotEmpty() || getConnections(maybe).size >= 4) {   //T(N) = O(N - n) + n*O(k/2)
        if (getConnections(maybe).size % 2 == 1 && getConnections(maybe).size != 1) break
        if (connections.values.isEmpty()) {         //Ситуация, когда вершина в листе vertices повторяется может
            connections = getConnections(maybe)     //возникнуть только тогда, когда у этой вершины не менее 4 ребер,
            vertices += maybe                       //и минимум одно из них ведет дальше по правильному циклу
        } else {
            current = connections.values.last()
            if (current.end in vertices) {
                if (current.begin !in vertices) {
                    vertices += current.begin
                    connections = getConnections(current.begin)
                }
            } else {
                vertices += current.end
                connections = getConnections(current.end)
            }
            maybe = if (vertices.last() == current.end) current.begin else current.end
            if (current !in result)
                result += current
            connections.values -= current
        }
    }
    if (result.size == 1 || result.size != edges.size) result.clear()
    return result
}
//Comments:
//(36): R(N) = O(N), где N - кол-во вершин, приблизительно, тк могут быть повторы
//(40): T(N) = O(N - n) + n*O(k/2), где N - кол-во ребер в графе, k - кол-во соседей у вершин с 4 и более ребрами,...
//(40): ...n - кол-во таких вершин, установлено, что в такие вершины алгоритм попадает с частотой k/2
//(41): Ускоряем алгоритм используя лемму о рукопожатиях(если есть вершины с нечетной степенью эйлерова цикла нет)
//(56): В случае "тупика" проверяем еще раз вершину *maybe*
//(57): Для графа с двумя вершинами и 1 ребром между ними инвариант не выполняется
//Summary: T(N) = O(N - n) + n*O(k/2) = O(N) - оценка снизу; R(N) = O(N) - Создаем несколько листов для промежуточных результатов

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
fun Graph.minimumSpanningTree(): Graph {
    TODO()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    TODO()
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
//Алгоритм ноходит самый длинный путь из *каждой* вершины и сохраняет в *best* лучший результат
fun Graph.longestSimplePath(): Path {           //DFS
    val paths = mutableListOf<Path>()       // T(N) = O(N), где N - кол-во вершин, а k - кол-во соседей у самой
    var best = Path()
    vertices.forEach { paths += Path(it) }  // T(N) = O(N), где N - кол-во вершин
    while (paths.isNotEmpty() && vertices.size != best.vertices.size) {   //Немного ускоряем алгоритм второй проверкой
        val current = paths.last()
        if (current.length > best.length)
            best = current
        for (neighbour in getNeighbors(current.vertices.last()))    //T(N) = O(k), где k - кол-во соседей
            if (neighbour !in current.vertices)
                paths += Path(current, this, neighbour)
        paths -= current
    }
    return best
}
//Comments:
//(158): T(N) = O(N!), где N - кол-во вершин, факториал т.к. решение имеет структору, похожую на решение задачи коммивояжера (Voyager.kt)
//



/**
 * Балда
 * Сложная
 *
 * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
 * поэтому задача присутствует в этом разделе
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}
