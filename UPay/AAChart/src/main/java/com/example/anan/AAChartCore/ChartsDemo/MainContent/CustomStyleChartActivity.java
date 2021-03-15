package com.example.anan.AAChartCore.ChartsDemo.MainContent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartAlignType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartFontWeightType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartStackingType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartSymbolStyleType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartSymbolType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartAnimationType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartVerticalAlignType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartZoomType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AADataElement;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AADataLabels;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAHalo;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAHover;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAMarker;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAMarkerHover;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAMarkerStates;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AASelect;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAShadow;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAStates;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAStyle;
import com.example.anan.AAChartCore.AAChartCoreLib.AATools.AAColor;
import com.example.anan.AAChartCore.AAChartCoreLib.AATools.AAGradientColor;
import com.example.anan.AAChartCore.AAChartCoreLib.AATools.AALinearGradientDirection;
import com.example.anan.AAChartCore.R;

import java.util.HashMap;
import java.util.Map;

public class CustomStyleChartActivity extends AppCompatActivity {

    private AAChartModel aaChartModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_style_chart);

        Intent intent = getIntent();
        String chartType = intent.getStringExtra("chartType");

        aaChartModel = configureTheAAChartModel(chartType);

        AAChartView aaChartView = findViewById(R.id.AAChartView);
        aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }

    private AAChartModel configureTheAAChartModel(String chartType) {
        switch (chartType) {
            case "colorfulChart":
                return configureColorfulChart();
            case "gradientColorfulChart":
                return configureColorfulGradientColorChart();
            case "discontinuousDataChart":
                return configureDiscontinuousDataChart();
            case "colorfulColumnChart":
                return configureColorfulColumnChart();
            case "nightingaleRoseChart":
                return configureNightingaleRoseChart();
            case "chartWithShadowStyle":
                return configureChartWithShadowStyle();
            case "colorfulGradientAreaChart":
                return configureColorfulGradientAreaChart();
            case "colorfulGradientSplineChart":
                return configureColorfulGradientSplineChart();
            case "gradientColorAreasplineChart":
                return configureGradientColorAreasplineChart();
            case "SpecialStyleMarkerOfSingleDataElementChart":
                return configureSpecialStyleMarkerOfSingleDataElementChart();
            case "SpecialStyleColumnOfSingleDataElementChart":
                return configureSpecialStyleColumnOfSingleDataElementChart();
            case "AreaChartThreshold":
                return configureAreaChartThreshold();
            case "customScatterChartMarkerSymbolContent":
                return customScatterChartMarkerSymbolContent();
            case "customLineChartMarkerSymbolContent":
                return customLineChartMarkerSymbolContent();
            case "TriangleRadarChart":
                return configureTriangleRadarChart();
            case "QuadrangleRadarChart":
                return configureQuadrangleRadarChart();
            case "PentagonRadarChart":
                return configurePentagonRadarChart();
            case "HexagonRadarChart":
                return configureHexagonRadarChart();

            case "adjustYAxisMaxAndMinValues": return adjustYAxisMaxAndMinValues();
            case "customSpecialStyleDataLabelOfSingleDataElementChart": return customSpecialStyleDataLabelOfSingleDataElementChart();
            case "customBarChartHoverColorAndSelectColor": return customBarChartHoverColorAndSelectColor();
            case "customChartHoverAndSelectHaloStyle": return customChartHoverAndSelectHaloStyle();
            case "customSplineChartMarkerStatesHoverStyle": return customSplineChartMarkerStatesHoverStyle();
            case "customNormalStackingChartDataLabelsContentAndStyle": return customNormalStackingChartDataLabelsContentAndStyle();
            case "upsideDownPyramidChart": return upsideDownPyramidChart();
            case "doubleLayerPieChart": return doubleLayerPieChart();
            case "disableSomeOfLinesMouseTrackingEffect": return disableSomeOfLinesMouseTrackingEffect();
            case "configureColorfulShadowSplineChart": return configureColorfulShadowSplineChart();
            case "configureColorfulDataLabelsStepLineChart": return configureColorfulDataLabelsStepLineChart();
            case "configureColorfulGradientColorAndColorfulDataLabelsStepAreaChart": return configureColorfulGradientColorAndColorfulDataLabelsStepAreaChart();
            case "disableSplineChartMarkerHoverEffect": return disableSplineChartMarkerHoverEffect();
//            case 38: return topRoundedCornersStackingColumnChart();
        }
        return configureColorfulChart();
    }

    AAChartModel configureColorfulChart() {
        String[] colorsNameArr = {
                "red",
                "orange",
                "yellow",
                "green",
                "cyan",
                "blue",
                "purple",
                "gray",
                "darkGray",
                "lightGray",
                "magenta",
                "brown",
                "black"};

        String[] colorsArr = {
                AAColor.Red,
                AAColor.Orange,
                AAColor.Yellow,
                AAColor.Green,
                AAColor.Cyan,
                AAColor.Blue,
                AAColor.Purple,
                AAColor.Gray,
                AAColor.DarkGray,
                AAColor.LightGray,
                AAColor.Magenta,
                AAColor.Brown,
                AAColor.Black,
        };

        return new AAChartModel()
                .chartType(AAChartType.Bar)
                .animationType(AAChartAnimationType.Bounce)
                .title("Colorful Chart")
                .subtitle("use AAColor to get color string")
                .dataLabelsEnabled(false)
                .categories(colorsNameArr)
                .colorsTheme(colorsArr)
                .stacking(AAChartStackingType.Percent)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo")
                                .data(new Object[]{
                                        149.9, 171.5, 106.4, 129.2, 144.0, 176.0, 135.6, 188.5, 276.4, 214.1, 95.6, 54.4})
                                .colorByPoint(true)
                });

    }

    AAChartModel configureColorfulGradientColorChart() {
        String[] gradientColorNamesArr = {
                "oceanBlue",
                "sanguine",
                "lusciousLime",
                "purpleLake",
                "freshPapaya",
                "ultramarine",
                "pinkSugar",
                "lemonDrizzle",
                "victoriaPurple",
                "springGreens",
                "mysticMauve",
                "reflexSilver",
                "newLeaf",
                "cottonCandy",
                "pixieDust",
                "fizzyPeach",
                "sweetDream",
                "firebrick",
                "wroughtIron",
                "deepSea",
                "coastalBreeze",
                "eveningDelight",
                "neonGlowColor",
                "berrySmoothieColor"
        };

        Map[] gradientColorArr = {
                AAGradientColor.OceanBlue,
                AAGradientColor.Sanguine,
                AAGradientColor.LusciousLime,
                AAGradientColor.PurpleLake,
                AAGradientColor.FreshPapaya,
                AAGradientColor.Ultramarine,
                AAGradientColor.PinkSugar,
                AAGradientColor.LemonDrizzle,
                AAGradientColor.VictoriaPurple,
                AAGradientColor.SpringGreens,
                AAGradientColor.MysticMauve,
                AAGradientColor.ReflexSilver,
                AAGradientColor.NewLeaf,
                AAGradientColor.CottonCandy,
                AAGradientColor.PixieDust,
                AAGradientColor.FizzyPeach,
                AAGradientColor.SweetDream,
                AAGradientColor.Firebrick,
                AAGradientColor.WroughtIron,
                AAGradientColor.DeepSea,
                AAGradientColor.CoastalBreeze,
                AAGradientColor.EveningDelight,
                AAGradientColor.NeonGlow,
                AAGradientColor.BerrySmoothie
        };

        return new AAChartModel()
                .chartType(AAChartType.Bar)
                .title("Colorful Column Chart")
                .subtitle("single data array colorful column chart")
                .categories(gradientColorNamesArr)
                .colorsTheme(gradientColorArr)
                .yAxisTitle("gradient color")
                .stacking(AAChartStackingType.Percent)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo")
                                .data(new Object[]{
                                        149.9, 171.5, 106.4, 129.2, 144.0, 176.0, 135.6, 188.5, 276.4, 214.1, 95.6, 54.4,
                                        149.9, 171.5, 106.4, 129.2, 144.0, 176.0, 135.6, 188.5, 276.4, 214.1, 95.6, 54.4})
                                .colorByPoint(true)
                });

    }

    AAChartModel configureDiscontinuousDataChart() {
        return new AAChartModel()
                .chartType(AAChartType.Column)
                .title("Discontinuous Data Chart")
                .yAxisTitle("")
                .dataLabelsEnabled(true)
                .tooltipEnabled(true)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo")
                                .data(new Object[]{6.9, 9.5, 14.5, 18.2, 21.5, null, null, null, null, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6})
                                .color(AAGradientColor.DeepSea)

                });

    }

    AAChartModel configureColorfulColumnChart() {
        return new AAChartModel()
                .chartType(AAChartType.Column)
                .title("Colorful Column Chart")
                .subtitle("single data array colorful column chart")
                .colorsTheme(new String[]{"#febc0f", "#FF14d4", "#0bf8f5", "#F33c52", "#1904dd",})
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("东京")
                                .data(new Object[]{149.9, 171.5, 106.4, 129.2, 144.0, 176.0, 135.6, 188.5, 276.4, 214.1, 95.6, 54.4})
                                .colorByPoint(true)
                });
    }

    AAChartModel configureNightingaleRoseChart() {
        return new AAChartModel()
                .title("南丁格尔玫瑰图")
                .subtitle("极地图中的一种")
                .yAxisTitle("cm")
                .chartType(AAChartType.Column)
                .xAxisVisible(false)//是否显示最外一层圆环
                .yAxisVisible(true)//是否显示中间的多个圆环
                .legendEnabled(false)//隐藏图例(底部可点按的小圆点)
                .categories(new String[]{"一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"})
                .dataLabelsEnabled(true)
                .polar(true)//极地化图形
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("东京")
                                .data(new Object[]{7.0, 6.9, 9.5, 9.6, 13.9, 14.5, 18.3, 18.2, 21.5, 25.2, 26.5, 23.3}),
                        }
                )
                ;
    }

    AAChartModel configureChartWithShadowStyle() {
        return new AAChartModel()
                .chartType(AAChartType.Spline)
                .title("")
                .subtitle("")
                .categories(new String[]{"一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"})
                .yAxisTitle("")
                .markerRadius(0f)
                .yAxisLineWidth(0f)
                .yAxisGridLineWidth(0f)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo Hot")
                                .lineWidth(8.0f)
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, 21.5, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6})
                                .shadow(
                                new AAShadow()
                                        .offsetX(15.0f)
                                        .offsetY(15.0f)
                                        .opacity(0.2f)
                                        .width(8.0f)
                                        .color(AAColor.Red))
                });
    }

    AAChartModel configureColorfulGradientAreaChart() {
        Object[][] stopsArr = {
                {0.00, "#febc0f"},
                {0.50, "#FF14d4"},
                {1.00, "#0bf8f5"},
        };//颜色字符串设置支持十六进制类型和 rgba 类型

        Map linearGradientColor = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToLeft,
                stopsArr
        );//颜色字符串设置支持十六进制类型和 rgba 类型

        return new AAChartModel()
                .chartType(AAChartType.Areaspline)
                .title("")
                .subtitle("")
                .categories(new String[]{"一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"})
                .yAxisTitle("")
                .markerRadius(0f)
                .yAxisLineWidth(0f)
                .yAxisGridLineWidth(0f)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo Hot")
                                .lineWidth(3f)
                                .color(linearGradientColor)
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, 21.5, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6}),
                        }
                );
    }


    AAChartModel configureColorfulGradientSplineChart() {
        Object[][] stopsArr = {
                {0.00, "#febc0f"},
                {0.25, "#FF14d4"},
                {0.50, "#0bf8f5"},
                {0.75, "#F33c52"},
                {1.00, "#1904dd"},
        };//颜色字符串设置支持十六进制类型和 rgba 类型

        Map linearGradientColor = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToLeft,
                stopsArr
        );//颜色字符串设置支持十六进制类型和 rgba 类型

        return new AAChartModel()
                .chartType(AAChartType.Spline)
                .title("")
                .subtitle("")
                .categories(new String[]{"一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"})
                .yAxisTitle("")
                .markerRadius(0f)
                .yAxisLineWidth(0f)
                .yAxisGridLineWidth(0f)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo Hot")
                                .lineWidth(15.0f)
                                .color(linearGradientColor)
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, 21.5, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6}),
                        }
                );
    }


    AAChartModel configureGradientColorAreasplineChart() {
        Map linearGradientColor = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                "rgba(2255,20,147,1)",//深粉色, alpha 透明度 1
                "rgba(255,105,180,0.1)"//热情的粉红, alpha 透明度 0.1
        );//颜色字符串设置支持十六进制类型和 rgba 类型

        return new AAChartModel()
                .chartType(AAChartType.Areaspline)
                .title("")
                .subtitle("")
                .categories(new String[]{"一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"})
                .yAxisTitle("")
                .markerRadius(8f)//marker点半径为8个像素
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)//marker点为空心效果
                .markerSymbol(AAChartSymbolType.Circle)//marker点为圆形点○
                .yAxisLineWidth(0f)
                .yAxisGridLineWidth(0f)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo Hot")
                                .lineWidth(5.0f)
                                .color(AAColor.rgbaColor(220, 20, 60, 1f))//猩红色, alpha 透明度 1
                                .fillColor(linearGradientColor)
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, 21.5, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6}),
                        }
                );
    }


    AAChartModel configureSpecialStyleMarkerOfSingleDataElementChart() {
        Object[][] stopsArr = {
                {0.00, "#febc0f"},
                {0.25, "#FF14d4"},
                {0.50, "#0bf8f5"},
                {0.75, "#F33c52"},
                {1.00, "#1904dd"},
        };//颜色字符串设置支持十六进制类型和 rgba 类型

        Map gradientColorDic1 = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToRight,
                stopsArr
        );

        AADataElement singleSpecialData = new AADataElement()
                .marker(new AAMarker()
                        .radius(8f)//曲线连接点半径
                        .symbol(AAChartSymbolType.Circle)//曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                        .fillColor("#FFFFFF")//点的填充色(用来设置折线连接点的填充色)
                        .lineWidth(5f)//外沿线的宽度(用来设置折线连接点的轮廓描边的宽度)
                        .lineColor("#FF0000")//外沿线的颜色(用来设置折线连接点的轮廓描边颜色，当值为空字符串时，默认取数据点或数据列的颜色)
                )
                .y(26.5f);

        return new AAChartModel()
                .chartType(AAChartType.Spline)
                .title("")
                .backgroundColor("#4b2b7f")
                .yAxisTitle("")//设置Y轴标题
                .dataLabelsEnabled(false)//是否显示值
                .tooltipEnabled(true)
                .markerRadius(0f)
                .xAxisVisible(false)
                .yAxisVisible(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Virtual Data")
                                .lineWidth(6f)
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, singleSpecialData, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6})
                                .color(gradientColorDic1)
                });
    }

    AAChartModel configureSpecialStyleColumnOfSingleDataElementChart() {
        AADataElement singleSpecialData = new AADataElement()
                .color(AAGradientColor.FreshPapaya)
                .y(49.5f);

        return new AAChartModel()
                .chartType(AAChartType.Column)
                .title("")
                .yAxisTitle("")//设置Y轴标题
                .dataLabelsEnabled(false)//是否显示值
                .tooltipEnabled(false)
                .markerRadius(0f)
                .xAxisVisible(false)
                .yAxisVisible(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Virtual Data")
                                .lineWidth(6f)
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, singleSpecialData, 5.2, 26.5, 23.3, 26.5, 13.9, 9.6})
                                .color(AAGradientColor.OceanBlue)
                });
    }

    AAChartModel configureAreaChartThreshold() {
        Map gradientColorDic1 = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                "rgba(30, 144, 255,1)",//DodgerBlue, alpha 透明度 1
                "rgba(30, 144, 255,0.1)"//DodgerBlue, alpha 透明度 0.1
        );

        return new AAChartModel()
                .chartType(AAChartType.Area)
                .title("")
                .yAxisTitle("")//设置Y轴标题
                .yAxisGridLineWidth(0f)
                .categories(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Spe", "Oct", "Nov", "Dec"})
                .dataLabelsEnabled(false)//是否显示值
                .markerRadius(8f)
                .markerSymbol(AAChartSymbolType.Circle)
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .threshold((-200f))
                                .data(new Object[]{106.4, 129.2, 269.9, -100.5, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4})
                                .lineWidth(6f)
                                .color("rgba(30, 144, 255,1)")
                                .fillColor(gradientColorDic1)
                });
    }

    //refer to online sample https://jshare.com.cn/github/highcharts/highcharts/tree/master/samples/highcharts/plotoptions/series-marker-symbol/
    AAChartModel customScatterChartMarkerSymbolContent() {
        String predefinedSymbol1 = AAChartSymbolType.Triangle;
        String predefinedSymbol2 = AAChartSymbolType.Circle;
        String imageSymbol = "url(https://www.highcharts.com/samples/graphics/sun.png)";
        String base64Symbol = "url(data:image/png;base64,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 1dT1gvWd+ 1YfqGnRs+FYmKrhTbF5cVf9go3HjlG4dvyr+Z3JS0qavEuWTPZtJm6ebeLZ5bDpaql+aXDm4N2dq0Dd9WtO319kXbL5fNKNu7g7ZDuaO/PLi8ZafJzs07P1SkVPRU+lQ27tLdtWHX+G7R7ht7vPY07NXbW7z3/T7JvttVAVVN1WbVZftJ+7P3P66Jqun4lvttXa1ObXHtxwPSA/0HIw6217nU1R3SPVRSj9Yr60cOxx+ 1/p3vdy0NNg1VjZzG4iNwRHnk6fcJ3/ceDTradox7rOEH0x92HWcdL2pCmvKaRptTmvtbYlu6T8w+0dbq3nr8R9sfD5w0PFl5SvNUyWna6YLTk2fyz4ydlZ19fi753GDborZ752PO32oPb+ 16EHTh0kX/i +c7vDvOXPK4dPKy2+UTV7hXmq86X23qdOo8/pPTT8e7nLuarrlca7nuer21e2b36RueN87d9L158Rb/1tWeOT3dvfN6b/fF9/XfFt1+cif9zsu72Xcn7q28T7xf9EDtQdlD3YfVP1v+3Njv3H9qwHeg89HcR/cGhYPP/pH1jw9DBY+Zj8uGDYbrnjg+OTniP3L96fynQ89kzyaeF/6i/suuFxYvfvjV69fO0ZjRoZfyl5O/bXyl/erA6xmv28bCxh6+yXgzMV70VvvtwXfcdx3vo98PT+R8IH8o/2j5sfVT0Kf7kxmTk/8EA5jz/GMzLdsAAAAgY0hSTQAAeiUAAICDAAD5/wAAgOkAAHUwAADqYAAAOpgAABdvkl/FRgAAAVVJREFUeNpi/P37NwOxYM2pHtm7lw8uYmBgYGAiVtPC3RWh+88vuneT474Dv4DkcUZibJy8PG72le/nkn+zMzAaMhnNyY1clMpCjKbz/86lMLAzMMA0MTAwMOC1Ea6JgYFB9pPwncbMg6owOaY1p3pk15zqkcWnie8j63ddY18nZHmWI2eW3vzN/Jf168c3UfGuHathAXHl+7lkBnYGBtafDP8NVd3jQ8xKHiNrZMyeqPPtE/9vTgYGBgb1H4oHlHXt43ZfWfDwNzsDIwMDA4POX831RXGrg9BdxLhob63VgTurjsAUsv5k+A9jC3/g/NCdfVoQm/+ZIu3qjhnyW3XABJANMNL19cYVcPBQrZpq9eyFwCdJmIT6D8UD5cmbHXFphKccI9Mgc84vTH9goYhPE4rGELOSx0bSjsUMDAwMunJ2FQST0+/fv1Hw5BWJbehi2DBgAHTKsWmiz+rJAAAAAElFTkSuQmCC)";

        AASeriesElement element1 = new AASeriesElement()
                .name("Predefined symbol")
                .data(new Object[]{0.45, 0.43, 0.50, 0.55, 0.58, 0.62, 0.83, 0.39, 0.56, 0.67, 0.50, 0.34, 0.50, 0.67, 0.58, 0.29, 0.46, 0.23, 0.47, 0.46, 0.38, 0.56, 0.48, 0.36})
                .marker(new AAMarker()
                        .symbol(predefinedSymbol1));

        AASeriesElement element2 = new AASeriesElement()
                .name("Image symbol")
                .data(new Object[]{0.38, 0.31, 0.32, 0.32, 0.64, 0.66, 0.86, 0.47, 0.52, 0.75, 0.52, 0.56, 0.54, 0.60, 0.46, 0.63, 0.54, 0.51, 0.58, 0.64, 0.60, 0.45, 0.36, 0.67})
                .marker(new AAMarker()
                        .symbol(imageSymbol));

        AASeriesElement element3 = new AASeriesElement()
                .name("Base64 symbol (*)")
                .data(new Object[]{0.46, 0.32, 0.53, 0.58, 0.86, 0.68, 0.85, 0.73, 0.69, 0.71, 0.91, 0.74, 0.60, 0.50, 0.39, 0.67, 0.55, 0.49, 0.65, 0.45, 0.64, 0.47, 0.63, 0.64})
                .marker(new AAMarker()
                        .symbol(base64Symbol));

        AASeriesElement element4 = new AASeriesElement()
                .name("Custom symbol")
                .data(new Object[]{0.60, 0.51, 0.52, 0.53, 0.64, 0.84, 0.65, 0.68, 0.63, 0.47, 0.72, 0.60, 0.65, 0.74, 0.66, 0.65, 0.71, 0.59, 0.65, 0.77, 0.52, 0.53, 0.58, 0.53})
                .marker(new AAMarker()
                        .symbol(predefinedSymbol2));

        return new AAChartModel()
                .chartType(AAChartType.Scatter)
                .title("")
                .subtitle("")
                .yAxisTitle("")
                .yAxisMax(3.5f)
                .yAxisGridLineWidth(0f)
                .stacking(AAChartStackingType.Normal)
                .markerRadius(8f)
                .series(new AASeriesElement[]{element1, element2, element3, element4});
    }

    //refer to online sample https://jshare.com.cn/github/highcharts/highcharts/tree/master/samples/highcharts/plotoptions/series-marker-symbol/
    AAChartModel customLineChartMarkerSymbolContent() {
        AAChartModel aaChartModel = customScatterChartMarkerSymbolContent();
        aaChartModel.chartType = AAChartType.Line;
        return aaChartModel;
    }

    //三角形雷达图
    AAChartModel configureTriangleRadarChart() {
        return new AAChartModel()
                .chartType(AAChartType.Area)
                .title("")
                .subtitle("")
                .yAxisTitle("")
                .yAxisMax(15.0f)
                .yAxisGridLineWidth(0f)
                .xAxisVisible(false)
                .markerRadius(0f)
                .polar(true)//是否极化图形
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .data(new Object[]{15.0, 15.0, 15.0,}),
                        new AASeriesElement()
                                .data(new Object[]{9.0, 9.0, 9.0,}),
                        new AASeriesElement()
                                .data(new Object[]{6.0, 6.0, 6.0,}),
                        new AASeriesElement()
                                .data(new Object[]{3.0, 3.0, 3.0,}),
                });
    }

    //四边形雷达图
    AAChartModel configureQuadrangleRadarChart() {
        return new AAChartModel()
                .chartType(AAChartType.Area)
                .title("")
                .subtitle("")
                .yAxisTitle("")
                .yAxisMax(15.0f)
                .yAxisGridLineWidth(0f)
                .xAxisVisible(false)
                .markerRadius(0f)
                .polar(true)//是否极化图形
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .data(new Object[]{15.0, 15.0, 15.0, 15.0,}),
                        new AASeriesElement()
                                .data(new Object[]{9.0, 9.0, 9.0, 9.0,}),
                        new AASeriesElement()
                                .data(new Object[]{6.0, 6.0, 6.0, 6.0,}),
                        new AASeriesElement()
                                .data(new Object[]{3.0, 3.0, 3.0, 3.0,}),
                });
    }

    //五边形雷达图
    AAChartModel configurePentagonRadarChart() {
        return new AAChartModel()
                .chartType(AAChartType.Area)
                .title("")
                .subtitle("")
                .yAxisTitle("")
                .yAxisMax(15.0f)
                .yAxisGridLineWidth(0f)
                .xAxisVisible(false)
                .markerRadius(0f)
                .polar(true)//是否极化图形
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .data(new Object[]{15.0, 15.0, 15.0, 15.0, 15.0,}),
                        new AASeriesElement()
                                .data(new Object[]{9.0, 9.0, 9.0, 9.0, 9.0,}),
                        new AASeriesElement()
                                .data(new Object[]{6.0, 6.0, 6.0, 6.0, 6.0,}),
                        new AASeriesElement()
                                .data(new Object[]{3.0, 3.0, 3.0, 3.0, 3.0,}),
                });
    }

    //六边形雷达图
    AAChartModel configureHexagonRadarChart() {
        return new AAChartModel()
                .chartType(AAChartType.Area)
                .title("")
                .subtitle("")
                .yAxisTitle("")
                .yAxisMax(15.0f)
                .yAxisGridLineWidth(0f)
                .xAxisVisible(false)
                .markerRadius(0f)
                .polar(true)//是否极化图形
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .data(new Object[]{15.0, 15.0, 15.0, 15.0, 15.0, 15.0,}),
                        new AASeriesElement()
                                .data(new Object[]{9.0, 9.0, 9.0, 9.0, 9.0, 9.0,}),
                        new AASeriesElement()
                                .data(new Object[]{6.0, 6.0, 6.0, 6.0, 6.0, 6.0,}),
                        new AASeriesElement()
                                .data(new Object[]{3.0, 3.0, 3.0, 3.0, 3.0, 3.0,}),
                });
    }

    AAChartModel adjustYAxisMaxAndMinValues() {
        String[] categoriesArr = {
                "孤岛危机",
                "使命召唤",
                "荣誉勋章",
                "狙击精英",
                "神秘海域",
                "最后生还者",
                "巫师3狂猎",
                "对马之魂",
                "蝙蝠侠阿甘骑士",
                "地狱边境",
                "闪客",
                "忍者之印",
        };

        return new AAChartModel()
                .chartType(AAChartType.Areaspline)
                .legendEnabled(false)
                .yAxisVisible(true)
                .markerRadius(6f)
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)
                .zoomType(AAChartZoomType.XY)
                .categories(categoriesArr)
                .yAxisMin(2.0f)
                .yAxisMax(45.4f)
                .xAxisTickInterval(2)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("体重")
                                .color("#2494F3")
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, 21.5, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6})
                })
        ;
}

    AAChartModel customSpecialStyleDataLabelOfSingleDataElementChart() {
        Object[][] redStopsArr = new Object[][]{
                new Object[]{0.0, AAColor.rgbaColor(255, 0, 0, 0.6f)},//颜色字符串设置支持十六进制类型和 rgba 类型
                new Object[]{0.5, AAColor.rgbaColor(255, 0, 0, 0.0f)},
                new Object[]{1.0, AAColor.rgbaColor(255, 0, 0, 0.0f)}
        };

        Map gradientColorDic1 = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToTop,
                AAColor.rgbaColor(255, 215, 0, 0.1f),
                AAColor.rgbaColor(255, 215, 0, 0.6f)
        );

        String formatStr =
                "<img src=\"https://www.highcharts.com/samples/graphics/sun.png\">" +
                "<span style=\"color:#FFFFFF;font-weight:thin;font-size:25px\">{y}</span>" +
                "<span style=\"color:#FFFFFF;font-weight:thin;font-size:17px\"> m</span>";


        AADataElement  singleSpecialData = new AADataElement()
                .dataLabels(new AADataLabels()
                        .enabled(true)
                        .useHTML(true)
                        .format(formatStr)
                        .style(new AAStyle()
                                .fontWeight(AAChartFontWeightType.Bold)
                                .color(AAColor.White)
                                .fontSize(16f))
                        .y((-35f))
                        .align(AAChartAlignType.Center)
                        .verticalAlign(AAChartVerticalAlignType.Top)
                        .overflow("none")
                        .crop(false)
                )
                .y(26.5f);

        return new AAChartModel()
                .chartType(AAChartType.Areaspline)
                .backgroundColor("#4b2b7f")
                .dataLabelsEnabled(false)//是否显示值
                .tooltipEnabled(true)
                .markerRadius(0f)
                .xAxisVisible(false)
                .yAxisVisible(false)
                .series(new AASeriesElement[]{
                       new AASeriesElement()
                                .name("Virtual Data")
                                .lineWidth(6f)
                                .color("rgba(255,215,0,1)")
                                .fillColor(gradientColorDic1)// gold color, alpha: 1.0
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, singleSpecialData, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6})
                });
}

    AAChartModel customBarChartHoverColorAndSelectColor() {
        return new AAChartModel()
                .chartType(AAChartType.Bar)
                .title("Custom Bar Chart select color")
                .yAxisReversed(true)
                .xAxisReversed(true)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("ElementOne")
                                .data(new Object[]{211, 183, 157, 133, 111, 91, 73, 57, 43, 31, 21, 13, 7, 3})
                                .allowPointSelect(true)
                                .states(
                                new AAStates()
                                        .hover(new AAHover()
                                                .color("rgba(220,20,60,1)"))//猩红色, alpha 透明度 1
                                        .select(new AASelect()
                                                .color(AAColor.Red))
                        )
                })
                ;

    }

    AAChartModel customChartHoverAndSelectHaloStyle() {
        Map hoverHaloAttributes = new HashMap<String, Object>();
        hoverHaloAttributes.put("stroke-width", 50);
        hoverHaloAttributes.put("fill", "#00BFFF");
        hoverHaloAttributes.put("stroke", "#00FA9A");

        Map selectHaloAttributes = new HashMap<String, Object>();
        selectHaloAttributes.put("stroke-width", 150);
        selectHaloAttributes.put("fill", AAColor.rgbaColor(138, 43, 226, 1f));
        selectHaloAttributes.put("stroke", AAColor.rgbaColor(30, 144, 255, 1f));

        return new AAChartModel()
                .chartType(AAChartType.Line)
                .title("Custom Chart Hover And Select Halo Style")
                .colorsTheme(new Object[]{AAColor.Red})
                .yAxisReversed(true)
                .xAxisReversed(true)
                .markerRadius(20f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("ElementOne")
                                .data(new Object[]{211, 183, 157, 133, 111, 91, 73, 57, 43, 31, 21, 13, 7, 3})
                                .allowPointSelect(true)
                                .states(
                                new AAStates()
                                        .hover(new AAHover()
                                                .halo(new AAHalo()
                                                        .size(130f)
                                                        .opacity(0.8f)
                                                        .attributes(hoverHaloAttributes)))
                                        .select(new AASelect()
                                                .halo(new AAHalo()
                                                        .size(130f)
                                                        .opacity(1.0f)
                                                        .attributes(selectHaloAttributes))))
                });
    }

    AAChartModel customSplineChartMarkerStatesHoverStyle() {
        return new AAChartModel()
                .chartType(AAChartType.Areaspline)
                .title("Custom Spline Chart Marker States Hover Style")
                .categories(new String[]{"一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"})
                .markerRadius(8.0f)//marker点半径为8个像素
                .yAxisLineWidth(0f)
                .yAxisGridLineWidth(0f)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                       new AASeriesElement()
                                .name("Tokyo Hot")
                                .lineWidth(5.0f)
                                .color("rgba(220,20,60,1)")//猩红色, alpha 透明度 1
                                .marker(new AAMarker()
                                        .states(new AAMarkerStates()
                                                .hover(new AAMarkerHover()
                                                        .fillColor(AAColor.White)
                                                        .radius(40f)
                                                        .lineColor(AAColor.Green)
                                                        .lineWidth(20f))))
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, 21.5, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6}),
                });
    }


    //Issue: https://github.com/AAChartModel/AAChartKit/issues/827
    AAChartModel customNormalStackingChartDataLabelsContentAndStyle() {
        String[] categories = {
                "孤岛危机",
                "使命召唤",
                "荣誉勋章",
                "狙击精英",
                "神秘海域",
                "最后生还者",
                "巫师3狂猎",
                "对马之魂",
                "死亡搁浅",
                "地狱边境",
                "闪客",
                "忍者之印"
        };

        String[] colorsTheme = {
                "#fe117c",
                "#ffc069",
                "#06caf4",
                "#7dffc0"
        };

        AASeriesElement element1 = new AASeriesElement()
                .name("2017")
                .dataLabels(new AADataLabels()
                        .y(-10f)
                        .format("{total} mm")
                        .color(AAColor.Red)
                        .shape("callout")
                        .backgroundColor(AAColor.White)
                        .borderColor(AAColor.Red)
                        .borderRadius(1f)
                        .borderWidth(1f)
                )
                .data(new Object[]{7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6});

        AASeriesElement element2 = new AASeriesElement()
                .name("2018")
                .data(new Object[]{0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5});

        AASeriesElement element3 = new AASeriesElement()
                .name("2019")
                .data(new Object[]{0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0});

        AASeriesElement element4 = new AASeriesElement()
                .name("2020")
                .data(new Object[]{3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8});

        AASeriesElement[] series = {element1, element2, element3, element4};

        return new AAChartModel()
                .chartType(AAChartType.Column)
                .stacking(AAChartStackingType.Normal)
                .yAxisGridLineWidth(0f)
                .markerRadius(0f)
                .categories(categories)
                .colorsTheme(colorsTheme)
                .series(series);
}

    //Issue: https://github.com/AAChartModel/AAChartKit-Swift/issues/190
