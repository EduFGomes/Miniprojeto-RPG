public class Personagem {
    protected String nome;
    protected int pontosDeVidaMax; 
    protected int pontosDeVida; 
    protected int ataque;
    protected int defesa;
    protected byte nivel;
    protected Inventario inventario;

    public Personagem(String nome, int pontosDeVidaMax, int ataque, int defesa, byte nivel, Inventario inventario) throws Exception {
        if (nome == null || inventario == null) {
            throw new Exception("Nome ou inventário não pode ser nulo.");
        }
        if (pontosDeVidaMax <= 0 || ataque < 0 || defesa < 0) { 
            throw new Exception("Atributos numéricos inválidos.");
        }

        this.nome = nome;
        this.pontosDeVidaMax = pontosDeVidaMax;
        this.pontosDeVida = pontosDeVidaMax; 
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel; 
        this.inventario = new Inventario(inventario);
    }

    //Savepoint
    public Personagem(Personagem outro) throws Exception {
        this.nome = outro.nome; 
        this.pontosDeVidaMax = outro.pontosDeVidaMax; 
        this.pontosDeVida = outro.pontosDeVida;       
        this.ataque = outro.ataque;
        this.defesa = outro.defesa;
        this.nivel = outro.nivel;
        this.inventario = new Inventario(outro.inventario); 
    }

    public void morrer() {
        System.out.println(this.nome + " foi derrotado!");
    }

    public void receberDano(int danoBruto) {
        int danoReduzido = Math.max(0, danoBruto - this.defesa);
        System.out.println(this.nome + " recebe " + danoReduzido + " de dano!");
        
        this.setPontosDeVida(this.pontosDeVida - danoReduzido);
    }
    
    public String getNome() {
        return nome;
    }

    public int getPontosDeVida() {
        return pontosDeVida; 
    }

    public int getPontosDeVidaMax() {
        return pontosDeVidaMax; 
    }

    public void setPontosDeVida(int pontosDeVida) {
        int novoHP = Math.min(pontosDeVida, this.pontosDeVidaMax);
        this.pontosDeVida = Math.max(0, novoHP);
        
        if (this.pontosDeVida == 0) {
            this.morrer();
        }
    }

    public void setNivel(byte nivel) {
        this.nivel = (byte) Math.max(1, nivel);
    }
    
    public byte getNivel() {
        return this.nivel;
    }

    public Inventario getInventario() {
        return inventario;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome + "\n" + "Nivel: " + this.nivel + "\n" + "Vida: " + this.pontosDeVida +"\n" + "Vida Máxima: " +this.pontosDeVidaMax + "\n" + "Ataque: " + this.ataque + "\n" +"Defesa: " + this.defesa;
    }
}