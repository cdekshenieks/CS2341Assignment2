import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;

public class Task2Job {
    public static void main(String[] args) { //reads file from user input
        Scanner scanner = new Scanner(args[0]);
        String filePath = scanner.nextLine();

        try {
            int[][] jobs = ReadJobsFromFile(filePath);
            scheduleJobs(jobs);

        }

        catch (IOException e){
            System.out.println("Error reading file: " + e.getMessage());

        }
    }

    public static int[][] ReadJobsFromFile(String filePath) throws IOException { //method for reading jobs from file
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int jobCount = 0;

        while ((line = reader.readLine()) != null) { //calc number of jobs in file
            jobCount++;
        }
        int[][] jobs = new int[jobCount][3]; //array stores jobs and processing times
        reader.close(); // Close and reopen to reset the buffer

        // Reopen the reader to actually read and parse the data
        reader = new BufferedReader(new FileReader(filePath));
        int index = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            int ID = Integer.parseInt(parts[0]);
            int processingTime = Integer.parseInt(parts[1]);
            int priority = Integer.parseInt(parts[2]);
            jobs[index][0] = ID;  // Job ID
            jobs[index][1] = processingTime;  // Processing Time
            jobs[index][2] = priority;
            index++;
        }
        reader.close();

        return jobs;
    }

    public static void scheduleJobs(int [][] jobs) {
        PriorityQueue<Job2> jobQueue = new PriorityQueue<>(new Comparator<Job2>(){
            @Override
            public int compare(Job2 o1, Job2 o2) {
                int comp = Integer.compare(o1.priority, o2.priority);
                if (comp != 0) {
                    return comp;
                }
                int comp1 = Integer.compare(o1.processingTime, o2.processingTime);
                return  comp1;
            }
        });

        int currTime = 0;
        int totalTime = 0;
        int jobCount = jobs.length;
        StringBuilder executionOrder = new StringBuilder();

        for (int [] job : jobs) {
            jobQueue.add(new Job2(job[0], job[1],job[2]));
        }

        while (!jobQueue.isEmpty()) {
            Job2 currJob = jobQueue.poll();
            totalTime += currJob.processingTime;
            executionOrder.append(currJob.ID).append(" ");
        }

        double averageCompletionTime = (double) totalTime / jobCount;

        // Output results
        System.out.println("Execution order: [" + executionOrder.toString().trim().replace(" ", ", ") + "]");
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }
}
