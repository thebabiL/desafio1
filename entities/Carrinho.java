package Desafio1.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Carrinho  implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String cliente;
    private List<Produto> produtos;
    private double valorTotal;

    public Carrinho() 
    {
        this.produtos = new ArrayList<>();
    }

    public Carrinho(int id, String cliente) 
    {
        this.id = id;
        this.cliente = cliente;
        this.produtos = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getCliente() 
    {
        return cliente;
    }

    public void setCliente(String cliente) 
    {
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() 
    {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) 
    {
        this.produtos = produtos;
        recalcularValorTotal();
    }

    public double getValorTotal() 
    {
        return valorTotal;
    }

    public void adicionarProduto(Produto produto, int quantidade) 
    {
        produto.setQuantidade(quantidade);
        produtos.add(produto);
        recalcularValorTotal();
    }

    public void removerProduto(int idProduto) 
    {
        produtos.removeIf(p -> p.getId() == idProduto);
        recalcularValorTotal();
    }

    private void recalcularValorTotal() 
    {
        valorTotal = produtos.stream()
                .mapToDouble(p -> p.getValor() * p.getQuantidade())
                .sum();
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrinho carrinho = (Carrinho) o;
        return id == carrinho.id &&
                Double.compare(carrinho.valorTotal, valorTotal) == 0 &&
                Objects.equals(cliente, carrinho.cliente) &&
                Objects.equals(produtos, carrinho.produtos);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(id, cliente, produtos, valorTotal);
    }

    @Override
    public String toString() 
    {
        StringBuilder produtosStr = new StringBuilder();
        for (Produto produto : produtos) 
        {
            produtosStr.append(String.format(
                    "Produto: %s | Quantidade: %d | Valor Unitário: %.2f | Subtotal: %.2f\n",
                    produto.getNome(), produto.getQuantidade(), produto.getValor(),
                    produto.getValor() * produto.getQuantidade()
            ));
        }

        return "Carrinho{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", produtos=\n" + produtosStr +
                ", valorTotal=" + String.format("%.2f", valorTotal) +
                '}';
    }
}
