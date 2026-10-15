public class Main {
    public static void main(String[] args) {
         String cliente = "Luna Toys";
         int secoes = 5;
         double valorPorSecao = 240.50;
         boolean urgente = true;
         double total = secoes * valorPorSecao;
int totalInteiro = (int) total;

System.out.println("Cliente: " + cliente);
System.out.println("Secoes: " + secoes);
System.out.println("Total: " + total);
System.out.println("Inteiro: " + totalInteiro);
System.out.println("Urgente: " + urgente);
    }
}
