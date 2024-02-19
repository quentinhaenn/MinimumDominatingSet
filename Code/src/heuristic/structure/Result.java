package heuristic.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Result {

    private class Tuple {
        String name;
        float value;

        public Tuple(String name, float value) {
            this.name = name;
            this.value = value;
        }
    }

    private String instanceName;
    private List<Tuple> map=new ArrayList<>();
    private Set<Integer> solutionSet;

    public Result(String instanceName){
        this.instanceName=instanceName;
    }

    public void add(String key, float value){
        map.add(new Tuple(key,value));
    }

    public float get(int pos){
        return map.get(pos).value;
    }

    public List<String> getKeys(){
        List<String> keys = new ArrayList<>();
        for (Tuple tuple : map) {
            keys.add(tuple.name);
        }
        return keys;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public Set<Integer> getSolutionSets() {
        return solutionSet;
    }

    public void setSolutionSet(Set<Integer> solutionSet) {
        this.solutionSet = solutionSet;
    }
}
