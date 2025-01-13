package entities;

import java.util.ArrayList;
import java.util.List;

import DAO.ProdutoJDBC;
import exceptions.CarrinhoException;
import exceptions.ProdutoException;

public class Carrinho 
{
    private int id;
    private String cliente;
    private double valorTotal;
    private List<Produto> produtos;

    public Carrinho(String cliente) 
    {
        this.cliente = cliente;
        this.valorTotal = 0;
        this.produtos = new ArrayList<>();
    }

    public void inserirProduto(Produto p, int quant) throws CarrinhoException, ProdutoException
    {
        if (quant <= 0) 
        {
            throw new CarrinhoException("A quantidade deve ser maior que zero.");
        }

        if (p.getQuant() < quant) 
        {
            throw new ProdutoException("Quantidade solicitada excede o estoque disponível para o produto: " + p.getProduto());
        }

        for (Produto produto : this.produtos) 
        {
            if (produto.getID() == p.getID()) 
            {
                produto.diminuirQuant(quant);
                double subtotal = p.getValor() * quant;
                this.valorTotal += subtotal;
                return;
            }
        }

        p.diminuirQuant(quant);
        double subtotal = p.getValor() * quant;
        this.valorTotal += subtotal;
        this.produtos.add(p);
    }

    public void removerProduto(Produto p, int quant) throws CarrinhoException, ProdutoException
    {
        if (quant <= 0) 
        {
            throw new CarrinhoException("A quantidade deve ser maior que zero.");
        }

        if (!this.produtos.contains(p)) 
        {
            throw new CarrinhoException("Produto não está no carrinho.");
        }

        if (quant > p.getQuant()) 
        {
            throw new CarrinhoException("Quantidade a ser removida é maior que a disponível no carrinho.");
        }

        p.aumentarQuant(quant);
        this.valorTotal -= p.getValor() * quant;

        if (p.getQuant() == 0) 
        {
            this.produtos.remove(p);
        }
    }

    public void encerrarCompra(ProdutoJDBC produtoDao) throws CarrinhoException
    {
        if (this.valorTotal == 0) 
        {
            throw new CarrinhoException("O carrinho está vazio. Não é possível finalizar a compra.");
        }

        for (Produto p : this.produtos) 
        {
            p.diminuirQuant(p.getQuant());
            produtoDao.atualizarQuantBanco(p);
        }
    }

    public String listarProdutos() 
    {
        StringBuilder listagem = new StringBuilder();
        for (Produto p : this.produtos) 
        {
            double valorTotalProduto = p.getValor() * p.getQuant();
            listagem.append(String.format("ID: %d | Nome: %s | Valor Unitário: R$%.2f | Valor Total: R$%.2f\n",
                    p.getID(), p.getProduto(), p.getValor(), valorTotalProduto));
        }
        listagem.append(String.format("Valor total do carrinho: R$%.2f", this.valorTotal));
        return listagem.toString();
    }

    public int getID() 
    {
        return id;
    }

    public String getCliente() 
    {
        return cliente;
    }

    public double getValorTotal() 
    {
        return valorTotal;
    }
}