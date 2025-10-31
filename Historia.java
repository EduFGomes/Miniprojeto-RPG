import java.util.Scanner;
import java.util.Random;

public class Historia {
    private Scanner entrada = new Scanner(System.in);
    private Personagem heroi;
    private Random dado = new Random();

    public static void main(String[] args) {
        try {
            new Historia().iniciar();
        } catch (Exception e) {
            System.err.println("Erro no jogo: " + e.getMessage());
        }
    }

    public void iniciar() throws Exception {
        System.out.println("=== BEM-VINDO AO RPG: A MALDIÇÃO DO VALE SOMBRIO ===");
        System.out.print("Qual é o seu nome, herói? ");
        String nome = entrada.nextLine();

        Inventario inventarioInicial = new Inventario(); 

        try {
            inventarioInicial.adicionarItem(new Item("Pocao de Cura", "Restaura 50 HP", "CURA", 50, 3));
            inventarioInicial.adicionarItem(new Item("Bomba Pequena", "Causa 15 de dano ao inimigo", "DANO", 40, 1));
        } catch (Exception e) {
            System.err.println("Erro ao criar itens iniciais: " + e.getMessage());
        }
        
        System.out.println("\nEscolha sua classe:");
        System.out.println("1 - Guerreiro");
        System.out.println("2 - Arqueiro");
        System.out.println("3 - Mago");
        System.out.print("Opção: ");
        int opcao = entrada.nextInt();
        entrada.nextLine();

        switch (opcao) {
            case 1 -> heroi = new Guerreiro(nome, (byte) 1, inventarioInicial, 0);
            case 2 -> heroi = new Arqueiro(nome, (byte) 1, inventarioInicial, 10);
            case 3 -> heroi = new Mago(nome, (byte) 1, inventarioInicial, 50);
            default -> {
                System.out.println("Classe inválida, você será um Guerreiro!");
                heroi = new Guerreiro(nome, (byte) 1, inventarioInicial, 0);
            }
        }

        System.out.println("\nBem-vindo, " + heroi.getNome() + "!");
        System.out.println(heroi.toString());
        pausar();

        menuDestinos();
    }

