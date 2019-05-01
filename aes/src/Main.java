public class Main {
    static String ming="abcdefghijkopqrs";
    static String key="abcdefghijkopqrs";
    static long w[]=new long[44];
    static long sp=1;
    static int rconi=0;
    static int m[][]={
            {0xc9,0xe5,0xfd,0x2b},
            {0x7a,0xf2,0x78,0x6e},
            {0x63,0x9c,0x26,0x67},
            {0xb0,0xa7,0x82,0xe5},
    };
    static int mul[][]={{2,3,1,1},{1,2,3,1},{1,1,2,3},{3,1,1,2}};
    static int rcon[]={
            0x01000000,
            0x02000000,
            0x04000000,
            0x08000000,
            0x10000000,
            0x20000000,
            0x40000000,
            0x80000000,
            0x1b000000,
            0x36000000
    };
    static int pres[]={
            0x63,0x7c,0x77,0x7b,0xf2,0x6b,0x6f,0xc5,0x30,0x01,0x67,0x2b,0xfe,0xd7,0xab,0x76,
            0xca,0x82,0xc9,0x7d,0xfa,0x59,0x47,0xf0,0xad,0xd4,0xa2,0xaf,0x9c,0xa4,0x72,0xc0,
            0xb7,0xfd,0x93,0x26,0x36,0x3f,0xf7,0xcc,0x34,0xa5,0xe5,0xf1,0x71,0xd8,0x31,0x15,
            0x04,0xc7,0x23,0xc3,0x18,0x96,0x05,0x9a,0x07,0x12,0x80,0xe2,0xeb,0x27,0xb2,0x75,
            0x09,0x83,0x2c,0x1a,0x1b,0x6e,0x5a,0xa0,0x52,0x3b,0xd6,0xb3,0x29,0xe3,0x2f,0x84,
            0x53,0xd1,0x00,0xed,0x20,0xfc,0xb1,0x5b,0x6a,0xcb,0xbe,0x39,0x4a,0x4c,0x58,0xcf,
            0xd0,0xef,0xaa,0xfb,0x43,0x4d,0x33,0x85,0x45,0xf9,0x02,0x7f,0x50,0x3c,0x9f,0xa8,
            0x51,0xa3,0x40,0x8f,0x92,0x9d,0x38,0xf5,0xbc,0xb6,0xda,0x21,0x10,0xff,0xf3,0xd2,
            0xcd,0x0c,0x13,0xec,0x5f,0x97,0x44,0x17,0xc4,0xa7,0x7e,0x3d,0x64,0x5d,0x19,0x73,
            0x60,0x81,0x4f,0xdc,0x22,0x2a,0x90,0x88,0x46,0xee,0xb8,0x14,0xde,0x5e,0x0b,0xdb,
            0xe0,0x32,0x3a,0x0a,0x49,0x06,0x24,0x5c,0xc2,0xd3,0xac,0x62,0x91,0x95,0xe4,0x79,
            0xe7,0xc8,0x37,0x6d,0x8d,0xd5,0x4e,0xa9,0x6c,0x56,0xf4,0xea,0x65,0x7a,0xae,0x08,
            0xba,0x78,0x25,0x2e,0x1c,0xa6,0xb4,0xc6,0xe8,0xdd,0x74,0x1f,0x4b,0xbd,0x8b,0x8a,
            0x70,0x3e,0xb5,0x66,0x48,0x03,0xf6,0x0e,0x61,0x35,0x57,0xb9,0x86,0xc1,0x1d,0x9e,
            0xe1,0xf8,0x98,0x11,0x69,0xd9,0x8e,0x94,0x9b,0x1e,0x87,0xe9,0xce,0x55,0x28,0xdf,
            0x8c,0xa1,0x89,0x0d,0xbf,0xe6,0x42,0x68,0x41,0x99,0x2d,0x0f,0xb0,0x54,0xbb,0x16,
    };
    static int s[][]=new int[16][16];
    public static void main(String[] args) {
        init();
        byte b[]=ming.getBytes();//ming是128位明文 m为其矩阵
        for(int i=0;i<4;++i)
        {
            for(int j=0;j<4;++j){
                m[i][j]=toint(b[i+j*4]);
                System.out.print(m[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("--------------------");



        m=SubByte(m);//正确
        m=ShiftRows(m);//正确
        System.out.println("2-------------");
        showm();
        m=MixColumns(m);//正确
        System.out.println("3--------------");
        showm();
        int keyi=0;
        m=AddRoundKey(m,keyi);
        showm();


        System.out.println((byte) s[15][6]);
    }
    static void init(){
        for(int i=0;i<32;++i)
            sp=sp*2;
        sp--;//sp=0xffffffff

        int k=0;
        for(int i=0;i<16;++i){
            for(int j=0;j<16;++j){
                s[i][j]=pres[k++];
            }
        }

        //处理出w0-w3//待测
        byte b[]=(key.getBytes());
        w[0]=toint(b[0]);
        for(int i=1;i<=3;++i)
            w[0]=(w[0]<<8)+toint(b[i]);
        w[1]=toint((b[4]));
        for(int i=5;i<=7;++i)
            w[1]=(w[1]<<8)+toint(b[i]);
        w[2]=toint((b[8]));
        for(int i=9;i<=11;++i)
            w[2]=(w[2]<<8)+toint(b[i]);
        w[3]=toint((b[12]));
        for(int i=13;i<=15;++i)
            w[3]=(w[3]<<8)+toint(b[i]);

        for(int i=4;i<44;++i)
        {
            if(i%4!=0)
                w[i]=(w[i-1]^w[i-4])&sp;
            else{
                w[i]=(w[i-4]^(T(w[i-1])))&sp;
                rconi=(rconi+1)%16;
                //System.out.println(Long.toHexString(w[i]));
            }
        }
    }
    static long T(long a){

        // System.out.println("prea:"+Long.toHexString(a));
        //System.out.println("a<<8:"+Long.toHexString((a<<8)&sp));
        //System.out.println("a&0xff:"+Long.toHexString(a>>24));

        System.out.println("prea:"+Long.toHexString(a));
        a=(a<<8)&sp^(a>>24);
        System.out.println("移位后:"+Long.toHexString(a));

        //System.out.println("a:"+Long.toHexString(a));
        long rt=0;
        long temp;
        temp=(a>>24);
        rt=((rt<<8)^subbyte(temp));
        temp=((a>>16)&0xff);
        rt=((rt<<8)^subbyte(temp));
        temp=((a>>8)&0xff);
        rt=((rt<<8)^subbyte(temp));
        temp=(a&0xff);
        rt=((rt<<8)^subbyte(temp));
        System.out.println("s盒变换后："+Long.toHexString(rt));
        return (rt&sp)^rcon[rconi];//////////////////
    }
    static long subbyte(long a){
        int x= (int) (a>>4);//前四位的int
        int y=(x&0xf);//后四位的int
        return s[x][y];
    }

    static int[][] SubByte(int m[][]){
        for(int i=0;i<4;++i)
        {
            for(int j=0;j<4;++j)
            {
                m[i][j]=s[q4(m[i][j])][h4(m[i][j])];
            }
        }
        return m;
    }

    static int q4(int a){
        //System.out.println("qian4:"+(a>>4));
        return a>>4;
    }

    static int h4(int a){
        //System.out.println("hou4:"+(a&0x0f));

        return a&(0x0f);
    }
    static void showm(){
        for(int i=0;i<4;++i){
            for(int j=0;j<4;++j){
                System.out.print(Integer.toHexString(m[i][j]));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    static void showw(long a ){
        long temp;
        temp=(a>>24);
        System.out.print(Integer.toHexString((int) temp)+" ");
        temp=((a>>16)&0xff);
        System.out.print(Integer.toHexString((int) temp)+" ");

        temp=((a>>8)&0xff);
        System.out.print(Integer.toHexString((int) temp)+" ");

        temp=(a&0xff);
        System.out.print(Integer.toHexString((int) temp)+" ");

    }
    static int toint(byte a){
        int ans=0;
        int temp=1;
        for(int i=0;i<8;++i){
            ans+=temp*((a>>i)&1);
            temp=temp*2;
        }
        return ans;
    }
    static int [][] ShiftRows(int m[][]){
        int temp[][]=new int[4][4];
        for(int i=0;i<4;++i)
            for(int j=0;j<4;++j)
            {
                temp[i][j]=m[i][j];
            }
        m[1][0]=temp[1][1];m[1][1]=temp[1][2];m[1][2]=temp[1][3];m[1][3]=temp[1][0];
        m[2][0]=temp[2][2];m[2][1]=temp[2][3];m[2][2]=temp[2][0];m[2][3]=temp[2][1];
        m[3][0]=temp[3][3];m[3][1]=temp[3][0];m[3][2]=temp[3][1];m[3][3]=temp[3][2];
        return m;
    }

    static int[][] MixColumns(int m[][]){
        int rt[][]=new int[4][4];
        for(int i=0;i<4;++i) {
            for(int j=0;j<4;++j){
                //3 0 1 2初值为1
                int tp=m[(i+3)%4][j];
                for(int k=0;k<4;++k) {
                    if(k!=((i+3)%4))
                        tp=(tp^(mix(mul[i][k],m[k][j])));
                }
                rt[i][j]=(tp&255);
            }
        }
        return rt;
    }
    static int mix(int a,int b){
        if(a==1){
            return b;
        }
        else if(a==2){
            return m2(b);
        }
        else if(a==3){
            return m2(b)^m1(b);
        }
        return b;

    }
    static int m1(int b){
        return b;
    }
    static int m2(int b){
        if(((b>>7)&1)==0)
            return b<<1;
        else{
            return ((b<<1)&0xff)^27;
        }
    }

    static int[][] AddRoundKey(int m[][],int keyi){
        int rt[][]=new int[4][4];

        for(int i=0;i<4;++i) {
            rt[0][i] = (int) (m[0][i] ^ (w[keyi+i] >> 24));
            rt[1][i] = (int) (m[1][i] ^ ((w[keyi+i] >> 16) & 0xff));
            rt[2][i] = (int) (m[2][i] ^ ((w[keyi+i] >> 8) & 0xff));
            rt[3][i] = (int) (m[3][i] ^ (w[keyi+i] & 0xff));
        }

        return rt;
    }

}
