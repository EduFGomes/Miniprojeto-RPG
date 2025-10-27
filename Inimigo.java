public class Inimigo extends Personagem {
    private String tipo;

    public Inimigo(String nome, int pontosDeVidaMax, int ataque, int defesa, byte nivel, Inventario inventario, String tipo) throws Exception {
        super(nome, pontosDeVidaMax, ataque, defesa, nivel, inventario);
        this.tipo = tipo;
    }

    //copia para as subclasses
    public Inimigo(Inimigo outro) {
        super(outro); 
        this.tipo = outro.tipo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public void morrer() {
        super.morrer();
        System.out.println(this.nome + " deixou cair um item!");
        //TODO: Lógica de "drop item"
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Inimigo: " + this.tipo;
    }
}

