package tareaasignacionmemoriaso;

import java.util.LinkedList;
import java.util.List;

public class TareaAsignacionMemoriaSO {

    public static int getIndex(List<Bloque> blocks, Bloque newBlock){
        for (Bloque block : blocks) {
            if(block.getAddress() > newBlock.getAddress()){
                return blocks.indexOf(block);
            }
        }
        return blocks.size();
    }
    
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
        // Falta tomar en cuenta lo de juntar bloques del mismo proceso
        boolean emptyBlock = false;
        int emptyBlockIndex = -1;
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        // Falta tomar en cuenta lo de juntar bloques del mismo proceso
        emptyBlock = false;
        emptyBlockIndex = -1;
        for (Bloque block : emptyBlocks) {
            int randomNum = 300;
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Liberar memoria
        Bloque freeBlock = fullBlocks.remove(0);
        int index = getIndex(emptyBlocks, freeBlock);
        boolean success = false;
        
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        // Falta tomar en cuenta lo de juntar bloques del mismo proceso
        emptyBlock = false;
        emptyBlockIndex = -1;
        for (Bloque block : emptyBlocks) {
            int randomNum = 50;
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        // Falta tomar en cuenta lo de juntar bloques del mismo proceso
        emptyBlock = false;
        emptyBlockIndex = -1;
        for (Bloque block : emptyBlocks) {
            int randomNum = 50;
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        // Falta tomar en cuenta lo de juntar bloques del mismo proceso
        emptyBlock = false;
        emptyBlockIndex = -1;
        for (Bloque block : emptyBlocks) {
            int randomNum = 3000;
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        // Falta tomar en cuenta lo de juntar bloques del mismo proceso
        emptyBlock = false;
        emptyBlockIndex = -1;
        for (Bloque block : emptyBlocks) {
            int randomNum = 2600;
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        //Pedir memoria
        // Falta tomar en cuenta lo de juntar bloques del mismo proceso
        emptyBlock = false;
        emptyBlockIndex = -1;
        for (Bloque block : emptyBlocks) {
            int randomNum = 4000;
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        freeBlock = fullBlocks.remove(1);
        index = getIndex(emptyBlocks, freeBlock);
        System.out.println("Index"+index);
        success = false;
        
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        freeBlock = fullBlocks.remove(3);
        index = getIndex(emptyBlocks, freeBlock);
        System.out.println("Index"+index);
        success = false;
        
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
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
        
        freeBlock = fullBlocks.remove(2);
        index = getIndex(emptyBlocks, freeBlock);
        System.out.println("Index"+index);
        success = false;
        
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
            } else if (previousBlock.getMemory()+previousBlock.getAddress() == freeBlock.getAddress() && success){
                emptyBlocks.get(index).setMemory(previousBlock.getMemory()+nextBlock.getMemory());
                emptyBlocks.get(index).setAddress(previousBlock.getAddress());
                emptyBlocks.remove(index-1);
            }
        }
        
        if(!success){
            emptyBlocks.add(index, freeBlock);
        }
        
        System.out.println("Full"+fullBlocks.toString());
        System.out.println("Free"+emptyBlocks.toString());
    }
}
