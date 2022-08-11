public class ObjetoVeiculo implements Comparable<ObjetoVeiculo> {
    private int id;
    private double peso;
    private double lucro;

    
    public ObjetoVeiculo(int id, double peso, double lucro) {
        this.id = id;
        this.peso = peso;
        this.lucro = lucro;
    }

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public double getLucro() {
        return lucro;
    }
    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    public double razaoObjeto(){
        return this.lucro / this.peso;
    }


    @Override
    public int compareTo(ObjetoVeiculo obv){
        if (this.razaoObjeto() < obv.razaoObjeto()){
            return 1;
        }
        else if (this.razaoObjeto() > obv.razaoObjeto()){
            return -1;
        }
        return 0;
    }
    
    

}
