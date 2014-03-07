/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project440Package;

import java.util.Random;
import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Forecaster;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.Observation;

/**
 *
 * @author Simant
 */
public class Prediction {
    
    public static void main(String args[])
    {
            
            DataSet dataSet=new DataSet();
            Random number=new Random(600);
		// Create Observation for quarter 1
	     
            
                Observation observationQ1 = new Observation(600.0);
                observationQ1.setIndependentValue("quarter",1);

                // Create Observation for quarter 2
                Observation observationQ2 = new Observation(700.0);
                observationQ2.setIndependentValue("quarter",2);

                // Create Observation for quarter 3
                Observation observationQ3 = new Observation(500.0);
                observationQ3.setIndependentValue("quarter",3);
	     for(int i=0;i<50;i++)
	     {
                 dataSet.add(observationQ1);
                 dataSet.add(observationQ2);
                 dataSet.add(observationQ3);
             }
             
	     ForecastingModel model=Forecaster.getBestForecast(dataSet);
	     String forecastname=model.getForecastType();
             System.out.println("Forecast:"+forecastname);
	     
             model.init(dataSet);
	     
	  // Create DataPoint/Observation for quarter 4
	     DataPoint fcDataPointQ4 = new Observation(0.0);
	     fcDataPointQ4.setIndependentValue("quarter",4);

	     // Create Observation/DataPoint for quarter 5
	     DataPoint fcDataPointQ5 = new Observation(0.0);
	     fcDataPointQ5.setIndependentValue("quarter",5);

	     // Create forecast data set and add these DataPoints
	     DataSet fcDataSet = new DataSet();
	     
             fcDataSet.add( fcDataPointQ4 );
	     fcDataSet.add( fcDataPointQ5 );
	     
	     model.forecast( fcDataSet );
             System.out.println("Hello");
             
             String x=fcDataSet.toString();
             System.out.println("Value:"+x);

    }
}
