public class Personagem
{
    private String nome;
    private int pontosDeVida;
    private int ataque;
    private int defesa;
    private byte nivel;
    private Inventario inventario;

    public Personagem(nome, pontosDeVida, ataque, defesa, nivel, inventario) throws Exceptions
    {
        if (nome         == null ||
            pontosDeVida == null ||
            ataque       == null ||
            defesa       == null ||
            nivel        == null ||
            inventario   == null) throw new Exception ("Um ou mais parâmetros inválidos.");

        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = new Inventario(inventario);
    }

    public void morrer()
    {

    }
}