package tareaasignacionmemoriaso;

import java.util.LinkedList;
import java.util.List;

public class TareaAsignacionMemoriaSO {
    private static List<Bloque> emptyBlocks; // Lista que simula los bloques libres de la memoria
    
    public static int getIndex(List<Bloque> blocks, Bloque newBlock){
        for (Bloque block : blocks) {
            if(block.getAddress() > newBlock.getAddress()){
                return blocks.indexOf(block);
            }
        }
        return blocks.size();
    }
    
    public static void requestMemory(){
        // Pedir memoria
        boolean emptyBlock = false; // Esta bandera sirve para identificar si el bloque de memoria después de una asignación queda con un valor de 0 en la variable memoria para así eliminarlo de las lista
        int emptyBlockIndex = -1; // Index del bloque con 0 en el atributo memoria, si es que existe
        for (Bloque block : emptyBlocks) {
            int randomNum = 100;
            if(block.getMemory() >= randomNum){
                Bloque dinamicMem = new Bloque(block.getAddress(), randomNum);
                block.setAddress(block.getAddress()+randomNum);
                block.setMemory(block.getMemory()-randomNum);
                if(block.getMemory() == 0){
                   emptyBlock = true;
                   emptyBlockIndex = emptyBlocks.indexOf(block);
                }
                fullBlocks.add(getIndex(fullBlocks, dinamicMem), dinamicMem);
                break;
            }
        }
        if(emptyBlock){
            emptyBlocks.remove(emptyBlockIndex);
        }
    }
    
    public static void main(String[] args) {
        emptyBlocks = new LinkedList<>(); 
        int totalMemory = 10000; // Memoria total inicial
        
        Bloque init = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa)
        emptyBlocks.add(init); // Añardilo a la lista de bloques vacíos
        
        
    }
}
