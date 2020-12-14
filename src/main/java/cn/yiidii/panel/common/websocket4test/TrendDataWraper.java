package cn.yiidii.panel.common.websocket4test;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.list.TreeList;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * 模拟趋势投资数据
 */
@Slf4j
@Component
public class TrendDataWraper {
    static Random rand = new Random();
    private static int times = 0;

    static {
        randomDataThread();
    }

    public static void main(String[] args) {
    }

    private static Collection<TrendServer> servers = Collections.synchronizedCollection(new ArrayList<TrendServer>());

    public static void randomDataThread() {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    JSONObject msgJo = new JSONObject();
                    msgJo.put("price", (int) (Math.random() * 100) + 6);
                    msgJo.put("total", servers.size());
                    broadCast(getSimulateData());
                    try {
                        Thread.sleep(3000l);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        t.start();
    }

    private static String getSimulateData() {
        JSONObject result = new JSONObject(new TreeMap<>());
        result.put("text", "指数趋势投资收益对比图");
        result.put("subtext", "数据纯属虚构");
        JSONObject xAxis = new JSONObject(new TreeMap<>());
        xAxis.put("name", "时间");
        List<String> times = new TreeList();
        Calendar c = Calendar.getInstance();
        Date beginOfDay = DateUtil.beginOfDay(new Date());
        c.setTime(beginOfDay);
        List<Integer> record1Data = new TreeList();
        for (int i = 0; i < 1440; i++) {
            c.add(Calendar.MINUTE, 1);
            Date d = c.getTime();
            long ms = d.getTime();
            String dateStr = DateUtil.format(d, "yyyy-MM-dd HH:mm:ss");
            times.add(dateStr);
            int randInt = rand.nextInt(2000);
            double dd = randInt * randInt + 123;
            randInt = (int) Math.sqrt(dd);
            record1Data.add(randInt);
        }
        xAxis.put("times", times);
        result.put("xAxis", xAxis);

        JSONObject yAxis = new JSONObject(new TreeMap<>());
        yAxis.put("name", "跌涨幅度");
        result.put("yAxis", yAxis);

        JSONArray series = new JSONArray();
        JSONObject record1 = new JSONObject(new TreeMap<>());
        record1.put("name", "趋势投资");
        record1.put("color", "#ec7259");

        record1.put("data", record1Data);
        record1.put("type", "line");
        record1.put("symbol", "none");
        record1.put("smooth", true);
        series.add(record1);
        result.put("series", series);
        return result.toString();
    }

    /**
     * 给每一个server发送
     *
     * @param msg
     */
    public static void broadCast(String msg) {
        for (TrendServer bitCoinServer : servers) {
            try {
                bitCoinServer.sendMessage(msg);
            } catch (IOException e) {
            }
        }
    }

    public static int getTotal() {
        return servers.size();
    }

    public static void add(TrendServer server) {
        servers.add(server);
        log.info("有新连接[" + WebSocketUtil.getRemoteAddress(server.getSession()) + "]加入！ 当前总连接数是：" + servers.size());
    }

    public static void remove(TrendServer server) {
        servers.remove(server);
        log.info("有连接[" + WebSocketUtil.getRemoteAddress(server.getSession()) + "]退出！ 当前总连接数是：" + servers.size());
    }

}
