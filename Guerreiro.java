//conceito: tank 
//parametro especifico: FURIA - aumenta quando leva dano 
public class Guerreiro extends Personagem {
    // Atributos nivel 1
    private static int VIDA_BASE = 200;
    private static int ATAQUE_BASE = 30;
    private static int DEFESA_BASE = 25;

    //aumento por nivel
    private static double FATOR_VIDA = 0.15;   
    private static double FATOR_ATAQUE = 0.10; 
    private static double FATOR_DEFESA = 0.10; 

    private int furia;

    public Guerreiro(String nome, byte nivel, Inventario inventario, int furiaInicial) throws Exception {
        super(nome, VIDA_BASE, ATAQUE_BASE, DEFESA_BASE, (byte) 1, inventario);
        
        this.furia = furiaInicial;
        this.setNivel(nivel);
    }
    
    //copia
    public Guerreiro(Guerreiro outro) {
        super(outro); 
        this.furia = outro.furia;
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

    //Logica para o ataque especifico GUERREIRO
    @Override
    public void receberDano(int danoBruto) {
        int hpAntes = this.pontosDeVida;
    
        super.receberDano(danoBruto); 
    
        int danoRealRecebido = hpAntes - this.pontosDeVida;

        if (danoRealRecebido > 0) {
            int furiaGanha = danoRealRecebido / 8; 
        
            if (furiaGanha > 0) {
                this.setFuria(this.furia + furiaGanha);
                System.out.println(this.nome + " fica furioso e gera " + furiaGanha + " de fúria!");
            }
        }
    }

    //ataque especifico GUERREIRO
    public void ataqueFurioso(Personagem alvo) {
        int custoFuria = 15;

        if (this.furia >= custoFuria) {
            System.out.println(this.nome + " usa 'Ataque Furioso' em " + alvo.getNome() + "!");
            this.setFuria(this.furia - custoFuria); 

            //logica do Ataque: Causa 150% do dano de ataque
            int danoCausado = (int) (this.ataque * 1.5);
            
            System.out.println(this.nome + " causa " + danoCausado + " de dano bruto!");
            
            alvo.receberDano(danoCausado);

        } else {
            System.out.println(this.nome + " não tem fúria suficiente (" + this.furia + "/" + custoFuria + ")!");
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
        return super.toString() + "\n" + "Furia: " + this.furia;
    }
}