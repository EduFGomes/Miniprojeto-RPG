public class Bruxa extends Inimigo {

    private static int VIDA_FIXA = 300;
    private static int ATAQUE_FIXO = 60;
    private static int DEFESA_FIXA = 20;

    public Bruxa(String nome, byte nivel, Inventario inventario) throws Exception {
        super(nome, VIDA_FIXA, ATAQUE_FIXO, DEFESA_FIXA, nivel, inventario, "Bruxa");
    }

    //ataque especifico
    public void lancarMaldicao(Personagem alvo) {
        int reducaoDefesa = 10; 
        int danoMagico = 15;

        System.out.println(this.nome + " lança uma maldição sombria em " + alvo.getNome() + "!");

        System.out.println(alvo.getNome() + " sente a magia e recebe " + danoMagico + " de dano!");
        alvo.setPontosDeVida(alvo.getPontosDeVida() - danoMagico);

        int defesaAntiga = alvo.defesa;
        
        alvo.defesa = Math.max(0, defesaAntiga - reducaoDefesa); 
        
        System.out.println("A defesa de " + alvo.getNome() + " foi reduzida de " + defesaAntiga + " para " + alvo.defesa + "!");
    }
}