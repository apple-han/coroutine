package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(9999));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            int selected = selector.select();
            if (selected==0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIter = selectionKeys.iterator();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Handler handler = new Handler();
            while (keyIter.hasNext()){
                SelectionKey key = keyIter.next();
                if (key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = channel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                }
                // 如何key中有新的数据过来，才处理
                if (key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.read(buffer);
                    String request = new String(buffer.array()).trim();
                    buffer.clear();
                    String response = handler.handle(request);
                    channel.write(ByteBuffer.wrap(response.getBytes()));
                }
                keyIter.remove();
            }
        }
    }
}
