package com.example.myapplicationnumba.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplicationnumba.entity_model.EquipmentBean;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.example.myapplicationnumba.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class findActivity extends AppCompatActivity {
    /*发送广播端的socket*/
    private DatagramSocket socket;
    //用来存放扫描到的设备（数据源）
    private List<EquipmentBean> arrayList = new ArrayList<EquipmentBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        try {
            /*创建socket实例*/
            //1.创建对象
            socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        send();
        //finish();
    }

    /**
     * 向局域网发送广播
     */
    public void send() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String broadcast = null;
                try {
                    broadcast = getBroadcast();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                System.out.println(broadcast);
                //发送的数据包，局网内的所有地址都可以收到该数据包
                DatagramPacket dataPacket = null;
                try {
                    String str = broadcast.substring(0, broadcast.lastIndexOf('.')) + ".";
                    byte[] arr = NetworkInterfaceTest.getLANAddressOnWindows().getHostAddress().getBytes();
                    for (int i = 0; i < 256; i++) {
                        //四个参数: 包的数据  包的长度  主机对象  端口号
                        dataPacket = new DatagramPacket(arr, arr.length, InetAddress.getByName(str + i), 4000);
                        socket.send(dataPacket);
                    }
                    socket.close();
                    new NIOUdpServer().service();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 获取本机的局域网广播地址
     *
     * @return
     * @throws SocketException
     */
    public String getBroadcast() throws SocketException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        for (Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces(); niEnum.hasMoreElements(); ) {
            NetworkInterface ni = niEnum.nextElement();
            if (!ni.isLoopback()) {
                for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses()) {
                    if (interfaceAddress.getBroadcast() != null) {
                        return interfaceAddress.getBroadcast().toString().substring(1);
                    }
                }
            }
        }
        return null;
    }

    class NIOUdpServer {
        // UDP协议服务端
        private int port = 4001;
        DatagramChannel channel;
        private Selector selector;

        //构造器
        public NIOUdpServer() throws IOException {
            try {
                selector = Selector.open();
                channel = DatagramChannel.open();
            } catch (Exception e) {
                selector = null;
                channel = null;
                System.out.println("超时");
            }
            System.out.println("服务器启动");
        }

        /* 服务器服务方法 */
        public void service() throws IOException {
            if (channel == null || selector == null) return;
            //设置为非阻塞
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(port));
            channel.register(selector, SelectionKey.OP_READ);
            /** 外循环，已经发生了SelectionKey数目 */
            while (selector.select() > 0) {
                System.out.println("有新channel加入");
                Iterator iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = null;
                    try {
                        key = (SelectionKey) iterator.next();
                        iterator.remove();

                        if (key.isReadable()) {
                            reveice(key);
                        }
                    } catch (IOException e) {

                    }
                }
            }
        }

        synchronized public void reveice(SelectionKey key) throws IOException {
            long firstTime = System.currentTimeMillis();
            if (key == null)
                return;
            DatagramChannel sc = (DatagramChannel) key.channel();
            String content = "";
            ByteBuffer buf = ByteBuffer.allocate(2048);
            buf.clear();
            SocketAddress address = sc.receive(buf); // read into buffer. 返回客户端的地址信息
            String clientAddress = address.toString().replace("/", "").split(":")[0];
            String clientPost = address.toString().replace("/", "").split(":")[1];
            buf.flip(); // make buffer ready for read
            while (buf.hasRemaining()) {
                buf.get(new byte[buf.limit()]);// read 1 byte at a time
                content += new String(buf.array());
            }
            buf.clear(); // make buffer ready for writing
            System.out.println("接收：" + content.trim());
            Gson gson = new Gson();
            //转化为Java对象
            EquipmentBean res = gson.fromJson(content.trim(), EquipmentBean.class);
            System.out.println(res);
            //加入列表
            arrayList.add(res);
            while (true) {
                if (System.currentTimeMillis() - firstTime > 5000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(findActivity.this, "扫描时间到", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                }
            }
            channel.close();
            //传递数据给下一个视图
            Intent intent = new Intent(findActivity.this, FindResultActivity.class);
            //Intent intent = new Intent (  );
            intent.putExtra("arrayList", (Serializable) arrayList);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}