import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Caesar {
    private JPanel mainpanel;
    private JButton 加密Button;
    private JTextField encrypttext;
    private JTextField yuan;
    private JTextField textn;
    private JLabel yw;

    public Caesar() {
        加密Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n=Integer.parseInt(textn.getText());
                String origin = yuan.getText();
                origin=origin.toLowerCase();
                origin=origin.replace(" ","");
                int l=origin.length();
                StringBuilder encrypted=new StringBuilder(origin);
                char c;
                for(int i=0;i<l;++i)
                {
                    c=origin.charAt(i);
                    c= (char) (c+n);
                    if(c>'z')c= (char) (c-26);
                    encrypted.replace(i,i+1, String.valueOf(c));
                }
                encrypttext.setText(String.valueOf(encrypted));

            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
        JFrame frame = new JFrame("Caesar");
        frame.setContentPane(new Caesar().mainpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(520,450);
        frame.setLocationRelativeTo(null);
    }
}
