package practicalsample.menu

/**
 * Triển khai Menu với thành phần RestorableMenuItem đơn lẻ.
 * Nếu route không được khai báo thì mặc định là "<Item>"
 */
data class RestorableMenuItem<T>(val data: T, val route: String = "<Item>") : Menu<T> {
    override var parent: Menu<T>? = null

    override fun printStructure(indent: String) {
        println("$indent$route: $data")
    }

    override fun find(predicate: (Menu<T>) -> Boolean): Menu<T>? {
        return if (predicate(this)) this else null
    }
}

/**
 * Triển khai RestorableMenu, lớp composite chứa các thành phần RestorableMenuItem hoặc RestorableMenu.
 * Nếu route không được khai báo thì mặc định là "<Menu>"
 * RestorableMenu được triển khai với Memento có thể lưu trữ và khôi phục trạng thái
 * của Menu:
 * - restore(memento): xóa toàn bộ danh sách children hiện tại và thay thế bằng snapshot đã lưu
 * - deepCopy(): tạo một bản sao độc lập của cây Menu
 */
class RestorableMenu<T>(val route: String = "<Menu>", override var parent: Menu<T>? = null) : Menu<T> {
    private val children = mutableListOf<Menu<T>>()

    fun add(data: T, route: String? = null) {
        val item = RestorableMenuItem(data, route ?: "<Item>")
        item.parent = this
        children.add(item)
    }

    fun add(menu: RestorableMenu<T>) {
        menu.parent = this
        children.add(menu)
    }

    fun removeItem(predicate: (T) -> Boolean): Boolean {
        var removed = false
        val iterator = children.iterator()
        while (iterator.hasNext()) {
            when (val child = iterator.next()) {
                is RestorableMenuItem -> {
                    if (predicate(child.data)) {
                        iterator.remove()
                        removed = true
                    }
                }

                is RestorableMenu -> {
                    if (child.removeItem(predicate)) {
                        removed = true
                    }
                }
            }
        }
        return removed
    }

    override fun printStructure(indent: String) {
        if (parent == null) {
            printMenu("", "")
        } else {
            printMenu(indent, indent)
        }
    }

    private fun printMenu(currentPrefix: String, currentIndent: String) {
        println(currentPrefix + route)
        children.forEachIndexed { i, child ->
            val isLastChild = (i == children.size - 1)
            val branchSymbol = if (isLastChild) "└─ " else "├─ "
            val childPrefix = currentIndent + branchSymbol
            val nextIndent = currentIndent + if (isLastChild) "   " else "│  "
            when (child) {
                is RestorableMenuItem -> child.printStructure(childPrefix)
                is RestorableMenu -> child.printMenu(childPrefix, nextIndent)
            }
        }
    }

    override fun find(predicate: (Menu<T>) -> Boolean): Menu<T>? {
        if (predicate(this)) return this
        for (child in children) {
            val found = child.find(predicate)
            if (found != null) return found
        }
        return null
    }

    /**
     * Memento lưu trữ trạng thái của danh sách children
     */
    data class Memento<T>(val childrenSnapshot: List<Menu<T>>)

    /**
     * Tạo một snapshot (deep copy) của trạng thái hiện tại.
     */
    fun createMemento(): Memento<T> {
        val snapshot = children.map { child ->
            when (child) {
                is RestorableMenu -> child.deepCopy()
                is RestorableMenuItem -> child.copy().also { it.parent = null }
                else -> child
            }
        }
        return Memento(snapshot)
    }

    /**
     * Khôi phục trạng thái từ memento.
     */
    fun restore(memento: Memento<T>) {
        children.clear()
        // Khi restore, cập nhật lại parent cho từng thành phần.
        memento.childrenSnapshot.forEach { child ->
            when (child) {
                is RestorableMenu -> {
                    child.parent = this
                    children.add(child)
                }

                is RestorableMenuItem -> {
                    child.parent = this
                    children.add(child)
                }

                else -> children.add(child)
            }
        }
    }

    /**
     * Deep copy của RestorableMenu:
     * tạo một bản sao độc lập của cây con hiện tại.
     */
    fun deepCopy(): RestorableMenu<T> {
        val copy = RestorableMenu<T>(route)
        children.forEach { child ->
            when (child) {
                is RestorableMenu -> {
                    val childCopy = child.deepCopy()
                    childCopy.parent = copy
                    copy.children.add(childCopy)
                }

                is RestorableMenuItem -> {
                    val itemCopy = child.copy()
                    itemCopy.parent = copy
                    copy.children.add(itemCopy)
                }

                else -> copy.children.add(child)
            }
        }
        return copy
    }
}

//////////////////////////////////////////////////////////////////////
fun main() {
    // Khai báo Menu
    val rootMenu = RestorableMenu<String>(route = "Main Menu")
    rootMenu.add("Home", route = "homeroute")
    rootMenu.add("About")
    val productsMenu = RestorableMenu<String>()
    productsMenu.add("Product A")
    productsMenu.add("Product B", route = "routeB")
    val servicesMenu = RestorableMenu<String>(route = "Services")
    servicesMenu.add("Service A")
    servicesMenu.add("Service B")
    productsMenu.add(servicesMenu)
    rootMenu.add(productsMenu)

    // Cấu trúc Menu
    println("===== Menu Structure =====")
    rootMenu.printStructure()

    // Lưu trạng thái hiện tại của menu trước khi xóa (tạo memento)
    val savedState = rootMenu.createMemento()

    // Ví dụ: xóa item có dữ liệu "Product A"
    val removed = rootMenu.removeItem { it == "Product A" }
    println("\n===== Sau khi xóa 'Product A' (removed: $removed) =====")
    rootMenu.printStructure()

    // Ví dụ: xóa item có dữ liệu "Product B"
    val removed1 = rootMenu.removeItem { it == "Product B" }
    println("\n===== Sau khi xóa 'Product B' (removed: $removed1) =====")
    rootMenu.printStructure()

    // Ví dụ: xóa item có dữ liệu "Home"
    val removed2 = rootMenu.removeItem { it == "Home" }
    println("\n===== Sau khi xóa 'Home' (removed: $removed2) =====")
    rootMenu.printStructure()

    // Khôi phục lại trạng thái menu từ memento đã lưu
    rootMenu.restore(savedState)
    println("\n===== Sau khi khôi phục trạng thái =====")
    rootMenu.printStructure()
}
