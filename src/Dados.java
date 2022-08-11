import java.util.ArrayList;

public class Dados {
    private ArrayList<ObjetoVeiculo> objetos = new ArrayList<>();

    public Dados() {
        ObjetoVeiculo obj1 = new ObjetoVeiculo(1, 400, 800);
        ObjetoVeiculo obj2 = new ObjetoVeiculo(2, 500, 750);
        ObjetoVeiculo obj3 = new ObjetoVeiculo(3, 700, 400);
        ObjetoVeiculo obj4 = new ObjetoVeiculo(4, 400, 450);
        ObjetoVeiculo obj5 = new ObjetoVeiculo(5, 600, 500);
        ObjetoVeiculo obj6 = new ObjetoVeiculo(6, 100, 200);
        ObjetoVeiculo obj7 = new ObjetoVeiculo(7, 800, 700);
        ObjetoVeiculo obj8 = new ObjetoVeiculo(8, 1000, 2000);

        this.objetos.add(obj1);
        this.objetos.add(obj2);
        this.objetos.add(obj3);
        this.objetos.add(obj4);
        this.objetos.add(obj5);
        this.objetos.add(obj6);
        this.objetos.add(obj7);
        this.objetos.add(obj8);

    }

    public ArrayList<ObjetoVeiculo> getObjetos() {
        return objetos;
    }

    public void setObjetos(ArrayList<ObjetoVeiculo> objetos) {
        this.objetos = objetos;
    }

    

    

    
}
