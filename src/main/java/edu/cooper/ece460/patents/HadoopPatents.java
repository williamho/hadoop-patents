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

public class HadoopPatents{
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Path inPath = new Path(args[0]);
        Path outPath = new Path(args[1]);

        Job job = new Job(conf, "HadoopPatents");

        job.setJarByClass(HadoopPatents.class);
        job.setMapperClass(PatentsMap.class);
        // job.setPartitionerClass(PicasaPartition.class);
        job.setReducerClass(PatentsReduce.class);

        job.setNumReduceTasks(12);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, inPath);
        FileOutputFormat.setOutputPath(job, outPath);

        job.waitForCompletion(true);
    }
}
