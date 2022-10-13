package tareaasignacionmemoriaso;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TareaAsignacionMemoriaSO {
    // Listas que simulan los bloques libres de las memorias
    private static List<Bloque> emptyBlocksFirst; 
    private static List<Bloque> emptyBlocksBest;
    private static List<Bloque> emptyBlocksWorst;
    private static List<Bloque> emptyBlocksBuddy;
    private int rejectedProcsFirst = 0;
    private int rejectedProcsBest = 0;
    private int rejectedProcsWorst = 0;
    private int rejectedProcsBuddy = 0;
    
    public static int getIndex(List<Bloque> blocks, Bloque newBlock){
        for (Bloque block : blocks) {
            if(block.getAddress() > newBlock.getAddress()){
                return blocks.indexOf(block);
            }
        }
        return blocks.size();
    }
    
    public static boolean requestMemoryFirst(Proceso currentProcess, int memory){
        // Pedir memoria
        boolean success = false; // Bandera para determinar si hay bloques libres
        boolean emptyBlock = false; // Esta bandera sirve para identificar si el bloque de memoria después de una asignación queda con un valor de 0 en la variable memoria para así eliminarlo de las lista
        int emptyBlockIndex = -1; // Index del bloque con 0 en el atributo memoria, si es que existe
        for (Bloque block : emptyBlocksFirst) {
            if(block.getMemory() >= memory){
                success = true;
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
        return success;
    }
    
    public static boolean requestMemoryBest(Proceso currentProcess, int memory){
        // Pedir memoria
        boolean success = false; // Bandera para determinar si hay bloques libres
        Bloque best = emptyBlocksBest.get(0);
        for (Bloque block : emptyBlocksBest) {
            if(!success && (block.getMemory()-memory) >= 0){
                best = block;
                success = true;
            } else if (success && (block.getMemory()-memory) < (best.getMemory()-memory) && (block.getMemory()-memory) >= 0){
                best = block;
            }
        }
        if(success){
            Bloque dinamicMem = new Bloque(best.getAddress(), memory);
            best.setAddress(best.getAddress()+memory);
            best.setMemory(best.getMemory()-memory);
            if(best.getMemory() == 0){
               int emptyBlockIndex = emptyBlocksBest.indexOf(best);// Index del bloque con 0 en el atributo memoria, si es que existe
               emptyBlocksBest.remove(emptyBlockIndex);
            }
            currentProcess.requestBlockBest( dinamicMem);
        }
        return success;
    }
    
    public static boolean requestMemoryWorst(Proceso currentProcess, int memory){
        // Pedir memoria
        boolean success = false; // Bandera para determinar si hay bloques libres
        Bloque worst = emptyBlocksWorst.get(0);
        for (Bloque block : emptyBlocksWorst) {
            if(!success && (block.getMemory()-memory) >= 0){
                worst = block;
                success = true;
            } else if (success && (block.getMemory()-memory) > (worst.getMemory()-memory) && (block.getMemory()-memory) >= 0){
                worst = block;
            }
        }
        
        if(success){
            Bloque dinamicMem = new Bloque(worst.getAddress(), memory);
            worst.setAddress(worst.getAddress()+memory);
            worst.setMemory(worst.getMemory()-memory);
            if(worst.getMemory() == 0){
               int emptyBlockIndex = emptyBlocksWorst.indexOf(worst); // Index del bloque con 0 en el atributo memoria, si es que existe
               emptyBlocksWorst.remove(emptyBlockIndex);
            }
            currentProcess.requestBlockWorst( dinamicMem);
        }
        return success;
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
    
    public static void getMemory(Proceso process, int ranNum){
        boolean success;
        // Solicitar memoria usando First Fit
        if(!process.getRejectedFirst()) success = requestMemoryFirst(process, ranNum);
        else success = false;
        if(!success && !process.getRejectedFirst()) process.setRejectedFirst(true);
        
        // Solicitar memoria usando Best Fit
        if(!process.getRejectedBest()) success = requestMemoryBest(process, ranNum);
        else success = false;
        if(!success && !process.getRejectedBest()) process.setRejectedBest(true);
        
        // Solicitar memoria usando Worst Fit
        if(!process.getRejectedWorst()) success = requestMemoryWorst(process, ranNum);
        else success = false;
        if(!success && !process.getRejectedWorst()) process.setRejectedWorst(true);
        
        // Solicitar memoria usando Worst Fit
        //if(!process.getRejectedBuddy()) success = requestMemoryBuddy(process, ranNum);
        //else success = false;
        //if(!success && !process.getRejectedBuddy()) process.setRejectedBuddy(true);
        
        // Actualizar ventana
    }
    
    
    public static List<Color> pick(int num) {
        List<Color> colors = new LinkedList<>();
        if (num < 2)
            return colors;
        float dx = 1.0f / (float) (num - 1);
        for (int i = 0; i < num; i++) {
            colors.add(get(i * dx));
        }
        return colors;
    }

    public static Color get(float x) {
        float r = 0.0f;
        float g = 0.0f;
        float b = 1.0f;
        if (x >= 0.0f && x < 0.2f) {
            x = x / 0.2f;
            r = 0.0f;
            g = x;
            b = 1.0f;
        } else if (x >= 0.2f && x < 0.4f) {
            x = (x - 0.2f) / 0.2f;
            r = 0.0f;
            g = 1.0f;
            b = 1.0f - x;
        } else if (x >= 0.4f && x < 0.6f) {
            x = (x - 0.4f) / 0.2f;
            r = x;
            g = 1.0f;
            b = 0.0f;
        } else if (x >= 0.6f && x < 0.8f) {
            x = (x - 0.6f) / 0.2f;
            r = 1.0f;
            g = 1.0f - x;
            b = 0.0f;
        } else if (x >= 0.8f && x <= 1.0f) {
            x = (x - 0.8f) / 0.2f;
            r = 1.0f;
            g = 0.0f;
            b = x;
        }
        return new Color(r, g, b);
    }
    
    public static void main(String[] args) {
        emptyBlocksFirst = new LinkedList<>();
        emptyBlocksBest = new LinkedList<>();
        emptyBlocksWorst = new LinkedList<>();
        emptyBlocksBuddy = new LinkedList<>();
        
        int totalMemory = 1000000; // Memoria total inicial
        List<Proceso> processes = new LinkedList<>();
        List<Proceso> finishedProcesses = new LinkedList<>();
        
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
        
        int procCount = 0; //Cantidad de procesos creados hasta el momento
        
        
        int firstRanNum = random.nextInt(151)+50;
        
        List<Color> colores = pick(100);
        System.out.println(colores.toString()); 
        
        /*
        Proceso firstProcess = new Proceso(random.nextInt(271)+30); // Crear una nueva instancia de la clase Proceso con un tiempo de vida de 30 a 300 segundos
        getMemory(firstProcess, firstRanNum);
        processes.add(firstProcess);
        procCount++;
        
        while(!processes.isEmpty()){
            for(Proceso process : processes){
                long currentTime = System.currentTimeMillis(); // Obtener el tiempo actual en milisegundos
                double timePassed = (double) (process.getStartTime()-currentTime)/1000; // Tiempo pasado desde la creación del proceso
                boolean finishFlag = timePassed > process.getLifetime(); // Bandera que determina si un proceso ya finalizo su tiempo de ejecución
                
                if(!finishFlag){ //Funcionamiento normal de los procesos
                    int action = random.nextInt(10)+1;
                    if(action <= 7){
                        int ranNum = random.nextInt(151)+50;
                        requestMemoryFirst(process, ranNum); // Solicitar un valor de 50 a 200 bytes de memoria
                        requestMemoryBest(process, ranNum);
                        requestMemoryWorst(process, ranNum);
                        //requestMemoryBuddy(process, ranNum);
                    }
                } else { //Liberar toda la memoria de un proceso que finaliza su ejecución, para las 4 memorias de cada algoritmo
                    for(int i = 0; i < process.getAllocatedBlocksFirst().size(); i++){
                        Bloque freeBlock = process.freeBlockFirst();
                        releaseMemory(freeBlock, emptyBlocksFirst);
                    }
                    for(int i = 0; i < process.getAllocatedBlocksBest().size(); i++){
                        Bloque freeBlock = process.freeBlockBest();
                        releaseMemory(freeBlock, emptyBlocksBest);
                    }
                    for(int i = 0; i < process.getAllocatedBlocksWorst().size(); i++){
                        Bloque freeBlock = process.freeBlockWorst();
                        releaseMemory(freeBlock, emptyBlocksWorst);
                    }
                    for(int i = 0; i < process.getAllocatedBlocksBuddy().size(); i++){
                        Bloque freeBlock = process.freeBlockBuddy();
                        releaseMemory(freeBlock, emptyBlocksBuddy);
                    }
                    finishedProcesses.add(process); 
                }
            }
            // Eliminar de la lista de procesos los finalizados o rechazados
            for(Proceso process : finishedProcesses){
                processes.remove(process);
            }
            finishedProcesses.removeAll(finishedProcesses);
        }
        */
    }
}
