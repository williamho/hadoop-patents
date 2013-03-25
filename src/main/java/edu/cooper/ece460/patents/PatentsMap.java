package edu.cooper.ece460.patents;

import java.io.*;
import java.util.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class PatentsMap extends Mapper<LongWritable, Text, Text, Text>{
    @Override
    public void map(LongWritable key, Text value,
                    Context context) throws IOException, InterruptedException{
        String line = value.toString();
        String[] lineParts = line.split(",");
        String citingPatent = lineParts[0];
        String citedPatent = lineParts[1];
        context.write(new Text(citedPatent), new Text(citingPatent));
    }
}
