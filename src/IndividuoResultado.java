import java.util.Comparator;

public class IndividuoResultado implements Comparator<Individuo>{


    private Dados dados = new Dados();

    public int compare(Individuo individuo, Individuo outroIndividuo){
        Double precoIndividuo = 0.0;
        Double precoOutroIndividuo = 0.0;

        for(int i = 0; i < individuo.getCromossomo().length; i++){
            if(individuo.getCromossomo()[i] == 1){
                precoIndividuo += dados.getObjetos().get(i).getPeso();
            }
        }

        for(int i = 0; i < outroIndividuo.getCromossomo().length; i++){
            if(outroIndividuo.getCromossomo()[i] == 1){
                precoOutroIndividuo += dados.getObjetos().get(i).getPeso();
            }
        }

        return precoOutroIndividuo.compareTo(precoIndividuo);
    }

}