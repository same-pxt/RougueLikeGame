package game.com.example.online;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.Scanner;

import game.com.example.screen.OnlineScreen;
import game.com.example.world.World;

public class Client extends Thread{

    public SocketChannel socketChannel;
    OnlineScreen os;
    static int id=0;
    public Client(OnlineScreen os) throws IOException {
        this.os = os;
        socketChannel = SocketChannel.open();
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        //绑定
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8888);
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("连接失败");
            }
        }
        System.out.println("连接!");
        id++;
    }

    @Override 
    public void run()
    {
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(10000000);
                int bytesRead=0;
                try {
                    bytesRead = socketChannel.read(buffer);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (bytesRead > 0) {
                    buffer.flip();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    ObjectInputStream ois=null;
                    try {
                        ois = new ObjectInputStream(new ByteArrayInputStream(data));
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        this.os.world = (World) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
    public void sendStringToServer(SocketChannel socketChannel, String input) throws IOException {
        byte[] data = input.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
        socketChannel.write(buffer);
    }
}

