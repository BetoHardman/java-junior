public class Main {
 
    static void cabecalho(String titulo) {
    System.out.println("== " + titulo + " ==");
     
        
    }

    
    static double orcamento(int secoes, double valorPorSecao) {
        
        return secoes * valorPorSecao;
    }

    
    static double orcamento(int secoes) {
        return orcamento(secoes, 240.50);
        
    }

    
    static double soma(double[] valores) {
        double t = 0;
for (double v : valores) {
t = t + v;
}
return t;
       }
    

    
    static double media(double[] valores) {
        return soma(valores) / valores.length;
        
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
