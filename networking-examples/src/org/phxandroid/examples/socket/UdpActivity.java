package org.phxandroid.examples.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;

import org.phxandroid.examples.AbstractTestConsoleActivity;

import android.os.Bundle;
import android.view.View;

public class UdpActivity extends AbstractTestConsoleActivity {
    @Override
    protected String getDefaultDestination() {
        return "10.0.2.2:21813";
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printf("Welcome to UDP Socket Example%n");
    }

    @Override
    protected void doTest(View v) {
        InetSocketAddress addr = getDestinationSocketAddress();
        if(addr == null) {
            return;
        }
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(2000);
            socket.connect(addr);
            byte dummy[] = new byte[1]; // The sent packet
            byte data[] = new byte[256]; // The received data
            DatagramPacket dummyPacket = new DatagramPacket(dummy, dummy.length, addr);
            DatagramPacket datePacket = new DatagramPacket(data, data.length);
            socket.send(dummyPacket);
            socket.receive(datePacket);
            String content = new String(datePacket.getData(), datePacket.getOffset(), datePacket.getLength());
            printf("%s%n", content);
        } catch (SocketTimeoutException e) {
            printf("Socket Timed Out attempting to communicate with %s on port %d%n", addr.getHostName(), addr.getPort());
        } catch (IOException e) {
            printf(e, "Unable to initiate DatagramSocket to host: %s (port %d)%n", addr.getHostName(), addr.getPort());
        }
    }

    public static void close(DatagramSocket socket) {
        if (socket == null) {
            return;
        }

        socket.disconnect();
        socket.close();
    }
}
