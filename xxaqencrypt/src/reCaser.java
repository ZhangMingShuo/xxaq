import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class reCaser {
    private JPanel recaserpanel;
    private JTextField recovertext;
    private JButton 解密Button;
    private JLabel yw;
    private JTextField encryptext;
    private JLabel reco;

    public reCaser() {
        解密Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encrypted=encryptext.getText();
                StringBuilder recover=new StringBuilder(encrypted);
                int l= encrypted.length();
                char c;
                for(int i=0;i<l;++i)
                {
                    c=encrypted.charAt(i);
                    c= (char) (c-3);
                    if(c<'a')c= (char) (c+26);
                    recover.replace(i,i+1, String.valueOf(c));
                }
                recovertext.setText(String.valueOf(recover));
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
        JFrame frame = new JFrame("古典加密算法_1617440518");
        frame.setContentPane(new reCaser().recaserpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(520,450);
        frame.setLocationRelativeTo(null);
    }
}
