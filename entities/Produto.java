package entities;

import exceptions.ProdutoException;

public class Produto 
{
    private int id;
    private String produto;
    private String descricao;
    private String categoria;
    private double valor;
    private int quantidade;

    public Produto(String produto, String descricao, String categoria, double valor, int quantidade) 
    {
        this.produto = produto;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public Produto(int id, String produto, String descricao, String categoria, double valor, int quantidade) 
    {
        this.id = id;
        this.produto = produto;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public void diminuirQuant(int quant) throws ProdutoException 
    {
        if (quant > this.quantidade) 
        {
            throw new ProdutoException("Quantidade solicitada excede o estoque disponível para o produto: " + this.produto);
        }
        this.quantidade -= quant;
    }

    public void aumentarQuant(int quant) 
    {
        this.quantidade += quant;
    }

    public int getID() 
    {
        return id;
    }

    public String getProduto() 
    {
        return produto;
    }

    public String getDescricao() 
    {
        return descricao;
    }

    public String getCategoria() 
    {
        return categoria;
    }

    public double getValor() 
    {
        return valor;
    }

    public int getQuant() 
    {
        return quantidade;
    }
}