    // ===============================================
    //          MENU PRINCIPAL DE NAVEGAÇÃO
    // ===============================================
    private void menuDestinos() throws Exception {
        boolean explorando = true;
        while (explorando) {
            System.out.println("\n--- ONDE VOCÊ DESEJA PROSSEGUIR, " + heroi.getNome() + "? ---");
            System.out.println("1. Entrar na Floresta Sombria (Missão Principal)");
            System.out.println("2. Explorar Ruínas Antigas (Missão Secundária)");
            System.out.println("3. Explorar a Trilha Perigosa (Aleatório)");
            System.out.println("4. Ver Status do Herói");
            System.out.println("5. Abrir Inventário");
            System.out.println("6. Sair do Jogo");
            System.out.print("Escolha: ");
            
            String escolhaDestino = entrada.nextLine().trim();
            
            switch (escolhaDestino) {
                case "1" -> {
                    introducao(); // Inicia a cadeia de batalhas original
                    // Se o herói venceu a quest, paramos o loop
                    if (heroi.getPontosDeVida() > 0) {
                        explorando = false; 
                    }
                }
                case "2" -> explorarRuinas();
                case "3" -> explorarTrilhaPerigosa(); // NOVA OPÇÃO [cite: 47, 52]
                case "4" -> System.out.println("\n--- STATUS ATUAIS ---\n" + heroi.toString());
                case "5" -> {
                    System.out.println("\n--- SEU INVENTÁRIO ---");
                    heroi.getInventario().listarItems(); // 
                    pausar();
                }
                case "6" -> {
                    System.out.println("Aventura encerrada. O Vale Sombrio terá que esperar.");
                    explorando = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            
            // Se o herói morreu em alguma exploração, encerra o loop principal
            if (heroi.getPontosDeVida() <= 0) {
                explorando = false;
            }
        }
    }

    // ===============================================
    //               TRILHA PERIGOSA 
    // ===============================================
    private void explorarTrilhaPerigosa() throws Exception {
        System.out.println("\nVocê decide explorar uma trilha perigosa na esperança de encontrar algo...");
        pausar();
        
        int evento = rolarDado(100);

        if (evento <= 40) { 
            System.out.println("Um Goblin faminto salta dos arbustos!");
            Inventario inventarioGoblin = new Inventario();
            inventarioGoblin.adicionarItem(new Item("Adaga Enferrujada", "Lixo", "LIXO", 1, 1));
            Inimigo goblin = new Gigante("Goblin Saqueador", (byte) 1, inventarioGoblin);
            
            batalha(heroi, goblin);
            
            if(heroi.getPontosDeVida() > 0) {
                System.out.println("Você derrotou o Goblin.");
                System.out.println("Você vasculha o corpo e encontra: ");
                heroi.getInventario().clonarEAdicionarItens(goblin.getInventario());
            }

        } else if (evento <= 60) {
            System.out.println("Você pisa em uma armadilha de espinhos!");
            int danoArmadilha = rolarDado(10) + 5;
            heroi.receberDano(danoArmadilha);
            System.out.println("Você sofreu " + danoArmadilha + " de dano. HP atual: " + heroi.getPontosDeVida());
            if (heroi.getPontosDeVida() <= 0) {
                finalDerrota();
            }

        } else {
            System.out.println("Você encontra uma pequena bolsa de couro abandonada no chão.");
            System.out.println("O que você faz?");
            System.out.println("1. Pegar a bolsa");
            System.out.println("2. Ignorar e seguir em frente");
            System.out.print("Escolha: ");
            String escolha = entrada.nextLine();

            if (escolha.equals("1")) {
                System.out.println("Você abre a bolsa e encontra...");
                pausar();
                try {
                    Item pocao = new Item("Pocao de Cura", "Restaura 50 HP", "CURA", 50, 1);
                    heroi.getInventario().adicionarItem(pocao); // 
                    System.out.println("Uma " + pocao.getNome() + " foi adicionada ao seu inventário!");
                } catch (Exception e) {
                    System.err.println("Erro ao adicionar item: " + e.getMessage());
                }
            } else {
                System.out.println("Você decide que é melhor não arriscar e continua seu caminho.");
            }
        }
        pausar();
    }

    // ===============================================
    //                    RUÍNAS
    // ===============================================
    private void explorarRuinas() throws Exception {
    System.out.println("\nVocê chega às Ruínas Antigas...");
    System.out.println("Um Espírito Vingativo... surge à sua frente!");
    pausar();
    
    Inventario lootFantasma = new Inventario();
    try {
        lootFantasma.adicionarItem(new Item("Bomba Gigante", "Causa 100 de dano ao inimigo", "DANO", 100, 1));
    } catch (Exception e) {
        System.err.println("Erro ao criar loot do fantasma: " + e.getMessage());
    }

    Inimigo fantasma = new Bruxa("Espírito Vingativo", (byte) 3, lootFantasma); 
    batalha(heroi, fantasma);

    if (heroi.getPontosDeVida() > 0) {
        System.out.println("\nO Espírito se dissipa. Você vasculha os restos...");
        try {
            heroi.getInventario().clonarEAdicionarItens(fantasma.getInventario());
        } catch (Exception e) {
            System.err.println("Erro ao adicionar loot: " + e.getMessage());
        }
        
        heroi.setNivel((byte) (heroi.getNivel() + 1));
        System.out.println("🎉 " + heroi.getNome() + " subiu de nível!");
    } else {
        finalDerrota();
    }
}
    
    // ===============================================
    //           CADEIA DE BATALHAS ORIGINAL
    // ===============================================

    private void introducao() throws Exception {
    System.out.println("\nO sol se põe sobre o Vale Sombrio...");
    pausar();
    System.out.println("Você caminha por uma floresta densa...");
    pausar();

    Inventario lootGigante = new Inventario();
    try {
        lootGigante.adicionarItem(new Item("Pocao de Cura", "Restaura 50 HP", "CURA", 50, 2));
    } catch (Exception e) {
         System.err.println("Erro ao criar loot do gigante: " + e.getMessage());
    }

    Inimigo gigante = new Gigante("Gigante das Montanhas", (byte) 2, lootGigante);
    batalha(heroi, gigante);

    if (heroi.getPontosDeVida() > 0) {
        System.out.println("\nO Gigante ruge e cai ao chão...");
        try {
            heroi.getInventario().clonarEAdicionarItens(gigante.getInventario());
        } catch (Exception e) {
            System.err.println("Erro ao adicionar loot: " + e.getMessage());
        }
        pausar();
        encontroComBruxa();
    } else {
        finalDerrota();
    }
}

private void encontroComBruxa() throws Exception {
    System.out.println("\nNo coração da floresta... surge a BRUXA DA NÉVOA!");
    pausar();

    Inventario lootBruxa = new Inventario();
     try {
        lootBruxa.adicionarItem(new Item("Pocao de Cura Maior", "Restaura 100 HP", "CURA", 100, 1));
    } catch (Exception e) {
         System.err.println("Erro ao criar loot da bruxa: " + e.getMessage());
    }

    Inimigo bruxa = new Bruxa("Bruxa da Névoa", (byte) 3, lootBruxa);
    batalha(heroi, bruxa);

    if (heroi.getPontosDeVida() > 0) {
        System.out.println("\nCom um grito, a Bruxa desaparece em fumaça...");
        try {
            heroi.getInventario().clonarEAdicionarItens(bruxa.getInventario());
        } catch (Exception e) {
            System.err.println("Erro ao adicionar loot: " + e.getMessage());
        }
        pausar();
        confrontoFinal();
    } else {
        finalDerrota();
    }
}

private void confrontoFinal() throws Exception {
    System.out.println("\nO Dragão Ancião desce dos céus...");
    pausar();

    Inventario lootDragao = new Inventario();
     try {
        lootDragao.adicionarItem(new Item("Fragmento de Escama", "Material raro", "MATERIAL", 999, 1));
        lootDragao.adicionarItem(new Item("Pocao Lendaria", "Restaura 500 HP", "CURA", 500, 1));
    } catch (Exception e) {
         System.err.println("Erro ao criar loot do dragão: " + e.getMessage());
    }

    Inimigo dragao = new Dragao("Dragão Ancião", (byte) 5, lootDragao);
    batalha(heroi, dragao);

    if (heroi.getPontosDeVida() > 0) {
        System.out.println("\nO Dragão foi derrotado e seu tesouro é seu!");
        heroi.getInventario().clonarEAdicionarItens(dragao.getInventario());
        pausar();
        finalVitoria();
    } else {
        finalDerrota();
    }
}

    // ===============================================
    //           MÉTODOS DE BATALHA E FINAIS
    // ===============================================

    private int rolarDado(int faces) {
        return dado.nextInt(faces) + 1;
    }

    private void batalha(Personagem heroi, Personagem inimigo) {
        System.out.println("\nBATALHA INICIADA: " + heroi.getNome() + " VS " + inimigo.getNome());

        while (heroi.getPontosDeVida() > 0 && inimigo.getPontosDeVida() > 0) {
            System.out.println("\n--- TURNO DO HERÓI ---");
            System.out.println("Seu HP: " + heroi.getPontosDeVida() + " | HP inimigo: " + inimigo.getPontosDeVida());
            System.out.println("1 - Atacar");
            System.out.println("2 - Defender");
            System.out.println("3 - Usar Item");
            System.out.println("4 - Fugir");
            System.out.print("Escolha sua ação: ");

            String acaoStr = entrada.nextLine().trim();
            boolean turnoDoInimigo = true;
            int defesaOriginalHeroi = heroi.defesa;

            switch (acaoStr) {
            case "1" -> {
                int rolagemHeroi = rolarDado(20);
                int ataqueTotalHeroi = heroi.ataque + rolagemHeroi;
                System.out.println("Você rolou " + rolagemHeroi + " (+" + heroi.ataque + " atk) = " + ataqueTotalHeroi);
                
                if (ataqueTotalHeroi > inimigo.defesa) {
                    inimigo.receberDano(heroi.ataque); 
                } else {
                    System.out.println("Seu ataque foi bloqueado pela defesa (" + inimigo.defesa + ") do inimigo!");
                }
            }
            case "2" -> {
                System.out.println("Você se prepara para o impacto, aumentando a defesa temporariamente.");
                heroi.defesa += 5;
            }
            case "3" -> {
                boolean usouItem = usarItemEmBatalha(inimigo);
                if (!usouItem) {
                    turnoDoInimigo = false;
                    System.out.println("Voltando para a seleção de ação...");
                }
            }
            case "4" -> {
                System.out.println("Você tenta fugir da batalha...");
                int rolagemFuga = rolarDado(20);
                if (rolagemFuga > 10) {
                    System.out.println("Você conseguiu escapar!");
                    return;
                } else {
                    System.out.println("A fuga falhou! Você está preso na batalha.");
                }
            }
            default -> System.out.println("Ação inválida. Você perdeu seu turno.");
        }

        pausar();

        if (inimigo.getPontosDeVida() > 0 && turnoDoInimigo) {
            System.out.println("\n--- TURNO DO INIMIGO ---");
            
            if (inimigo instanceof Gigante && rolarDado(100) > 70) {
                ((Gigante) inimigo).pisaoEsmagador(heroi);
            } else {
                int rolagemInimigo = rolarDado(20);
                int ataqueTotalInimigo = inimigo.ataque + rolagemInimigo;
                System.out.println(inimigo.getNome() + " rolou " + rolagemInimigo + " (+" + inimigo.ataque + " atk) = " + ataqueTotalInimigo);

                if (ataqueTotalInimigo > heroi.defesa) {
                    heroi.receberDano(inimigo.ataque);
                } else {
                    System.out.println(inimigo.getNome() + " atacou, mas sua defesa (" + heroi.defesa + ") bloqueou!");
                }
            }
        }
        
        heroi.defesa = defesaOriginalHeroi;
        pausar();
    }
}

    private boolean usarItemEmBatalha(Personagem inimigo) {
        System.out.println("\n--- INVENTÁRIO DE BATALHA ---");
        heroi.getInventario().listarItems();
        System.out.print("Digite o nome do item que deseja usar (ou 'cancelar'): ");
        String nomeItem = entrada.nextLine();

        if (nomeItem.equalsIgnoreCase("cancelar")) {
            return false;
        }

        Item item = heroi.getInventario().buscarItem(nomeItem);

        if (item == null) {
            System.out.println("Item não encontrado.");
            return false;
        }

        switch (item.getTipoEfeito()) {
            case "CURA" -> {
                int cura = item.getForcaEfeito();
                heroi.setPontosDeVida(heroi.getPontosDeVida() + cura);
                System.out.println(heroi.getNome() + " usa " + item.getNome() + " e restaura " + cura + " HP.");
                heroi.getInventario().usarItem(item);
                return true;
            }
            case "DANO" -> {
                int dano = item.getForcaEfeito();
                inimigo.receberDano(dano);
                heroi.getInventario().usarItem(item);
                return true;
            }
            default -> {
                System.out.println("O item '" + item.getNome() + "' não pode ser usado em batalha.");
                return false;
            }
        }
    }

    private void finalVitoria() {
        System.out.println("\nVocê derrotou o Dragão e libertou o vale da escuridão!");
        System.out.println("A lenda do herói " + heroi.getNome() + " ecoará por gerações...");
        System.out.println("\nFIM DE JOGO - FINAL HEROICO");
    }

    private void finalDerrota() {
        System.out.println("\nVocê caiu em batalha...");
        System.out.println("Mas talvez um novo herói surja um dia para continuar sua jornada.");
        System.out.println("\n=== GAME OVER ===");
    }

    private void pausar() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ignora pausa
        }
    }
}