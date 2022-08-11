import java.util.Random;

public class Individuo implements Comparable<Individuo> {
    private int[] id = new int[2];
    private Double valFitness = 0.0;
    private Double probabilidade;
    private int[] cromossomo;
    private boolean selecionadoCross;

    private Random numRandon = new Random();


    //private final int tamanhoIndividuo = 8;


    public Individuo(int[] id, int tamanhoIndividuo) {
        this.id = id;

        this.cromossomo = new int[tamanhoIndividuo];
        
        //Dados dados = new Dados();
        for (int i = 0; i < tamanhoIndividuo; i++){
            this.cromossomo[this.numRandon.nextInt(tamanhoIndividuo)] = 1;
        }
        
    }
    
    

    public boolean isSelecionadoCross() {
        return selecionadoCross;
    }

    public void setSelecionadoCross(boolean selecionadoCross) {
        this.selecionadoCross = selecionadoCross;
    }

    public int[] getCromossomo() {
        return cromossomo;
    }


    public void setCromossomo(int[] cromossomo) {
        this.cromossomo = cromossomo;
    }


    public Double getProbabilidade() {
        return probabilidade;
    }


    public void setProbabilidade(Double probabilidade) {
        this.probabilidade = probabilidade;
    }


    public int[] getid() {
        return id;
    }


    public void setid(int[] id) {
        this.id = id;
    }


    public Double getValFitness() {
        return valFitness;
    }


    public void setValFitness(Double valFitness) {
        this.valFitness = valFitness;
    }

    @Override
    public int compareTo(Individuo i){
        if(this.valFitness > i.getValFitness()){
            return -1;
        }
        if (this.valFitness < i.getValFitness()){
            return 1;
        }
        return 0;
    }    

    
    public void imprimeInviduo(){
        System.out.print("[ ");
        for(int i = 0; i < this.id.length; i++){
            System.out.print(this.id[i]);
            System.out.print(" ");
        }
        System.out.print(" ] : ");
        System.out.print("[ ");
        for(int x = 0; x < this.cromossomo.length; x++){
            System.out.print(this.cromossomo[x]);
            System.out.print(" ");
        }
        System.out.print(" ] / ");
        
        System.out.print("Fit.: "+this.valFitness);
        System.out.println(" / Prob.:"+this.probabilidade);

    }

}


