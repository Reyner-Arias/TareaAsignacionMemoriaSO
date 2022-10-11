package tareaasignacionmemoriaso;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    
    public static void requestMemory(Proceso currentProcess, int memory){
        // Pedir memoria
        boolean emptyBlock = false; // Esta bandera sirve para identificar si el bloque de memoria después de una asignación queda con un valor de 0 en la variable memoria para así eliminarlo de las lista
        int emptyBlockIndex = -1; // Index del bloque con 0 en el atributo memoria, si es que existe
        for (Bloque block : emptyBlocks) {
            if(block.getMemory() >= memory){
                Bloque dinamicMem = new Bloque(block.getAddress(), memory);
                block.setAddress(block.getAddress()+memory);
                block.setMemory(block.getMemory()-memory);
                if(block.getMemory() == 0){
                   emptyBlock = true;
                   emptyBlockIndex = emptyBlocks.indexOf(block);
                }
                currentProcess.requestBlock( dinamicMem);
                break;
            }
        }
        if(emptyBlock){
            emptyBlocks.remove(emptyBlockIndex);
        }
    }
    
    public static void releaseMemory(Proceso currentProcess){
        // Liberar memoria
        Bloque freeBlock = currentProcess.freeFirstBlock();
        int index = getIndex(emptyBlocks, freeBlock);
        boolean success = false; // Bandera que nos dice si el bloque liberado se combinó con algún otro
        
        if(index == 0 && !emptyBlocks.isEmpty()){
            Bloque nextBlock = emptyBlocks.get(index);
            if(freeBlock.getAddress()+freeBlock.getMemory() == nextBlock.getAddress()){
                emptyBlocks.get(index).setAddress(freeBlock.getAddress());
                emptyBlocks.get(index).setMemory(nextBlock.getMemory()+freeBlock.getMemory());
                success = true;
            }
        } else if (index == emptyBlocks.size() && !emptyBlocks.isEmpty()){
            Bloque previousBlock = emptyBlocks.get(index-1);
            if(previousBlock.getMemory()+previousBlock.getAddress() == freeBlock.getAddress()){
                emptyBlocks.get(index-1).setMemory(previousBlock.getMemory()+freeBlock.getMemory());
                success = true;
            }
        } else if (!emptyBlocks.isEmpty()){
            Bloque previousBlock = emptyBlocks.get(index-1);
            Bloque nextBlock = emptyBlocks.get(index);
            if(freeBlock.getAddress()+freeBlock.getMemory() == nextBlock.getAddress()){
                emptyBlocks.get(index).setAddress(freeBlock.getAddress());
                emptyBlocks.get(index).setMemory(nextBlock.getMemory()+freeBlock.getMemory());
                success = true;
            }
            if(previousBlock.getMemory()+previousBlock.getAddress() == freeBlock.getAddress() && !success){
                emptyBlocks.get(index-1).setMemory(previousBlock.getMemory()+freeBlock.getMemory());
                success = true;
            } else {
                emptyBlocks.get(index).setMemory(previousBlock.getMemory()+nextBlock.getMemory());
                emptyBlocks.get(index).setAddress(previousBlock.getAddress());
                emptyBlocks.remove(index-1);
            }
        }
        if(!success){
            emptyBlocks.add(index, freeBlock);
        }
    }
    
    public static void main(String[] args) {
        emptyBlocks = new LinkedList<>(); 
        int totalMemory = 10000; // Memoria total inicial
        
        Bloque init = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa)
        emptyBlocks.add(init); // Añardilo a la lista de bloques vacíos
        
        
    }
}
