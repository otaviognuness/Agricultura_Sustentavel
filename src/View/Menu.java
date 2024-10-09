package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.ControlarDados;
import Model.DadosSafra;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Menu {
    public static void main(String[] args) {
        iniciarMenu(args);
    }
    private static void iniciarMenu(String[] args) {
        try {
            ControlarDados controlarDados = new ControlarDados();

            JFrame frame = new JFrame("Menu Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLayout(new BorderLayout());

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nome");
            model.addColumn("Data de Plantio");
            model.addColumn("Data de Colheita");
            model.addColumn("Variedade Cultivada");
            model.addColumn("Área Plantada (m²)");
            model.addColumn("Produtividade (kg)");
            model.addColumn("Condição Climática");
            model.addColumn("ID Agroquímicos");
            model.addColumn("Fertilizantes");
            model.addColumn("Pesticida");

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            List<DadosSafra> safraList = controlarDados.listarPlantacoes();
            for (DadosSafra safra : safraList) {
                model.addRow(new Object[]{
                        safra.getId(),
                        safra.getNome(),
                        safra.getDataPlantio(),
                        safra.getDataColheita(),
                        safra.getVariedaCultivada(),
                        safra.getAreaPlantada(),
                        safra.getProdutividade(),
                        safra.getCondicaoClimatica(),
                        safra.getIdAgroquimicos(),
                        safra.getFertilizantes(),
                        safra.getPesticida()
                });
            }

            boolean continuar = true;
            while (continuar) {
                try {
                    JPanel panel = new JPanel(new GridBagLayout());
                    panel.setPreferredSize(new Dimension(700, 490));
                    panel.setBackground(Color.WHITE); 

                    GridBagConstraints gbc = new GridBagConstraints();

                    JButton helpButton = new JButton("?");
                    helpButton.setToolTipText("Clique para mais informações");
                    helpButton.setPreferredSize(new Dimension(40, 40));
                    helpButton.setBackground(new Color(3, 112, 23));
                    helpButton.setForeground(Color.WHITE);
                    helpButton.setFont(new Font("Arial", Font.BOLD, 20));
                    helpButton.setFocusPainted(false);
                    helpButton.setBorder(BorderFactory.createEmptyBorder());
                    helpButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JOptionPane.showMessageDialog(frame, "Escolha uma opção do menu:\n1. Cadastrar - Adicionar nova safra\n2. Remover/Atualizar - Remover ou atualizar safra existente\n3. Buscar por ID - Buscar detalhes de uma safra específica\n4. Buscar Instruções - Ver instruções sobre pesticidas, fertilizantes e condições climáticas\n5. Relatório - Gerar relatório das safras\n6. Exibir Gráfico - Exibe gráfico de produtividade e área plantada (m²)\n7. Comparar Produtividade - Compara a produtividade entre duas safras\n8. Sair - Fechar o programa");
                        }
                    });

                    panel.add(helpButton, gbc);

                    JLabel label = new JLabel("<html>" +
                            "<div style='padding: 20px; text-align: center;'>" +
                            "<h2 style='text-align: center; margin-bottom: 15px; font-size: 18px;'><font color='#4CAF50'><b>Menu</b></font></h2>" +
                            "<hr style='border-color: #4CAF50;'>" +
                            "<ul style='list-style-type: none; padding-left: 0; margin-left: 50; font-size: 10px; text-align: left;'>" +
                            "<li style='margin-bottom: 10px;'><font color='#4CAF50'><b>1.</b></font> Cadastrar</li>" +
                            "<li style='margin-bottom: 10px;'><font color='#4CAF50'><b>2.</b></font> Remover/Atualizar</li>" +
                            "<li style='margin-bottom: 10px;'><font color='#4CAF50'><b>3.</b></font> Buscar por ID</li>" +
                            "<li style='margin-bottom: 10px;'><font color='#4CAF50'><b>4.</b></font> Buscar Instruções</li>" +
                            "<li style='margin-bottom: 10px;'><font color='#4CAF50'><b>5.</b></font> Relatório</li>" +
                            "<li style='margin-bottom: 10px;'><font color='#4CAF50'><b>6.</b></font> Exibir Gráfico</li>" +
                            "<li style='margin-bottom: 10px; white-space: nowrap;'><font color='#4CAF50'><b>7.</b></font> Comparar Produtividade</li>" +
                            "<li style='margin-bottom: 10px;'><font color='#4CAF50'><b>8.</b></font> Sair</li>" +
                            "</ul>" +
                            "<p style='font-weight: bold; text-align: center; margin-top: 5px; color: #333;'>Escolha uma Opção:</p>" +
                            "</div>");

                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.anchor = GridBagConstraints.CENTER;
                    panel.add(label, gbc);

                    JTextField textField = new JTextField();
                    textField.setMaximumSize(new Dimension(400, 30));
                    textField.setPreferredSize(new Dimension(400, 30));

                    gbc.gridy = 2;
                    panel.add(textField, gbc);

                    JPanel centralPanel = new JPanel(new GridBagLayout());
                    centralPanel.setBackground(Color.WHITE);
                    centralPanel.add(panel);

                    int result = JOptionPane.showConfirmDialog(null, centralPanel, "Menu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    int escolha;
                    if (result == JOptionPane.OK_OPTION) {
                        escolha = Integer.parseInt(textField.getText());
                    } else {
                        escolha = 8;

                    }

                    switch (escolha) {
                        case 1:
                            try {
                                DadosSafra safra = new DadosSafra();

                                safra.setNome(JOptionPane.showInputDialog(frame, "Nome da Safra: "));
                                safra.setDataPlantio(JOptionPane.showInputDialog(frame, "Data de Plantio (YYYY-MM-DD): "));
                                safra.setDataColheita(JOptionPane.showInputDialog(frame, "Data de Colheita (YYYY-MM-DD): "));
                                safra.setVariedaCultivada(JOptionPane.showInputDialog(frame, "Variedade Cultivada: "));
                                safra.setAreaPlantada(Double.parseDouble(JOptionPane.showInputDialog(frame, "Área Plantada (m²): ")));
                                safra.setProdutividade(Double.parseDouble(JOptionPane.showInputDialog(frame, "Produtividade (kg): ")));
                                safra.setCondicaoClimatica(JOptionPane.showInputDialog(frame, "Condição Climática: "));
                                safra.setFertilizantes(JOptionPane.showInputDialog(frame, "Fertilizantes: "));
                                safra.setPesticida(JOptionPane.showInputDialog(frame, "Pesticida: "));

                                controlarDados.adicionarDados(safra);
                                model.addRow(new Object[]{
                                        safra.getId(),
                                        safra.getNome(),
                                        safra.getDataPlantio(),
                                        safra.getDataColheita(),
                                        safra.getVariedaCultivada(),
                                        safra.getAreaPlantada(),
                                        safra.getProdutividade(),
                                        safra.getCondicaoClimatica(),
                                        safra.getIdAgroquimicos(),
                                        safra.getFertilizantes(),
                                        safra.getPesticida()
                                });
                                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao cadastrar safra");
                            }
                            break;

                        case 2:
                            try {
                                int idRemoverAtualizar = Integer.parseInt(JOptionPane.showInputDialog(frame, "Informe o ID da safra: "));
                                boolean idExistente = false;
                                for (DadosSafra safraExistente : safraList) {
                                    if (safraExistente.getId() == idRemoverAtualizar) {
                                        idExistente = true;
                                        break;
                                    }
                                }
                                if (!idExistente) {
                                    JOptionPane.showMessageDialog(frame, "Safra com o ID informado não encontrada.");
                                    break;
                                }

                                Object[] options = {"Remover", "Atualizar"};
                                int opcao = JOptionPane.showOptionDialog(frame,
                                        "O que deseja fazer?",
                                        "Opções",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        options,
                                        options[1]);

                                if (opcao == JOptionPane.YES_OPTION) {
                                    controlarDados.removerDados(idRemoverAtualizar);
                                    for (int i = 0; i < model.getRowCount(); i++) {
                                        if (model.getValueAt(i, 0).equals(idRemoverAtualizar)) {
                                            model.removeRow(i);
                                            break;
                                        }
                                    }
                                    JOptionPane.showMessageDialog(frame, "Remoção realizada com sucesso!");
                                } else if (opcao == JOptionPane.NO_OPTION) {
                                    Object[] updateOptions = {"Toda a Safra", "Apenas Pesticida e Fertilizante"};
                                    int updateChoice = JOptionPane.showOptionDialog(frame,
                                            "O que deseja atualizar?",
                                            "Opções de Atualização",
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            updateOptions,
                                            updateOptions[0]);

                                    if (updateChoice == JOptionPane.YES_OPTION) {
                                        DadosSafra safraAtualizada = new DadosSafra();

                                        safraAtualizada.setNome(JOptionPane.showInputDialog(frame, "Novo Nome: "));
                                        safraAtualizada.setDataPlantio(JOptionPane.showInputDialog(frame, "Nova Data de Plantio (YYYY-MM-DD): "));
                                        safraAtualizada.setDataColheita(JOptionPane.showInputDialog(frame, "Nova Data de Colheita (YYYY-MM-DD): "));
                                        safraAtualizada.setVariedaCultivada(JOptionPane.showInputDialog(frame, "Nova Variedade Cultivada: "));
                                        safraAtualizada.setAreaPlantada(Double.parseDouble(JOptionPane.showInputDialog(frame, "Nova Área Plantada (m²): ")));
                                        safraAtualizada.setProdutividade(Double.parseDouble(JOptionPane.showInputDialog(frame, "Nova Produtividade (kg): ")));
                                        safraAtualizada.setCondicaoClimatica(JOptionPane.showInputDialog(frame, "Nova Condição Climática: "));
                                        safraAtualizada.setFertilizantes(JOptionPane.showInputDialog(frame, "Novos Fertilizantes: "));
                                        safraAtualizada.setPesticida(JOptionPane.showInputDialog(frame, "Novo Pesticida: "));

                                        controlarDados.atualizarDados(idRemoverAtualizar, safraAtualizada);

                                        for (int i = 0; i < model.getRowCount(); i++) {
                                            if (model.getValueAt(i, 0).equals(idRemoverAtualizar)) {
                                                model.setValueAt(safraAtualizada.getNome(), i, 1);
                                                model.setValueAt(safraAtualizada.getDataPlantio(), i, 2);
                                                model.setValueAt(safraAtualizada.getDataColheita(), i, 3);
                                                model.setValueAt(safraAtualizada.getVariedaCultivada(), i, 4);
                                                model.setValueAt(safraAtualizada.getAreaPlantada(), i, 5);
                                                model.setValueAt(safraAtualizada.getProdutividade(), i, 6);
                                                model.setValueAt(safraAtualizada.getCondicaoClimatica(), i, 7);
                                                model.setValueAt(safraAtualizada.getFertilizantes(), i, 9);
                                                model.setValueAt(safraAtualizada.getPesticida(), i, 10);
                                                break;
                                            }
                                        }
                                        JOptionPane.showMessageDialog(frame, "Safra atualizada com sucesso!");
                                    } else if (updateChoice == JOptionPane.NO_OPTION) {
                                        String novosFertilizantes = JOptionPane.showInputDialog(frame, "Novos Fertilizantes: ");
                                        String novoPesticida = JOptionPane.showInputDialog(frame, "Novo Pesticida: ");

                                        controlarDados.atualizarPesticidaEFertilizante(idRemoverAtualizar, novosFertilizantes, novoPesticida);

                                        for (int i = 0; i < model.getRowCount(); i++) {
                                            if (model.getValueAt(i, 0).equals(idRemoverAtualizar)) {
                                                model.setValueAt(novosFertilizantes, i, 9);
                                                model.setValueAt(novoPesticida, i, 10);
                                                break;
                                            }
                                        }
                                        JOptionPane.showMessageDialog(frame, "Fertilizantes e Pesticida atualizados com sucesso!");
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "Opção inválida.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Opção inválida.");
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao remover/atualizar safra");
                            }
                            break;

                        case 3:
                            try {
                                int idBuscar = Integer.parseInt(JOptionPane.showInputDialog(frame, "Informe o ID da safra a ser buscada: "));
                                DadosSafra safraEncontrada = controlarDados.listarDadosSafraPorID(idBuscar);
                                if (safraEncontrada != null) {
                                    StringBuilder detalhes = new StringBuilder();
                                    detalhes.append("<html><head><style>");
                                    detalhes.append("h2 { color: #008000; font-family: Arial, sans-serif; }");
                                    detalhes.append("p { font-family: Arial, sans-serif; margin-bottom: 5px; }");
                                    detalhes.append("</style></head><body>");
                                    detalhes.append("<h2>Detalhes da Safra:</h2>");
                                    detalhes.append("<p><b>ID:</b><br> ").append(safraEncontrada.getId()).append("</p>");
                                    detalhes.append("<p><b>Nome:</b><br> ").append(safraEncontrada.getNome()).append("</p>");
                                    detalhes.append("<p><b>Data de Plantio:</b><br> ").append(safraEncontrada.getDataPlantio()).append("</p>");
                                    detalhes.append("<p><b>Data de Colheita:</b><br> ").append(safraEncontrada.getDataColheita()).append("</p>");
                                    detalhes.append("<p><b>Variedade Cultivada:</b><br> ").append(safraEncontrada.getVariedaCultivada()).append("</p>");
                                    detalhes.append("<p><b>Área Plantada (m²):</b><br> ").append(safraEncontrada.getAreaPlantada()).append("</p>");
                                    detalhes.append("<p><b>Produtividade (kg):</b><br> ").append(safraEncontrada.getProdutividade()).append("</p>");
                                    detalhes.append("<p><b>Condição Climática:</b><br> ").append(safraEncontrada.getCondicaoClimatica()).append("</p>");
                                    detalhes.append("<p><b>Fertilizantes:</b><br> ").append(safraEncontrada.getFertilizantes()).append("</p>");
                                    detalhes.append("<p><b>Pesticida:</b><br> ").append(safraEncontrada.getPesticida()).append("</p>");
                                    detalhes.append("</body></html>");

                                    JOptionPane.showMessageDialog(frame, detalhes.toString());
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Safra não encontrada para o ID informado.");
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao buscar safra");
                            }
                            break;


                        case 4:
                            try {
                                int escolhaInstrucoes;
                                String[] instrucoes = new String[0];
                                do {
                                    escolhaInstrucoes = Integer.parseInt(JOptionPane.showInputDialog(frame,
                                            "<html>" +
                                                    "<div style='background-color: #f5f5f5; border: 2px solid #4CAF50; border-radius: 8px; padding: 50px;'>" +
                                                    "<h2 style='text-align: center; margin-bottom: 20px;'><font color='#4CAF50'><b>Menu de Instruções</b></font></h2>" +
                                                    "<hr style='border-color: #4CAF50;'>" +
                                                    "<ul style='list-style-type: none; padding-left: 0; margin-left: 0;'>" +
                                                    "<li style='margin-bottom: 15px;'><font color='#4CAF50'><b>1.</b></font>  Instruções Sobre Pesticidas</li>" +
                                                    "<li style='margin-bottom: 15px;'><font color='#4CAF50'><b>2.</b></font> Instruções Sobre Fertilizantes</li>" +
                                                    "<li style='margin-bottom: 15px;'><font color='#4CAF50'><b>3.</b></font> Instruções Sobre Condições Climáticas</li>" +
                                                    "<li style='margin-bottom: 15px;'><font color='#4CAF50'><b>4. </b></font> Voltar ao Menu Principal</li>" +
                                                    "</ul>" +
                                                    "<p style='font-weight: bold; text-align: center; margin-top: 20px; color: #333;'>Escolha uma opção:</p>" +
                                                    "<input type='number' style='width: 100%; height: 40px; padding: 10px; margin-top: 20px; border: 1px solid #4CAF50; border-radius: 5px;'>" +
                                                    "</div>"));

                                    switch (escolhaInstrucoes) {
                                        case 1:
                                            instrucoes = new String[]{
                                                    "Os pesticidas são substâncias utilizadas para controlar pragas em plantações.",
                                                    "O uso de pesticidas pode aumentar a produtividade agrícola ao proteger as plantas de insetos e doenças.",
                                                    "É importante seguir as instruções de uso dos pesticidas para garantir a segurança dos trabalhadores e consumidores.",
                                                    "Os pesticidas podem ser classificados em herbicidas, inseticidas, fungicidas e rodenticidas.",
                                                    "A exposição excessiva a pesticidas pode causar problemas de saúde em humanos e animais.",
                                                    "O manejo integrado de pragas busca reduzir o uso de pesticidas através de práticas agrícolas sustentáveis.",
                                                    "Alguns pesticidas persistem no ambiente, contaminando o solo e a água por longos períodos.",
                                                    "Os pesticidas orgânicos são uma alternativa aos pesticidas sintéticos, sendo menos tóxicos para o meio ambiente.",
                                                    "É fundamental monitorar os resíduos de pesticidas nos alimentos para garantir a segurança alimentar.",
                                                    "O desenvolvimento de resistência aos pesticidas por parte das pragas é um desafio contínuo para a agricultura moderna.",
                                            };
                                            break;
                                        case 2:
                                            instrucoes = new String[]{
                                                    "Os fertilizantes fornecem nutrientes essenciais para o crescimento saudável das plantas.",
                                                    "A aplicação adequada de fertilizantes pode aumentar significativamente a produtividade agrícola.",
                                                    "Fertilizantes orgânicos melhoram a estrutura do solo e aumentam a retenção de água.",
                                                    "Os fertilizantes químicos são frequentemente usados para fornecer nutrientes rapidamente às plantas.",
                                                    "A escolha do fertilizante certo depende das necessidades específicas da cultura e do solo.",
                                                    "O uso excessivo de fertilizantes pode levar à poluição do solo e da água.",
                                                    "A rotação de culturas e o uso de fertilizantes naturais podem ajudar a manter a fertilidade do solo.",
                                                    "Fertilizantes de liberação controlada fornecem nutrientes às plantas ao longo do tempo.",
                                                    "A análise do solo é fundamental para determinar a quantidade adequada de fertilizante necessária.",
                                                    "Fertilizantes balanceados contêm uma combinação de nitrogênio, fósforo e potássio, essenciais para o crescimento das plantas.",
                                            };
                                            break;
                                        case 3:
                                            instrucoes = new String[]{
                                                    "Escolher variedades de plantas adaptadas ao clima local aumenta as chances de sucesso da colheita e reduz a necessidade de intervenções químicas.",
                                                    "Os agricultores devem acompanhar de perto as previsões de chuva para planejar a irrigação e evitar períodos de seca que podem prejudicar as plantações.",
                                                    "Em regiões com climas variados, a rotação de culturas ajuda a manter a fertilidade do solo e a reduzir a incidência de pragas e doenças.",
                                                    "O uso de drones e imagens de satélite pode ajudar a monitorar a saúde das culturas e detectar problemas relacionados ao clima.",
                                                    "Técnicas como plantio direto e cobertura vegetal ajudam a proteger o solo contra a erosão causada por chuvas intensas.",
                                                    "Construir reservatórios de água pode ser vital para enfrentar períodos de seca prolongada, garantindo o suprimento de água para as plantações.",
                                                    "Mudanças climáticas podem aumentar a incidência de pragas e doenças; monitorar o clima ajuda a antecipar e controlar esses problemas.",
                                                    "Sistemas de irrigação eficientes, como a irrigação por gotejamento, ajudam a economizar água e garantir que as plantas recebam a quantidade necessária de umidade.",
                                                    "Práticas de gestão sustentável, como o uso eficiente de água e solo, são essenciais para manter a produtividade agrícola em climas variáveis.",
                                            };
                                            break;
                                        case 4:
                                            break;
                                        default:
                                            JOptionPane.showMessageDialog(frame, "Opção inválida. Por favor, tente novamente.");
                                            break;
                                    }

                                    if (escolhaInstrucoes != 4) {
                                        if (instrucoes.length > 0) {
                                            String instrucaoAleatoria = instrucoes[(int) (Math.random() * instrucoes.length)];

                                            Icon icon = new Icon() {
                                                @Override
                                                public void paintIcon(Component c, Graphics g, int x, int y) {
                                                    g.setColor(Color.RED);
                                                    g.setFont(new Font("Arial", Font.BOLD, 24));
                                                    g.drawString("!", x + 6, y + 24);
                                                }

                                                @Override
                                                public int getIconWidth() {
                                                    return 32;
                                                }

                                                @Override
                                                public int getIconHeight() {
                                                    return 32;
                                                }
                                            };

                                            JOptionPane.showMessageDialog(frame,
                                                    "<html><font color='red'>⚠</font> " + instrucaoAleatoria,
                                                    "Alerta", JOptionPane.INFORMATION_MESSAGE, icon);
                                        } else {
                                            JOptionPane.showMessageDialog(frame, "Opção ainda não implementada.");
                                        }
                                    }
                                } while (escolhaInstrucoes != 4);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao buscar instruções");
                            }
                            break;

                        case 5:
                            try {
                                safraList = controlarDados.listarPlantacoes();

                                StringBuilder relatorio = new StringBuilder();
                                relatorio.append("<html><head><style>");
                                relatorio.append("table {");
                                relatorio.append(" width: 100%;");
                                relatorio.append(" border-collapse: collapse;");
                                relatorio.append("}");
                                relatorio.append("th, td {");
                                relatorio.append(" border: 1px solid #dddddd;");
                                relatorio.append(" text-align: left;");
                                relatorio.append(" padding: 8px;");
                                relatorio.append("}");
                                relatorio.append("th {");
                                relatorio.append(" background-color: #f2f2f2;");
                                relatorio.append("}");
                                relatorio.append("</style></head><body>");
                                relatorio.append("<h2 style='color: #008000;'>Relatório:</h2>");
                                relatorio.append("<table>");
                                relatorio.append("<tr>");
                                relatorio.append("<th>ID</th>");
                                relatorio.append("<th>Nome</th>");
                                relatorio.append("<th>Data de Plantio</th>");
                                relatorio.append("<th>Data de Colheita</th>");
                                relatorio.append("<th>Variedade Cultivada</th>");
                                relatorio.append("<th>Área Plantada (m²)</th>");
                                relatorio.append("<th>Produtividade (kg)</th>");
                                relatorio.append("<th>Condição Climática</th>");
                                relatorio.append("<th>Fertilizantes</th>");
                                relatorio.append("<th>Pesticida</th>");
                                relatorio.append("</tr>");
                                for (DadosSafra safra1 : safraList) {
                                    relatorio.append("<tr>");
                                    relatorio.append("<td>").append(safra1.getId()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getNome()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getDataPlantio()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getDataColheita()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getVariedaCultivada()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getAreaPlantada()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getProdutividade()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getCondicaoClimatica()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getFertilizantes()).append("</td>");
                                    relatorio.append("<td>").append(safra1.getPesticida()).append("</td>");
                                    relatorio.append("</tr>");
                                }
                                relatorio.append("</table></body></html>");

                                JOptionPane.showMessageDialog(frame, relatorio.toString());
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao gerar relatório");
                            }
                            break;

                        case 6:
                            try {
                                int idGrafico = Integer.parseInt(JOptionPane.showInputDialog(frame, "Informe o ID da safra para o gráfico: "));
                                DadosSafra safraGrafico = controlarDados.listarDadosSafraPorID(idGrafico);
                                if (safraGrafico != null) {
                                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                                    dataset.addValue(safraGrafico.getProdutividade(), "Produtividade (kg)", "Produtividade");
                                    dataset.addValue(safraGrafico.getAreaPlantada(), "Área Plantada (m²)", "Área Plantada");

                                    JFreeChart barChart = ChartFactory.createBarChart(
                                            "Dados da Safra - ID: " + safraGrafico.getId(),
                                            "Categoria",
                                            "Valor",
                                            dataset,
                                            PlotOrientation.VERTICAL,
                                            true, true, false);

                                    CategoryPlot plot = barChart.getCategoryPlot();
                                    BarRenderer renderer = (BarRenderer) plot.getRenderer();
                                    renderer.setSeriesPaint(0, Color.BLUE);
                                    renderer.setSeriesPaint(1, Color.GREEN);

                                    renderer.setItemMargin(-0.7);
                                    renderer.setMaximumBarWidth(0.05);

                                    double maxValue = Math.max(safraGrafico.getProdutividade(), safraGrafico.getAreaPlantada());
                                    plot.getRangeAxis().setRange(0, maxValue * 1.2);

                                    ChartPanel chartPanel = new ChartPanel(barChart);
                                    chartPanel.setPreferredSize(new Dimension(800, 600));
                                    chartPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                                    JFrame chartFrame = new JFrame("Gráfico de Dados da Safra");
                                    chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    chartFrame.setLayout(new BorderLayout());
                                    chartFrame.add(chartPanel, BorderLayout.CENTER);
                                    chartFrame.setSize(1000, 650);
                                    chartFrame.setLocationRelativeTo(null);
                                    chartFrame.setVisible(true);

                                    chartFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                                        @Override
                                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                            iniciarMenu(args);
                                        }
                                    });

                                    continuar = false;
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Safra não encontrada para o ID informado.");
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao gerar gráfico");
                            }
                            break;

                        case 7:
                            try {
                                int idGrafico1 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Informe o ID da primeira safra: "));
                                int idGrafico2 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Informe o ID da segunda safra: "));
                                DadosSafra safraGrafico1 = controlarDados.listarDadosSafraPorID(idGrafico1);
                                DadosSafra safraGrafico2 = controlarDados.listarDadosSafraPorID(idGrafico2);

                                if (safraGrafico1 != null && safraGrafico2 != null) {
                                    DefaultCategoryDataset datasetComparacao = new DefaultCategoryDataset();
                                    datasetComparacao.addValue(safraGrafico1.getProdutividade(), safraGrafico1.getNome(), "Produtividade (kg)");
                                    datasetComparacao.addValue(safraGrafico2.getProdutividade(), safraGrafico2.getNome(), "Produtividade (kg)");

                                    JFreeChart barChartComparacao = ChartFactory.createBarChart(
                                            "Comparação de Produtividade",
                                            "Safra",
                                            "Produtividade (kg)",
                                            datasetComparacao,
                                            PlotOrientation.VERTICAL,
                                            true, true, false);
                                    CategoryPlot plotComparacao = barChartComparacao.getCategoryPlot();
                                    BarRenderer rendererComparacao = (BarRenderer) plotComparacao.getRenderer();
                                    rendererComparacao.setSeriesPaint(0, Color.BLUE);
                                    rendererComparacao.setSeriesPaint(1, Color.GREEN);

                                    rendererComparacao.setItemMargin(-0.3);

                                    rendererComparacao.setMaximumBarWidth(0.05);

                                    double maxValue = Math.max(safraGrafico1.getProdutividade(), safraGrafico2.getProdutividade());
                                    plotComparacao.getRangeAxis().setRange(0, maxValue * 1.2);

                                    ChartPanel chartPanelComparacao = new ChartPanel(barChartComparacao);
                                    chartPanelComparacao.setPreferredSize(new Dimension(800, 600));
                                    chartPanelComparacao.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                                    JFrame chartFrameComparacao = new JFrame("Comparação de Produtividade");
                                    chartFrameComparacao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    chartFrameComparacao.setLayout(new BorderLayout());
                                    chartFrameComparacao.add(chartPanelComparacao, BorderLayout.CENTER);

                                    JButton exclamationButton = new JButton("!");
                                    exclamationButton.setFont(new Font("Arial", Font.BOLD, 20));
                                    exclamationButton.setForeground(Color.RED);
                                    exclamationButton.setToolTipText("Clique para ver a comparação percentual");
                                    exclamationButton.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            double produtividade1 = safraGrafico1.getProdutividade();
                                            double produtividade2 = safraGrafico2.getProdutividade();
                                            double porcentagemComparacao = ((produtividade2 - produtividade1) / produtividade1) * 100;
                                            String mensagemComparacao = String.format("A safra '%s' teve uma produtividade %.2f%% %s do que a safra '%s'.",
                                                    safraGrafico2.getNome(),
                                                    Math.abs(porcentagemComparacao),
                                                    (porcentagemComparacao >= 0) ? "maior" : "menor",
                                                    safraGrafico1.getNome());
                                            JOptionPane.showMessageDialog(chartFrameComparacao, mensagemComparacao, "Comparação de Produtividade", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    });

                                    JPanel exclamationPanel = new JPanel();
                                    exclamationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                                    exclamationPanel.add(exclamationButton);
                                    chartFrameComparacao.add(exclamationPanel, BorderLayout.SOUTH);

                                    chartFrameComparacao.setSize(1000, 650);
                                    chartFrameComparacao.setLocationRelativeTo(null);
                                    chartFrameComparacao.setVisible(true);

                                    chartFrameComparacao.addWindowListener(new java.awt.event.WindowAdapter() {
                                        @Override
                                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                            iniciarMenu(args);
                                        }
                                    });

                                    continuar = false; 
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Uma ou ambas as safras não foram encontradas para os IDs informados.");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "IDs inválidos. Por favor, insira números inteiros válidos.");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao comparar produtividade");
                            }
                            break;

                        case 8:
                            JOptionPane.showMessageDialog(frame, "Saindo do programa...");
                            continuar = false;
                            System.exit(0);
                            break;

                        default:
                            JOptionPane.showMessageDialog(frame, "Opção inválida. Por favor, tente novamente.");
                            break;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao iniciar o menu: ");
        }
    }
}