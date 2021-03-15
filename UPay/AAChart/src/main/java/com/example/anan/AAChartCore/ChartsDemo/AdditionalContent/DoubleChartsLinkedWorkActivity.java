package com.example.anan.AAChartCore.ChartsDemo.AdditionalContent;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAMoveOverEventMessageModel;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAOptionsConstructor;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AADataElement;
import com.example.anan.AAChartCore.AAChartCoreLib.AAOptionsModel.AAOptions;
import com.example.anan.AAChartCore.AAChartCoreLib.AATools.AAGradientColor;
import com.example.anan.AAChartCore.R;

import java.util.Map;

public class DoubleChartsLinkedWorkActivity extends AppCompatActivity implements AAChartView.AAChartViewCallBack {

    private Map selectedGradientColor;
    private AAChartView aaChartView1;
    private AAChartView aaChartView2;
    private Map[] gradientColorsArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_charts_linked_work);

        aaChartView1 = findViewById(R.id.AAChartView1);
        aaChartView1.callBack = this;

        aaChartView2 = findViewById(R.id.AAChartView2);

        aaChartView1.aa_drawChartWithChartOptions(configureChartOptions1());
        aaChartView2.aa_drawChartWithChartOptions(configureChartOptions2());
    }


    private AAOptions configureChartOptions1() {
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
                "neonGlowColor",
                "berrySmoothieColor",
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

        this.gradientColorsArr = gradientColorArr;

        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Column)
                .title("")
                .yAxisTitle("")
                .categories(gradientColorNamesArr)
                .colorsTheme(gradientColorArr)
                .xAxisReversed(true)
                .yAxisReversed(true)
                .inverted(true)
                .legendEnabled(false)
                .touchEventEnabled(true)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo")
                                .data(new Object[]{211,183,157,133,111,91,73,57,43,31,21,13,
                                        211,183,157,133,111,91,73,57,43,31,21,13,})
                                .colorByPoint(true)
                });

        AAOptions aaOptions = aaChartModel.aa_toAAOptions();
        aaOptions.plotOptions.column.groupPadding = 0f;

        return aaOptions;
    }


    private  AAOptions configureChartOptions2() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Column)
                .title("")
                .yAxisTitle("")
                .legendEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Tokyo")
                                .data(new Object[]{
                                149.9, 171.5, 106.4, 129.2, 144.0, 176.0, 135.6, 188.5, 276.4, 214.1, 95.6, 54.4,
                                149.9, 171.5, 106.4, 129.2, 144.0, 176.0, 135.6, 188.5, 276.4, 214.1, 95.6, 54.4})

                });
        AAOptions aaOptions = aaChartModel.aa_toAAOptions();
        aaOptions.plotOptions.column.groupPadding = 0f;
        return aaOptions;
    }

    private AADataElement[] configureSeriesDataArray() {
        int maxRange = 40;
        AADataElement[] numberArr1 = new AADataElement[maxRange];

        double y1;
        int max = 38, min = 1;
        int random = (int) (Math.random() * (max - min) + min);
        for (int i = 0; i < maxRange; i++) {
            y1 = Math.sin(random * (i * Math.PI / 180)) + i * 2 * 0.01;
            AADataElement aaDataElement = new AADataElement()
                    .color(selectedGradientColor)
                    .y((float) y1);

            numberArr1[i] = aaDataElement;
        }

        return numberArr1;
    }

    @Override
    public void chartViewDidFinishLoad(AAChartView aaChartView) {

    }

    @Override
    public void chartViewMoveOverEventMessage(AAChartView aaChartView, AAMoveOverEventMessageModel messageModel) {
        this.selectedGradientColor = gradientColorsArr[messageModel.index];


        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                //已在主线程中，可以更新UI
                AASeriesElement[] aaSeriesElementsArr = new AASeriesElement[]{
                        new AASeriesElement()
                                .data(configureSeriesDataArray())
                };
                aaChartView2.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(aaSeriesElementsArr);
            }
        });

    }


}
