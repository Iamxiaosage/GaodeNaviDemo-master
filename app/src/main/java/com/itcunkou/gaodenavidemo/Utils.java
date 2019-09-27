package com.itcunkou.gaodenavidemo;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;

import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Utils {

    /**
     * 某个点是否在区域内
     *
     * @param aMap       地图元素
     * @param latLngList 区域坐标合集
     * @param latLng     需要判断的点
     * @return
     */
    public static boolean polygonCon(AMap aMap, List<LatLng> latLngList, LatLng latLng) {


//        LatLng latLng1 = new LatLng(113.171196, 23.431667);
//        LatLng latLng2 = new LatLng(113.170724,23.43146);
//        LatLng latLng3 = new LatLng(113.17095,23.431942);
//        LatLng latLng4 = new LatLng(113.170435,23.431942);



        PolygonOptions options = new PolygonOptions();
        for (LatLng i : latLngList) {
            options.add(i);
        }
        options.visible(true); //设置区域是否显示
        Polygon polygon = aMap.addPolygon(options);
        boolean contains = polygon.contains(latLng);
        polygon.remove();
        return contains;
    }

}
