package com.boun.semanticweb.viewModel;

public class Edge {

    private int from;
    private int to;
    private int width;

    public Edge(int from, int to, int width){
        this.from = from;
        this.to = to;
        this.width = width;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
