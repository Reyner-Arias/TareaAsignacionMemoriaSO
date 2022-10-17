package tareaasignacionmemoriaso;

import Visualization.Visualization;
import java.awt.Color;
import static java.lang.Math.ceil;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Thread.sleep;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TareaAsignacionMemoriaSO {
    // Listas que simulan los bloques libres de las memorias
    private static List<Bloque> emptyBlocksFirst; 
    private static List<Bloque> emptyBlocksBest;
    private static List<Bloque> emptyBlocksWorst;
    private static List<Bloque> emptyBlocksBuddy;
    private static int rejectedProcsFirst = 0;
    private static int rejectedProcsBest = 0;
    private static int rejectedProcsWorst = 0;
    private static int rejectedProcsBuddy = 0;
    
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
    
    //Se intentó simular el Buddy System, pero tenía un error no identificado, por lo que aparecerá comentado en la entrega final
    public static boolean requestMemoryBuddy(Proceso currentProcess, int memory){
        // Pedir memoria
        boolean success = false; // Bandera para determinar si hay bloques libres
        boolean reject = false;
        int iterCount = 0;
        int emptyBlockIndex = -1;
        int indexOfBlock = -1;
        boolean notOptimalBlock = false;
        while(!success && !reject){
            if(notOptimalBlock){
                Bloque block = emptyBlocksBuddy.get(indexOfBlock);
                int halfMemory = block.getMemory()/2;
                emptyBlocksBuddy.get(indexOfBlock).setMemory(block.getMemory()-halfMemory);
                Bloque emptyBlock = new Bloque(block.getAddress()+halfMemory, halfMemory);
                if(emptyBlocksBuddy.size() != indexOfBlock) emptyBlocksBuddy.add(indexOfBlock+1,emptyBlock);
                else emptyBlocksBuddy.add(emptyBlock);
                notOptimalBlock = false;
            }
            for (Bloque block : emptyBlocksBuddy){
                
                indexOfBlock = emptyBlocksBuddy.indexOf(block); 
                if(block.getMemory() == 1){
                    Bloque dinamicMem = new Bloque(block.getAddress(), 1);
                    block.setAddress(block.getAddress()+1);
                    block.setMemory(block.getMemory()-1);
                    if(block.getMemory() == 0){
                       emptyBlockIndex = emptyBlocksBuddy.indexOf(block); // Index del bloque con 0 en el atributo memoria, si es que existe
                    }
                    currentProcess.requestBlockBuddy( dinamicMem);
                    success = true;
                    break;
                }else if (block.getMemory() > memory){
                    int halfMemory = block.getMemory()/2;
                    if (halfMemory <= memory){
                        Bloque dinamicMem = new Bloque(block.getAddress(), block.getMemory());
                        //System.out.println("Bloque a pedir"+dinamicMem.toString());
                        block.setAddress(block.getAddress()+block.getMemory());
                        block.setMemory(block.getMemory()-block.getMemory());
                        if(block.getMemory() == 0){
                           emptyBlockIndex = emptyBlocksBuddy.indexOf(block); // Index del bloque con 0 en el atributo memoria, si es que existe
                        }
                        currentProcess.requestBlockBuddy( dinamicMem);
                        success = true;
                    }else{
                        notOptimalBlock = true;
                    }
                    break;
                }
                iterCount++;
            }
            if (iterCount == emptyBlocksBuddy.size()){
                reject = true;
            }
        }
        if (emptyBlockIndex != -1){
            emptyBlocksBuddy.remove(emptyBlockIndex);
        }
        return success;
    }
    
    public static void releaseMemory(Bloque freeBlock, List<Bloque> emptyBlocks){
        // Liberar memoria
        int index = getIndex(emptyBlocks, freeBlock);
        boolean success = false; // Bandera que nos dice si el bloque liberado se combinó con algún otro
        //System.out.println("Bloque a liberar"+freeBlock.toString());
        if(index == 0){
            Bloque nextBlock = emptyBlocks.get(index);
            if(freeBlock.getAddress()+freeBlock.getMemory() == nextBlock.getAddress()){
                emptyBlocks.get(index).setAddress(freeBlock.getAddress());
                emptyBlocks.get(index).setMemory(nextBlock.getMemory()+freeBlock.getMemory());
                success = true;
            }
        } else if (index == emptyBlocks.size()){
            Bloque previousBlock = emptyBlocks.get(index-1);
            if(previousBlock.getMemory()+previousBlock.getAddress() == freeBlock.getAddress()){
                emptyBlocks.get(index-1).setMemory(previousBlock.getMemory()+freeBlock.getMemory());
                success = true;
            }
        } else {
            Bloque previousBlock = emptyBlocks.get(index-1);
            Bloque nextBlock = emptyBlocks.get(index);
            if (freeBlock.getAddress()+freeBlock.getMemory() == nextBlock.getAddress() && previousBlock.getMemory()+previousBlock.getAddress() == freeBlock.getAddress()){
                emptyBlocks.get(index).setMemory(previousBlock.getMemory()+nextBlock.getMemory()+freeBlock.getMemory());
                emptyBlocks.get(index).setAddress(previousBlock.getAddress());
                emptyBlocks.remove(index-1);
                success = true;
            }else if(freeBlock.getAddress()+freeBlock.getMemory() == nextBlock.getAddress()){
                emptyBlocks.get(index).setAddress(freeBlock.getAddress());
                emptyBlocks.get(index).setMemory(nextBlock.getMemory()+freeBlock.getMemory());
                success = true;
            }else if(previousBlock.getMemory()+previousBlock.getAddress() == freeBlock.getAddress()){
                emptyBlocks.get(index-1).setMemory(previousBlock.getMemory()+freeBlock.getMemory());
                success = true;
            }
        }
        if(!success){
            emptyBlocks.add(index, freeBlock);
        }
    }
    
    public static double next(int mem){
        return pow(2, ceil(log(mem)/log(2)));
    }
    
    public static void releaseMemoryBuddy(Bloque freeBlock){
        // Liberar memoria
        int index = getIndex(emptyBlocksBuddy, freeBlock);
        //System.out.println("Bloque a liberar"+freeBlock.toString());
        
        Bloque nextBlock = emptyBlocksBuddy.get(index);
        while(freeBlock.getAddress()+nextBlock.getMemory() == freeBlock.getAddress()*2 && freeBlock.getAddress()+freeBlock.getMemory() == nextBlock.getAddress() && next(nextBlock.getMemory()+freeBlock.getMemory()) == nextBlock.getMemory()+freeBlock.getMemory()){
            emptyBlocksBuddy.get(index).setAddress(freeBlock.getAddress());
            emptyBlocksBuddy.get(index).setMemory(nextBlock.getMemory()+freeBlock.getMemory());
            freeBlock = emptyBlocksBuddy.remove(index);
            nextBlock = emptyBlocksBuddy.get(index);
        }
        
        emptyBlocksBuddy.add(index, freeBlock);
    }
    
    public static void getMemory(Proceso process, int ranNum){
        boolean success;
        // Solicitar memoria usando First Fit
        if(!process.getRejectedFirst()) success = requestMemoryFirst(process, ranNum);
        else success = false;
        if(!success && !process.getRejectedFirst()) {
            process.setRejectedFirst(true);
            rejectedProcsFirst++;
        }
        
        // Solicitar memoria usando Best Fit
        if(!process.getRejectedBest()) success = requestMemoryBest(process, ranNum);
        else success = false;
        if(!success && !process.getRejectedBest()) {
            process.setRejectedBest(true);
            rejectedProcsBest++;
        }
        
        // Solicitar memoria usando Worst Fit
        if(!process.getRejectedWorst()) success = requestMemoryWorst(process, ranNum);
        else success = false;
        if(!success && !process.getRejectedWorst()) {
            process.setRejectedWorst(true);
            rejectedProcsWorst++;
        }
        /*
        // Solicitar memoria usando Buddy System
        if(!process.getRejectedBuddy()) success = requestMemoryBuddy(process, ranNum);
        else success = false;
        if(!success && !process.getRejectedBuddy()) {
            process.setRejectedBuddy(true);
            rejectedProcsBuddy++;
        }*/
    }
    
    // Liberar un espacio de memoria random por cada algoritmo
    public static void freeMemory(Proceso process, Random random){
        // Liberar memoria random usando First Fit
        if(!process.getRejectedFirst() && process.getAllocatedBlocksFirst().size() > 1) {
            int ranNum = random.nextInt(process.getAllocatedBlocksFirst().size()-1)+1;
            Bloque removedBlock = process.freeBlockFirst(ranNum);
            releaseMemory(removedBlock, emptyBlocksFirst);
        }
        
        // Liberar memoria random usando Best Fit
        if(!process.getRejectedBest() && process.getAllocatedBlocksBest().size() > 1) {
            int ranNum = random.nextInt(process.getAllocatedBlocksBest().size()-1)+1;
            Bloque removedBlock = process.freeBlockBest(ranNum);
            releaseMemory(removedBlock, emptyBlocksBest);
        }
        
        // Liberar memoria random usando Worst Fit
        if(!process.getRejectedWorst() && process.getAllocatedBlocksWorst().size() > 1) {
            int ranNum = random.nextInt(process.getAllocatedBlocksWorst().size()-1)+1;
            Bloque removedBlock = process.freeBlockWorst(ranNum);
            releaseMemory(removedBlock, emptyBlocksWorst);
        }
        
        /*
        // Liberar memoria random usando Buddy System
        if(!process.getRejectedBuddy() && process.getAllocatedBlocksBuddy().size() > 1) {
            int ranNum = random.nextInt(process.getAllocatedBlocksBuddy().size()-1)+1;
            Bloque removedBlock = process.freeBlockBuddy(ranNum);
            releaseMemoryBuddy(removedBlock);
        }
        */
    }

    public static Color get(int pid) {
        Random random = new Random();
        random.setSeed(pid); // Establecer semilla para los valores randomizados
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        
        return new Color(r, g, b);
    }
    
    public static int getFreeMemory(){
        int res = 0;
        for (Bloque block : emptyBlocksBuddy){
            res += block.getMemory();
        }
        return res;
    }
    
    public static void main(String[] args) throws InterruptedException{
        emptyBlocksFirst = new LinkedList<>();
        emptyBlocksBest = new LinkedList<>();
        emptyBlocksWorst = new LinkedList<>();
        emptyBlocksBuddy = new LinkedList<>();
        
        //System.out.println(emptyBlocksBuddy.toString());
        
        int totalMemory = 1920; // Memoria total inicial
        List<Proceso> processes = new LinkedList<>();
        List<Proceso> finishedProcesses = new LinkedList<>();
        
        Bloque initFirst = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) First Fit
        Bloque initBest = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) Best Fit
        Bloque initWorst = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) Worst Fit
        Bloque initBuddy = new Bloque(0, totalMemory); // Inicializar el primer bloque libre (la memoria completa) Buddy System
        
        emptyBlocksFirst.add(initFirst); // Añardilo a la lista de bloques vacíos First Fit
        emptyBlocksBest.add(initBest); // Añardilo a la lista de bloques vacíos Best Fit
        emptyBlocksWorst.add(initWorst); // Añardilo a la lista de bloques vacíos Worst Fit
        emptyBlocksBuddy.add(initBuddy); // Añardilo a la lista de bloques vacíos Buddy System
        
        Random random = new Random();
        random.setSeed(12345L); // Establecer semilla para los valores randomizados
        
        int procCount = 0; //Cantidad de procesos creados hasta el momento
        
        int firstRanNum = random.nextInt(7)+3;
        //System.out.println("primer bloque"+emptyBlocksBuddy.toString());
        
        Proceso firstProcess = new Proceso(random.nextInt(31)+20, get(procCount)); // Crear una nueva instancia de la clase Proceso con un tiempo de vida de 30 a 300 segundos
        getMemory(firstProcess, firstRanNum);
        processes.add(firstProcess);
        //System.out.println("primera memoria"+emptyBlocksBuddy.toString());
        procCount++;
        long pastProcessTime = System.currentTimeMillis(); // Obtener el tiempo actual en milisegundos para conocer hace cuánto se definió el último proceso
        int betweenTime = random.nextInt(6)+5;
        
        Visualization currentVis = new Visualization(processes);
        
        while(procCount < 100 || !processes.isEmpty()){
            //Revisar si se debe crear un nuevo proceso
            long currentProcessTime = System.currentTimeMillis(); // Obtener el tiempo actual en milisegundos
            double time = (double) (currentProcessTime-pastProcessTime)/1000;
            
            if(time>betweenTime && procCount < 100){
                // Código de creación de un proceso nuevo cada n tiempo entre 10 o 30 segundos
                int ranNum = random.nextInt(7)+3;
                Proceso process = new Proceso(random.nextInt(31)+20, get(procCount)); // Crear una nueva instancia de la clase Proceso con un tiempo de vida de 30 a 300 segundos
                getMemory(process, ranNum);
                processes.add(process);
                procCount++;
                pastProcessTime = System.currentTimeMillis(); // Obtener el tiempo actual en milisegundos para conocer hace cuánto se definió el último proceso
                betweenTime = random.nextInt(6)+5;
            }
            
            //Lista que define que acción hace cada proceso
            for(Proceso process : processes){
                
                long currentTime = System.currentTimeMillis(); // Obtener el tiempo actual en milisegundos
                double timePassed = (double) (currentTime-process.getStartTime())/1000; // Tiempo pasado desde la creación del proceso
                
                boolean finishFlag = timePassed > process.getLifetime(); // Bandera que determina si un proceso ya finalizo su tiempo de ejecución
                
                if(!finishFlag){ //Funcionamiento normal de los procesos
                    int action = random.nextInt(10)+1;
                    if(action <= 5){
                        int ranNum = random.nextInt(7)+3;
                        getMemory(process, ranNum);
                        if(process.isProcessRejected()) finishedProcesses.add(process);
                        //System.out.println("Por cada request "+emptyBlocksBuddy.toString());
                    } else {
                        freeMemory(process, random);
                        //System.out.println("Por cada free"+emptyBlocksBuddy.toString());
                    }
                } else { //Liberar toda la memoria de un proceso que finaliza su ejecución, para las 4 memorias de cada algoritmo
                    finishedProcesses.add(process); 
                }
                Visualization otherVis = new Visualization(processes);
                sleep(300);
                currentVis.destroyWindow();
                currentVis = otherVis;
            }
            // Eliminar de la lista de procesos los finalizados o rechazados\
            for(Proceso process : finishedProcesses){
                
                for(int i = process.getAllocatedBlocksFirst().size()-1; i >= 0; i--){
                    Bloque freeBlock = process.freeBlockFirst(i);
                    releaseMemory(freeBlock, emptyBlocksFirst);
                }
                for(int i = process.getAllocatedBlocksBest().size()-1; i >= 0; i--){
                    Bloque freeBlock = process.freeBlockBest(i);
                    releaseMemory(freeBlock, emptyBlocksBest);
                }
                
                for(int i = process.getAllocatedBlocksWorst().size()-1; i >= 0; i--){
                    Bloque freeBlock = process.freeBlockWorst(i);
                    releaseMemory(freeBlock, emptyBlocksWorst);
                    
                }
                //System.out.println("Pre Liberar Mem final"+emptyBlocksBuddy.toString());
                for(int i = process.getAllocatedBlocksBuddy().size()-1; i >= 0; i--){
                    Bloque freeBlock = process.freeBlockBuddy(i);
                    releaseMemoryBuddy(freeBlock);
                    //System.out.println("Liberar Mem final"+emptyBlocksBuddy.toString());
                }
                processes.remove(process);
            }
            finishedProcesses.removeAll(finishedProcesses);
        }
        //System.out.println(rejectedProcsFirst);
        //System.out.println(rejectedProcsBest);
        //System.out.println(rejectedProcsWorst);
        //System.out.println(rejectedProcsBuddy);
        //System.out.println(procCount);
    }
}
