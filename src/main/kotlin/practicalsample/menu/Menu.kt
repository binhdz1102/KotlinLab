package practicalsample.menu

/**
 * Cấu trúc Menu được triển khai theo Composite Pattern
 */
interface Menu<T> {
    var parent: Menu<T>? // con trỏ tới phần tử cha
    fun printStructure(indent: String = "") // in cấu trúc cây từ phần tử hiện tại (dùng để diễn họa cấu trúc cây)
    fun find(predicate: (Menu<T>) -> Boolean): Menu<T>? // tìm phần tử trong cấu trúc cây thỏa mãn điều kiện
    fun getRoot(): Menu<T> = parent?.getRoot() ?: this // đệ quy tìm phần tử gốc (không có parent)
}