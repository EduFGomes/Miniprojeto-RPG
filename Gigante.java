public class Gigante extends Inimigo {

    private static int VIDA_FIXA = 800;
    private static int ATAQUE_FIXO = 45;
    private static int DEFESA_FIXA = 60;

    private int furia;

    public Gigante(String nome, byte nivel, Inventario inventario, int furiaInicial) throws Exception {
        super(nome, VIDA_FIXA, ATAQUE_FIXO, DEFESA_FIXA, nivel, inventario, "Gigante");
        this.furia = furiaInicial;
    }

    public void pisaoEsmagador() {
        if (this.furia >= 15) {
            System.out.println(this.nome + " usa um Pisão Esmagador!");
            this.furia -= 15;
        } else {
            System.out.println(this.nome + " está exausto.");
        }
    }
    
    public int getFuria() {
        return furia;
    }

    public void setFuria(int furia) {
        this.furia = Math.max(0, furia);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Fúria: " + this.furia;
    }
}

public class Gigante extends Inimigo {

    private static int VIDA_FIXA = 800;
    private static int ATAQUE_FIXO = 45;
    private static int DEFESA_FIXA = 60;

    public Gigante(String nome, byte nivel, Inventario inventario) throws Exception {
        super(nome, VIDA_FIXA, ATAQUE_FIXO, DEFESA_FIXA, nivel, inventario, "Gigante");
    }

    //ataque especifico
    public void pisaoEsmagador(Personagem alvo) {
        // Dano = 45 * 2.0 = 90
        int danoBruto = (int) (this.ataque * 2.0);

        System.out.println(this.nome + " usa um Pisão Esmagador em " + alvo.getNome() + "!");

        alvo.receberDano(danoBruto);
    }
}