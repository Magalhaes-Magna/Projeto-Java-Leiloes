import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VendasDialog extends javax.swing.JDialog {
    
    private ProdutosDAO produtosDAO;
    private DefaultTableModel modelTabela; 
    private JTable tabelaVendas;

    public VendasDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        // Inicialize o DAO e o Model da tabela ANTES de initComponents ou da configuração da UI
        produtosDAO = new ProdutosDAO();
        modelTabela = new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nome", "Valor", "Status"}
        );
        
        initComponents();
        
        
        // 2. Configure a UI personalizada DEPOIS de initComponents
        configurarInterfacePersonalizada();

        // 3. Liste os produtos vendidos
        listarProdutosVendidosNaTela();

        // 4. Configurações finais do diálogo
        setTitle("Produtos Vendidos");
        // setSize(500, 400); // Opcional, pack() pode ser suficiente
        // pack(); // Chamado novamente para garantir o tamanho correto após adicionar componentes
        setLocationRelativeTo(parent); // Centraliza em relação à janela pai
    }

    /**
     * Configura e adiciona os componentes personalizados da UI ao diálogo.
     * Este método é chamado após o initComponents() gerado pelo NetBeans.
     */
    private void configurarInterfacePersonalizada() {
        // O contentPane já foi configurado com GroupLayout pelo initComponents.
        // Para simplificar, vamos remover tudo e usar BorderLayout.
        Container contentPane = getContentPane();
        contentPane.removeAll(); // Limpa o que o GroupLayout possa ter deixado (geralmente nada visível)
        contentPane.setLayout(new BorderLayout(5, 5)); // Define BorderLayout com espaçamento

        // Título
        JLabel lblTitulo = new JLabel("Lista de Produtos Vendidos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Lucida Fax", Font.BOLD, 18));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        // Tabela
        tabelaVendas = new JTable(modelTabela); // Usa o modelTabela da classe
        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Botão Fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose()); // Fecha o diálogo

        // Painel para o botão, para alinhá-lo à direita
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnFechar);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // Revalida o container e chama pack() para ajustar o tamanho
        // contentPane.revalidate(); // Geralmente não necessário se pack() for chamado depois
        // contentPane.repaint();
        pack(); // Importante chamar pack() AQUI para que o JDialog se ajuste aos novos componentes
    }

    /**
     * Busca os produtos vendidos no DAO e os exibe na tabela.
     */
    private void listarProdutosVendidosNaTela() {
        // Limpa quaisquer dados antigos da tabela
        if (modelTabela == null) {
             System.err.println("modelTabela não inicializado em listarProdutosVendidosNaTela!");
             return;
        }
        modelTabela.setNumRows(0);

        if (produtosDAO == null) {
            System.err.println("produtosDAO não inicializado em listarProdutosVendidosNaTela!");
            return;
        }
        ArrayList<ProdutosDTO> listaVendidos = produtosDAO.listarProdutosVendidos();

        if (listaVendidos.isEmpty()) {
            System.out.println("Nenhum produto vendido para exibir na tela de Vendas.");
            // Opcional: Adicionar uma linha na tabela indicando que não há dados
            // modelTabela.addRow(new Object[]{"", "Nenhum produto vendido", "", ""});
        } else {
            for (ProdutosDTO produto : listaVendidos) {
                modelTabela.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getValor(),
                    produto.getStatus()
                });
            }
        }
    }

     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VendasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VendasDialog dialog = new VendasDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
