package practicalsample

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.graph.SimpleWeightedGraph
import org.jgrapht.traverse.BreadthFirstIterator
import org.jgrapht.traverse.DepthFirstIterator



fun testGraph() {
    // Tạo một đồ thị có trọng số
    val graph: Graph<Int, DefaultEdge> = SimpleWeightedGraph(DefaultEdge::class.java)

    val source_vertex = 1
    val target_vertex = 4


    // Thêm các đỉnh
    graph.addVertex(1)
    graph.addVertex(2)
    graph.addVertex(3)
    graph.addVertex(4)

    addEdgeWithWeight(graph, 1, 2, 3.0)
    addEdgeWithWeight(graph, 1, 3, 2.0)
    addEdgeWithWeight(graph, 2, 3, 1.0)
    addEdgeWithWeight(graph, 1, 4, 1.0)
    addEdgeWithWeight(graph, 3, 4, 4.0)
    addEdgeWithWeight(graph, 2, 4, 3.0)

    val dijkstraAlg = DijkstraShortestPath(graph)
    val path = dijkstraAlg.getPath(source_vertex, target_vertex)
    if (path != null) {
        println("check123 Shortest path from $source_vertex to $target_vertex: " + path.getVertexList());
        println("check123 Total path weight: " + path.getWeight());
    } else {
        println("check123 No path available from $source_vertex to $target_vertex");
    }


    val primMST = PrimMinimumSpanningTree(graph)
    val mst = primMST.spanningTree
    println("check123 Edges in the minimum spanning tree:")
    mst.edges.forEach { edge ->
        println("check123 ${graph.getEdgeSource(edge)} - ${graph.getEdgeTarget(edge)}")
    }
    println("check123 Total weight of the minimum spanning tree: ${mst.weight}")
}

fun addEdgeWithWeight(graph: Graph<Int, DefaultEdge>, source: Int, target: Int, weight: Double) {
    val edge = graph.addEdge(source, target)
    if (edge != null) {
        graph.setEdgeWeight(edge, weight)
    } else {
        println("check123 Failed to add edge between $source and $target")
    }
}


fun testGraph2() {
    // Tạo một đồ thị vô hướng
    val graph: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)

    // Thêm các đỉnh
    graph.addVertex(1)
    graph.addVertex(2)
    graph.addVertex(3)
    graph.addVertex(4)

    // Thêm các cạnh
    graph.addEdge(1, 2)
    graph.addEdge(1, 3)
    graph.addEdge(2, 3)
    graph.addEdge(2, 4)
    graph.addEdge(3, 4)

    // Sử dụng BFS
    println("check123 BFS traversal from vertex 1:")
    val bfs = BreadthFirstIterator(graph, 1)
    while (bfs.hasNext()) {
        print("check123 ${bfs.next()} ")
    }
    println("\n")

    // Sử dụng DFS
    println("check123 DFS traversal from vertex 1:")
    val dfs = DepthFirstIterator(graph, 1)
    while (dfs.hasNext()) {
        print("check123 ${dfs.next()} ")
    }
    println()
}