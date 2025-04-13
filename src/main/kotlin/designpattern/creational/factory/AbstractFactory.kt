package designpattern.creational.factory

/**
 * Abstract Factory là cách triển khai 1 giao diện để tạo
 * nhóm các đối tượng liên quan tới nhau mà không cần chỉ ra
 * kiểu cụ thể (Khác với Factory Method chỉ tạo 1 đối tượng).
 */
// Nhiều kiểu đối tượng <Thành phần> cha
interface Button { fun paint(): String }
interface Checkbox { fun render(): String}

// Các kiểu đối tượng <Thành phần> con cụ thể
class WinButton : Button { override fun paint() = "Vẽ nút kiểu Windows" }
class WinCheckbox : Checkbox { override fun render() = "Render checkbox kiểu Windows" }

class MacButton : Button { override fun paint() = "Vẽ nút kiểu macOS" }
class MacCheckbox : Checkbox { override fun render() = "Render checkbox kiểu macOS" }


// Abstract Factory
interface GUIFactory {
    fun createButton(): Button
    fun createCheckbox(): Checkbox
}


// Các kiểu Factory cho từng họ <Thành phần>
class WinFactory : GUIFactory {
    override fun createButton(): Button = WinButton()
    override fun createCheckbox(): Checkbox = WinCheckbox()
}
class MacFactory : GUIFactory {
    override fun createButton(): Button = MacButton()
    override fun createCheckbox(): Checkbox = MacCheckbox()
}


// Sử dụng Abstract Factory
fun drawUI(factory: GUIFactory) {
    val btn = factory.createButton()
    val cb = factory.createCheckbox()
    println(btn.paint())
    println(cb.render())
}

/////////////////////////////////////////////////////////
//fun main() {
//    drawUI(WinFactory())
//    drawUI(MacFactory())
//}