package designpattern.creational.factory

/**
 * Triển khai mẫu Factory cơ bản
 */
sealed interface Shape {
    fun draw()
}

class Circle : Shape {
    override fun draw() = println("Draw a circle")
}

class Rectangle : Shape {
    override fun draw() = println("Draw a rectangle")
}

object ShapeFactory {
    fun getShape(shapeType: String): Shape? {
        return when (shapeType.lowercase()) {
            "circle" -> Circle()
            "rectangle" -> Rectangle()
            else -> null
        }
    }
}

object FactoryPatternDemo {
    fun runDemo() {
        println("=== Factory Pattern Demo ===")
        // Nhận đối tượng shape circle
        val circle = ShapeFactory.getShape("circle")
        circle?.draw()

        // Nhận đối tượng shape rectangle
        val rectangle = ShapeFactory.getShape("rectangle")
        rectangle?.draw()

        // Thử 1 shape không hỗ trợ
        val unknownShape = ShapeFactory.getShape("triangle")
        println("Triangle shape? -> $unknownShape")
    }
}