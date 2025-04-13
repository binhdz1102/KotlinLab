package designpattern.creational.factory

/**
 * Mẫu Factory cung cấp 1 giao diện chung để tạo đối tượng trong
 * lớp cha. Cho phép các lớp con quyết định kiểu khởi tạo cụ thể.
 */
// Kiểu đối tượng <Thành Phần> cha
interface Transport { fun deliver(): String }

// Kiểu đối tượng <Thành Phần> con cụ thể
class Truck : Transport { override fun deliver() = "Giao hàng bằng xe tải" }
class Ship : Transport { override fun deliver() = "Giao hàng bằng tàu thủy" }



// Factory Method:
// Đối tượng <Khởi Tạo> cha cho phép 1 đối tượng con
// quyết định kiểu khởi tạo cụ thể
abstract class Logistics {
    abstract fun createTransport(): Transport
    fun planDelivery() {
        val transport = createTransport()
        println("Đã lên kế hoạch: " + transport.deliver())
    }
}

// Các lớp con <Khởi Tạo> chính quyết định đối tượng khởi tạo
class RoadLogistics : Logistics() {
    override fun createTransport(): Transport = Truck()
}
class SeaLogistics : Logistics() {
    override fun createTransport(): Transport = Ship()
}

///////////////////////////////////////////////
//fun main() {
//    val logistics: Logistics = RoadLogistics()
//    logistics.planDelivery()
//}