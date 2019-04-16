import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class reVG {
    private JPanel revgpanel;
    private JLabel reco;
    private JButton 解密Button;
    private JLabel yw;
    private JTextField encryptext;
    private JTextArea recovertext;

    public reVG() {
        解密Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //统计概率
                double[] probability = new double[]{0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.02, 0.061, 0.07, 0.002, 0.008,
                        0.04, 0.024, 0.067, 0.075, 0.019, 0.001, 0.06, 0.063, 0.091, 0.028, 0.01, 0.023, 0.001, 0.02, 0.001};
                double M[] = new double[26];
                double f[] = new double[26];//出现频率(次数)
                String encrypted=encryptext.getText().toLowerCase().replace(" ","");
                System.out.println(encrypted.length());
                int l=encrypted.length();
                double ans=0;
                double min=1;
                int ansn=0;
                for(int i=2;i<=7;++i) {//密钥长度
                    double sum=0;
                    for(int j=0;j<i;j++) {//每组
                        ans = 0;
                        Map<Character,Integer> map=new HashMap<>();
                        int temp=l/i;
                        if(i*temp!=l)temp++;
                        int temppp=0;
                        for(int k=0;k<temp;++k)
                        {
                                if(k*i+j<l) {
                                    if (map.containsKey(encrypted.charAt(k * i + j)))
                                        map.put(encrypted.charAt(k * i + j), map.get(encrypted.charAt(k * i + j)) + 1);
                                    else
                                        map.put(encrypted.charAt(k * i + j), 1);
                                    temppp++;
                                }
                        }
                        for (Integer value : map.values()) {
                            ans += cal(1.0*value, 1.0*temppp);//i=2, l/i=9
                        }
                        sum+=ans;
                    }
                    double average=sum/i;
                    System.out.println(average+" "+i);
                    if(Math.abs(0.065-average)<Math.abs(0.065-min))
                    {
                        min=average;
                        ansn=i;
                    }
                }
                System.out.println("分组密钥长度"+ansn);
                String blocks[] = new String[200];
                for(int j=0;j<ansn;j++) {//按ans分组
                    int temp=l/ansn;
                    if(ansn*temp!=l)temp++;
                    blocks[j] = "";
                    for(int k=0;k<temp;++k)
                    {
                        if(k*ansn+j<l) {
                            blocks[j] += encrypted.charAt(k*ansn+j);
                        }
                    }
                    System.out.println(blocks[j]);
                }

                //统计密文中所有字母的概率
                String anskey="";
                double close;
                char ansc='0';
                double sump=0;
                for(int t=0;t<ansn;++t) {//对于每个block
                    close=2;
                    System.out.println("block "+blocks[t]);
                    Map<Character,Integer> map=new HashMap<>();
                    //blocks[t]中进行统计
                    int len=blocks[t].length();

                    for(int i=0;i<len;++i){//加到map
                        if (map.containsKey(blocks[t].charAt(i)))
                                map.put(blocks[t].charAt(i), map.get(blocks[t].charAt(i)) + 1);
                            else
                                map.put(blocks[t].charAt(i), 1);
                    }
                    for (int i = 0; i < 26; ++i) {
                        if (!map.containsKey((char) ('a' + i)))
                        {
                            f[i] = 0;
                        }
                        else
                        {
                            f[i] = 1.0*map.get((char)('a' + i)) / blocks[t].length();
                        }
                    }
                    for (int i = 0; i < 26; ++i) {//移位数//////////////////
                        double sum = 0;
                        for (int j = 0; j < 26; ++j) {
                            sum += (1.0*probability[j] * f[(j + i) % 26]) ;
                        }
                        M[i] = sum;
                        if(Math.abs(M[i]-0.065)<Math.abs(0.065-close)){
                            close=M[i];
                            ansc= (char) ('a'+i);
                        }
                        //System.out.println((char)(i+'a')+" "+M[i]);
                    }
                    sump+=close;
                    anskey+=ansc;
                }
                System.out.println(anskey+" "+sump/ansn);
                String keys=anskey;
                while(keys.length()<l)
                    keys+=anskey;
                StringBuilder recover = new StringBuilder(encrypted);
                for(int i=0;i<l;++i){
                    int ttp=(encrypted.charAt(i)-(keys.charAt(i)-'a'));
                    recover.replace(i,i+1, String.valueOf((char)(ttp<'a'?ttp+26:ttp)));
                }
                for(int i=0;i<l;++i){
                    if(i!=0&&i%30==0){
                        recover=recover.insert(i,'\n');
                    }
                }
                System.out.println(recover);
                recovertext.setText(String.valueOf(recover));
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
        frame.setSize(620,550);
        frame.setLocationRelativeTo(null);
    }
    static double cal(double fz,double fm){
        double temp=(fz-1)*fz/(fm*(fm-1));
        return temp;
    }
}
