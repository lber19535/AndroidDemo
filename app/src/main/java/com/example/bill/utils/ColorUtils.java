package com.example.bill.utils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Random;

/**
 * 随机颜色生成工具类，限制了饱和度和明度来得到看起来比较舒服的一些颜色。
 * 
 * 使用比较直观的HSV/HSB来得到颜色，然后转成RGB/ARGB的方式
 * 
 * @author Lv Beier
 *
 */
public class ColorUtils {

    private static final Random rand = new Random();

    /**
     * 产生一个随机颜色
     * 
     * @return
     */
    public static int randomColor() {
        return randomColor(null);
    }

    /**
     * 产生一个随机颜色，得到颜色对应的rgb值
     * 
     * @param rgb
     *            需要填充的rgb数组
     * @return
     */
    public static int randomColor(int[] rgb) {
        int h = rand.nextInt(360);
        int s = 100 - rand.nextInt(30);
        int v = 100 - rand.nextInt(30);
        return HSV2RGB(h, s, v, rgb);
    }

    /**
     * HSV转RGB
     * 
     * @param h
     *            [0,360)
     * @param s
     *            [0~100]
     * @param v
     *            [0~100]
     * @return 如果转换出问题则会返回0;
     */
    public static int HSV2RGB(int h, int s, int v, int[] rgb) {
        return HSV2RGB(h, s / 100f, v / 100f, rgb);
    }

    /**
     * HSV转RGB
     * 
     * @param h
     *            0~360
     * @param s
     *            0~1
     * @param v
     *            0~1
     * @return 如果转换出问题则会返回0，返回结果中加入了0xFF的A的值;
     */
    public static int HSV2RGB(int h, float s, float v, int[] rgb) {
        int hi = h / 60 % 6;
        float f = h / 60f - hi;
        float p = v * (1 - s);
        float q = v * (1 - f * s);
        float t = v * (1 - (1 - f) * s);
        int[] rgbArray = null;
        switch (hi) {
            case 0:
                rgbArray = getRgb(v, t, p);
                break;
            case 1:
                rgbArray = getRgb(q, v, p);
                break;
            case 2:
                rgbArray = getRgb(p, v, t);
                break;
            case 3:
                rgbArray = getRgb(p, q, v);
                break;
            case 4:
                rgbArray = getRgb(t, p, v);
                break;
            case 5:
                rgbArray = getRgb(v, p, q);
                break;
            default:
                break;
        }
        copyValue(rgbArray, rgb);
        return 0xff << 24 | hexRgb(rgbArray);
    }

    /**
     * rgb转换为hsv
     * 
     * @param r
     *            [0,255]
     * @param g
     *            [0,255]
     * @param b
     *            [0,255]
     * @return hsv数组
     */
    public static float[] RGB2HSV(int r, int g, int b) {
        float[] hsv = new float[3];
        int[] rgb = { r, g, b };
        Arrays.sort(rgb);
        int max = rgb[2];
        int min = rgb[0];
        // 差值
        float dvalue = max - min;
        // 常量
        float cvalue = 60f / dvalue;

        // 计算 h
        if (max == r) {
            if (g >= b) {
                hsv[0] = cvalue * (g - b);
            } else if (g < b) {
                hsv[0] = cvalue * (g - b) + 360;
            }
        } else if (max == g) {
            hsv[0] = cvalue * (b - r) + 120;
        } else if (max == b) {
            hsv[0] = cvalue * (r - g) + 240;
        }

        // s
        if (max == 0) {
            hsv[1] = 0;
        } else {
            hsv[1] = dvalue / max;
        }

        // v
        hsv[2] = max / 255f;

        return hsv;
    }

    /**
     * 将hex颜色分离成ARGB的int数组
     * 
     * @param color
     *            0xFFFFFF or 0xFFFFFFFF
     * @return ARGB的int数组
     */
    public static int[] seperateColor2RGB(int color) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(color);
        IntBuffer ib = IntBuffer.allocate(4);
        for (byte value : bb.array()) {
            ib.put(value);
        }
        return ib.array();
    }

    private static void copyValue(int[] source, int[] target) {
        if (source == null | target == null) {
            return;
        }
        for (int i = 0; i < target.length; i++) {
            target[i] = source[i];
        }
    }

    /*
     * 将百分比转为真实值
     */
    private static int[] getRgb(float r, float g, float b) {
        int[] rgb = new int[3];
        rgb[0] = (int) (r * 255);
        rgb[1] = (int) (g * 255);
        rgb[2] = (int) (b * 255);
        return rgb;
    }

    /*
     * 转为16进制的color值
     */
    private static int hexRgb(int... rgb) {
        return rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

}
