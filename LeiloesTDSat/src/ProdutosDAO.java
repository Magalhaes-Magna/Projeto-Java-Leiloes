
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
     ArrayList<ProdutosDTO> listagem = new ArrayList<>();
     
        //Cadastra um novo produto no banco de dados.
        public boolean cadastrarProduto(ProdutosDTO produto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            // 1. Obter a conexão do conectaDAO
            conn = conectaDAO.conectar(); // Chama o método estático da sua classe conectaDAO

            // 2. Preparar a instrução SQL
            stmt = conn.prepareStatement(sql);

            // 3. Definir os valores para os placeholders (?)
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor()); // Seu DTO usa Integer, o banco usa INT. Está OK.
            stmt.setString(3, produto.getStatus()); // Ex: "A Venda"

            // 4. Executar a inserção
            int rowsAffected = stmt.executeUpdate();

            // 5. Verificar se a inserção foi bem-sucedida (1 linha afetada)
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto no banco de dados: " + e.getMessage());
            // e.printStackTrace(); // Descomente para ver o stack trace completo durante o desenvolvimento
            return false; // Retorna false em caso de erro
        } finally {
            // 6. Fechar PreparedStatement e Connection no bloco finally
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }
            // Usar o método desconectar da sua classe conectaDAO
            conectaDAO.desconectar(conn);
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos(){
        
          ArrayList<ProdutosDTO> listaDeProdutos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT id, nome, valor, status FROM produtos"; // Seleciona todas as colunas

        try {
            // 1. Obter a conexão
            conn = conectaDAO.conectar();

            // 2. Preparar a instrução SQL
            stmt = conn.prepareStatement(sql);

            // 3. Executar a consulta
            rs = stmt.executeQuery();

            // 4. Iterar sobre os resultados
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor")); // No seu DTO está Integer, no banco é INT
                produto.setStatus(rs.getString("status"));

                listaDeProdutos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos do banco de dados: " + e.getMessage());
            // e.printStackTrace(); // Descomente para depuração se necessário
            // Em caso de erro, uma lista vazia será retornada, o que é um comportamento aceitável
            // para a interface gráfica não quebrar, mas o erro será logado no console.
        } finally {
            // 5. Fechar ResultSet, PreparedStatement e Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet ou PreparedStatement: " + e.getMessage());
            }
            conectaDAO.desconectar(conn);
        }
        return listaDeProdutos;
    }
    
    /**
     * Atualiza o status de um produto para "Vendido" no banco de dados.
     * @param id O ID do produto a ser vendido.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean venderProduto(int id) {
        boolean sucesso = false;
        String sql = "UPDATE produtos SET status = ? WHERE id = ?"; // Assumindo que a tabela se chama 'produtos' e a coluna de status é 'status'

        try {
            conn = conectaDAO.conectar(); // Obtém a conexão do conectaDAO [cite: 42]
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido"); // Define o novo status
            prep.setInt(2, id);           // Define o ID do produto a ser atualizado

            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto com ID " + id + " atualizado para Vendido.");
                sucesso = true;
            } else {
                System.out.println("Nenhum produto encontrado com o ID " + id + " ou o status já era Vendido.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao vender produto: " + e.getMessage());
            e.printStackTrace(); // Importante para depuração
            JOptionPane.showMessageDialog(null, "Erro ao vender o produto: " + e.getMessage());
        } finally {
            // Bloco finally para garantir que os recursos sejam fechados
            if (prep != null) {
                try {
                    prep.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }
            conectaDAO.desconectar(conn); // Fecha a conexão utilizando o método da sua classe conectaDAO [cite: 52]
        }
        return sucesso;
    }
  
  /**
     * Lista todos os produtos com o status "Vendido" do banco de dados.
     * @return ArrayList<ProdutosDTO> contendo os produtos vendidos.
     */
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = ?"; // Assumindo que a tabela se chama 'produtos' e a coluna de status é 'status'

        try {
            conn = conectaDAO.conectar(); // Obtém a conexão do conectaDAO
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido"); // Define o status a ser buscado

            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                produtosVendidos.add(produto);
            }
            System.out.println(produtosVendidos.size() + " produtos vendidos encontrados.");

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos vendidos: " + e.getMessage());
            e.printStackTrace(); // Importante para depuração
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        } finally {
            // Bloco finally para garantir que os recursos sejam fechados
            if (resultset != null) {
                try {
                    resultset.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }
            if (prep != null) {
                try {
                    prep.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }
            conectaDAO.desconectar(conn); // Fecha a conexão
        }
        return produtosVendidos;
    }  
}

