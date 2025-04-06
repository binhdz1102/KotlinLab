package designpattern

import designpattern.creational.FactoryPatternDemo
import designpattern.creational.SingletonPatternDemo


fun main() {
    println("hello")
}




























fun main1() {
    // Biến tạm để lưu state xem khi nào thì dừng chương trình
    var isRunning = true

    while (isRunning) {
        println("========================================")
        println(" DESIGN PATTERNS PLAYGROUND ")
        println(" Chọn mẫu pattern demo: ")
        println(" 1. Singleton Pattern")
        println(" 2. Factory Pattern")
        println(" 3. Thoát")
        println("========================================")

        print("Nhập lựa chọn (1/2/3): ")
        when (readLine()?.trim()) {
            "1" -> {
                // Gọi code demo Singleton
                println(">> Chạy demo Singleton <<")
                SingletonPatternDemo.runDemo()
            }
            "2" -> {
                // Gọi code demo Factory
                println(">> Chạy demo Factory <<")
              FactoryPatternDemo.runDemo()
            }
            "0" -> {
                // Thoát
                println("Bạn đã thoát chương trình. Tạm biệt!")
                isRunning = false
            }
            else -> {
                // Trường hợp người dùng nhập sai
                println("Lựa chọn không hợp lệ, vui lòng thử lại.")
            }
        }
        println() // Xuống dòng cho đẹp
    }
}
