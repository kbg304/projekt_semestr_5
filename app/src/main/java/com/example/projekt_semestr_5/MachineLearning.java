package com.example.projekt_semestr_5;

import org.tensorflow.lite.Interpreter;
import java.nio.MappedByteBuffer;


public class MachineLearning
{
    private Interpreter tflite;


    public MachineLearning(MappedByteBuffer file)
    {
        this.tflite = new Interpreter(file);
    }

    private float[][] predict(float[] data)
    {
        float[][] output = new float[1][4];

        tflite.run(data, output);

        return output;
    }

    public int getPrediction(float[] data)
    {

        float[][] result = predict(data);

        return getMaxValueIndex(result);
    }

    private int getMaxValueIndex(float[][] x)
    {
        int index=0;
        float value = x[0][index];

        for(int i=1; i<4; i++)
        {
            if(x[0][i] > value)
            {
                value = x[0][i];
                index = i;
            }
        }

        return index;

    }
    

}
