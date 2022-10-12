package tareaasignacionmemoriaso;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TareaAsignacionMemoriaSO {
    // Listas que simulan los bloques libres de las memorias
    private static List<Bloque> emptyBlocksFirst; 
    private static List<Bloque> emptyBlocksBest;
    private static List<Bloque> emptyBlocksWorst;
    private static List<Bloque> emptyBlocksBuddy;
    
    public static int getIndex(List<Bloque> blocks, Bloque newBlock){
        for (Bloque block : blocks) {
            if(block.getAddress() > newBlock.getAddress()){
                return blocks.indexOf(block);
            }
        }
        return blocks.size();
    }
    
    public static void requestMemoryFirst(Proceso currentProcess, int memory){
        // Pedir memoria
        boolean emptyBlock = false; // Esta bandera sirve para identificar si el bloque de memoria después de una asignación queda con un valor de 0 en la variable memoria para así eliminarlo de las lista
        int emptyBlockIndex = -1; // Index del bloque con 0 en el atributo memoria, si es que existe
        for (Bloque block : emptyBlocksFirst) {
            if(block.getMemory() >= memory){
                Bloque dinamicMem = new Bloque(block.getAddress(), memory);
                block.setAddress(block.getAddress()+memory);
                block.setMemory(block.getMemory()-memory);
                if(block.getMemory() == 0){
                   emptyBlock = true;
                   emptyBlockIndex = emptyBlocksFirst.indexOf(block);
                }
                currentProcess.requestBlockFirst(dinamicMem);
                break;
            }
        }
        if(emptyBlock){
            emptyBlocksFirst.remove(emptyBlockIndex);
        }
    }
    
    public static void requestMemoryBest(Proceso currentProcess, int memory){
        // Pedir memoria
        int first = -1;
        Bloque best = emptyBlocksBest.get(0);
        for (Bloque block : emptyBlocksBest) {
            if(first == -1 && (block.getMemory()-memory) >= 0){
                best = block;
                first = 0;
            } else if ((block.getMemory()-memory) < (best.getMemory()-memory)){
                best = block;
            }
        }
        Bloque dinamicMem = new Bloque(best.getAddress(), memory);
        best.setAddress(best.getAddress()+memory);
        best.setMemory(best.getMemory()-memory);
        if(best.getMemory() == 0){
           int emptyBlockIndex = emptyBlocksBest.indexOf(best);// Index del bloque con 0 en el atributo memoria, si es que existe
           emptyBlocksBest.remove(emptyBlockIndex);
        }
        currentProcess.requestBlockFirst( dinamicMem);
    }
    
    public static void requestMemoryWorst(Proceso currentProcess, int memory){
        // Pedir memoria
        int first = -1; 
        Bloque worst = emptyBlocksWorst.get(0);
        for (Bloque block : emptyBlocksWorst) {
            if(first == -1 && (block.getMemory()-memory) >= 0){
                worst = block;
                first = 0;
            } else if ((block.getMemory()-memory) > (worst.getMemory()-memory)){
                worst = block;
            }
        }
        Bloque dinamicMem = new Bloque(worst.getAddress(), memory);
        worst.setAddress(worst.getAddress()+memory);
        worst.setMemory(worst.getMemory()-memory);
        if(worst.getMemory() == 0){
           int emptyBlockIndex = emptyBlocksWorst.indexOf(worst); // Index del bloque con 0 en el atributo memoria, si es que existe
           emptyBlocksWorst.remove(emptyBlockIndex);
        }
        currentProcess.requestBlockFirst( dinamicMem);
    }
    
    public static void releaseMemory(Bloque freeBlock, List<Bloque> emptyBlocks){
        // Liberar memoria
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
        emptyBlocksFirst = new LinkedList<>();
        emptyBlocksBest = new LinkedList<>();
        emptyBlocksWorst = new LinkedList<>();
        emptyBlocksBuddy = new LinkedList<>();
        
        int totalMemory = 1000000; // Memoria total inicial
        List<Proceso> processes = new LinkedList<>();
        
        Bloque initFirst = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) First Fit
        Bloque initBest = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) Best Fit
        Bloque initWorst = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) Worst Fit
        Bloque initBuddy = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) Buddy System
        
        emptyBlocksFirst.add(initFirst); // Añardilo a la lista de bloques vacíos First Fit
        emptyBlocksFirst.add(initBest); // Añardilo a la lista de bloques vacíos Best Fit
        emptyBlocksFirst.add(initWorst); // Añardilo a la lista de bloques vacíos Worst Fit
        emptyBlocksFirst.add(initBuddy); // Añardilo a la lista de bloques vacíos Buddy System
        
        Random random = new Random();
        random.setSeed(12345L); // Establecer semilla para los valores randomizados
        
        for(int i = 0; i < 100; i++){ // For para inicializar los 100 procesos
            Proceso process = new Proceso(random.nextInt(271)+30); // Crear una nueva instancia de la clase Proceso con un tiempo de vida de 30 a 300 segundos
            int ranNum = random.nextInt(151)+50;
            requestMemoryFirst(process, ranNum); // Solicitar el bloque de memoria inicial de 50 a 200 bytes
            requestMemoryBest(process, ranNum);
            requestMemoryWorst(process, ranNum);
            //requestMemoryBuddy(process, ranNum);
            System.out.println(process.toString());
            processes.add(process);
        }
    }
}
