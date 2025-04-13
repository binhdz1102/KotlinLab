package designpattern.creational.singleton

/**
 * Singleton
 */
object MySingleton {
    // Các biến trạng thái nội tại
    var counter: Int = 0

    fun doSomething() {
        counter++
        println("MySingleton.doSomething() called, counter = $counter")
    }
}

object SingletonPatternDemo {
    fun runDemo() {
        println("=== Singleton Pattern Demo ===")
        println("Khởi tạo MySingleton lần đầu và gọi doSomething()")
        MySingleton.doSomething()

        println("Gọi lại MySingleton doSomething()")
        MySingleton.doSomething()

        println("So sánh 2 instance singleton: (MySingleton == MySingleton) ?")
        println(MySingleton == MySingleton) // true
    }
}