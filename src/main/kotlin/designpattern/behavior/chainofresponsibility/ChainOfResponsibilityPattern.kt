package designpattern.behavior.chainofresponsibility

// 1. Handler interface
interface Logger {
    var next: Logger?
    fun log(message: String, level: LogLevel)
    fun setNext(logger: Logger): Logger {
        next = logger
        return logger
    }
}

// 2. Concrete Handlers
enum class LogLevel { DEBUG, INFO, ERROR }

class DebugLogger : Logger {
    override var next: Logger? = null
    override fun log(message: String, level: LogLevel) {
        if (level == LogLevel.DEBUG) {
            println("DEBUG: $message")
        } else {
            next?.log(message, level)
        }
    }
}

class ErrorLogger : Logger {
    override var next: Logger? = null
    override fun log(message: String, level: LogLevel) {
        if (level == LogLevel.ERROR) {
            println("ERROR: $message")
        } else {
            next?.log(message, level)
        }
    }
}

//// 3. Cấu hình chain và sử dụng
//fun main() {
//    val debugLogger = DebugLogger()
//    val errorLogger = ErrorLogger()
//    debugLogger.setNext(errorLogger)
//
//    debugLogger.log("System starting", LogLevel.DEBUG)
//    debugLogger.log("Null pointer occurred", LogLevel.ERROR)
//}
