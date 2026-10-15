public class Main {

    // 1. metodo sem retorno (void)
    static void cabecalho(String titulo) {
        // imprima: == titulo ==
    }

    // 2. metodo com retorno
    static double orcamento(int secoes, double valorPorSecao) {
        // devolva secoes * valorPorSecao
        return 0;
    }

    // 3. overloading: mesmo nome, um parametro so
    static double orcamento(int secoes) {
        // chame o de cima usando 240.50 como valor padrao
        return 0;
    }

    // 4. array como parametro
    static double soma(double[] valores) {
        // some tudo com um for-each e devolva
        return 0;
    }

    // 5. um metodo chamando outro
    static double media(double[] valores) {
        // soma dividido por valores.length
        return 0;
    }

    public static void main(String[] args) {
        cabecalho("Orcamentos");
        System.out.println(orcamento(5, 240.50));
        System.out.println(orcamento(5));
        System.out.println(orcamento(3, 300.0));

        double[] fechados = {1202.5, 900.0, 1500.0};
        cabecalho("Resumo");
        System.out.println("Quantos: " + fechados.length);
        System.out.println("Soma: " + soma(fechados));
        System.out.println("Media: " + media(fechados));
    }
}
