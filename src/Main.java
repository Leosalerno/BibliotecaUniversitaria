import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final int PRAZO_DIAS = 14;
    private static final int LIMITE_EMPRESTIMOS = 3;
    private static final double MULTA_DIARIA_EXEMPLO = 2.00;

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        boolean executando = true;

        System.out.println("=== BIBLIOTECA UNIVERSITARIA - TP1 ===");
        System.out.println("Prototipo de regras, sem cadastro ou persistencia.");

        while (executando) {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Consultar disponibilidade simulada");
            System.out.println("2 - Verificar se um emprestimo seria permitido");
            System.out.println("3 - Simular devolucao e calculo de multa");
            System.out.println("4 - Verificar se uma reserva seria permitida");
            System.out.println("0 - Sair");
            int opcao = lerInteiro(entrada, "Escolha: ");

            switch (opcao) {
                case 1 -> {
                    System.out.println("\n[CONSULTA DE DISPONIBILIDADE]");
                    int total = lerInteiro(entrada, "Total de exemplares do livro: ");
                    int emprestados = lerInteiro(entrada, "Exemplares emprestados: ");
                    int danificados = lerInteiro(entrada, "Exemplares danificados (nao emprestados): ");

                    if (total < 0 || emprestados < 0 || danificados < 0
                            || emprestados + danificados > total) {
                        System.out.println("Dados inconsistentes. Nenhuma consulta realizada.");
                    } else {
                        int disponiveis = total - emprestados - danificados;
                        System.out.println("Exemplares aptos e disponiveis: " + disponiveis);
                        System.out.println(disponiveis > 0
                                ? "Existe exemplar que pode ser emprestado."
                                : "Nenhum exemplar pode ser emprestado agora.");
                    }
                }
                case 2 -> {
                    System.out.println("\n[VERIFICACAO DE EMPRESTIMO]");
                    int cadastro = lerInteiro(entrada, "Cadastro valido? (1=sim, 0=nao): ");
                    int ativos = lerInteiro(entrada, "Emprestimos ativos do membro: ");
                    int disponivel = lerInteiro(entrada, "Exemplar disponivel? (1=sim, 0=nao): ");
                    int adequado = lerInteiro(entrada, "Exemplar em boas condicoes? (1=sim, 0=nao): ");

                    if (!ehBooleanoNumerico(cadastro) || ativos < 0
                            || !ehBooleanoNumerico(disponivel)
                            || !ehBooleanoNumerico(adequado)) {
                        System.out.println("Dados invalidos. Use 0 ou 1 nas perguntas sim/nao.");
                    } else if (cadastro == 0) {
                        System.out.println("EMPRESTIMO NEGADO: cadastro invalido.");
                    } else if (ativos >= LIMITE_EMPRESTIMOS) {
                        System.out.println("EMPRESTIMO NEGADO: limite de 3 emprestimos ativos.");
                    } else if (disponivel == 0) {
                        System.out.println("EMPRESTIMO NEGADO: exemplar indisponivel.");
                    } else if (adequado == 0) {
                        System.out.println("EMPRESTIMO NEGADO: exemplar sem condicoes de uso.");
                    } else {
                        System.out.println("EMPRESTIMO PERMITIDO (simulacao).");
                        System.out.println("Prazo padrao: " + PRAZO_DIAS + " dias corridos.");
                    }
                }
                case 3 -> {
                    System.out.println("\n[SIMULACAO DE DEVOLUCAO]");
                    int diasDecorridos = lerInteiro(entrada,
                            "Dias corridos entre retirada e devolucao: ");

                    if (diasDecorridos < 0) {
                        System.out.println("Dias decorridos nao podem ser negativos.");
                    } else {
                        int diasAtraso = 0;
                        if (diasDecorridos > PRAZO_DIAS) {
                            diasAtraso = diasDecorridos - PRAZO_DIAS;
                        }
                        double multa = diasAtraso * MULTA_DIARIA_EXEMPLO;
                        System.out.println("Dias de atraso: " + diasAtraso);
                        System.out.printf(Locale.forLanguageTag("pt-BR"),
                                "Multa ilustrativa: R$ %.2f%n", multa);
                        System.out.println("Tarifa de R$ 2,00/dia.");
                        System.out.println("Nenhum emprestimo real foi encerrado.");
                    }
                }
                case 4 -> {
                    System.out.println("\n[VERIFICACAO DE RESERVA]");
                    int cadastro = lerInteiro(entrada, "Cadastro valido? (1=sim, 0=nao): ");
                    int total = lerInteiro(entrada, "Exemplares cadastrados para o livro: ");
                    int emprestados = lerInteiro(entrada, "Exemplares emprestados no momento: ");

                    if (!ehBooleanoNumerico(cadastro) || total < 0
                            || emprestados < 0 || emprestados > total) {
                        System.out.println("Dados invalidos. Reserva nao verificada.");
                    } else if (cadastro == 0) {
                        System.out.println("RESERVA NEGADA: cadastro invalido.");
                    } else if (emprestados == 0) {
                        System.out.println("RESERVA NEGADA: nenhum exemplar esta emprestado.");
                    } else {
                        System.out.println("RESERVA PERMITIDA para o LIVRO (simulacao).");
                        System.out.println("Basta ao menos um exemplar estar emprestado.");
                    }
                }
                case 0 -> {
                    System.out.println("Encerrando o prototipo. Ate logo!");
                    executando = false;
                }
                default -> System.out.println("Opcao inexistente. Escolha de 0 a 4.");
            }
        }
        entrada.close();
    }

    private static int lerInteiro(Scanner entrada, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (entrada.hasNextInt()) {
                return entrada.nextInt();
            }
            System.out.println("Entrada invalida. Digite um numero inteiro.");
            entrada.next();
        }
    }

    private static boolean ehBooleanoNumerico(int valor) {
        return valor == 0 || valor == 1;
    }
}
