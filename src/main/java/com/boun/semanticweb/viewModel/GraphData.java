package com.boun.semanticweb.viewModel;

import java.util.List;

public class GraphData {
    private List<Node> nodes;
    private List<Edge> edges;

    public GraphData(List<Node> nodes, List<Edge> edges){
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
