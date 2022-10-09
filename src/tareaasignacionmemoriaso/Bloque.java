package tareaasignacionmemoriaso;

public class Bloque {
    private Integer address;
    private Integer memory;

    public Bloque(Integer address, Integer memory) {
        this.address = address;
        this.memory = memory;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Bloque{" + "address=" + address + ", memory=" + memory + '}';
    }
}
