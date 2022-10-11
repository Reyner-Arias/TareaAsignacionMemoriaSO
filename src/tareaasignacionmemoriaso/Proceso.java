package tareaasignacionmemoriaso;

import java.util.LinkedList;
import java.util.List;

public class Proceso {
    private static int consecutivo = 0;
    private final List<Bloque> allocatedBlocks;
    private final int lifetime;
    private final int pid;

    public Proceso(int lifetime) {
        this.allocatedBlocks = new LinkedList<>();
        this.lifetime = lifetime;
        this.pid = consecutivo++;
    }

    public List<Bloque> getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getPid() {
        return pid;
    }
    
    public void requestBlock(Bloque block){
        allocatedBlocks.add( block);
    }
    
    public Bloque freeFirstBlock(){
        return allocatedBlocks.remove(1);
    }
    
    @Override

    public String toString() {
        return "Proceso{" + "allocatedBlocks=" + allocatedBlocks.toString() + ", lifetime=" + lifetime + '}';
    } 
}
