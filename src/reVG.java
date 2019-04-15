import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class reVG {
    private JPanel revgpanel;
    private JLabel reco;
    private JTextField recovertext;
    private JButton 解密Button;
    private JLabel yw;
    private JTextField encryptext;

    public reVG() {
        解密Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encrypted=encryptext.getText().toLowerCase().replace(" ","");
                int l=encrypted.length();
                double ans=0;
                double min=1;
                double ansn=0;
                for(int i=2;i<=7;++i) {
                    double sum=0;
                    for(int j=0;j<i;++j) {//每组
                        ans = 0;
                        Map<Character,Integer> map=new HashMap<>();
                        for(int k=0;k<l/i;++k)
                        {
                            //System.out.print(encrypted.charAt(k*i+j));
                            if(map.containsKey(encrypted.charAt(k*i+j)))
                                map.put(encrypted.charAt(k*i+j),map.get(encrypted.charAt(k*i+j))+1);
                            else
                                map.put(encrypted.charAt(k*i+j),1);
                        }
                        //System.out.println();
                        //System.out.println(map.size());
                        for (Integer value : map.values()) {
                            ans += cal(1.0*value, 1.0*l/i);
                        }
                        sum+=ans;
                    }
                    double average=sum/(i);
                    if(Math.abs(0.065-average)<Math.abs(0.065-min))
                    {
                        min=average;
                        ansn=i;
                    }
                }
                System.out.println("计算分组为"+ansn);
                System.out.println("分组长度为"+l/ansn);
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
        JFrame frame = new JFrame("reVG");
        frame.setContentPane(new reVG().revgpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(520,450);
        frame.setLocationRelativeTo(null);
    }
    static double cal(double fz,double fm){
        double temp=(fz-1)*fz/(fm*(fm-1));
        return temp;
    }
}
