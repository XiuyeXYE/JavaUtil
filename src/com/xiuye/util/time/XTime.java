package com.xiuye.util.time;

import com.xiuye.util.cls.XMeta;
import com.xiuye.util.cls.XMeta.Caller;
import com.xiuye.util.log.XLog;

import java.util.Objects;

/**
 * time operator
 *
 * @author xiuye
 */
public class XTime {

    private long stime = -1;

    private Caller scaller = null;

    private Thread parent;

    private int level;

    public XTime() {
        this(3);
    }

    public XTime(int level) {
        this(Thread.currentThread(), level);
    }

    public XTime(Thread parent, int level) {
        this.level = level;
        this.parent = parent;
    }

    /**
     * input new level and return old
     *
     * @param level
     * @return
     */
    public int setLevel(int level) {
        int lvl = level;
        this.level = level;
        return lvl;
    }

    public int getLevel() {
        return level;
    }

    public int attach(int delta) {
        int old = level;
        level += delta;
        return old;
    }

    public int dettach(int delta) {
        int old = level;
        level -= delta;
        return old;
    }

    /**
     * check start time and start caller existence
     */
    private void checkTime() {
        if (stime == -1 || Objects.isNull(scaller)) {
            throw new RuntimeException("Not call start() function firstly!");
        }
    }

    /**
     * execute callbacks
     *
     * @param cs
     */
    private void executeAllCallback(Callback... cs) {
        for (Callback c : cs) {
            c.run();
        }
    }

    /**
     * System.out running code section
     */
    private void codeSectionMSG() {
        Caller eCaller = XMeta.caller(parent, level + 1);
        String codeSectionMSGStr = "\r\n=====This Code Section=====\nFrom\n";
        codeSectionMSGStr += "    Class Name : " + scaller.getClassName() + "\n    Method Name : "
                + scaller.getMethodName() + "\n    File Name : " + scaller.getFileName() + "\n    Enter Line : "
                + (scaller.getLineNumber()) + "\nTo\n" + "    Class Name : " + eCaller.getClassName()
                + "\n    Method Name : " + eCaller.getMethodName() + "\n    File Name : " + eCaller.getFileName()
                + "\n    Exit Line : " + (eCaller.getLineNumber());
        XLog.log(codeSectionMSGStr);
    }

    /**
     * callback
     *
     * @author xiuye
     */
    public interface Callback {
        void run();
    }

    /**
     * start and init first time and caller
     *
     * @return nanoseconds
     */
    public long start() {
        scaller = XMeta.caller(parent, level);
        return stime = System.nanoTime();
    }

    /**
     * from start to now cost time
     *
     * @return
     */
    public long cost() {
        checkTime();
        return System.nanoTime() - stime;
    }

    /**
     * set start time not inited!
     */
    public void reset() {
        stime = -1;
    }

    /**
     * output runing code section info nanoseconds
     *
     * @param cs
     * @return
     */
    public long outByNS(Callback... cs) {
        long e = System.nanoTime();// place it here precision
        checkTime();
        codeSectionMSG();
        long cost = e - stime;
        XLog.log("This running time costs : " + cost + " ns");
        executeAllCallback(cs);
        scaller = XMeta.caller(parent, level);
        stime = System.nanoTime();// place it here precision
        return cost;
    }

    /**
     * output runing code section info milliseconds
     *
     * @param cs
     * @return
     */
    public long outByMS(Callback... cs) {
        long e = System.nanoTime();
        checkTime();
        codeSectionMSG();
        long cost = e - stime;
        XLog.log("This running time costs : " + String.format("%.6f", cost / 1000000.0) + " ms");
        executeAllCallback(cs);
        scaller = XMeta.caller(parent, level);
        stime = System.nanoTime();
        return cost;
    }

    /**
     * output runing code section info seconds
     *
     * @param cs
     * @return
     */
    public long outByS(Callback... cs) {
        long e = System.nanoTime();
        checkTime();
        codeSectionMSG();
        long cost = e - stime;
        XLog.log("This running time costs : " + String.format("%.9f", cost / 1000000000.0) + " s");
        executeAllCallback(cs);
        scaller = XMeta.caller(parent, level);
        stime = System.nanoTime();
        return cost;
    }

}
