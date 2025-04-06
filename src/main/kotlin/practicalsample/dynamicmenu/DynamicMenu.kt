package practicalsample.dynamicmenu

/**
 * Cấu trúc Dynamic Menu được triển khai theo Composite Pattern
 */
interface DynamicMenu<T> {
    var parent: DynamicMenu<T>? // con trỏ tới phần tử cha
    fun printStructure(indent: String = "") // in cấu trúc cây từ phần tử hiện tại (chức năng này được sử dụng cho mục đích diễn họa cấu trúc cây)
    fun find(predicate: (DynamicMenu<T>) -> Boolean): DynamicMenu<T>? // tìm phần tử trong cấu trúc cây thỏa mãn điều kiện
    fun getRoot(): DynamicMenu<T> = parent?.getRoot() ?: this // đệ quy tìm phần tử cao nhất khác null
}

/**
 * Triển khai cấu trúc Dynamic Menu với thành phần MenuItem đơn lẻ
 * Nếu route không được khai báo thì sẽ được mặc định là <Item>
 */
data class MenuItem<T>(val data: T, val route: String = "<Item>") : DynamicMenu<T> {
    override var parent: DynamicMenu<T>? = null

    override fun printStructure(indent: String) {
        println("$indent$route: $data")
    }

    override fun find(predicate: (DynamicMenu<T>) -> Boolean): DynamicMenu<T>? {
        return if (predicate(this)) this else null
    }
}

/**
 * Triển khai cấu trúc Dynamic Menu với
 * Composite class Menu chứa các thành phần MenuItem hoặc Menu
 * Nếu route không được khai báo thì sẽ được mặc định là <Menu>
 */
class Menu<T>(val route: String = "<Menu>", override var parent: DynamicMenu<T>? = null) : DynamicMenu<T> {
    private val children = mutableListOf<DynamicMenu<T>>()

    // add Item
    fun add(data: T, route: String? = null) {
        val item = MenuItem(data, route ?: "<Item>")
        item.parent = this
        children.add(item)
    }

    // add Menu
    fun add(menu: Menu<T>) {
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
                is MenuItem -> child.printStructure(childPrefix)
                is Menu -> child.printMenu(childPrefix, nextIndent)
            }
        }
    }

    override fun find(predicate: (DynamicMenu<T>) -> Boolean): DynamicMenu<T>? {
        if (predicate(this)) return this
        for (child in children) {
            val found = child.find(predicate)
            if (found != null) return found
        }
        return null
    }
}

/////////////////////////////////////////////////////////////////

//fun main() {
//    // Tạo Menu gốc
//    val rootMenu = Menu<String>(route = "Main Menu")
//
//    // Thêm các mục đơn giản vào menu gốc
//    rootMenu.add("Home", route = "homeroute")
//    rootMenu.add("About")
//
//    // Tạo Menu con "Products"
////    val productsMenu = Menu<String>(route = "Products")
//    val productsMenu = Menu<String>()
//    productsMenu.add("Product A")
//    productsMenu.add("Product B")
//
//    // Tạo Menu con lồng "Services" bên trong "Products"
//    val servicesMenu = Menu<String>(route = "Services")
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
//        it is MenuItem && it.data == "Service A"
//    }
//
//    println("\n===== Search Result =====")
//    if (found != null && found is MenuItem) {
//        println("Found route: ${found.route}")
//        println("Found item: ${found.data}")
//        println("Parent menu: ${(found.parent as? Menu)?.route}")
//        println("Root menu: ${(found.getRoot() as Menu).route}")
//    } else {
//        println("Item not found")
//    }
//
//    // Tìm menu "Services"
//    val foundMenu = rootMenu.find {
//        it is Menu && it.route == "Services"
//    }
//
//    println("\n===== Found Menu =====")
//    if (foundMenu != null && foundMenu is Menu) {
//        println("Found menu: ${foundMenu.route}")
//        println("Parent menu: ${(foundMenu.parent as? Menu)?.route}")
//    }
//}
