package SceneStuff;

import MyMath.Matrix;
import MyMath.Utils;
import MyMath.Vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Vertex> vertexList;
    private List<Edge> edgeList;

    public void loadScn(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        int numVertices = 0;
        int numEdges = 0;

        /* vertex */
        // read num of vertex
        if ((str = br.readLine()) != null) {
            numVertices = Integer.parseInt(str);
            this.vertexList = new ArrayList<>(numVertices);
        }
        // create vertex
        for (int i = 0; i < numVertices; ++i) {
            if ((str = br.readLine()) != null) {
                String[] crdnts = str.split(" ");
                Vertex v = new Vertex(crdnts[0], crdnts[1], crdnts[2], i);
                this.vertexList.add(v);
            }
        }

        /* SceneStuff.Edge */
        // read num of edges
        if ((str = br.readLine()) != null) {
            numEdges = Integer.parseInt(str);
            this.edgeList = new ArrayList<>(numEdges);
        }
        // create edges
        Vertex from, to;
        for (int i = 0; i < numEdges; ++i) {
            if ((str = br.readLine()) != null) {
                String[] vertices = str.split(" ");
                from = this.vertexList.get(Integer.parseInt(vertices[0]));
                to = this.vertexList.get(Integer.parseInt(vertices[1]));
                Edge v = new Edge(from, to);
                this.edgeList.add(v);
            }
        }
        br.close();
    }

    public List<Edge> apply(Matrix FT) {
        // calc final transformation on each vertex
        List<Vertex> transVertices = new ArrayList<>(this.vertexList.size());
        for (Vertex v : this.vertexList) {
            Vector temp = Utils.matrixToVector(Matrix.mult(FT, Utils.vectorToMatrix(v)));
            Vertex nv = Utils.vectorToVertex(temp);
            nv.setIndex(v.getIndex());
            transVertices.add(nv);
        }

        // create list of updated edges
        List<Edge> transEdges = new ArrayList<>(this.edgeList);
        for (Edge e : transEdges) {
            e.setFrom(transVertices.get(e.getFrom().getIndex()));
            e.setTo(transVertices.get(e.getTo().getIndex()));
        }

        return transEdges;
    }
}