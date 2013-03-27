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


public class CitationHistMap extends Mapper<LongWritable, Text, IntWritable, IntWritable>{

	int uno = 1;
	private int citationCount;
	
	//@Override
    public void map(LongWritable key, Text value,
                    Context context) throws IOException, InterruptedException {

		citationCount = Integer.parseInt(value.toString());
        context.write(new IntWritable(citationCount), new IntWritable(uno));
    }
}
