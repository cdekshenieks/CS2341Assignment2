import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;


public class JobScheduler {

    public static void main(String[] args) { //reads file from user input
        //System.out.println(args.length);

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
        int[][] jobs = new int[jobCount][2]; //array stores jobs and processing times
        reader.close(); // Close and reopen to reset the buffer

        // Reopen the reader to actually read and parse the data
        reader = new BufferedReader(new FileReader(filePath));
        int index = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            //int ID = Integer.parseInt(parts[0]);
            //int processingTime = Integer.parseInt(parts[1]);
            //int priority = Integer.parseInt(parts[2]);
            jobs[index][0] = Integer.parseInt(parts[0]);  // Job ID
            jobs[index][1] = Integer.parseInt(parts[1]);  // Processing Time
            index++;
        }
        reader.close();

        return jobs;
    }

    public static void scheduleJobs(int [][] jobs) {
        PriorityQueue<Job> jobQueue = new PriorityQueue<>((a, b) -> a.processingTime - b.processingTime);

        int currTime = 0;
        int totalTime = 0;
        int jobCount = jobs.length;
        StringBuilder executionOrder = new StringBuilder();

        for (int [] job : jobs) {
            jobQueue.add(new Job(job[0], job[1]));
        }

        while (!jobQueue.isEmpty()) {
            Job currJob = jobQueue.poll();
            totalTime += currJob.processingTime;
            executionOrder.append(currJob.ID).append(" ");
        }

        double averageCompletionTime = (double) totalTime / jobCount;

        // Output results
        System.out.println("Execution order: [" + executionOrder.toString().trim().replace(" ", ", ") + "]");
        System.out.printf("Average completion time: %.2f\n", averageCompletionTime);
    }
}