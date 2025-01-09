package Desafio1.application;

import Desafio1.entities.Carrinho;
import Desafio1.entities.Produto;

public class program {

  public static void main(String[] args)
  {
    Carrinho carrinho1 = new Carrinho(1, "João ");
        Produto produto1 = new Produto(1, "Notebook", "Laptop", "Eletrônicos", 3000.0, 0);
        Produto produto2 = new Produto(2, "Mouse", "Acessório sem fio", "Acessórios", 150.0, 0);

        carrinho1.adicionarProduto(produto1, 1);
        carrinho1.adicionarProduto(produto2, 2);

        
        Carrinho carrinho2 = new Carrinho(2, "João Silva");
        carrinho2.adicionarProduto(produto1, 1);
        carrinho2.adicionarProduto(produto2, 2);

        System.out.println(carrinho1);

        System.out.println(carrinho2);
  }
}
