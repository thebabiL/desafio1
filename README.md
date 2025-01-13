# desafio1
Primeiro desafio estágio UOL - jan/2025

Sistema de Gerenciamento de uma Loja Virtual

Visão Geral do Projeto
  Este projeto consiste em um sistema de gerenciamento de loja virtual desenvolvido em Java. Ele permite que clientes e vendedores interajam de maneira eficaz, oferecendo funcionalidades como manipulação de produtos no estoque, criação de carrinhos de compras e finalização de transações. Embora funcional, o sistema apresenta algumas áreas que necessitam de melhorias.

Funcionalidades
Cliente
  * Cadastro: O usuário insere seu nome para identificação.
  * Carrinho de Compras:
   * Visualização dos produtos disponíveis no estoque.
   * Adição, atualização e remoção de produtos no carrinho.
   * Finalização da compra, com ajuste automático das quantidades no estoque.
Vendedor
  * Gerenciamento de Estoque:
   * Adição de novos produtos.
   * Atualização de informações dos produtos existentes.
   * Remoção de itens.
   * Listagem de todos os produtos disponíveis no sistema.

Conexão com o Banco de Dados
  A conexão com o banco de dados MySQL é realizada através da API JDBC, que permite uma interação eficiente e segura com o banco de dados. A classe de configuração é responsável por gerenciar essa conexão, garantindo a execução correta das operações de CRUD (Create, Read, Update, Delete).

Problemas Conhecidos
  * Embora o programa esteja operando, algumas falhas persistem.
    * Carrinho de Compras:
      Erros na atualização da quantidade de produtos.
      Listagem incorreta de produtos, às vezes exibindo dados do estoque.
      Problemas na remoção de produtos, não atualizando corretamente os valores.

  Essas falhas podem ser resultado da combinação das lógicas de carrinho e estoque, causando inconsistências. No entanto, o gerenciamento do estoque funciona corretamente.


