package game.com.example.online;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import game.com.example.screen.OnlineScreen;
import game.com.example.world.Creature;
import game.com.example.world.Creature.creatureType;
public class Server extends Thread{

    public ServerSocketChannel serverSocketChannel;
    public OnlineScreen os;
    Selector selector;
    SocketChannel socketChannel;
    public Server(OnlineScreen os) throws IOException
    {
        this.os=os;
        //打开ServerSocketChannel等待连接
        serverSocketChannel = ServerSocketChannel.open();
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口号6666
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        //打开选择器
        selector = Selector.open();
        //注册,监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
    @Override
    public void run()
    {
        while (true){
            //循环监听
            //select是阻塞方法
            try {
                selector.select();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //获得存在事件的SelectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //迭代Set集合
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){

                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()){
                    System.out.println("得到客户端连接");
                    //accept得到SocketChannel
                    
                    try {
                        socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        sendObjectToClient(socketChannel, this.os.world);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    try {
                        socketChannel.read(byteBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byteBuffer.clear();
                    String ins=new String(byteBuffer.array());
                    String a[]=ins.split(" ");
                    System.out.println(ins);
                    if("put".equals(a[0]))
                    {
                        this.os.creatureFactory.newPlayer(Integer.parseInt(a[1].trim()), Integer.parseInt(a[2].trim()), Integer.parseInt(a[3].trim()), null);
                        System.out.println(a[1]+"was put at "+a[2]+" "+a[3]);
                    }
                    else
                    {
                        for(Creature creature:this.os.world.creatures)
                        {
                            if(creature.type==creatureType.PLAYER && creature.playerid==Integer.parseInt(a[1].trim()))
                            {
                                if("move".equals(a[0]))
                                {
                                    creature.moveBy(Integer.parseInt(a[2].trim()),Integer.parseInt(a[3].trim()));
                                    System.out.println(creature.id+"moves "+creature.x()+" "+creature.y());
                                }
                            }
                        }
                    }
                    
                }
                keyIterator.remove();
            }
            try {
                Thread.sleep(500);
                try {
                    sendObjectToClient(socketChannel,this.os.world);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();;
        }
    }
    private void sendObjectToClient(SocketChannel socketChannel, Serializable object) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(object);
    oos.flush();

    byte[] data = bos.toByteArray();
    ByteBuffer buffer = ByteBuffer.allocate(data.length);
    buffer.put(data);
    buffer.flip();

    socketChannel.write(buffer);
    }
}
