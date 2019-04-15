import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VG {
    private JTextField encrypttext;
    private JButton 加密Button;
    private JTextField origintext;
    private JPanel vgpanel;
    private JTextField keytext;
    static  HashMap<Character,Integer> map=new HashMap<>();
    static  char m[]=new char[26];

    public VG() {
        加密Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origin = origintext.getText().toLowerCase();
                origin=origin.replace(" ","");
                String key = keytext.getText().toLowerCase();
                int l=origin.length();
                while(key.length()<l)
                    key=key+key;
                StringBuilder encrypted = new StringBuilder(origin);
                for(int i=0;i<l;++i)
                {
                    encrypted.replace(i,i+1, String.valueOf(m[(origin.charAt(i)-'a'+(key.charAt(i))-'a')%26]));
                }
                encrypttext.setText(String.valueOf(encrypted));

            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
        JFrame frame = new JFrame("VG");
        frame.setContentPane(new VG().vgpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(520,450);
        frame.setLocationRelativeTo(null);
        for(int i=0;i<=25;++i){
            m[i]= (char) ('a'+i);
            map.put((char) ('a'+i),i);
        }


    }
}
