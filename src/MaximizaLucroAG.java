import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MaximizaLucroAG {
    private int geracaoI = 1;
    private int tamanhoPopulacao;
    private double taxaMutacao;
    private double taxaReproducao;
    private int parada;
    private Dados dados;

    

    private ArrayList<Individuo> individuos = new ArrayList<Individuo>();
    //private ArrayList<Individuo> proxGeracao = new ArrayList<Individuo>();

    private ArrayList<Individuo> selecionados = new ArrayList<>();

    private final Double capacidadeCaminhao = 3000.0;

    private Individuo individuo;


    public MaximizaLucroAG(int tamanhoPopulacao, double taxaMutacao, double taxaReproducao, int parada) {
        this.tamanhoPopulacao = tamanhoPopulacao;
        this.taxaMutacao = taxaMutacao;
        this.taxaReproducao = taxaReproducao;
        this.parada = parada;
        this.dados = new Dados();
    }

    public void algoritmoGenetico(){
        System.out.println("\nGeração da População inicial: \n");
        GeraPopulacao();
        ImprimePopulacao();


        for(int i = 0; i < this.parada; i++){
            System.out.println("\nAvaliação da população: \n");
            avaliaPopulacao();
            ImprimePopulacao();

            System.out.println("\nSeleciona os indivíduos: \n");
            selecionaIndividuos();

            System.out.println("\nCrossOver dos selecionados: \n");
            crossOverSelecionados();
            ImprimePopulacao();

            System.out.println("\nProvoca mutação na população: \n");
            mutaGeracao();
            ImprimePopulacao();


            System.out.println("\nAjuste população: \n");
            ajustePopulacao();
            ImprimePopulacao();
        }

        System.out.println("\n Possíveis Soluções: \n");
        ImprimeResultado();

    }



    public void GeraPopulacao(){
        int geracao = 1;
        for (int i = 0; i < this.tamanhoPopulacao; i++){
            int[] id = new int[2];
            id[0] = i;
            id[1] = geracao;
            this.individuo = new Individuo(id, this.tamanhoPopulacao);
            this.individuos.add(this.individuo);
        }        
    }

    public void avaliaPopulacao(){
        double soma_valFitness = 0.0;
        double soma_total_ValFitness = 0.0;
        
        
         
        for(int i = 0; i < this.individuos.size(); i++){
            for(int x = 0; x < this.individuos.get(i).getCromossomo().length; x++){
                if (this.individuos.get(i).getCromossomo()[x] == 1){
                    soma_valFitness = calcValFitness(this.individuos.get(i));
                    this.individuos.get(i).setValFitness(soma_valFitness);
                }               

            }

            soma_total_ValFitness += soma_valFitness;
            calcProbabilidade(soma_total_ValFitness);

        }

        Collections.sort(this.individuos);
        
    }

    private ArrayList<Individuo> avaliaPopulacaoRotela(ArrayList<Individuo> individuo_local){
        double soma_valFitness = 0.0;
        double soma_total_ValFitness = 0.0;    
         
        for(int i = 0; i < individuo_local.size(); i++){
            for(int x = 0; x < individuo_local.get(i).getCromossomo().length; x++){
                if (individuo_local.get(i).getCromossomo()[x] == 1){
                    soma_valFitness = calcValFitness(individuo_local.get(i));
                    individuo_local.get(i).setValFitness(soma_valFitness);
                }               
            }

            soma_total_ValFitness += soma_valFitness;
            calcProbabilidade(soma_total_ValFitness);

        }

        Collections.sort(individuo_local);
        return individuo_local;
    }

    private ArrayList<Individuo> avaliaProxGenIndividuos (ArrayList<Individuo> individuo_local){
        double soma_valFitness = 0.0;
        double soma_total_ValFitness = 0.0;
        
                 
        for(int i = 0; i < individuo_local.size(); i++){
            for(int x = 0; x < individuo_local.get(i).getCromossomo().length; x++){
                if (individuo_local.get(i).getCromossomo()[x] == 1){
                    soma_valFitness = calcValFitnessProxGen(individuo_local.get(i));
                    individuo_local.get(i).setValFitness(soma_valFitness);
                }               
            }

            soma_total_ValFitness += soma_valFitness;
            for(int u = 0; u < individuo_local.size(); u++){   
                individuo_local.get(u).setProbabilidade(individuo_local.get(u).getValFitness()/soma_total_ValFitness);
                
            }

        }

        Collections.sort(individuo_local);
        return individuo_local;
    }

    private double calcValFitnessProxGen(Individuo ind){

        double aux_peso = 0.0;
        double soma_valFitness = 0.0;
        for(int i = 0; i < ind.getCromossomo().length; i++){
            if(ind.getCromossomo()[i] == 1){
                soma_valFitness += dados.getObjetos().get(i).getLucro();
                aux_peso += dados.getObjetos().get(i).getPeso();
            }
        }

        //if (aux_peso < this.capacidadeCaminhao){
            return soma_valFitness;
        //}else{
        //    return 0.0;
        //}

    }
    
    private ArrayList<Individuo> calcProbabilidadeProxGen(double somaFitness,  ArrayList<Individuo> individuo_local){;        
        for(int i = 0; i < individuo_local.size(); i++){          
            individuo_local.get(i).setProbabilidade(individuo_local.get(i).getValFitness()/somaFitness);
            
        }
        return individuo_local;
    }


    private double calcValFitness(Individuo ind){
        double soma_valFitness = 0.0;
        for(int i = 0; i < ind.getCromossomo().length; i++){
            if(ind.getCromossomo()[i] == 1){
                soma_valFitness += dados.getObjetos().get(i).getLucro();
            }
        }

        return soma_valFitness;

    }

    private void calcProbabilidade(double somaFitness ){;        
        for(int i = 0; i < this.individuos.size(); i++){            
            this.individuos.get(i).setProbabilidade(this.individuos.get(i).getValFitness()/somaFitness);

        }
    }

    private ArrayList<Integer> geraRoleta( ArrayList<Individuo> populacao_atual ){
        ArrayList<Integer> roleta = new ArrayList<>();

        int proporcao = 0;
        for(int i = 0; i < populacao_atual.size(); i++){
            proporcao = (int) (Math.round(populacao_atual.get(i).getProbabilidade() * 100));
            //if (proporcao != 0.0){
                populacao_atual.get(i).imprimeInviduo();
                System.err.println("Proporção: "+ proporcao);
                for(int j = 0; j < proporcao; j++){
                    roleta.add(i);
                }
            //}
           
        }
        return (roleta);
    }

    public void selecionaIndividuos(){
        Random random = new Random();

        ArrayList<Individuo> copia_individuos = new ArrayList<>();
        for(int i = 0; i < this.individuos.size(); i++){
            copia_individuos.add(this.individuos.get(i));
        }

        for(int x = 0; x < this.individuos.size()*this.taxaReproducao; x++){
            System.out.println("\n--------- Giro da Roleta: "+x+" ---------\n");

            int val_random = random.nextInt(99);
            ArrayList<Integer> roleta = geraRoleta(copia_individuos); 
            int val_selecionado = roleta.get(val_random);
            selecionados.add(copia_individuos.get(val_selecionado));
            copia_individuos.remove(val_selecionado);
            copia_individuos = avaliaPopulacaoRotela(copia_individuos);
        }
    }

    public void crossOverSelecionados(){
        Random random = new Random();

        int num_random = random.nextInt(this.tamanhoPopulacao-1);

        int[] aux_cromossomo1 = new int[this.tamanhoPopulacao];
        int[] aux_cromossomo2 = new int[this.tamanhoPopulacao];
        
        geracaoI += 1;

        
        int numInd = 0;
        int i = 0;
        System.out.println(selecionados.size());
        while(i < Math.round(selecionados.size()/2)){

            for(int x = 0; x < selecionados.get(i).getCromossomo().length; x++){
                aux_cromossomo1[x] = selecionados.get(i).getCromossomo()[x];
            }
            int[] listGenI1 = new int[2];
            listGenI1[0] = numInd;
            listGenI1[1] = geracaoI;
            Individuo in1 = new Individuo(listGenI1, this.tamanhoPopulacao);
            for(int j = 0; j < in1.getCromossomo().length; j++){
                in1.getCromossomo()[j] = aux_cromossomo1[j];
            }
            individuos.add(in1);

            numInd+=1;

            i += 1;
            for(int x = 0; x < selecionados.get(i).getCromossomo().length; x++){
                aux_cromossomo2[x] = selecionados.get(i).getCromossomo()[x];
            }
            
            int[] listGenI2 = new int[2];
            
            listGenI2[0] = numInd;
            listGenI2[1] = geracaoI;
            Individuo in2 = new Individuo(listGenI2, this.tamanhoPopulacao);
            for(int j = 0; j < in2.getCromossomo().length; j++){
                in2.getCromossomo()[j] = aux_cromossomo2[j];
            }
            individuos.add(in2);

            for(int j = num_random; j < this.tamanhoPopulacao; j++){
                int aux = aux_cromossomo1[j];
                aux_cromossomo1[j] = aux_cromossomo2[j];
                aux_cromossomo2[j] = aux;
            }
            int[] listGenI3 = new int[2];
            
            numInd += 1;


            listGenI3[0] = numInd;
            listGenI3[1] = geracaoI;
            Individuo in3 = new Individuo(listGenI3, this.tamanhoPopulacao);
            for(int j = 0; j < in3.getCromossomo().length; j++){
                in3.getCromossomo()[j] = aux_cromossomo1[j];
            }
            individuos.add(in3);
            
            numInd += 1;


            int[] listGenI4 = new int[2];
            
            listGenI4[0] = numInd;
            listGenI4[1] = geracaoI;
            Individuo in4 = new Individuo(listGenI4, this.tamanhoPopulacao);
            for(int j = 0; j < in4.getCromossomo().length; j++){
                in4.getCromossomo()[j] = aux_cromossomo1[j];
            }
            individuos.add(in4);

            numInd+=1;

            i += 1;
        }
        selecionados.clear();

    }

    public void mutaGeracao(){
        Random random = new Random();

        Double val_random = random.nextDouble();
        int val_random_cromossomo = random.nextInt(this.individuos.size());
        int val_random_gene = random.nextInt(this.tamanhoPopulacao);
        if (val_random < this.taxaMutacao){
            int val_gene = this.individuos.get(val_random_cromossomo).getCromossomo()[val_random_gene];
            if(val_gene == 1){
                this.individuos.get(val_random_cromossomo).getCromossomo()[val_random_gene] = 0;
            }else{
                this.individuos.get(val_random_cromossomo).getCromossomo()[val_random_gene] = 1;
            }
        }
    }

    public void ajustePopulacao(){
        avaliaPopulacao();
        Collections.sort(this.individuos);

        
        for(int i = 0; i < this.tamanhoPopulacao; i++){
            this.individuos.remove(this.individuos.size() - 1);
        }
    }


    public void ImprimePopulacao(){
        Double pesoTotal = 0.0;
        for(int i = 0; i < this.individuos.size(); i++){
            System.out.print("[ ");
            for (int x = 0; x < this.individuos.get(i).getid().length; x++){
                System.out.print(this.individuos.get(i).getid()[x]);
                System.out.print(" ");
            }
            System.out.print("]: ");

            System.out.print("[ ");
            for(int z = 0; z < this.individuos.get(i).getCromossomo().length; z++){
                System.out.print(this.individuos.get(i).getCromossomo()[z]);
                System.out.print(" ");
                if(this.individuos.get(i).getCromossomo()[z] == 1){
                    pesoTotal += dados.getObjetos().get(z).getPeso();
                }
            }   
            System.out.print("]");
            System.out.println(" / Fit.: "+this.individuos.get(i).getValFitness()+" / Prob.:"+this.individuos.get(i).getProbabilidade()+" / Peso:"+pesoTotal);
            pesoTotal = 0.0;
        }
    }

    public void ImprimeResultado(){
        ArrayList<Individuo> individuos_resultado = new ArrayList<>();
        Double peso_objeto_resultado = 0.0;

        for(int i = 0; i < this.individuos.size(); i++){
            for(int x = 0; x < this.individuos.get(i).getCromossomo().length; x++){
                if(this.individuos.get(i).getCromossomo()[x] == 1){
                    peso_objeto_resultado += dados.getObjetos().get(x).getPeso();
                }
            }

            if(peso_objeto_resultado <= capacidadeCaminhao){
                individuos_resultado.add(this.individuos.get(i));
            }
            peso_objeto_resultado = 0.0;
        }

        if(individuos_resultado.isEmpty()){
            System.out.println("Não foi encontrado uma solução com esses parâmetros.");
        }else{
            IndividuoResultado individuoResultado = new IndividuoResultado();
            Collections.sort(individuos_resultado, individuoResultado);


            Double pesoTotal = 0.0;
            for(int i = 0; i < individuos_resultado.size(); i++){
                if(i == 0){
                    System.out.print(" > [ ");
                }else{
                    System.out.print("[ ");
                }
                for (int x = 0; x < individuos_resultado.get(i).getid().length; x++){
                    System.out.print(individuos_resultado.get(i).getid()[x]);
                    System.out.print(" ");
                }
                System.out.print("]: ");
    
                System.out.print("[ ");
                for(int z = 0; z < individuos_resultado.get(i).getCromossomo().length; z++){
                    System.out.print(individuos_resultado.get(i).getCromossomo()[z]);
                    System.out.print(" ");
                    if(individuos_resultado.get(i).getCromossomo()[z] == 1){
                        pesoTotal += dados.getObjetos().get(z).getPeso();
                    }
                }   
                System.out.print("]");
                System.out.println(" / Peso:"+pesoTotal);
                pesoTotal = 0.0;
            }

        }
    }
}
