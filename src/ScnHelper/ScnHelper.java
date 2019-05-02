package ScnHelper;

import java.io.*;

public class ScnHelper {
    private static int numEdges;
    private static int numVertices;

    public static void main(String[] args) {
        try {
            String objFileName = "names1.obj";
            String scnFileName = "names1.scn";

            updateNumVerticesNumEdges(objFileName, scnFileName);
            writeVerticesEdgesToScn(objFileName, scnFileName);
        } catch (Exception e){
        }
    }

    public static void writeVerticesEdgesToScn(String objName, String scnName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(objName)));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(scnName)));

        String readStr;

        boolean isFirstVertex = true;
        boolean isFirstEdge = true;

        while ((readStr = br.readLine()) != null) {
            if (readStr.startsWith("v")) {
                if (isFirstVertex) {
                    isFirstVertex = false;
                    bw.write("" + (numVertices + 1));
                    bw.newLine();
                    bw.write("0 0 0");
                    bw.newLine();
                }
                int begin = 2;
                String writeStr = readStr.substring(2);
                if(writeStr.charAt(0) == ' '){
                    writeStr = writeStr.substring(1);
                }
                bw.write(writeStr);
                bw.newLine();

            } else if (readStr.startsWith("f")) {
                if (isFirstEdge) {
                    isFirstEdge = false;
                    bw.write("" + numEdges);
                    bw.newLine();
                }

                String[] tokens = readStr.split(" ");
                for (int i = 1; i < tokens.length - 1; i++) {
                    bw.write(tokens[i].substring(0, tokens[i].indexOf("/")) + " ");
                    bw.write(tokens[i + 1].substring(0, tokens[i + 1].indexOf("/")));
                    bw.newLine();
                }
                bw.write(tokens[tokens.length - 1].substring(0, tokens[tokens.length - 1].indexOf("/")) + " ");
                bw.write(tokens[1].substring(0, tokens[1].indexOf("/")));
                bw.newLine();
            }
        }
        br.close();
        bw.close();
    }

    public static void updateNumVerticesNumEdges(String objName, String scnName) throws Exception {
        numVertices = numEdges = 0;

        BufferedReader br = new BufferedReader(new FileReader(new File(objName)));

        String readStr;

        while ((readStr = br.readLine()) != null) {
            if (readStr.startsWith("v")) {
                numVertices++;
            } else if (readStr.startsWith("f")) {
                numEdges+=((readStr.split(" ")).length-1);
            }
        }
    }
}
