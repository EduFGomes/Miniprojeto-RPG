//conceito: usa mana
public class Mago extends Personagem {

    //atributos nivel 1    
    private static int VIDA_BASE = 120;
    private static int ATAQUE_BASE = 45;
    private static int DEFESA_BASE = 10;
    
    //aumento por nivel
    private static double FATOR_VIDA = 0.10;   
    private static double FATOR_ATAQUE = 0.15; 
    private static double FATOR_DEFESA = 0.10; 

    private int mana;

    public Mago(String nome, byte nivel, Inventario inventario, int manaInicial) throws Exception {
        super(nome, VIDA_BASE, ATAQUE_BASE, DEFESA_BASE, (byte) 1, inventario);
        this.mana = manaInicial;
        
        this.setNivel(nivel);
    }

    //copia
    public Mago(Mago outro) throws Exception {
        super(outro);
        this.mana = outro.mana;
    }

    //aumento dos atributos por nivel
    @Override
    public void setNivel(byte nivel) {
        byte novoNivel = (byte) Math.max(1, nivel);
        super.setNivel(novoNivel);

        this.pontosDeVidaMax = (int) (VIDA_BASE * (1 + (FATOR_VIDA * (this.nivel - 1))));
        this.ataque = (int) (ATAQUE_BASE * (1 + (FATOR_ATAQUE * (this.nivel - 1))));
        this.defesa = (int) (DEFESA_BASE * (1 + (FATOR_DEFESA * (this.nivel - 1))));
        
        // Cura totalmente o personagem ao subir de nivel
        this.setPontosDeVida(this.pontosDeVidaMax);
    }

    
    public void lancarFeitico(Personagem alvo, String nomeFeitico) {
        int custoMana = 0;
        int danoFeitico = 0;

        switch (nomeFeitico.toLowerCase()) {
            case "bola de fogo":
                custoMana = 20;
                // Dano = 30 (base) + 110% do ataque do mago
                danoFeitico = 30 + (int)(this.ataque * 1.1); 
                break;

            case "raio congelante":
                custoMana = 10;
                // Dano = 15 (base) + 70% do ataque do mago
                danoFeitico = 15 + (int)(this.ataque * 0.7);
                break;
                
            default:
                System.out.println("Tentativa de lançar feitiço não reconhecido: '" + nomeFeitico + "'. Nenhuma ação foi tomada.");
                break;
        }

        if (this.mana >= custoMana) {
            this.setMana(this.mana - custoMana);
            
            System.out.println(this.nome + " lança '" + nomeFeitico + "' em " + alvo.getNome() + "!");
            
            alvo.receberDano(danoFeitico);
            
        } else {
            System.out.println(this.nome + " tentou lançar '" + nomeFeitico + "', mas não tem mana suficiente! (Requer: " + custoMana + ")");
        }
    }
    
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, mana);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Mana: " + this.mana;
    }
}