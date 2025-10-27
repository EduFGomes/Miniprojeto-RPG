public class Personagem
{
    private String nome;
    private int pontosDeVida;
    private int ataque;
    private int defesa;
    private byte nivel;
    private Inventario inventario;

    public Personagem(String nome,
                      int pontosDeVida,
                      int ataque,
                      int defesa,
                      byte nivel,
                      Inventario inventario) throws Exception
    {
        if (    (nome == null)      ||
                (pontosDeVida <= 0) ||
                (ataque <= 0)    ||
                (defesa < 0)     ||
                (nivel < 0)      ||
                (inventario == null))
        {
            throw new Exception ("Um ou mais parâmetros inválidos.");
        }

        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = new Inventario(inventario);
    }
}