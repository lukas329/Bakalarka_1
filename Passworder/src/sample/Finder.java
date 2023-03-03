package sample;

import java.io.*;
import java.util.ArrayList;

public class Finder{
    static int i;
    static boolean goThread;
    String password;
    int threads;
    String fileF;

    ArrayList<Thread> threadsList = new ArrayList<Thread>();

    public Finder(String password, int threads){
        this.password = password;
        this.threads = threads;
        System.out.println("počet : " + threads);
        i = 0;
        goThread=true;
    }

    void find() {

        final ArrayList<File> files = new ArrayList<File>();
        files.add(new File("src/assets/rockyou.txt"));

        for (int i = 1; i<=16; i++){
            String tempFile = "src/assets/file"+i+".txt";
            files.add(new File(tempFile));
        }


        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    while (i<17){
                        File file = files.get(i);
                        i++;
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        System.out.println("Thread " + Thread.currentThread().getName() + " si berie file " + file.getName());
                        String line;
                        while (goThread) {
                            line = br.readLine();
                            if (line == null) {
                                System.out.println("prešiel som celý súbor " + file.getName());
                                break;
                            }
                            else if (line.equals(password)) {
                                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                                System.out.println("mám ho "+file.getName());
                                fileF = file.getName();
                                goThread = false;
                                for (int l = 0; l<threadsList.size(); l++){
                                    setProgres();
                                    threadsList.get(l).stop();
                                }

                                break;
                            }

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        for (int i = 0; i<threads; i++){
            Thread th = new Thread(thread);
            th.setName("Thread" + i);
            th.setPriority(Thread.MIN_PRIORITY);
            threadsList.add(th);
            th.start();
        }
    }

    public String getFileF(){
        return fileF;
    }

    public int getProgres(){
        return i;
    }
    private void setProgres(){
        i = 17;
    }
}
