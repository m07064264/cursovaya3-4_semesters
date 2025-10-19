package Curse_Work;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.shape.Line;
import javafx.scene.canvas.Canvas;

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class Method_Monte_Carlo extends Application { // класс наследует класс Application из javaFX
    private Parent rectangle() {
        Rectangle box = new Rectangle(1182, 500, Color.web("#B0E0E6"));
        box.setOpacity(0.5); // Прозрачность прямоугольника
        transform(box);
        return new Pane(box);
    }
    private void transform(Rectangle box) {
    box.setTranslateX(100);
    box.setTranslateY(120);
    }
    private int PointInside;
    private int Total;
    @Override
    public void start(Stage primaryStage) throws Exception {

        int PointInside = 0;
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.print("количество точек: ");
        int Total = scanner.nextInt();

        NumberAxis xAxis = new NumberAxis(0, 150, 10);
        NumberAxis yAxis = new NumberAxis(0, 150, 10);
        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        Canvas canvas = new Canvas(1200, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.beginPath();
        gc.setStroke(Color.BLUE);
        for (double x = -50; x <= 50; x+=0.1) {
            double xPos = x * 30 + 405; // Масштабирование и смещение по X
            double yPos = 630 - ((x * x * 10) / 12); // Парабола y = x^2 /12
            System.out.println("parab: " + String.valueOf(x) + " " + String.valueOf(yPos));

            if (x > -10) {
                gc.lineTo(xPos, yPos);
            } else {
                gc.moveTo(xPos, yPos);
            }
        }
        gc.stroke();
        gc.beginPath();
        gc.setStroke(Color.GREEN);
        for (double x = -120; x <= 120; x+= 10){
            double xPos = x * 30 + 405;
            double yPos = 300 - ((Math.cos(x)*50) / 3);
            System.out.println("cos: " + " " + String.valueOf(yPos));

            if (x > - 10) {
                gc.lineTo(xPos, yPos);
            } else {
                gc.moveTo(xPos, yPos);
            }
        }
        gc.stroke();

        Line verticalLine1 = new Line(30 * (1200.0 / 150), 0, 30 * (1200.0 / 150), 720);
        verticalLine1.setStroke(Color.RED);
        verticalLine1.setStrokeWidth(2);

        Line verticalLine2 = new Line(153.7 * (1200.0 / 150), 0, 153.7 * (1200.0 / 150), 720);
        verticalLine2.setStroke(Color.YELLOW);
        verticalLine2.setStrokeWidth(2);

        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> pointSeries = new XYChart.Series<>();

        for (int i = 0; i < Total; i++) {
            int x = random.nextInt(4, 126);
            int y = random.nextInt(20, 126);
            System.out.println("точка: " + String.valueOf(x) + " " + String.valueOf(y));

            if (x >= 18 && x <= 120) {
                if (y >= (300 - ((Math.cos(x)*50) / 3)) && y <= (630 - ((x * x * 10) / 12))) {
                    PointInside += 1;


                }
            }
            pointSeries.getData().add(new XYChart.Data<>(x, y));
        }
        scatterChart.getData().add(pointSeries);

        Pane root = new Pane();
        root.getChildren().addAll(scatterChart, rectangle(), verticalLine2, verticalLine1, canvas);

        primaryStage.show();

        Scene scene = new Scene(root, 1200, 720, Color.web("#B0E0E6"));
        primaryStage.setScene(scene);

        scatterChart.prefWidthProperty().bind(scene.widthProperty());
        scatterChart.prefHeightProperty().bind(scene.heightProperty());

        System.out.println("Площадь фигуры = " + String.valueOf((((125 - 10)*(125-18)) * PointInside/Total) + " кв. мм."));
        System.out.println("Количество точек внутри фигуры = " + PointInside);

    }
    public int area() {

        return ((125 - 10)*(125-18)) * PointInside/Total;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
