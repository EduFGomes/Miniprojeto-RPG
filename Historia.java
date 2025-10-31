import java.util.Scanner;

public class Historia {
    private Scanner entrada = new Scanner(System.in);
    private Personagem heroi;

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

        // Chama o novo menu de destinos
        menuDestinos();
    }

    // ===============================================
    //               NOVO MENU DE DESTINOS
    // ===============================================
    private void menuDestinos() throws Exception {
        boolean explorando = true;
        while (explorando) {
            System.out.println("\n--- ONDE VOCÊ DESEJA PROSSEGUIR, " + heroi.getNome() + "? ---");
            System.out.println("1. Entrar na Floresta Sombria");
            System.out.println("2. Explorar Ruínas Antigas");
            System.out.println("3. Ver Status do Herói");
            System.out.println("4. Abrir Inventário");
            System.out.println("5. Sair do Jogo");
            System.out.print("Escolha: ");
            
            // Usamos nextLine() e parseamos para int para maior segurança com o Scanner
            String escolhaDestino = entrada.nextLine().trim();
            
            switch (escolhaDestino) {
                case "1" -> {
                    introducao(); // Inicia a cadeia de batalhas original
                    explorando = false; // Termina o loop após o fim da cadeia
                }
                case "2" -> explorarRuinas();
                // Assumindo que o herói retorna ao menu após a exploração/batalha
                case "3" -> System.out.println("\n--- STATUS ATUAIS ---\n" + heroi.toString());
                case "4" -> {
                    System.out.println("\n--- SEU INVENTÁRIO ---");
                    heroi.getInventario().listarItems();
                    pausar();
                }
                case "5" -> {
                    System.out.println("Aventura encerrada. O Vale Sombrio terá que esperar.");
                    explorando = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // ===============================================
    //              NOVO DESTINO - RUÍNAS
    // ===============================================
    private void explorarRuinas() throws Exception {
        System.out.println("\nVocê chega às Ruínas Antigas. A atmosfera é pesada e silenciosa.");
        pausar();
        System.out.println("Um Espírito Vingativo, guardião do local, surge à sua frente!");
        pausar();
        
        Inimigo fantasma = new Bruxa("Espírito Vingativo", (byte) 3, new Inventario()); 
        batalha(heroi, fantasma);

        if (heroi.getPontosDeVida() > 0) {
            System.out.println("\nO Espírito se dissipa. Você encontra um baú vazio, mas sente-se mais forte.");
            try {
                Item loot = new Item("Bomba Gigante", "Causa 100 de dano ao inimigo", "DANO", 100, 1);
                heroi.getInventario().adicionarItem(loot);
                System.out.println("Você obteve: " + loot.getNome() + "!");
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
        System.out.println("Rumores dizem que uma Bruxa ancestral retornou, trazendo monstros e caos.");
        pausar();

        System.out.println("Você caminha por uma floresta densa, quando ouve um som de passos pesados...");
        pausar();

        Inimigo gigante = new Gigante("Gigante das Montanhas", (byte) 2, new Inventario());
        batalha(heroi, gigante);

        if (heroi.getPontosDeVida() > 0) {
            System.out.println("\nO Gigante ruge e cai ao chão...");
            try {
                Item loot = new Item("Pocao de Cura", "Restaura 50 HP", "CURA", 50, 2);
                heroi.getInventario().adicionarItem(loot);
                System.out.println("O Gigante deixou cair uma " + loot.getNome() + "!");
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
        System.out.println("\nNo coração da floresta, uma figura encapuzada surge: é a temida BRUXA DA NÉVOA!");
        pausar();

        Inimigo bruxa = new Bruxa("Bruxa da Névoa", (byte) 3, new Inventario());
        batalha(heroi, bruxa);

        if (heroi.getPontosDeVida() > 0) {
            System.out.println("\nCom um grito, a Bruxa desaparece em fumaça — mas uma sombra surge nos céus...");
            try {
                Item loot = new Item("Pocao de Cura Maior", "Restaura 100 HP", "CURA", 100, 1);
                heroi.getInventario().adicionarItem(loot);
                System.out.println("A Bruxa deixou para trás uma " + loot.getNome() + "!");
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
        System.out.println("\nO Dragão Ancião desce dos céus, cuspindo chamas sobre o vale!");
        pausar();

        Inimigo dragao = new Dragao("Dragão Ancião", (byte) 5, new Inventario());
        batalha(heroi, dragao);

        if (heroi.getPontosDeVida() > 0) {
            finalVitoria();
        } else {
            finalDerrota();
        }
    }

    // ===============================================
    //           MÉTODOS DE BATALHA E FINAIS
    // ===============================================

    private void batalha(Personagem heroi, Personagem inimigo) {
        System.out.println("\nBATALHA INICIADA: " + heroi.getNome() + " VS " + inimigo.getNome());

        while (heroi.getPontosDeVida() > 0 && inimigo.getPontosDeVida() > 0) {
            System.out.println("\nSeu HP: " + heroi.getPontosDeVida() + " | HP do inimigo: " + inimigo.getPontosDeVida());
            System.out.println("1 - Atacar");
            System.out.println("2 - Defender");
            System.out.println("3 - Usar Item");
            System.out.print("Escolha sua ação: ");
            
            String acaoStr = entrada.nextLine().trim();
            int acao;
            try {
                acao = Integer.parseInt(acaoStr);
            } catch (NumberFormatException e) {
                System.out.println("Ação inválida. Perdendo turno.");
                pausar();
                continue;
            }

            int defesaOriginal = heroi.defesa;

            switch (acao) {
                case 1 -> {
                    inimigo.receberDano(heroi.ataque);
                    System.out.println("Você ataca " + inimigo.getNome() + "!");
                }
                case 2 -> {
                    System.out.println("Você se defende e aumenta a defesa temporariamente.");
                    heroi.defesa += 5;
                }
                case 3 -> {
                    boolean turnoUsado = usarItemEmBatalha(inimigo);
                    if (!turnoUsado) {
                        System.out.println("Voltando para a seleção de ação...");
                        heroi.defesa = defesaOriginal;
                        continue;
                    }
                }
                default -> System.out.println("Ação inválida. Perdendo turno.");
            }

            if (inimigo.getPontosDeVida() > 0) {
                heroi.receberDano(inimigo.ataque);
                System.out.println(inimigo.getNome() + " contra-ataca!");
            }
            
            if (acao == 2) {
                heroi.defesa = defesaOriginal;
            }

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
                System.out.println("✨ " + heroi.getNome() + " usa " + item.getNome() + " e restaura " + cura + " HP.");
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