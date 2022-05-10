package lesson4

import java.util.*


/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: SortedMap<Char, Node> = sortedMapOf()
    }

    private val root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> =
        TrieIterator()


    inner class TrieIterator internal constructor() : MutableIterator<String> {

        private var words = arrayListOf<String>()
        private var next = ""

        init {
            traverse("", root)
        }

        private fun traverse(word: String, node: Node) {
            for (child in node.children.keys)               //T(N) = O(N); R(N) = O(N), где N - число узлов в дереве
                if (child == 0.toChar()) words += word
                else traverse(word + child, node.children[child]!!)
        }

        override fun hasNext(): Boolean = words.isNotEmpty()   //T(N) = O(1); R(N) = O(1)

        override fun next(): String {       //T(N) = O(1); R(N) = O(1)
            if (!hasNext())
                throw NoSuchElementException()
            next = words[0]
            words.removeAt(0)
            return next
        }

        override fun remove() {             //T(N) = O(1); R(N) = O(1)
            if (next == "")
                throw IllegalStateException()
            remove(next)
            next = ""
        }
    }
}
// Comments:
//(88): T(N) = O(N) - тк проверку проходит каждый эл-т; R(N) = O(N) - тк добавляем в массив элементы в линейном цикле
//(93,95,103): T(N) = O(1); R(N) = O(1) - ничего не создаем, код без циклов
//Summary: T(N) = O(N); R(N) = O(N) - трудо- и ресурсо- емкости обуславливаются инициализацией итератора, его методы же имеют константные показатели времени и памяти