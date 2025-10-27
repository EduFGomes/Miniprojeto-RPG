//conceito: equilibrio, distancia
public class Arqueiro extends Personagem {

    //atributos nivel 1
    private static int VIDA_BASE = 140;
    private static int ATAQUE_BASE = 35; 
    private static int DEFESA_BASE = 15;
    
    //aumento por nivel
    private static double FATOR_VIDA = 0.10;   
    private static double FATOR_ATAQUE = 0.10; 
    private static double FATOR_DEFESA = 0.15; 

    private int flechas;

    public Arqueiro(String nome, byte nivel, Inventario inventario, int flechasIniciais) throws Exception {
        super(nome, VIDA_BASE, ATAQUE_BASE, DEFESA_BASE, (byte) 1, inventario);
        this.flechas = flechasIniciais;
        
        this.setNivel(nivel);
    }

    //copia
    public Arqueiro(Arqueiro outro) {
        super(outro);
        this.flechas = outro.flechas;
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

    public void atirarFlecha(Personagem alvo, String tipoTiro) {
        int custoFlechas = 0;
        int danoBruto = 0;

        String tiroNomeado = tipoTiro.toLowerCase();

        switch (tiroNomeado) {
            case "tiro preciso":
                custoFlechas = 1;
                // Dano = 10 (base) + 140% do ataque do arqueiro
                danoBruto = 10 + (int)(this.ataque * 1.4);
                break;

            case "tiro duplo":
                custoFlechas = 2;
                // Dano = 20 (base) + 180% do ataque (dois tiros)
                danoBruto = 20 + (int)(this.ataque * 1.8);
                break;
            
            case "chuva de flechas":
                custoFlechas = 5;
                // Dano = 30 (base) + 220% do ataque (dano em área simulado)
                danoBruto = 30 + (int)(this.ataque * 2.2);
                break;

            default:
                System.out.println("Tentativa de usar tipo de tiro não reconhecido: '" + tipoTiro + "'. Nenhuma ação foi tomada.");
                break;
        }

        if (custoFlechas == 0) {
            return;
        }

        if (this.flechas >= custoFlechas) {
            this.flechas -= custoFlechas;
            
            System.out.println(this.nome + " usa '" + tipoTiro + "' em " + alvo.getNome() + "!");
            alvo.receberDano(danoBruto);
            
        } else {
            System.out.println(this.nome + " tentou usar '" + tipoTiro + "', mas não tem flechas suficientes! (Requer: " + custoFlechas + ")");
        }
    }
    
    public int getFlechas() {
        return flechas;
    }

    public void adicionarFlechas(int quantidade) {
        this.flechas += Math.max(0, quantidade);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Número de flechas: " + this.flechas;
    }
}