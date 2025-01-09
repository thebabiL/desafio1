// Métodos de Banco de Dados
public static void criarProduto(Connection conn, produto produto) throws SQLException
{
    String sql = "INSERT INTO produtos (nome, descricao, categoria, valor, quantidade) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
    {
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setString(3, produto.getCategoria());
        stmt.setDouble(4, produto.getValor());
        stmt.setInt(5, produto.getQuantidade());
        stmt.executeUpdate();

        try (ResultSet generatedKeys = stmt.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                produto.id = generatedKeys.getInt(1);
            }
        }
    }
}

public static List<produto> listarProdutos(Connection conn) throws SQLException
{
    String sql = "SELECT * FROM produtos";
    List<produto> produtos = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery())
    {
        while (rs.next())
        {
            produtos.add(new produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("categoria"),
                    rs.getDouble("valor"),
                    rs.getInt("quantidade")
            ));
        }
    }
    return produtos;
}