public class Dragao extends Inimigo {

    private static int VIDA_FIXA = 500;
    private static int ATAQUE_FIXO = 50;
    private static int DEFESA_FIXA = 30;

    public Dragao(String nome, byte nivel, Inventario inventario) throws Exception {
        super(nome, VIDA_FIXA, ATAQUE_FIXO, DEFESA_FIXA, nivel, inventario, "Dragão");
    }

    //ataque especial
    public void cuspirFogo(Personagem alvo) {
        System.out.println(this.nome + " cospe uma bola de fogo em " + alvo.getNome() + "!");

        int danoBruto = (int) (this.ataque * 1.5);
        alvo.receberDano(danoBruto);
    }
}