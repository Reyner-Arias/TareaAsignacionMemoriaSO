package tareaasignacionmemoriaso;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class Proceso {
    private static int consecutivo = 0;
    private final List<Bloque> allocatedBlocksFirst;
    private final List<Bloque> allocatedBlocksBest;
    private final List<Bloque> allocatedBlocksWorst;
    private final List<Bloque> allocatedBlocksBuddy;
    private boolean rejectedFirst;
    private boolean rejectedBest;
    private boolean rejectedWorst;
    private boolean rejectedBuddy;
    private final int lifetime;
    private final int pid;
    private final long startTime;
    private final Color color;

    public Proceso(int lifetime, Color color) {
        this.allocatedBlocksFirst = new LinkedList<>();
        this.allocatedBlocksBest = new LinkedList<>();
        this.allocatedBlocksWorst = new LinkedList<>();
        this.allocatedBlocksBuddy = new LinkedList<>();
        this.lifetime = lifetime;
        this.pid = consecutivo++;
        this.startTime = System.currentTimeMillis();
        this.rejectedFirst = false;
        this.rejectedBest = false;
        this.rejectedWorst = false;
        this.rejectedBuddy = false;
        this.color = color;
    }

    public List<Bloque> getAllocatedBlocksFirst() {
        return allocatedBlocksFirst;
    }
    
    public List<Bloque> getAllocatedBlocksBest() {
        return allocatedBlocksBest;
    }
    
    public List<Bloque> getAllocatedBlocksWorst() {
        return allocatedBlocksWorst;
    }
    
    public List<Bloque> getAllocatedBlocksBuddy() {
        return allocatedBlocksBuddy;
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getPid() {
        return pid;
    }
    
    public void requestBlockFirst(Bloque block){
        allocatedBlocksFirst.add( block);
    }
    
    public void requestBlockBest(Bloque block){
        allocatedBlocksBest.add( block);
    }
    
    public void requestBlockWorst(Bloque block){
        allocatedBlocksWorst.add( block);
    }
    
    public void requestBlockBuddy(Bloque block){
        allocatedBlocksBuddy.add( block);
    }
    
    public Bloque freeBlockFirst(){
        return allocatedBlocksFirst.remove(1);
    }
    
    public Bloque freeBlockBest(){
        return allocatedBlocksBest.remove(1);
    }
    
    public Bloque freeBlockWorst(){
        return allocatedBlocksWorst.remove(1);
    }
    
    public Bloque freeBlockBuddy(){
        return allocatedBlocksBuddy.remove(1);
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean getRejectedFirst() {
        return rejectedFirst;
    }

    public void setRejectedFirst(boolean rejectedFirst) {
        this.rejectedFirst = rejectedFirst;
    }

    public boolean getRejectedBest() {
        return rejectedBest;
    }

    public void setRejectedBest(boolean rejectedBest) {
        this.rejectedBest = rejectedBest;
    }

    public boolean getRejectedWorst() {
        return rejectedWorst;
    }

    public void setRejectedWorst(boolean rejectedWorst) {
        this.rejectedWorst = rejectedWorst;
    }

    public boolean getRejectedBuddy() {
        return rejectedBuddy;
    }

    public void setRejectedBuddy(boolean rejectedBuddy) {
        this.rejectedBuddy = rejectedBuddy;
    }

    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return "Proceso{" + "allocatedBlocksFirst=" + allocatedBlocksFirst.toString() + ", allocatedBlocksBest=" + allocatedBlocksBest.toString() + ", allocatedBlocksWorst=" + allocatedBlocksWorst.toString() + ", allocatedBlocksBuddy=" + allocatedBlocksBuddy.toString() + ", lifetime=" + lifetime + '}';
    }
}
