
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
     
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
        
          return new ArrayList<>();
    }   
        
}

