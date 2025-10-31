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
        System.out.println("✨=== BEM-VINDO AO RPG: A MALDIÇÃO DO VALE SOMBRIO ===✨");
        System.out.print("Qual é o seu nome, herói? ");
        String nome = entrada.nextLine();

        // O Inventário é instanciado aqui, mas sem itens para evitar o erro "Item cannot be resolved".
        Inventario inventarioInicial = new Inventario(); 
        
        // CORREÇÃO: Usamos nextInt() e precisamos limpar o buffer com nextLine()
        System.out.println("\nEscolha sua classe:");
        System.out.println("1 - Guerreiro ⚔️");
        System.out.println("2 - Arqueiro 🏹");
        System.out.println("3 - Mago 🔮");
        System.out.print("Opção: ");
        int opcao = entrada.nextInt();
        entrada.nextLine(); // Limpa o buffer após nextInt()

        switch (opcao) {
            case 1:
                heroi = new Guerreiro(nome, (byte) 1, inventarioInicial, 0);
                break;
            case 2:
                heroi = new Arqueiro(nome, (byte) 1, inventarioInicial, 10);
                break;
            case 3:
                heroi = new Mago(nome, (byte) 1, inventarioInicial, 50);
                break;
            default:
                System.out.println("Classe inválida, você será um Guerreiro!");
                heroi = new Guerreiro(nome, (byte) 1, inventarioInicial, 0);
                break;
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
            System.out.println("1. Entrar na Floresta Sombria (Cadeia de Batalhas Gigante -> Bruxa -> Dragão)");
            System.out.println("2. Explorar Ruínas Antigas (Novo Destino!)");
            System.out.println("3. Ver Status do Herói");
            System.out.println("4. Sair do Jogo");
            System.out.print("Escolha: ");
            
            // Usamos nextLine() e parseamos para int para maior segurança com o Scanner
            String escolhaDestino = entrada.nextLine().trim();
            
            switch (escolhaDestino) {
                case "1":
                    introducao(); // Inicia a cadeia de batalhas original
                    explorando = false; // Termina o loop após o fim da cadeia
                    break;
                case "2":
                    explorarRuinas();
                    // Assumindo que o herói retorna ao menu após a exploração/batalha
                    break; 
                case "3":
                    System.out.println("\n--- STATUS ATUAIS ---\n" + heroi.toString());
                    break;
                case "4":
                    System.out.println("Aventura encerrada. O Vale Sombrio terá que esperar.");
                    explorando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    // ===============================================
    //              NOVO DESTINO - RUÍNAS
    // ===============================================
    private void explorarRuinas() throws Exception {
        System.out.println("\n🏛️ Você chega às Ruínas Antigas. A atmosfera é pesada e silenciosa.");
        pausar();
        System.out.println("Um Espírito Vingativo, guardião do local, surge à sua frente!");
        pausar();
        
        // Exemplo de inimigo (pode ser qualquer subclasse de Inimigo)
        Inimigo fantasma = new Bruxa("Espírito Vingativo", (byte) 3, new Inventario()); 
        batalha(heroi, fantasma);

        if (heroi.getPontosDeVida() > 0) {
            System.out.println("\nO Espírito se dissipa. Você encontra um baú vazio, mas sente-se mais forte.");
            // Poderia adicionar Level-up aqui ou um item.
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
        System.out.println("\n🌒 O sol se põe sobre o Vale Sombrio...");
        System.out.println("Rumores dizem que uma Bruxa ancestral retornou, trazendo monstros e caos.");
        pausar();

        System.out.println("Você caminha por uma floresta densa, quando ouve um som de passos pesados...");
        pausar();

        Inimigo gigante = new Gigante("Gigante das Montanhas", (byte) 2, new Inventario());
        batalha(heroi, gigante);

        if (heroi.getPontosDeVida() > 0) {
            System.out.println("\nO Gigante ruge e cai ao chão...");
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
            pausar();
            confrontoFinal();
        } else {
            finalDerrota();
        }
    }

    private void confrontoFinal() throws Exception {
        System.out.println("\n🔥 O Dragão Ancião desce dos céus, cuspindo chamas sobre o vale!");
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
        System.out.println("\n⚔️ BATALHA INICIADA: " + heroi.getNome() + " VS " + inimigo.getNome());

        while (heroi.getPontosDeVida() > 0 && inimigo.getPontosDeVida() > 0) {
            System.out.println("\nSeu HP: " + heroi.getPontosDeVida() + " | HP do inimigo: " + inimigo.getPontosDeVida());
            System.out.println("1 - Atacar");
            System.out.println("2 - Defender");
            System.out.print("Escolha sua ação: ");
            
            // Lendo a ação como String e depois convertendo
            String acaoStr = entrada.nextLine().trim();
            int acao = 0;
            try {
                acao = Integer.parseInt(acaoStr);
            } catch (NumberFormatException e) {
                System.out.println("Ação inválida. Perdendo turno.");
                pausar();
                continue; // Pula para a próxima iteração do loop
            }

            int defesaOriginal = heroi.defesa;

            if (acao == 1) {
                inimigo.receberDano(heroi.ataque);
                System.out.println("Você ataca " + inimigo.getNome() + "!");
            } else if (acao == 2) {
                System.out.println("Você se defende e aumenta a defesa temporariamente.");
                heroi.defesa += 5;
            } else {
                System.out.println("Ação inválida. Perdendo turno.");
            }

            if (inimigo.getPontosDeVida() > 0) {
                heroi.receberDano(inimigo.ataque);
                System.out.println(inimigo.getNome() + " contra-ataca!");
            }
            
            // Restaura a defesa
            if (acao == 2) {
                heroi.defesa = defesaOriginal;
            }

            pausar();
        }
    }

    private void finalVitoria() {
        System.out.println("\n🏆 Você derrotou o Dragão e libertou o vale da escuridão!");
        System.out.println("A lenda do herói " + heroi.getNome() + " ecoará por gerações...");
        System.out.println("\n✨ FIM DE JOGO - FINAL HEROICO ✨");
    }

    private void finalDerrota() {
        System.out.println("\n💀 Você caiu em batalha...");
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