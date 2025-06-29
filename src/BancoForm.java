import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class BancoForm extends JFrame{
    private JPanel mainPanel;
    private JButton btnDeposito;
    private JButton btnRetiro;
    private JButton btnTransferencia;
    private JButton btnSalir;
    private JLabel lblNombreCliente;
    private JLabel lblSaldoActual;
    private JTextArea txtAreaHistorial;

    private double saldo = 1000.00;
    private String nombreCliente = "JuanLucero";
    private DecimalFormat df = new DecimalFormat("#,##0.00"); // Formato para el saldo

    public BancoForm() {
        setTitle("Operaciones Bancarias");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración del panel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        // Panel superior para información del cliente y saldo
        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        lblNombreCliente = new JLabel("Cliente: " + nombreCliente);
        lblNombreCliente.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSaldoActual = new JLabel("Saldo Actual: $" + df.format(saldo));
        lblSaldoActual.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblSaldoActual.setForeground(new Color(0, 100, 0)); // Color verde para el saldo

        panelInfo.add(lblNombreCliente);
        panelInfo.add(lblSaldoActual);
        mainPanel.add(panelInfo, BorderLayout.NORTH);


        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 filas, 1 columna, espaciado
        btnDeposito = new JButton("Depósito");
        btnRetiro = new JButton("Retiro");
        btnTransferencia = new JButton("Transferencia");
        btnSalir = new JButton("Salir");

        // Estilo de los botones
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 14);
        btnDeposito.setFont(buttonFont);
        btnRetiro.setFont(buttonFont);
        btnTransferencia.setFont(buttonFont);
        btnSalir.setFont(buttonFont);

        panelBotones.add(btnDeposito);
        panelBotones.add(btnRetiro);
        panelBotones.add(btnTransferencia);
        panelBotones.add(btnSalir);
        mainPanel.add(panelBotones, BorderLayout.CENTER);

        // Panel inferior para el historial de transacciones
        txtAreaHistorial = new JTextArea(8, 30);
        txtAreaHistorial.setEditable(false); // No permitir edición manual
        txtAreaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtAreaHistorial);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);


        btnDeposito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarDeposito();
            }
        });

        btnRetiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarRetiro();
            }
        });

        btnTransferencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarTransferencia();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cierra la aplicación
            }
        });


        actualizarHistorial("Inicio de sesión. Saldo inicial: $" + df.format(saldo));
    }



    private void actualizarSaldoEnPantalla() {
        lblSaldoActual.setText("Saldo Actual: $" + df.format(saldo));
    }

    private void actualizarHistorial(String mensaje) {
        txtAreaHistorial.append(mensaje + "\n");
        txtAreaHistorial.setCaretPosition(txtAreaHistorial.getDocument().getLength());
    }

    private void realizarDeposito() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el monto a depositar:", "Depósito", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.trim().isEmpty()) {
            try {
                double monto = Double.parseDouble(input);
                if (monto > 0) {
                    saldo += monto;
                    actualizarSaldoEnPantalla();
                    actualizarHistorial("Depósito: +$" + df.format(monto) + " | Nuevo saldo: $" + df.format(saldo));
                    JOptionPane.showMessageDialog(this, "Depósito exitoso de $" + df.format(monto), "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "El monto del depósito debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void realizarRetiro() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el monto a retirar:", "Retiro", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.trim().isEmpty()) {
            try {
                double monto = Double.parseDouble(input);
                if (monto > 0) {
                    if (saldo >= monto) {
                        saldo -= monto;
                        actualizarSaldoEnPantalla();
                        actualizarHistorial("Retiro: -$" + df.format(monto) + " | Nuevo saldo: $" + df.format(saldo));
                        JOptionPane.showMessageDialog(this, "Retiro exitoso de $" + df.format(monto), "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Saldo insuficiente. Su saldo actual es $" + df.format(saldo), "Advertencia", JOptionPane.WARNING_MESSAGE);
                        actualizarHistorial("Intento de retiro fallido (saldo insuficiente): $" + df.format(monto));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El monto del retiro debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void realizarTransferencia() {
        JTextField destinatarioField = new JTextField(15);
        JTextField montoField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre del Destinatario:"));
        panel.add(destinatarioField);
        panel.add(new JLabel("Monto a Transferir:"));
        panel.add(montoField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Realizar Transferencia",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombreDestinatario = destinatarioField.getText().trim();
            String montoStr = montoField.getText().trim();

            if (nombreDestinatario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del destinatario no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double monto = Double.parseDouble(montoStr);
                if (monto > 0) {
                    if (saldo >= monto) {
                        saldo -= monto;
                        actualizarSaldoEnPantalla();
                        String mensajeConfirmacion = "Transferencia exitosa a " + nombreDestinatario + " por $" + df.format(monto);
                        actualizarHistorial("Transferencia: -$" + df.format(monto) + " a " + nombreDestinatario + " | Nuevo saldo: $" + df.format(saldo));
                        JOptionPane.showMessageDialog(this, mensajeConfirmacion, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Saldo insuficiente para la transferencia. Su saldo actual es $" + df.format(saldo), "Advertencia", JOptionPane.WARNING_MESSAGE);
                        actualizarHistorial("Intento de transferencia fallido (saldo insuficiente): $" + df.format(monto) + " a " + nombreDestinatario);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El monto a transferir debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
