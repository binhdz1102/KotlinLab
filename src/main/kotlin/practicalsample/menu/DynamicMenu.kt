package practicalsample.menu

/**
 * Triển khai Menu với thành phần DynamicMenuItem đơn lẻ.
 * Nếu route không được khai báo thì mặc định là "<Item>"
 */
data class DynamicMenuItem<T>(val data: T, val route: String = "<Item>") : Menu<T> {
    override var parent: Menu<T>? = null

    override fun printStructure(indent: String) {
        println("$indent$route: $data")
    }

    override fun find(predicate: (Menu<T>) -> Boolean): Menu<T>? {
        return if (predicate(this)) this else null
    }
}

/**
 * Triển khai DynamicMenu, lớp composite chứa các thành phần DynamicMenuItem hoặc DynamicMenu.
 * Nếu route không được khai báo thì mặc định là "<Menu>"
 */
class DynamicMenu<T>(val route: String = "<Menu>", override var parent: Menu<T>? = null) : Menu<T> {
    private val children = mutableListOf<Menu<T>>()

    fun add(data: T, route: String? = null) {
        val item = DynamicMenuItem(data, route ?: "<Item>")
        item.parent = this
        children.add(item)
    }

    fun add(menu: DynamicMenu<T>) {
        menu.parent = this
        children.add(menu)
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
                is DynamicMenuItem -> child.printStructure(childPrefix)
                is DynamicMenu -> child.printMenu(childPrefix, nextIndent)
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
}

//////////////////////////////////////////////////////////////////////
//fun main() {
//    // Tạo Menu gốc
//    val rootMenu = DynamicMenu<String>(route = "Main Menu")
//
//    // Thêm các mục đơn giản vào menu gốc
//    rootMenu.add("Home", route = "homeroute")
//    rootMenu.add("About")
//
//    // Tạo DynamicMenu con "Products" (sử dụng route mặc định "<Menu>")
//    val productsMenu = DynamicMenu<String>()
//    productsMenu.add("Product A")
//    productsMenu.add("Product B", route = "routeB")
//
//    // Tạo DynamicMenu con lồng "Services" bên trong "Products"
//    val servicesMenu = DynamicMenu<String>(route = "Services")
//    servicesMenu.add("Service A")
//    servicesMenu.add("Service B")
//
//    // Thêm "Services" vào "Products"
//    productsMenu.add(servicesMenu)
//
//    // Thêm "Products" vào menu gốc
//    rootMenu.add(productsMenu)
//
//    // In toàn bộ cấu trúc menu
//    println("===== Menu Structure =====")
//    rootMenu.printStructure()
//
//    // Tìm một phần tử có dữ liệu là "Service A"
//    val found = rootMenu.find {
//        it is DynamicMenuItem && it.data == "Service A"
//    }
//
//    println("\n===== Search Result =====")
//    if (found != null && found is DynamicMenuItem) {
//        println("Found route: ${found.route}")
//        println("Found item: ${found.data}")
//        println("Parent menu: ${(found.parent as? DynamicMenu)?.route}")
//        println("Root menu: ${(found.getRoot() as DynamicMenu).route}")
//    } else {
//        println("Item not found")
//    }
//}
