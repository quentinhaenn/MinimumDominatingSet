package heuristic;

import heuristic.algorithm.IG;
import heuristic.algorithm.IAlgorithm;
import heuristic.constructive.GIP;
import heuristic.localSearch.*;
import heuristic.structure.Instance;
import heuristic.structure.RandomManager;
import heuristic.structure.Result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    final static boolean readAllFolders = false;
    final static boolean readAllInstances = false;

    final static String folderIndex = "adjacency_matrices";
    static String instanceIndex = "temp.txt"; //= "waveform-5000_None.data";
    final static String homeDir = System.getProperty("user.home");
    static String resultPath; // = homeDir + "/Documents/feasibility-report-dominating-set/code/results/";
    static String pathFolder; // = homeDir + "/Documents/feasibility-report-dominating-set/code/datasets/";

    static List<String> foldersNames;
    static List<String> instancesNames;
    static String instanceFolderPath;

    static IAlgorithm algorithm;
    static GIP constructive=new GIP();
    static LocalSearchEfficient_1_1 localSearchEfficient_1_1=new LocalSearchEfficient_1_1();

    public static int best = Integer.MAX_VALUE;
    public static void main(String[] args) {
        String folderPath = args[0];
        String resultPath = args[1];
        setPaths(folderPath, resultPath);
        algorithm=new IG(constructive, localSearchEfficient_1_1);
        execute();
    }

    private static void execute()  {
        File file=new File(pathFolder);
        instanceFolderPath = file.getPath() + "/";
        printHeaders(resultPath+"/result.txt");
        readData();
    }

    private static void printHeaders(String path) {
        try (PrintWriter pw = new PrintWriter(path)) {
            pw.print("Instance;Time;OF;Solution_set");
            pw.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void printResults(String path, Result result, String name) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path,true))) {

            pw.print(name+";");
            int nElems=result.getKeys().size();
            for (int i = 0; i < nElems; i++) {
                pw.print(result.get(i));
                if (i < nElems-1) pw.print(";");
            }
            pw.print(";");
            Set<Integer> watchers=result.getSolutionSets();
            pw.print(watchers);
            
            pw.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readData(){
        foldersNames = Arrays.asList(new File(pathFolder).list());

        readFolder(pathFolder);
        //System.out.println("Folder index exceeds the bounds of the array");

    }

    private static void readAllFolders(){
        String [] folders =new File(pathFolder).list();

        for(String fileName : folders){
            readFolder(fileName);
        }
    }

    private static void readFolder(String fileName){
        File file;
        file=new File(pathFolder); //+"/"+fileName);
        if(!fileName.startsWith(".") && !fileName.startsWith("..") && file.isDirectory()){
            instancesNames = Arrays.asList(file.list());
            System.out.println(instanceIndex);
            instanceFolderPath = file.getPath() + "/";
            if(readAllInstances) readAllInstances();
            else if (instancesNames.contains(instanceIndex)) readInstance(instanceIndex);
            else System.out.println("Instance index exceeds the bounds of the array");
        }
    }

    private static void readAllInstances(){
        for(String instanceName : instancesNames){
            if(!instanceName.startsWith(".") && !instanceName.startsWith(".."))
                readInstance(instanceName);
        }
    }

    private static void readInstance(String instanceName){
        Instance instance=new Instance(instanceFolderPath +instanceName);
        RandomManager.setSeed(13);
        Result result= algorithm.execute(instance,false);
        printResults(resultPath+"/result.txt", result, instanceName);
    }

    private static void setPaths(String folderPath, String resultPath){
        Main.resultPath = resultPath;
        Main.pathFolder = folderPath;
    }

}
