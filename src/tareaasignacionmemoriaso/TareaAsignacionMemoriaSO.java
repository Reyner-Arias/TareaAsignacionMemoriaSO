package tareaasignacionmemoriaso;

import java.util.LinkedList;
import java.util.List;

public class TareaAsignacionMemoriaSO {

    public static void main(String[] args) {
        List<Bloque> fullBlocks = new LinkedList<>();
        List<Bloque> emptyBlocks = new LinkedList<>();
        int initialMem = 10000;
        
        //Memoria inicial
        Bloque init = new Bloque(0, initialMem);
        emptyBlocks.add(init);
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        for (Bloque block : emptyBlocks) {
            int randomNum = 100;
            if(block.getMemory() >= randomNum){
                Bloque dinamicMem = new Bloque(block.getAddress(), randomNum);
                block.setAddress(block.getAddress()+randomNum);
                block.setMemory(block.getMemory()-randomNum);
                fullBlocks.add(dinamicMem);
                break;
            }
        }
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        for (Bloque block : emptyBlocks) {
            int randomNum = 300;
            if(block.getMemory() >= randomNum){
                Bloque dinamicMem = new Bloque(block.getAddress(), randomNum);
                block.setAddress(block.getAddress()+randomNum);
                block.setMemory(block.getMemory()-randomNum);
                fullBlocks.add(dinamicMem);
                break;
            }
        }
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        Bloque freeBlock = fullBlocks.remove(0);
        emptyBlocks.add(0, freeBlock); // Falta metodo
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        for (Bloque block : emptyBlocks) {
            int randomNum = 50;
            if(block.getMemory() >= randomNum){
                Bloque dinamicMem = new Bloque(block.getAddress(), randomNum);
                block.setAddress(block.getAddress()+randomNum);
                block.setMemory(block.getMemory()-randomNum);
                fullBlocks.add(dinamicMem);
                break;
            }
        }
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
    }
}