//API Doc: https://api.highcharts.com.cn/highcharts#series%3Cpyramid%3E.reversed
    AAChartModel upsideDownPyramidChart() {
        return new AAChartModel()
                .chartType(AAChartType.Pyramid)
                .yAxisTitle("摄氏度")
                .inverted(true)
                .legendEnabled(true)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("2020")
                                .reversed(true)
                                .data(new Object[][]{
                                {"Swift",      15654},
                                {"Objective-C", 4064},
                                {"JavaScript",  1987},
                                {"GO",           976},
                                {"Python",       846}
                        })
                });
}

    //Issue: https://github.com/AAChartModel/AAChartKit/issues/888
    AAChartModel doubleLayerPieChart() {
        return new AAChartModel()
                .chartType(AAChartType.Pie)
                .title("浏览器市场占比历史对比")
                .subtitle("无任何可靠依据的虚拟数据")
                .dataLabelsEnabled(true)//是否直接显示扇形图数据
                .yAxisTitle("摄氏度")
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Past")
                                .size("40%")//尺寸大小
                                .innerSize("30%")//内部圆环半径大小占比
                                .borderWidth(0f)//描边的宽度
                                .allowPointSelect(false)//是否允许在点击数据点标记(扇形图点击选中的块发生位移)
                                .data(new Object[][]{
                                {"Firefox Past",   3336.2},
                                {"Chrome Past",      26.8},
                                {"Safari Past",      88.5},
                                {"Opera Past",       46.0},
                                {"Others Past",     223.0},
                        }),

                        new AASeriesElement()
                                .name("Now")
                                .size("80%")//尺寸大小
                                .innerSize("70%")//内部圆环半径大小占比
                                .borderWidth(0f)//描边的宽度
                                .allowPointSelect(false)//是否允许在点击数据点标记(扇形图点击选中的块发生位移)
                                .data(new Object[][]{
                                {"Firefox Now",    336.2},
                                {"Chrome Now",    6926.8},
                                {"Safari Now",     388.5},
                                {"Opera Now",      446.0},
                                {"Others Now",     223.0},
                        })
                });
    }

    //GitHub issue https://github.com/AAChartModel/AAChartKit/issues/903
    AAChartModel disableSomeOfLinesMouseTrackingEffect() {
        return new AAChartModel()
                .chartType(AAChartType.Line)//图表类型
                .tooltipValueSuffix("万元")//设置浮动提示框单位后缀
                .yAxisTitle("万元")//设置 Y 轴标题
                .categories(new String[]{
                        "一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"
                })
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("2017")
                                .data(new Object[]{7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6}),
                        new AASeriesElement()
                                .name("2018")
                                .enableMouseTracking(false)
                                .data(new Object[]{0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5}),
                        new AASeriesElement()
                                .name("2019")
                                .enableMouseTracking(false)
                                .data(new Object[]{0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0}),
                        new AASeriesElement()
                                .name("2020")
                                .enableMouseTracking(false)
                                .data(new Object[]{3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8}),
                });
    }


    // GitHub issue https://github.com/AAChartModel/AAChartKit/issues/904
    AAChartModel configureColorfulShadowSplineChart() {
        return new AAChartModel()
                .chartType(AAChartType.Spline)
                .yAxisVisible(false)
                .stacking(AAChartStackingType.Normal)
                .colorsTheme(new String[]{"#1e90ff", "#ef476f", "#ffd066", "#04d69f"})
                .markerSymbol(AAChartSymbolType.Circle)
                .markerRadius(8.0f)
                .markerSymbolStyle(AAChartSymbolStyleType.BorderBlank)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("2017")
                                .lineWidth(5f)
                                .data(new Object[]{0.45, 0.43, 0.50, 0.55, 0.58, 0.62, 0.83, 0.39, 0.56, 0.67, 0.50, 0.34, 0.50, 0.67, 0.58, 0.29, 0.46, 0.23, 0.47, 0.46, 0.38, 0.56, 0.48, 0.36})
                                .shadow(
                                new AAShadow()
                                        .offsetX(15.0f)
                                        .offsetY(15.0f)
                                        .opacity(0.2f)
                                        .width(8.0f)
                                        .color("#1e90ff")),
                        new AASeriesElement()
                                .name("2018")
                                .lineWidth(5f)
                                .data(new Object[]{0.38, 0.31, 0.32, 0.32, 0.64, 0.66, 0.86, 0.47, 0.52, 0.75, 0.52, 0.56, 0.54, 0.60, 0.46, 0.63, 0.54, 0.51, 0.58, 0.64, 0.60, 0.45, 0.36, 0.67})
                                .shadow(
                                new AAShadow()
                                        .offsetX(15.0f)
                                        .offsetY(15.0f)
                                        .opacity(0.2f)
                                        .width(8.0f)
                                        .color("#ef476f")),
                        new AASeriesElement()
                                .name("2019")
                                .lineWidth(5f)
                                .data(new Object[]{0.46, 0.32, 0.53, 0.58, 0.86, 0.68, 0.85, 0.73, 0.69, 0.71, 0.91, 0.74, 0.60, 0.50, 0.39, 0.67, 0.55, 0.49, 0.65, 0.45, 0.64, 0.47, 0.63, 0.64})
                                .shadow(
                                new AAShadow()
                                        .offsetX(15.0f)
                                        .offsetY(15.0f)
                                        .opacity(0.2f)
                                        .width(8.0f)
                                        .color("#ffd066")),
                        new AASeriesElement()
                                .name("2020")
                                .lineWidth(5f)
                                .data(new Object[]{0.60, 0.51, 0.52, 0.53, 0.64, 0.84, 0.65, 0.68, 0.63, 0.47, 0.72, 0.60, 0.65, 0.74, 0.66, 0.65, 0.71, 0.59, 0.65, 0.77, 0.52, 0.53, 0.58, 0.53})
                                .shadow(
                                new AAShadow()
                                        .offsetX(15.0f)
                                        .offsetY(15.0f)
                                        .opacity(0.2f)
                                        .width(8.0f)
                                        .color("#04d69f")),
                });
    }

    // GitHub issue https://github.com/AAChartModel/AAChartKit/issues/905
    AAChartModel configureColorfulDataLabelsStepLineChart() {
        return new AAChartModel()
                .chartType(AAChartType.Line)
                .yAxisVisible(false)
                .stacking(AAChartStackingType.Normal)
                .colorsTheme(new String[]{"#1e90ff","#ef476f","#ffd066","#04d69f"})
                .markerSymbol(AAChartSymbolType.Circle)
                .markerRadius(8.0f)
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("2017")
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(new AAStyle()
                                                .color("#1e90ff")
                                                .fontSize(11f)))
                                .data(new Object[]{2.10, 2.54, 2.78, 3.62, 4.41, 4.09, 3.83, 4.47, 4.20, 3.94, 3.80, 3.58, 3.19, 4.30, 3.69, 3.52, 3.02, 3.30}),
                        new AASeriesElement()
                                .name("2018")
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(new AAStyle()
                                                .color("#ef476f")
                                                .fontSize(11f)))
                                .data(new Object[]{1.56, 1.91, 2.45, 3.87, 3.24, 4.90, 4.61, 4.10, 4.17, 3.85, 4.17, 3.46, 3.46, 3.55, 3.50, 4.13, 2.58, 2.28}),
                        new AASeriesElement()
                                .name("2019")
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(new AAStyle()
                                                .color("#ffd066")
                                                .fontSize(11f)))
                                .data(new Object[]{1.16, 1.67, 2.64, 2.86, 3.00, 3.21, 4.14, 4.07, 3.68, 3.11, 3.41, 3.25, 3.32, 3.07, 3.92, 3.05, 2.18, 3.24}),
                        new AASeriesElement()
                                .name("2020")
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(new AAStyle()
                                                .color("#04d69f")
                                                .fontSize(11f)))
                                .data(new Object[]{5.59, 3.09, 4.09, 6.14, 5.33, 6.05, 5.71, 6.22, 6.56, 4.75, 5.27, 6.02, 5.22, 5.77, 6.19, 5.68, 4.33, 5.48}),
                });
    }


    // GitHub issue https://github.com/AAChartModel/AAChartKit-Swift/issues/223
    AAChartModel configureColorfulGradientColorAndColorfulDataLabelsStepAreaChart() {
        Object[][] blueStopsArr = new Object[][]{
                {0.0, AAColor.rgbaColor(30, 144, 255, 1.0f)},//颜色字符串设置支持十六进制类型和 rgba 类型
                {0.5, AAColor.rgbaColor(30, 144, 255, 0.2f)},
                {1.0, AAColor.rgbaColor(30, 144, 255, 0.0f)}
        };
        Map gradientBlueColorDic = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                blueStopsArr
        );

        Object[][] redStopsArr = new Object[][]{
                {0.0, AAColor.rgbaColor(255, 0, 0, 1.0f)},//颜色字符串设置支持十六进制类型和 rgba 类型
                {0.5, AAColor.rgbaColor(255, 0, 0, 0.2f)},
                {1.0, AAColor.rgbaColor(255, 0, 0, 0.0f)}
        };
        Map gradientRedColorDic = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                redStopsArr
        );

        Object[][] goldStopsArr = new Object[][]{
                {0.0, AAColor.rgbaColor(255, 215, 0, 1.0f)},//颜色字符串设置支持十六进制类型和 rgba 类型
                {0.5, AAColor.rgbaColor(255, 215, 0, 0.2f)},
                {1.0, AAColor.rgbaColor(255, 215, 0, 0.0f)}
        };
        Map gradientGoldColorDic = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                goldStopsArr
        );

        Object[][] greenStopsArr = new Object[][]{
                {0.0, AAColor.rgbaColor(50, 205, 50, 1.0f)},//颜色字符串设置支持十六进制类型和 rgba 类型
                {0.5, AAColor.rgbaColor(50, 205, 50, 0.2f)},
                {1.0, AAColor.rgbaColor(50, 205, 50, 0.0f)}
        };
        Map gradientGreenColorDic = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                greenStopsArr
        );

        return new AAChartModel()
                .chartType(AAChartType.Area)
                .yAxisVisible(false)
                .stacking(AAChartStackingType.Normal)
                .colorsTheme(new String[]{"#1e90ff", "#ef476f", "#ffd066", "#04d69f"})
                .markerSymbol(AAChartSymbolType.Circle)
                .markerRadius(5f)
                .dataLabelsEnabled(true)
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("2017")
                                .fillColor(gradientBlueColorDic)
                                .lineWidth(6f)
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(AAStyle.style("#1e90ff",11f)))
                                .data(new Object[]{2.10, 2.54, 2.78, 3.62, 4.41, 4.09, 3.83, 4.47, 4.20, 3.94, 3.80, 3.58, 3.19, 4.30, 3.69, 3.52, 3.02, 3.30}),
                        new AASeriesElement()
                                .name("2018")
                                .fillColor(gradientRedColorDic)
                                .lineWidth(6f)
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(AAStyle.style("#ef476f",11f))
                                )
                                .data(new Object[]{1.56, 1.91, 2.45, 3.87, 3.24, 4.90, 4.61, 4.10, 4.17, 3.85, 4.17, 3.46, 3.46, 3.55, 3.50, 4.13, 2.58, 2.28}),
                        new AASeriesElement()
                                .name("2019")
                                .fillColor(gradientGoldColorDic)
                                .lineWidth(6f)
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(AAStyle.style("#ffd066",11f)))
                                .data(new Object[]{1.16, 1.67, 2.64, 2.86, 3.00, 3.21, 4.14, 4.07, 3.68, 3.11, 3.41, 3.25, 3.32, 3.07, 3.92, 3.05, 2.18, 3.24}),
                        new AASeriesElement()
                                .name("2020")
                                .fillColor(gradientGreenColorDic)
                                .lineWidth(6f)
                                .step((true))
                                .dataLabels(new AADataLabels()
                                        .style(AAStyle.style("#04d69f",11f)))
                                .data(new Object[]{5.59, 3.09, 4.09, 6.14, 5.33, 6.05, 5.71, 6.22, 6.56, 4.75, 5.27, 6.02, 5.22, 5.77, 6.19, 5.68, 4.33, 5.48}),
                });
}

    // Refer to https://api.highcharts.com.cn/highcharts#plotOptions.spline.marker.states.hover.enabled
    AAChartModel disableSplineChartMarkerHoverEffect() {
        return new AAChartModel()
                .chartType(AAChartType.Spline)
                .title("Disable Spline Chart Marker Hover Effect")
                .categories(new String[]{
                        "一月", "二月", "三月", "四月", "五月", "六月",
                        "七月", "八月", "九月", "十月", "十一月", "十二月"})
                .markerRadius(0f)//marker点半径为0个像素
                .yAxisLineWidth(0f)
                .yAxisGridLineWidth(0f)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo Hot")
                                .lineWidth(5.0f)
                                .color("rgba(220,20,60,1)")//猩红色, alpha 透明度 1
                                .marker(new AAMarker()
                                        .states(new AAMarkerStates()
                                                .hover(new AAMarkerHover()
                                                        .enabled(false))))
                                .data(new Object[]{7.0, 6.9, 2.5, 14.5, 18.2, 21.5, 5.2, 26.5, 23.3, 45.3, 13.9, 9.6}),
                });
    }

}


