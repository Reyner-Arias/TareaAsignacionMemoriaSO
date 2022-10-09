package tareaasignacionmemoriaso;

public class Bloque {
    private int address;
    private int memory;

    public Bloque(int address, int memory){
        this.address = address;
        this.memory = memory;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Bloque{" + "address=" + address + ", memory=" + memory + '}';
    }
}
