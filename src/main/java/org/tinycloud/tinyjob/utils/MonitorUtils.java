package org.tinycloud.tinyjob.utils;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 网络监控服务工具类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-01 14:17
 */
public class MonitorUtils {
    private static final Logger logger = LoggerFactory.getLogger(MonitorUtils.class);


    /**
     * 使用telnet协议监听ip:port是否通
     * 使用场景 1、监听服务是否启动成功
     * 2、监听服务的健康状态
     *
     * @param ip   ip地址
     * @param port 端口号
     * @return true成功 或者 false失败
     */
    public static boolean testTelnet(String ip, int port) {
        TelnetClient telnet = new TelnetClient();
        // 设置超时时长为5秒
        telnet.setConnectTimeout(5 * 1000);

        boolean connected = false;
        try {
            port = port == 0 ? 23 : port;
            if (logger.isInfoEnabled()) {
                logger.info("执行命令：telnet {} {}", ip, port);
            }
            telnet.connect(ip, port);
            connected = telnet.isConnected();
        } catch (IOException e) {
            logger.error("执行命令出现错误：telnet-IOException ：", e);
            connected = false;
        } finally {
            try {
                // 释放连接
                telnet.disconnect();
            } catch (IOException e) {
                logger.error("telnet 释放连接出现错误IOException ：", e);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("执行结果: telnet isConnected = {}", connected);
        }
        return connected;
    }


    /**
     * 执行ping命令，看看服务器通不通
     *
     * @param ip host地址
     * @return true成功 或者 false失败
     */
    public static boolean testPingIcmp(String ip) {
        boolean connected = false;
        // 超时应该在3秒以上
        int timeout = 3000;
        try {
            // 当返回值是true时，说明host是可用的，false则不可
            connected = InetAddress.getByName(ip).isReachable(timeout);
        } catch (IOException e) {
            logger.error("执行命令出现错误：ping-IOException ：", e);
        }
        if (logger.isInfoEnabled()) {
            logger.info("执行命令结果: ping-isConnected = {}", connected);
        }
        return connected;
    }


    /**
     * 测试ntp服务器通不通
     *
     * @param ntpServer ntp服务地址
     * @return true成功 或者 false失败
     */
    public static boolean testNTP(String ntpServer) {
        boolean connected = false;

        int port = 123;
        NTPUDPClient timeClient = new NTPUDPClient();
        try {
            InetAddress timeServerAddress = InetAddress.getByName(ntpServer);
            TimeInfo timeInfo = timeClient.getTime(timeServerAddress, port);
            TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
            Date date = timeStamp.getDate();
            if (logger.isInfoEnabled()) {
                logger.info("testNTP: current time = {}", date);
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            if (logger.isInfoEnabled()) {
                logger.info("testNTP: current format time = {}", dateFormat.format(date));
            }
            connected = true;
        } catch (Exception e) {
            logger.error("testNTP: 执行命令出现错误 = ", e);
        } finally {
            timeClient.close();
        }
        if (logger.isInfoEnabled()) {
            logger.info("testNTP: 执行命令结果 = {}", connected);
        }
        return connected;
    }


    /**
     * 手工测试
     *
     * @param args
     */
    public static void main(String[] args) {
        testTelnet("172.20.88.224", 8012);
    }
}
