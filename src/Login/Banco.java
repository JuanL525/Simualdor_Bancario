package Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import FormularioBnaco.BancoForm;

public class Banco extends JFrame {
    private JPanel LoginForm;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton iniciarSesionButton;

    public Banco() {
        setTitle("Iniciar Sesión - Banco");
        setContentPane(LoginForm);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarCredenciales();
            }
        });
    }

    // Validar credenciales
    private void validarCredenciales() {
        String usuarioIngresado = textField1.getText();
        String contrasenaIngresada = new String(passwordField1.getPassword());


        String usuarioCorrecto = "cliente123";
        String contrasenaCorrecta = "clave456";

        if (usuarioIngresado.equals(usuarioCorrecto) && contrasenaIngresada.equals(contrasenaCorrecta)) {
            JOptionPane.showMessageDialog(this, "¡Inicio de Sesión Exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrarVentanaPrincipal();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o Contraseña Incorrectos", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarVentanaPrincipal() {
        this.setVisible(false);
        this.dispose();

        SwingUtilities.invokeLater(() -> {
            new BancoForm().setVisible(true);
        });
    }
}
