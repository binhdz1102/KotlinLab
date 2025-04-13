package designpattern.behavior.observer

/**
 * Triển khai mẫu Observer cho phép nhiều đối tượng lắng nghe sự kiện
 * từ 1 nguồn.
 */
interface Observer<T> {
    fun onDataUpdated(data: T) // callback thực hiện lắng nghe data T
}

interface Observable<T> {
    fun registerObserver(observer: Observer<T>)
    fun unregisterObserver(observer: Observer<T>)
    fun notifyObservers(data: T)
}

class ConcreteObservable<T> : Observable<T> {
    private val observers = mutableListOf<Observer<T>>()

    override fun registerObserver(observer: Observer<T>) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: Observer<T>) {
        observers.remove(observer)
    }

    override fun notifyObservers(data: T) {
        for (observer in observers) {
            observer.onDataUpdated(data)
        }
    }
}

class ConcreteObserver(private val label: String = "") : Observer<Int> {
    override fun onDataUpdated(data: Int) {
        // triển khai cụ thể với 1 observer cụ thể
        println("Observer $label nhận dữ liệu: $data")
    }
}

class ConcreteListener(
    private val onDataUpdatedCallback: (Int) -> Unit
) : Observer<Int> {
    override fun onDataUpdated(data: Int) {
        // đối với listener thì callback có thể được handle từ bên ngoài
        // (listener này vẫn chỉ là theo mẫu Observer không phải một Listener
        // hoàn chỉnh).
        onDataUpdatedCallback(data)
    }
}

/////////////////////////////////////////////////////////
//fun main() {
//    // khai báo
//    val observable = ConcreteObservable<Int>()
//    val observer1 = ConcreteObserver("1")
//    val observer2 = ConcreteListener {
//        println("observer2 nhận được thay đổi $it")
//    }
//    val observer3 = ConcreteObserver("3")
//    val observer4 = ConcreteObserver("4")
//
//    // đăng kí
//    observable.registerObserver(observer1)
//    observable.registerObserver(observer2)
//    observable.registerObserver(observer3)
//    observable.registerObserver(observer1)
//
//    // theo dõi
//    observable.notifyObservers(100)
//    observable.unregisterObserver(observer4)
//    println("-----------------------")
//    observable.notifyObservers(123)
//}
