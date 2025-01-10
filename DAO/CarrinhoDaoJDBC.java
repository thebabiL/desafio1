package Desafio1.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Desafio1.entities.Carrinho;
import Desafio1.entities.Produto;
import Desafio1.exceptions.AtualizacaoException;
import Desafio1.exceptions.ExclusaoException;
import Desafio1.exceptions.InsercaoException;

public class CarrinhoDaoJDBC implements CarrinhoDAO 
{
    
    private Connection conn;

    public CarrinhoDaoJDBC(Connection conn) 
    {
        this.conn = conn;
    }

    @Override
    public void inserir(Carrinho carrinho) 
    {
        String sql = "INSERT INTO Carrinho (cliente) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            stmt.setString(1, carrinho.getCliente());
            
            int linhas = stmt.executeUpdate();
            if (linhas > 0) 
            {
                try (ResultSet rs = stmt.getGeneratedKeys()) 
                {
                    if (rs.next()) 
                    {
                        int idCarrinho = rs.getInt(1);
                        carrinho.setId(idCarrinho); 
                    }
                }
                for (Produto produto : carrinho.getProdutos()) {
                    String sqlProduto = "INSERT INTO Carrinho_Produto (idCarrinho, idProduto, quantidade) VALUES (?, ?, ?)";
                    try (PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto)) {
                        stmtProduto.setInt(1, carrinho.getId());
                        stmtProduto.setInt(2, produto.getId());
                        stmtProduto.setInt(3, produto.getQuantidade());
                        stmtProduto.executeUpdate();
                    }
                }
            }
        } 
        catch (SQLException e) 
        {
            throw new InsercaoException("Erro ao inserir carrinho: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Carrinho carrinho) 
    {
        String sql = "UPDATE Carrinho SET cliente = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, carrinho.getCliente());
            stmt.setInt(2, carrinho.getId());
            stmt.executeUpdate();
            
            String deleteProdutosSql = "DELETE FROM Carrinho_Produto WHERE idCarrinho = ?";
            try (PreparedStatement stmtDelete = conn.prepareStatement(deleteProdutosSql)) 
            {
                stmtDelete.setInt(1, carrinho.getId());
                stmtDelete.executeUpdate();
            }

            for (Produto produto : carrinho.getProdutos()) 
            {
                String sqlProduto = "INSERT INTO Carrinho_Produto (idCarrinho, idProduto, quantidade) VALUES (?, ?, ?)";
                try (PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto))
                {
                    stmtProduto.setInt(1, carrinho.getId());
                    stmtProduto.setInt(2, produto.getId());
                    stmtProduto.setInt(3, produto.getQuantidade());
                    stmtProduto.executeUpdate();
                }
            }
        } 
        catch (SQLException e) 
        {
            throw new AtualizacaoException("Erro ao atualizar carrinho: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletarPorId(int id) 
    {
        String sql = "DELETE FROM Carrinho WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            String deleteProdutosSql = "DELETE FROM Carrinho_Produto WHERE idCarrinho = ?";
            try (PreparedStatement stmtDelete = conn.prepareStatement(deleteProdutosSql)) 
            {
                stmtDelete.setInt(1, id);
                stmtDelete.executeUpdate();
            }
        } 
        catch (SQLException e) 
        {
            throw new ExclusaoException("Erro ao deletar carrinho: " + e.getMessage(), e);
        }
    }

    @Override
    public Carrinho buscarPorId(int id) {
        String sql = "SELECT * FROM Carrinho WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Carrinho carrinho = new Carrinho();
                carrinho.setId(rs.getInt("id"));
                carrinho.setCliente(rs.getString("cliente"));

                // Buscar produtos associados ao carrinho
                String sqlProdutos = "SELECT * FROM Carrinho_Produto cp JOIN Produto p ON cp.idProduto = p.id WHERE cp.idCarrinho = ?";
                try (PreparedStatement stmtProdutos = conn.prepareStatement(sqlProdutos)) {
                    stmtProdutos.setInt(1, id);
                    ResultSet rsProdutos = stmtProdutos.executeQuery();
                    List<Produto> produtos = new ArrayList<>();
                    while (rsProdutos.next()) {
                        Produto produto = new Produto();
                        produto.setId(rsProdutos.getInt("idProduto"));
                        produto.setNome(rsProdutos.getString("nome"));
                        produto.setDescricao(rsProdutos.getString("descricao"));
                        produto.setCategoria(rsProdutos.getString("categoria"));
                        produto.setValor(rsProdutos.getDouble("valor"));
                        produto.setQuantidade(rsProdutos.getInt("quantidade"));
                        produtos.add(produto);
                    }
                    carrinho.setProdutos(produtos);
                }
                return carrinho;
            }
            return null;
        } catch (SQLException e) {
            throw new ExcecaoConsulta("Erro ao buscar carrinho: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Carrinho> buscarTudo() {
        String sql = "SELECT * FROM Carrinho";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Carrinho> carrinhos = new ArrayList<>();
            while (rs.next()) {
                Carrinho carrinho = new Carrinho();
                carrinho.setId(rs.getInt("id"));
                carrinho.setCliente(rs.getString("cliente"));

                // Buscar produtos associados ao carrinho
                String sqlProdutos = "SELECT * FROM Carrinho_Produto cp JOIN Produto p ON cp.idProduto = p.id WHERE cp.idCarrinho = ?";
                try (PreparedStatement stmtProdutos = conn.prepareStatement(sqlProdutos)) {
                    stmtProdutos.setInt(1, carrinho.getId());
                    ResultSet rsProdutos = stmtProdutos.executeQuery();
                    List<Produto> produtos = new ArrayList<>();
                    while (rsProdutos.next()) {
                        Produto produto = new Produto();
                        produto.setId(rsProdutos.getInt("idProduto"));
                        produto.setNome(rsProdutos.getString("nome"));
                        produto.setDescricao(rsProdutos.getString("descricao"));
                        produto.setCategoria(rsProdutos.getString("categoria"));
                        produto.setValor(rsProdutos.getDouble("valor"));
                        produto.setQuantidade(rsProdutos.getInt("quantidade"));
                        produtos.add(produto);
                    }
                    carrinho.setProdutos(produtos);
                }
                carrinhos.add(carrinho);
            }
            return carrinhos;
        } catch (SQLException e) {
            throw new ExcecaoConsulta("Erro ao buscar todos os carrinhos: " + e.getMessage(), e);
        }
    }
}