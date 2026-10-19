String nome = Roberto;
String whatsapp = 619955729995;
String status = Negado; 

Cliente(String nome, String whatsapp) {
this.nome = nome;
this.wahtsapp = whatsapp;
this.status = "novo";
}

void mudarStatus(String novo) {
this.status = novo;
}
String resumo() {
return nome + " (" + whatsapp + ") - " + status;
}

