package com.example.labo1v2;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        stage.setTitle("Process scheduler");
        final NumberAxis x2 = new NumberAxis();
        final NumberAxis y2 = new NumberAxis();
        final NumberAxis x = new NumberAxis();
        final NumberAxis y = new NumberAxis(0, 100, 10);
        final NumberAxis x4 = new NumberAxis();
        final NumberAxis y4 = new NumberAxis();
        final NumberAxis x3 = new NumberAxis();
        final NumberAxis y3 = new NumberAxis(0, 100, 10);
        final NumberAxis x6 = new NumberAxis();
        final NumberAxis y6 = new NumberAxis();
        final NumberAxis x5 = new NumberAxis();
        final NumberAxis y5 = new NumberAxis(0, 100, 10);

        y.setLabel("Norm omlooptijd");
        x.setLabel("Bedieningstijd");
        y2.setLabel("Wachttijd");
        x2.setLabel("Bedieningstijd");
        y3.setLabel("Norm omlooptijd");
        x3.setLabel("Bedieningstijd");
        y4.setLabel("Wachttijd");
        x4.setLabel("Bedieningstijd");
        y5.setLabel("Norm omlooptijd");
        x5.setLabel("Bedieningstijd");
        y6.setLabel("Wachttijd");
        x6.setLabel("Bedieningstijd");

        final LineChart<Number, Number> lineChart1 = new LineChart<Number, Number>(x, y);
        lineChart1.setTitle("Genormaliseerde TAT in functie van bedieningstijd /20000");
        lineChart1.setCreateSymbols(false);

        final LineChart<Number, Number> lineChart2 = new LineChart<Number, Number>(x2, y2);
        lineChart2.setTitle("Wachttijd in functie van bedieningstijd /20000");
        lineChart2.setCreateSymbols(false);

        final LineChart<Number, Number> lineChart3 = new LineChart<Number, Number>(x3, y3);
        lineChart3.setTitle("Genormaliseerde TAT in functie van bedieningstijd /10000");
        lineChart3.setCreateSymbols(false);

        final LineChart<Number, Number> lineChart4 = new LineChart<Number, Number>(x4, y4);
        lineChart4.setTitle("Wachttijd in functie van bedieningstijd /10000");
        lineChart4.setCreateSymbols(false);

        final LineChart<Number, Number> lineChart5 = new LineChart<Number, Number>(x5, y5);
        lineChart5.setTitle("Genormaliseerde TAT in functie van bedieningstijd /50000");
        lineChart5.setCreateSymbols(false);

        final LineChart<Number, Number> lineChart6 = new LineChart<Number, Number>(x6, y6);
        lineChart6.setTitle("Wachttijd in functie van bedieningstijd /50000");
        lineChart6.setCreateSymbols(false);

        XYChart.Series series1_1 = new XYChart.Series();
        series1_1.setName("FIFO");
        XYChart.Series series1_2 = new XYChart.Series();
        series1_2.setName("RR");
        XYChart.Series series1_3 = new XYChart.Series();
        series1_3.setName("HRRN");
        XYChart.Series series1_4 = new XYChart.Series();
        series1_4.setName("MultilevelFeedback");
        XYChart.Series series2_1 = new XYChart.Series();
        series2_1.setName("FIFO");
        XYChart.Series series2_2 = new XYChart.Series();
        series2_2.setName("RR");
        XYChart.Series series2_3 = new XYChart.Series();
        series2_3.setName("HRRN");
        XYChart.Series series2_4 = new XYChart.Series();
        series2_4.setName("Multilevelfeedback");
        XYChart.Series series3_1 = new XYChart.Series();
        series3_1.setName("FIFO");
        XYChart.Series series3_2 = new XYChart.Series();
        series3_2.setName("RR");
        XYChart.Series series3_3 = new XYChart.Series();
        series3_3.setName("HRRN");
        XYChart.Series series3_4 = new XYChart.Series();
        series3_4.setName("Multilevelfeedback");
        XYChart.Series series4_1 = new XYChart.Series();
        series4_1.setName("FIFO");
        XYChart.Series series4_2 = new XYChart.Series();
        series4_2.setName("RR");
        XYChart.Series series4_3 = new XYChart.Series();
        series4_3.setName("HRRN");
        XYChart.Series series4_4 = new XYChart.Series();
        series4_4.setName("Multilevelfeedback");
        XYChart.Series series5_1 = new XYChart.Series();
        series5_1.setName("FIFO");
        XYChart.Series series5_2 = new XYChart.Series();
        series5_2.setName("RR");
        XYChart.Series series5_3 = new XYChart.Series();
        series5_3.setName("HRRN");
        XYChart.Series series5_4 = new XYChart.Series();
        series5_4.setName("Multilevelfeedback");
        XYChart.Series series6_1 = new XYChart.Series();
        series6_1.setName("FIFO");
        XYChart.Series series6_2 = new XYChart.Series();
        series6_2.setName("RR");
        XYChart.Series series6_3 = new XYChart.Series();
        series6_3.setName("HRRN");
        XYChart.Series series6_4 = new XYChart.Series();
        series6_4.setName("Multilevelfeedback");

        ProcessReader fact = new ProcessReader();
        Queue<Process> processen1 = fact.leesProcessen("50000");
        Queue<Process> processen2 = fact.leesProcessen("20000");
        Queue<Process> processen3 = fact.leesProcessen("10000");

        Scheduler fifo = new FIFO();
        PriorityQueue<Process> firstinfirstout = fifo.schedule(processen2);

        int procentSize = firstinfirstout.size() / 100;
        double waitTime = 0;
        Process process;
        double normTAT = 0;
        int amount = 0;

        while (!firstinfirstout.isEmpty()) {
            process = firstinfirstout.poll();
            waitTime += process.getWaittime();
            normTAT += process.getNormtat();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series1_1.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series2_1.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                waitTime = 0;
                normTAT = 0;
            }
            amount++;
        }

        Scheduler rr = new RR(2);
        PriorityQueue<Process> roundrobin = rr.schedule(processen2);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!roundrobin.isEmpty()) {
            process = roundrobin.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series1_2.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series2_2.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        Scheduler hrrn = new HRRN();
        PriorityQueue<Process> highestresponserationext = hrrn.schedule(processen2);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!highestresponserationext.isEmpty()) {
            process = highestresponserationext.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series1_3.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series2_3.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        Scheduler mlfb = new MultiLevelFeedbackModeII();
        PriorityQueue<Process> multilevelfeedback = mlfb.schedule(processen2);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!multilevelfeedback.isEmpty()) {
            process = multilevelfeedback.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series1_4.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series2_4.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        double[] fcfs1 = fifo.getParameters();
        double[] rr1 = rr.getParameters();
        double[] hrrn1 = hrrn.getParameters();
        double[] mlfb1 = mlfb.getParameters();


        firstinfirstout = fifo.schedule(processen3);
        normTAT = 0;
        amount = 0;
        waitTime = 0;
        procentSize = processen3.size() / 100;
        while (!firstinfirstout.isEmpty()) {
            process = firstinfirstout.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series3_1.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series4_1.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        roundrobin = rr.schedule(processen3);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!roundrobin.isEmpty()) {
            process = roundrobin.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series3_2.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series4_2.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        highestresponserationext = hrrn.schedule(processen3);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!highestresponserationext.isEmpty()) {
            process = highestresponserationext.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series3_3.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series4_3.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        multilevelfeedback = mlfb.schedule(processen3);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!multilevelfeedback.isEmpty()) {
            process = multilevelfeedback.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series3_4.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series4_4.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        double[] fcfs12 = fifo.getParameters();
        double[] rr2 = rr.getParameters();
        double[] hrrn2 = hrrn.getParameters();
        double[] mlfb2 = mlfb.getParameters();



        firstinfirstout = fifo.schedule(processen1);
        normTAT = 0;
        amount = 0;
        waitTime = 0;
        procentSize = processen1.size() / 100;
        while (!firstinfirstout.isEmpty()) {
            process = firstinfirstout.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series5_1.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series6_1.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        roundrobin = rr.schedule(processen1);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!roundrobin.isEmpty()) {
            process = roundrobin.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series5_2.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series6_2.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        highestresponserationext = hrrn.schedule(processen1);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!highestresponserationext.isEmpty()) {
            process = highestresponserationext.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series5_3.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series6_3.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        multilevelfeedback = mlfb.schedule(processen1);

        normTAT = 0;
        amount = 0;
        waitTime = 0;
        while (!multilevelfeedback.isEmpty()) {
            process = multilevelfeedback.poll();
            normTAT += process.getNormtat();
            waitTime += process.getWaittime();
            if (amount % procentSize == 0 && amount != 0) {
                normTAT = normTAT / procentSize;
                waitTime = waitTime / procentSize;
                series5_4.getData().add(new XYChart.Data(amount / procentSize, normTAT));
                series6_4.getData().add(new XYChart.Data(amount / procentSize, waitTime));
                normTAT = 0;
                waitTime = 0;
            }
            amount++;
        }

        double[] fcfs3 = fifo.getParameters();
        double[] rr3 = rr.getParameters();
        double[] hrrn3 = hrrn.getParameters();
        double[] mlfb3 = mlfb.getParameters();


        Label fcfs11 = new Label("First first served /10000");
        Label rr11 = new Label("Round robin /10000");
        Label hrrn11 = new Label("Highest response ratio next /10000");
        Label mlfb11 = new Label("Multilevel feedback /10000");

        Label gemomloopfcfs = new Label();
        Label gemGenomloopfcfs = new Label();
        Label gemWaitfcfs = new Label();

        gemomloopfcfs.setText(String.valueOf(fcfs1[0]));
        gemGenomloopfcfs.setText(String.valueOf(fcfs1[1]));
        gemWaitfcfs.setText(String.valueOf(fcfs1[2]));

        Label gemomlooprr = new Label();
        Label gemGenomlooprr = new Label();
        Label gemWaitrr = new Label();

        gemomlooprr.setText(String.valueOf(rr1[0]));
        gemGenomlooprr.setText(String.valueOf(rr1[1]));
        gemWaitrr.setText(String.valueOf(rr1[2]));

        Label gemomloophrrn = new Label();
        Label gemGenomloophrrn = new Label();
        Label gemWaithrrn = new Label();

        gemomloophrrn.setText(String.valueOf(hrrn1[0]));
        gemGenomloophrrn.setText(String.valueOf(hrrn1[1]));
        gemWaithrrn.setText(String.valueOf(hrrn1[2]));

        Label gemomloopmlfb = new Label();
        Label gemGenomloopmlfb = new Label();
        Label gemWaitmlfb = new Label();

        gemomloopmlfb.setText(String.valueOf(mlfb1[0]));
        gemGenomloopmlfb.setText(String.valueOf(mlfb1[1]));
        gemWaitmlfb.setText(String.valueOf(mlfb1[2]));

        Label white = new Label(" ");
        Label white1 = new Label(" ");
        Label white2 = new Label(" ");
        Label white3 = new Label (" ");

        Label legende1 = new Label("Gemiddelde omlooptijd");
        Label legende2 = new Label("Gemiddelde genormaliseerde omlooptijd");
        Label legende3 = new Label("Gemiddelde wachttijd");


        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        VBox labels = new VBox();
        labels.getChildren().addAll(legende1, legende2, legende3, white3, fcfs11, gemomloopfcfs, gemGenomloopfcfs, gemWaitfcfs, white, rr11, gemomlooprr, gemGenomlooprr, gemWaitrr, white1,  hrrn11, gemomloophrrn, gemGenomloophrrn, gemWaithrrn, white2, mlfb11, gemomloopmlfb, gemGenomloopmlfb, gemWaitmlfb);
        hbox1.getChildren().addAll(lineChart3,lineChart1, lineChart5, labels);
        hbox2.getChildren().addAll(lineChart4, lineChart2, lineChart6);

        vbox.getChildren().addAll(hbox1, hbox2);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 1000, 800);
        lineChart1.getData().addAll(series1_1, series1_2, series1_3, series1_4);
        lineChart2.getData().addAll(series2_1, series2_2, series2_3, series2_4);
        lineChart3.getData().addAll(series3_1, series3_2, series3_3, series3_4);
        lineChart4.getData().addAll(series4_1, series4_2, series4_3, series4_4);
        lineChart5.getData().addAll(series5_1, series5_2, series5_3, series5_4);
        lineChart6.getData().addAll(series6_1, series6_2, series6_3, series6_4);


        stage.setScene(scene);
        stage.show();

    }

}